/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.service;

import java.util.List;

import com.jeesite.common.entity.DataScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.yf.entity.YFGarageTree;
import com.jeesite.modules.yf.dao.YFGarageTreeDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 怡丰车库组织Service
 * @author liu
 * @version 2020-03-07
 */
@Service
@Transactional(readOnly=true)
public class YFGarageTreeService extends TreeService<YFGarageTreeDao, YFGarageTree> {
	
	/**
	 * 获取单条数据
	 * @param yFGarageTree
	 * @return
	 */
	@Override
	public YFGarageTree get(YFGarageTree yFGarageTree) {
		return super.get(yFGarageTree);
	}

	@Override
	public void addDataScopeFilter(YFGarageTree entity) {
		super.addDataScopeFilter(entity);
	}

	/**
	 * 查询列表数据
	 * @param yFGarageTree
	 * @return
	 */
	@Override
	public List<YFGarageTree> findList(YFGarageTree yFGarageTree) {
		return super.findList(yFGarageTree);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param yFGarageTree
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(YFGarageTree yFGarageTree) {
		super.save(yFGarageTree);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(yFGarageTree.getId(), "yFGarageTree_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(yFGarageTree.getId(), "yFGarageTree_file");
	}
	
	/**
	 * 更新状态
	 * @param yFGarageTree
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(YFGarageTree yFGarageTree) {
		super.updateStatus(yFGarageTree);
	}
	
	/**
	 * 删除数据
	 * @param yFGarageTree
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(YFGarageTree yFGarageTree) {
		super.delete(yFGarageTree);
	}
	
}