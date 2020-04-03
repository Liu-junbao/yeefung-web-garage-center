/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.accountlog.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hyperic.sigar.DiskUsage;
import org.hyperic.sigar.pager.PageControl;

/**
 * 出入库记录Entity
 * @author liu
 * @version 2020-03-05
 */
@Table(name="accountlog", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="parkcardtrace", attrName="parkcardtrace", label="parkcardtrace"),
		@Column(name="cardno", attrName="cardno", label="cardno"),
		@Column(name="parksapceid", attrName="parksapceid", label="parksapceid"),
		@Column(name="carsizetype", attrName="carsizetype", label="carsizetype"),
		@Column(name="carphoto", attrName="carphoto", label="carphoto"),
		@Column(name="platenumber", attrName="platenumber", label="platenumber"),
		@Column(name="cardintime", attrName="cardintime", label="cardintime"),
		@Column(name="parkintime", attrName="parkintime", label="parkintime"),
		@Column(name="cardouttime", attrName="cardouttime", label="cardouttime"),
		@Column(name="parkouttime", attrName="parkouttime", label="parkouttime"),
		@Column(name="lastaccounttime", attrName="lastaccounttime", label="lastaccounttime"),
		@Column(name="duepay", attrName="duepay", label="duepay"),
		@Column(name="actualpay", attrName="actualpay", label="actualpay"),
		@Column(name="paymethod", attrName="paymethod", label="paymethod"),
		@Column(name="cardtype", attrName="cardtype", label="cardtype"),
		@Column(name="logoperator", attrName="logoperator", label="logoperator"),
		@Column(name="garageid", attrName="garageid", label="garageid"),
		@Column(name="remark", attrName="remark", label="remark"),
		@Column(name="carmodelname", attrName="carmodelname", label="carmodelname"),
		@Column(name="inport", attrName="inport", label="inport"),
		@Column(name="outport", attrName="outport", label="outport"),
	}, orderBy="a.id DESC"
)
public class Accountlog extends DataEntity<Accountlog> {
	
	private static final long serialVersionUID = 1L;
	private String parkcardtrace;		// parkcardtrace
	private Integer cardno;		// cardno
	private Integer parksapceid;		// parksapceid
	private Integer carsizetype;		// carsizetype
	private String carphoto;		// carphoto
	private String platenumber;		// platenumber
	private Date cardintime;		// cardintime
	private Date parkintime;		// parkintime
	private Date cardouttime;		// cardouttime
	private Date parkouttime;		// parkouttime
	private Date lastaccounttime;		// lastaccounttime
	private Double duepay;		// duepay
	private Double actualpay;		// actualpay
	private Integer paymethod;		// paymethod
	private String cardtype;		// cardtype
	private String logoperator;		// logoperator
	private Integer garageid;		// garageid
	private String remark;		// remark
	private String carmodelname;		// carmodelname
	private String inport;		// inport
	private String outport;		// outport
	
	public Accountlog() {
		this(null);
	}

	public Accountlog(String id){
		super(id);
	}
	
	public String getParkcardtrace() {
		return parkcardtrace;
	}

	public void setParkcardtrace(String parkcardtrace) {
		this.parkcardtrace = parkcardtrace;
	}
	
	@NotNull(message="cardno不能为空")
	public Integer getCardno() {
		return cardno;
	}

	public void setCardno(Integer cardno) {
		this.cardno = cardno;
	}
	
	@NotNull(message="parksapceid不能为空")
	public Integer getParksapceid() {
		return parksapceid;
	}

	public void setParksapceid(Integer parksapceid) {
		this.parksapceid = parksapceid;
	}
	
	@NotNull(message="carsizetype不能为空")
	public Integer getCarsizetype() {
		return carsizetype;
	}

	public void setCarsizetype(Integer carsizetype) {
		this.carsizetype = carsizetype;
	}
	
	public String getCarphoto() {
		return carphoto;
	}

	public void setCarphoto(String carphoto) {
		this.carphoto = carphoto;
	}
	
	public String getPlatenumber() {
		return platenumber;
	}

	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCardintime() {
		return cardintime;
	}

	public void setCardintime(Date cardintime) {
		this.cardintime = cardintime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getParkintime() {
		return parkintime;
	}

	public void setParkintime(Date parkintime) {
		this.parkintime = parkintime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCardouttime() {
		return cardouttime;
	}

	public void setCardouttime(Date cardouttime) {
		this.cardouttime = cardouttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getParkouttime() {
		return parkouttime;
	}

	public void setParkouttime(Date parkouttime) {
		this.parkouttime = parkouttime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastaccounttime() {
		return lastaccounttime;
	}

	public void setLastaccounttime(Date lastaccounttime) {
		this.lastaccounttime = lastaccounttime;
	}
	
	@NotNull(message="duepay不能为空")
	public Double getDuepay() {
		return duepay;
	}

	public void setDuepay(Double duepay) {
		this.duepay = duepay;
	}
	
	@NotNull(message="actualpay不能为空")
	public Double getActualpay() {
		return actualpay;
	}

	public void setActualpay(Double actualpay) {
		this.actualpay = actualpay;
	}
	
	@NotNull(message="paymethod不能为空")
	public Integer getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(Integer paymethod) {
		this.paymethod = paymethod;
	}
	
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	public String getLogoperator() {
		return logoperator;
	}

	public void setLogoperator(String logoperator) {
		this.logoperator = logoperator;
	}
	
	@NotNull(message="garageid不能为空")
	public Integer getGarageid() {
		return garageid;
	}

	public void setGarageid(Integer garageid) {
		this.garageid = garageid;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCarmodelname() {
		return carmodelname;
	}

	public void setCarmodelname(String carmodelname) {
		this.carmodelname = carmodelname;
	}
	
	public String getInport() {
		return inport;
	}

	public void setInport(String inport) {
		this.inport = inport;
	}
	
	public String getOutport() {
		return outport;
	}

	public void setOutport(String outport) {
		this.outport = outport;
	}

	@Override
	public Date getCreateDate_gte() {
		return (Date)this.sqlMap.getWhere().getValue("cardintime", QueryType.GTE);
	}
	@Override
	public void setCreateDate_gte(Date createDate) {
		createDate = DateUtils.getOfDayFirst(createDate);
		this.sqlMap.getWhere().and("cardintime", QueryType.GTE, createDate);
	}
	@Override
	public Date getCreateDate_lte() {
		return (Date)this.sqlMap.getWhere().getValue("cardintime", QueryType.LTE);
	}
	@Override
	public void setCreateDate_lte(Date createDate) {
		createDate = DateUtils.getOfDayLast(createDate);
		this.sqlMap.getWhere().and("cardintime", QueryType.LTE, createDate);
	}
}