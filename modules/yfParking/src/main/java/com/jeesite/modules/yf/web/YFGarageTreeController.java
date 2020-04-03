/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.web;

import java.util.List;
import java.util.Map;

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
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.yf.entity.YFGarageTree;
import com.jeesite.modules.yf.service.YFGarageTreeService;

/**
 * 怡丰车库组织Controller
 * @author liu
 * @version 2020-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/yf/yFGarageTree")
public class YFGarageTreeController extends BaseController {

	@Autowired
	private YFGarageTreeService yFGarageTreeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public YFGarageTree get(String groupCode, boolean isNewRecord) {
		return yFGarageTreeService.get(groupCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("yf:yFGarageTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(YFGarageTree yFGarageTree, Model model) {
		model.addAttribute("yFGarageTree", yFGarageTree);
		return "modules/yf/yFGarageTreeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("yf:yFGarageTree:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<YFGarageTree> listData(YFGarageTree yFGarageTree) {
		if (StringUtils.isBlank(yFGarageTree.getParentCode())) {
			yFGarageTree.setParentCode(YFGarageTree.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(yFGarageTree.getGroupName())){
			yFGarageTree.setParentCode(null);
		}
		if (StringUtils.isNotBlank(yFGarageTree.getGroupType())){
			yFGarageTree.setParentCode(null);
		}
		if (StringUtils.isNotBlank(yFGarageTree.getDatasourcename())){
			yFGarageTree.setParentCode(null);
		}
		if (StringUtils.isNotBlank(yFGarageTree.getRemarks())){
			yFGarageTree.setParentCode(null);
		}
		List<YFGarageTree> list = yFGarageTreeService.findList(yFGarageTree);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("yf:yFGarageTree:view")
	@RequestMapping(value = "form")
	public String form(YFGarageTree yFGarageTree, Model model) {
		// 创建并初始化下一个节点信息
		yFGarageTree = createNextNode(yFGarageTree);
		model.addAttribute("yFGarageTree", yFGarageTree);
		return "modules/yf/yFGarageTreeForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public YFGarageTree createNextNode(YFGarageTree yFGarageTree) {
		if (StringUtils.isNotBlank(yFGarageTree.getParentCode())){
			yFGarageTree.setParent(yFGarageTreeService.get(yFGarageTree.getParentCode()));
		}
		if (yFGarageTree.getIsNewRecord()) {
			YFGarageTree where = new YFGarageTree();
			where.setParentCode(yFGarageTree.getParentCode());
			YFGarageTree last = yFGarageTreeService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				yFGarageTree.setTreeSort(last.getTreeSort() + 30);
			}
		}
		// 以下设置表单默认数据
		if (yFGarageTree.getTreeSort() == null){
			yFGarageTree.setTreeSort(YFGarageTree.DEFAULT_TREE_SORT);
		}
		return yFGarageTree;
	}

	/**
	 * 保存车库组织
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated YFGarageTree yFGarageTree) {
		yFGarageTreeService.save(yFGarageTree);
		return renderResult(Global.TRUE, text("保存车库组织成功！"));
	}
	
	/**
	 * 停用车库组织
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(YFGarageTree yFGarageTree) {
		YFGarageTree where = new YFGarageTree();
		where.setStatus(YFGarageTree.STATUS_NORMAL);
		where.setParentCodes("," + yFGarageTree.getId() + ",");
		long count = yFGarageTreeService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, text("该车库组织包含未停用的子车库组织！"));
		}
		yFGarageTree.setStatus(YFGarageTree.STATUS_DISABLE);
		yFGarageTreeService.updateStatus(yFGarageTree);
		return renderResult(Global.TRUE, text("停用车库组织成功"));
	}
	
	/**
	 * 启用车库组织
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(YFGarageTree yFGarageTree) {
		yFGarageTree.setStatus(YFGarageTree.STATUS_NORMAL);
		yFGarageTreeService.updateStatus(yFGarageTree);
		return renderResult(Global.TRUE, text("启用车库组织成功"));
	}
	
	/**
	 * 删除车库组织
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(YFGarageTree yFGarageTree) {
		yFGarageTreeService.delete(yFGarageTree);
		return renderResult(Global.TRUE, text("删除车库组织成功！"));
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("yf:yFGarageTree:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<YFGarageTree> list = yFGarageTreeService.findList(new YFGarageTree());
		for (int i=0; i<list.size(); i++){
			YFGarageTree e = list.get(i);
			// 过滤非正常的数据
			if (!YFGarageTree.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getGroupCode(), e.getGroupName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("yf:yFGarageTree:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(YFGarageTree yFGarageTree){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		yFGarageTreeService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}