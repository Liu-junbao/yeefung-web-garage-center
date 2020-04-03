/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.yf.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 怡丰车库组织Entity
 * @author liu
 * @version 2020-03-07
 */
@Table(name="yf_garage_tree", alias="a", columns={
		@Column(name="group_code", attrName="groupCode", label="group_code", isPK=true),
		@Column(name="group_name", attrName="groupName", label="组织名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="group_type", attrName="groupType", label="组织类型"),
		@Column(name="datasourcename", attrName="datasourcename", label="数据源"),
		@Column(includeEntity=TreeEntity.class),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.group_code"
)
public class YFGarageTree extends TreeEntity<YFGarageTree> {
	
	private static final long serialVersionUID = 1L;
	private String groupCode;		// group_code
	private String groupName;		// 组织名称
	private String groupType;		// 组织类型
	private String datasourcename;		// 数据源
	
	public YFGarageTree() {
		this(null);
	}

	public YFGarageTree(String id){
		super(id);
	}
	
	@Override
	public YFGarageTree getParent() {
		return parent;
	}

	@Override
	public void setParent(YFGarageTree parent) {
		this.parent = parent;
	}
	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	@NotBlank(message="组织名称不能为空")
	@Length(min=0, max=200, message="组织名称长度不能超过 200 个字符")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@NotBlank(message="组织类型不能为空")
	@Length(min=0, max=64, message="组织类型长度不能超过 64 个字符")
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	@Length(min=0, max=20, message="数据源长度不能超过 20 个字符")
	public String getDatasourcename() {
		return datasourcename;
	}

	public void setDatasourcename(String datasourcename) {
		this.datasourcename = datasourcename;
	}
	
}