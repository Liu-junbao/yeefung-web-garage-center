/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 怡丰车库状态Entity
 * @author liu
 * @version 2020-03-10
 */
@Table(name="yf_garage_status", alias="a", columns={
		@Column(name="group_code", attrName="groupCode", label="group_code", isPK=true),
		@Column(name="group_signals", attrName="groupSignals", label="group_signals"),
		@Column(name="group_alarms", attrName="groupAlarms", label="group_alarms"),
	}, orderBy="a.group_code DESC"
)
public class YFGarageStatus extends DataEntity<YFGarageStatus> {
	
	private static final long serialVersionUID = 1L;
	private String groupCode;		// group_code
	private String groupSignals;		// group_signals
	private String groupAlarms;		// group_alarms
	
	public YFGarageStatus() {
		this(null);
	}

	public YFGarageStatus(String id){
		super(id);
	}
	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public String getGroupSignals() {
		return groupSignals;
	}

	public void setGroupSignals(String groupSignals) {
		this.groupSignals = groupSignals;
	}
	
	public String getGroupAlarms() {
		return groupAlarms;
	}

	public void setGroupAlarms(String groupAlarms) {
		this.groupAlarms = groupAlarms;
	}
	
}