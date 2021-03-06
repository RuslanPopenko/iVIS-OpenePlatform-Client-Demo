/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.core.daos.implementations.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import se.unlogic.hierarchy.core.beans.SimpleSectionDescriptor;
import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.hierarchy.core.daos.interfaces.AttributeDAO;
import se.unlogic.hierarchy.core.handlers.SimpleMutableAttributeHandler;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.dao.querys.ArrayListQuery;
import se.unlogic.standardutils.dao.querys.HashMapQuery;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.populators.StringEntryPopulator;

public class MySQLSectionAttributeDAO<Descriptor extends SimpleSectionDescriptor> extends BaseDAO implements AttributeDAO<Descriptor> {

	public static final StringEntryPopulator POPULATOR = new StringEntryPopulator();

	private final String tableName;

	protected MySQLSectionAttributeDAO(DataSource ds, String tableName) {
		super(ds);
		this.tableName = tableName;
	}

	@Override
	public void set(Descriptor sectionDescriptor) throws SQLException {

		TransactionHandler transactionHandler = null;

		try{
			transactionHandler = new TransactionHandler(dataSource);
			set(sectionDescriptor, transactionHandler);
			transactionHandler.commit();
		}finally{

			TransactionHandler.autoClose(transactionHandler);
		}
	}

	@Override
	public void set(Descriptor sectionDescriptor, TransactionHandler transactionHandler) throws SQLException {

		UpdateQuery deleteQuery = transactionHandler.getUpdateQuery("DELETE FROM " + tableName + " WHERE sectionID = ?");

		deleteQuery.setInt(1, sectionDescriptor.getSectionID());

		deleteQuery.executeUpdate();

		if (sectionDescriptor.getAttributeHandler() != null && !sectionDescriptor.getAttributeHandler().isEmpty()) {

			for (Entry<String, String> entry : sectionDescriptor.getAttributeHandler().getAttributeMap().entrySet()) {

				UpdateQuery updateQuery = transactionHandler.getUpdateQuery("INSERT INTO " + tableName + " VALUES (?,?,?)");

				updateQuery.setInt(1, sectionDescriptor.getSectionID());
				updateQuery.setString(2, entry.getKey());
				updateQuery.setString(3, entry.getValue());

				updateQuery.executeUpdate();
			}
		}
	}

	@Override
	public void getAttributeHandler(Descriptor sectionDescriptor, Connection connection) throws SQLException {

		HashMapQuery<String, String> query = new HashMapQuery<String, String>(connection, false, "SELECT name, value FROM " + tableName + " WHERE sectionID = ?", POPULATOR);

		query.setInt(1, sectionDescriptor.getSectionID());

		HashMap<String, String> attributeMap = query.executeQuery();

		if (attributeMap == null) {
			return;
		}

		sectionDescriptor.setAttributeHandler(new SimpleMutableAttributeHandler(attributeMap, 255, 4096));
	}
	
	public List<Integer> getIDsByAttribute(String name, String value, QueryOperators operator) throws SQLException {
		
		ArrayListQuery<Integer> query = new ArrayListQuery<Integer>(dataSource, "SELECT sectionID FROM " + tableName + " WHERE name = ? AND `value` " + operator.getOperator() + " ?", IntegerPopulator.getPopulator());
		
		query.setString(1, name);
		query.setString(2, value);
		
		return query.executeQuery();
	}

	@Override
	public List<Integer> getIDsByAttribute(String name) throws SQLException {

		ArrayListQuery<Integer> query = new ArrayListQuery<Integer>(dataSource, "SELECT sectionID FROM " + tableName + " WHERE name = ?", IntegerPopulator.getPopulator());

		query.setString(1, name);

		return query.executeQuery();
	}
}
