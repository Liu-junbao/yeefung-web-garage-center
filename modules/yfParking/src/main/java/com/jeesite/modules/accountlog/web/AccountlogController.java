/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.accountlog.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jeesite.common.lang.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.ext.fn.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.accountlog.entity.Accountlog;
import com.jeesite.modules.accountlog.service.AccountlogService;

import java.util.*;

/**
 * 出入库记录Controller
 * @author liu
 * @version 2020-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/accountlog/accountlog")
public class AccountlogController extends BaseController {

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
	 * 数据库菜单
	 */
	@RequiresPermissions({"accountlog:accountlog:view"})
	@RequestMapping({"index", ""})
	public String index(Accountlog accountlog, Model model) {
		model.addAttribute("accountlog", accountlog);
		return "modules/accountlog/accountlogIndex";
	}

	@RequiresPermissions({"accountlog:accountlog:view"})
	@RequestMapping({"indexTree", ""})
	public String indexTree(Accountlog accountlog, Model model) {
		model.addAttribute("accountlog", accountlog);

		return "[{name:'龙岗妇幼',open:true,children:[{name:'E库'},{name:'F库'}]}]";
	}

	@RequestMapping({"test", ""})
	@ResponseBody
	public String test() {
		return "[{name:'龙岗妇幼',open:true,children:[{name:'E库'},{name:'F库'}]}]";
	}

	@ResponseBody
	@RequestMapping(value = "importData", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	public String importData(@RequestBody String postContent) {
		return postContent + "成功";
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("accountlog:accountlog:view")
	@RequestMapping(value = {"list", ""})
	public String list(Accountlog accountlog, Model model) {
		model.addAttribute("accountlog", accountlog);
		return "modules/accountlog/accountlogList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("accountlog:accountlog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Accountlog> listData(Accountlog accountlog, HttpServletRequest request, HttpServletResponse response) {
		accountlog.setPage(new Page<>(request, response));
		Page<Accountlog> page = accountlogService.findPage(accountlog);
		return page;
	}

	/**
	 * 查看统计
	 */
	@RequiresPermissions("accountlog:accountlog:view")
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
		int[] timeList = new int[24];
		int[] timeParkinList = new int[24];
		int[] timeParkoutList = new int[24];
		for (int i = 0; i < dayCount; i++) {
			dayList[i] = (i + 1) + "号";
		}
		for (int i = 0; i < 24; i++) {
			timeList[i] = i + 1;
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

		return "modules/accountlog/accountlogSum";
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("accountlog:accountlog:view")
	@RequestMapping(value = "form")
	public String form(Accountlog accountlog, Model model) {
		model.addAttribute("accountlog", accountlog);
		return "modules/accountlog/accountlogForm";
	}

	/**
	 * 保存出入库记录
	 */
	@RequiresPermissions("accountlog:accountlog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Accountlog accountlog) {
		accountlogService.save(accountlog);
		return renderResult(Global.TRUE, text("保存出入库记录成功！"));
	}
	
	/**
	 * 删除出入库记录
	 */
	@RequiresPermissions("accountlog:accountlog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Accountlog accountlog) {
		accountlogService.delete(accountlog);
		return renderResult(Global.TRUE, text("删除出入库记录成功！"));
	}
	
}