/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.accountlog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.accountlog.entity.Accountlog;
import com.jeesite.modules.accountlog.dao.AccountlogDao;

/**
 * 出入库记录Service
 * @author liu
 * @version 2020-03-05
 */
@Service
@Transactional(readOnly=true)
public class AccountlogService extends CrudService<AccountlogDao, Accountlog> {
	
	/**
	 * 获取单条数据
	 * @param accountlog
	 * @return
	 */
	@Override
	public Accountlog get(Accountlog accountlog) {
		return super.get(accountlog);
	}
	
	/**
	 * 查询分页数据
	 * @param accountlog 查询条件
	 * @param accountlog.page 分页对象
	 * @return
	 */
	@Override
	public Page<Accountlog> findPage(Accountlog accountlog) {
		return super.findPage(accountlog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param accountlog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Accountlog accountlog) {
		super.save(accountlog);
	}
	
	/**
	 * 更新状态
	 * @param accountlog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Accountlog accountlog) {
		super.updateStatus(accountlog);
	}
	
	/**
	 * 删除数据
	 * @param accountlog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Accountlog accountlog) {
		super.delete(accountlog);
	}
	
}