package com.nordicpeak.flowengine.beans;

import java.io.Serializable;
import java.lang.reflect.Field;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.enums.QueryState;
import com.nordicpeak.flowengine.interfaces.MutableQueryInstanceDescriptor;

@Table(name = "flowengine_query_instance_descriptors")
@XMLElement
public class QueryInstanceDescriptor extends GeneratedElementable implements MutableQueryInstanceDescriptor, Serializable {

	private static final long serialVersionUID = 9079092615887303186L;

	public static final Field QUERY_DESCRIPTOR_RELATION = ReflectionUtils.getField(QueryInstanceDescriptor.class, "queryDescriptor");
	
	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged
	@XMLElement
	private QueryState queryState;

	@DAOManaged
	@XMLElement
	private boolean populated;

	@DAOManaged
	@XMLElement
	private Integer flowInstanceID;

	@DAOManaged(columnName = "queryID")
	@ManyToOne
	@XMLElement
	private QueryDescriptor queryDescriptor;

	public QueryInstanceDescriptor() {};

	public QueryInstanceDescriptor(QueryDescriptor queryDescriptor) {

		this.queryDescriptor = queryDescriptor;

		this.queryState = queryDescriptor.getDefaultQueryState();
	}

	@Override
	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}

	public void setQueryInstanceID(Integer queryDescriptorID) {

		this.queryInstanceID = queryDescriptorID;
	}

	@Override
	public QueryState getQueryState() {

		return queryState;
	}

	@Override
	public void setQueryState(QueryState queryState) {

		this.queryState = queryState;
	}

	@Override
	public boolean isPopulated() {

		return populated;
	}

	@Override
	public void setPopulated(boolean values) {

		this.populated = values;
	}

	@Override
	public QueryDescriptor getQueryDescriptor() {

		return queryDescriptor;
	}

	public void setQueryDescriptor(QueryDescriptor queryDescriptor) {

		this.queryDescriptor = queryDescriptor;
	}

	@Override
	public Integer getFlowInstanceID() {

		return flowInstanceID;
	}

	public void setFlowInstanceID(Integer flowInstanceID) {

		this.flowInstanceID = flowInstanceID;
	}

	public void copyQueryDescriptorValues() {

		this.queryState = queryDescriptor.getDefaultQueryState();
	}

	@Override
	public String toString() {

		if(this.queryDescriptor != null){

			return queryDescriptor.getName() + " (ID: " + queryInstanceID + ", queryID: " + queryDescriptor.getQueryID() + ")";
		}

		return "QueryInstanceDescriptor (ID: " + queryInstanceID + ")";
	}
}
