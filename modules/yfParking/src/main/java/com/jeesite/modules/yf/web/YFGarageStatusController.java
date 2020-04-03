/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.yf.entity.YFGarageStatus;
import com.jeesite.modules.yf.service.YFGarageStatusService;

/**
 * 怡丰车库状态Controller
 * @author liu
 * @version 2020-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/yf/yFGarageStatus")
public class YFGarageStatusController extends BaseController {

	@Autowired
	private YFGarageStatusService yFGarageStatusService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public YFGarageStatus get(String groupCode, boolean isNewRecord) {
		return yFGarageStatusService.get(groupCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("yf:yFGarageStatus:view")
	@RequestMapping(value = {"list", ""})
	public String list(YFGarageStatus yFGarageStatus, Model model) {
		model.addAttribute("yFGarageStatus", yFGarageStatus);
		return "modules/yf/yFGarageStatusList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("yf:yFGarageStatus:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<YFGarageStatus> listData(YFGarageStatus yFGarageStatus, HttpServletRequest request, HttpServletResponse response) {
		yFGarageStatus.setPage(new Page<>(request, response));
		Page<YFGarageStatus> page = yFGarageStatusService.findPage(yFGarageStatus);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("yf:yFGarageStatus:view")
	@RequestMapping(value = "form")
	public String form(YFGarageStatus yFGarageStatus, Model model) {
		model.addAttribute("yFGarageStatus", yFGarageStatus);
		return "modules/yf/yFGarageStatusForm";
	}

	/**
	 * 保存车库状态
	 */
	@RequiresPermissions("yf:yFGarageStatus:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated YFGarageStatus yFGarageStatus) {
		yFGarageStatusService.save(yFGarageStatus);
		return renderResult(Global.TRUE, text("保存车库状态成功！"));
	}
	
	/**
	 * 删除车库状态
	 */
	@RequiresPermissions("yf:yFGarageStatus:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(YFGarageStatus yFGarageStatus) {
		yFGarageStatusService.delete(yFGarageStatus);
		return renderResult(Global.TRUE, text("删除车库状态成功！"));
	}
	
}