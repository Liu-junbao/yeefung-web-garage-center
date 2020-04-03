/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.accountlog.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.datasource.DataSourceHolder;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.accountlog.entity.Accountlog;

/**
 * 出入库记录DAO接口
 * @author liu
 * @version 2020-03-05
 */
@MyBatisDao(dataSourceName= "YF_PCY_DUAL_LGFY_F")
public interface AccountlogDao extends CrudDao<Accountlog> {
	
}