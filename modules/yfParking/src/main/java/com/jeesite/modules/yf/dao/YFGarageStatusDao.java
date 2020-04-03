/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.yf.entity.YFGarageStatus;

/**
 * 怡丰车库状态DAO接口
 * @author liu
 * @version 2020-03-10
 */
@MyBatisDao
public interface YFGarageStatusDao extends CrudDao<YFGarageStatus> {
	
}