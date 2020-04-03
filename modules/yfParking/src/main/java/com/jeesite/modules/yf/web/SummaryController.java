package com.jeesite.modules.yf.web;

import com.google.gson.Gson;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.accountlog.entity.Accountlog;
import com.jeesite.modules.accountlog.service.AccountlogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/yf/summary")
public class SummaryController {

    @Autowired
    private AccountlogService accountlogService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public Accountlog get(Integer id, boolean isNewRecord) {
        //
        String strId = null;
        if (id != null) strId = id.toString();
//		DataSourceHolder.clearDataSourceName();
//		DataSourceHolder.setDataSourceName("YF_PCY_DUAL_LGFY_F");
        return accountlogService.get(strId, isNewRecord);
    }

    /**
     * 汇总
     */
    @RequiresPermissions("summary:summary:view")
    @RequestMapping(value = "sum")
    public String sum(Accountlog accountlog, Model model) {
        model.addAttribute("accountlog", accountlog);

        //获取时间信息
        //
        Date toDay = new Date();
        //当前月份天数

        int dayCount = DateUtils.getMonthHasDays(toDay);
        //当前月份开始时间和结束时间
        Date beginDay = DateUtils.parseDate(DateUtils.formatDate(toDay, "yyyy-MM-01"));
        Date endDay = DateUtils.parseDate(DateUtils.formatDate(toDay, "yyyy-MM-" + dayCount));
        Date BeginTime = DateUtils.getOfDayFirst(beginDay);
        Date endTime = DateUtils.getOfDayLast(endDay);

        //设置查询条件
        accountlog.setCreateDate_gte(beginDay);
        accountlog.setCreateDate_lte(endDay);

        //查询数据
        List<Accountlog> logs = accountlogService.findList(accountlog);

        //数量
        int parkinCount = 0, parkoutCount = 0;

        //列表
        String[] dayList = new String[dayCount];
        int[] dayParkinList = new int[dayCount];
        int[] dayParkoutList = new int[dayCount];
        String[] timeList = new String[24];
        int[] timeParkinList = new int[24];
        int[] timeParkoutList = new int[24];
        for (int i = 0; i < dayCount; i++) {
            dayList[i] = (i + 1) + "号";
        }
        for (int i = 0; i < 24; i++) {
            timeList[i] = (i + 1) + "点";
        }
        for (Accountlog log : logs) {
            Date parkinDay = log.getParkintime();
            Date parkoutDay = log.getParkouttime();
            int parkinDayIndex = 0, parkoutDayIndex = 0, parkinTimeIndex = 0, parkoutTimeIndex = 0;
            if (parkinDay != null) {
                parkinDayIndex = parkinDay.getDate();
                parkinTimeIndex = parkinDay.getHours();
                parkinCount++;
            }
            if (parkoutDay != null) {
                parkoutDayIndex = parkoutDay.getDate();
                parkoutTimeIndex = parkoutDay.getHours();
                parkoutCount++;
            }
            boolean parkinSelected = false, parkoutSelected = false;
            for (int i = 0; i < dayCount; i++) {
                int dayIndex = i + 1;
                if (parkinDayIndex == dayIndex) {
                    dayParkinList[i]++;
                    parkinSelected = true;
                }
                if (parkoutDayIndex == dayIndex) {
                    dayParkoutList[i]++;
                    parkoutSelected = true;
                }
                if (parkinSelected && parkoutSelected) break;
            }

            parkinSelected = false;
            parkoutSelected = false;
            for (int i = 0; i < 24; i++) {
                int timeIndex = i;
                if (parkinTimeIndex == timeIndex) {
                    timeParkinList[i]++;
                    parkinSelected = true;
                }
                if (parkoutTimeIndex == timeIndex) {
                    timeParkoutList[i]++;
                    parkoutSelected = true;
                }
                if (parkinSelected && parkoutSelected) break;
            }
        }

        model.addAttribute("date", DateUtils.formatDate(toDay, "yyyy-MM"));
        model.addAttribute("parkinCount", parkinCount);
        model.addAttribute("parkoutCount", parkoutCount);
        model.addAttribute("dayList", new Gson().toJson(dayList));
        model.addAttribute("dayParkinList", new Gson().toJson(dayParkinList));
        model.addAttribute("dayParkoutList", new Gson().toJson(dayParkoutList));
        model.addAttribute("timeList", new Gson().toJson(timeList));
        model.addAttribute("timeParkinList", new Gson().toJson(timeParkinList));
        model.addAttribute("timeParkoutList", new Gson().toJson(timeParkoutList));

        return "modules/yf/yFGarageSum";
    }

}
