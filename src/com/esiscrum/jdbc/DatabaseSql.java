package com.esiscrum.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

public class DatabaseSql {
	
	
	private DataSource dataSource;
	//we will work with the interface for the abstraction , because we need to work with any DB
	private Connection db;
	private DatabaseMetaData dbm;
	
	public DatabaseSql() {
		
 	}
	

	public DatabaseSql(DataSource dataSource) {
		 setDataSource(dataSource);
	}
	
	public String delimit(String name) {
		if(name.contains(" ")) {
			name = dataSource.startDelimiter()+name + dataSource.endDelimiter();
		}
		return name;
	}
	
	public String[][] select(String tableName){
		String query = "SELECT * FROM "+ delimit(tableName);
		return excuteQuery(query);
	}
	
	public String[][] select(String tableName,String key,Object value){
		String query = "SELECT * FROM "+ delimit(tableName)+" WHERE " + delimit(key) + " = '"+value+"'";
		return excuteQuery(query);
	}
	
	
	public String[][] selectLike(String tableName,String key,Object value){
		String query = "SELECT * FROM "+ delimit(tableName)+" WHERE " + delimit(key) + " Like '%"+value+"%'";
		return excuteQuery(query);
	}
	
	public String[][] excuteQuery(String query){
		//System.out.println("Query : " + query);
 		try {
			Statement sql = db.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY
			);
			ResultSet rs = sql.executeQuery(query);
			
			
			//recuperer les information sur la sturcture de donn�es (chmaps .. leurs nom.. etc)
			ResultSetMetaData rsm = rs.getMetaData();
			int cols = rsm.getColumnCount();
			rs.last();
			int rows = rs.getRow();
			String data[][] = new String[rows+1][cols];
			rs.beforeFirst();
			
			for (int i = 0; i < cols; i++) {
				data[0][i] = rsm.getColumnLabel(i+1);
			}
			
			int row = 0; 
			 while(rs.next()){
				 row++;
				 for (int i = 0; i < cols; i++) {
					 data[row][i] = rs.getString(i + 1);
				 } 
			 } 
			return data;
		} catch (Exception e) {
			//System.out.println("Error : "  + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(String tableName,Object ...row ) {
		try {
			StringBuffer query = new StringBuffer("INSERT INTO " + delimit(tableName)
					+ " VALUES('" + row[0] + "'");
			for (int i = 1; i < row.length; i++) {
				query.append(",'"+row[i]+"'");
			}
			query.append(")");
			Statement sql = db.createStatement();
			return sql.executeUpdate(query.toString());
			
		} catch (Exception e) {
			//System.out.println("Erreur : " + e.getMessage());
			return 0;
		}
	}
	
	public int update(String tableName,Object ...row) {
		tableName = delimit(tableName);
		try {
			
			String cols[] = getColumnNames(tableName);
			StringBuffer query = new StringBuffer("UPDATE " + tableName + " SET "+ delimit(cols[1]) + " = '"+row[1]+"'");
			
			for (int i = 2; i < row.length; i++) {
				query.append(", "+delimit(cols[i])+" = '" +row[i]+"'");
			}
			query.append(" WHERE " + delimit(cols[0]) + " = '" + row[0]+ "'");
			Statement sql = db.createStatement();
			return sql.executeUpdate(query.toString());
			
		} catch (Exception e) {
			//System.out.println("Erreur : " + e.getMessage());
			return -1;
		}
	}
	
	
	public String[] getColumnNamesOld(String tableName) {
		try {
		Statement sql = db.createStatement();
		ResultSet rs = sql.executeQuery("SELECT * FROM " + delimit(tableName));	
		ResultSetMetaData rsm = rs.getMetaData();
		
		int cols = rsm.getColumnCount();
		String data[] = new String[cols];
		for (int i = 0; i < cols; i++) {
			data[i] = rsm.getColumnName(i+1);
		}
		rs.close();
		return data;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public String[] getColumnNames(String tableName) {
		try {
			ResultSet rs = dbm.getColumns(null, null, tableName, null);
			/*
			ResultSetMetaData rsm = rs.getMetaData();
			int n = rsm.getColumnCount();
			for (int i = 1; i <= n; i++) {
				//System.out.println(" - " + rsm.getColumnName(i));
			}
			
			while(rs.next()) {
				//System.out.println(rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6));
				
			}
			*/
				Vector<String> v = new Vector<>();
				while(rs.next() ) {
					v.add(rs.getString(4));
				}
				String t[] = new String[v.size()];
				v.toArray(t);
				return t;
			} 
			catch (Exception e) {
				//System.out.println("Erreur : " + e.getMessage());
				return null;
			}
	
			
		}
	public int delete(String tableName,String key,Object value) {
		try {
			String query = "DELETE FROM " + delimit(tableName) + " WHERE " + delimit(key) + " = '"+value+"'";
			Statement sql = db.createStatement();
			return sql.executeUpdate(query.toString());
			
		} catch (Exception e) {
			//System.out.println("Erreur : " + e.getMessage());
			return -1;
		}
	}
	
	public String[] getTableNames() {
		try {
			ResultSet rs =  dbm.getTables(null, null, null, new String[] {"TABLE"/*,"VIEW","SYSTEM TABLE"*/});
			/*ResultSetMetaData rsm = rs.getMetaData();
			int n = rsm.getColumnCount();
			for (int i = 1; i <= n; i++) {
				//System.out.println(" - " + rsm.getColumnName(i));
			}*/
			Vector<String> v = new Vector<>();
			while(rs.next() ) {
				v.add(rs.getString(3));
				////System.out.println(rs.getString(3) + " : " + rs.getString(4));
			}
			String t[] = new String[v.size()];
			v.toArray(t);
			
			return t;
		} catch (Exception e) {
			//System.out.println("Erreur : " + e.getMessage());
			return null;
		}
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		db = dataSource.getConnection();
		try {
			dbm = db.getMetaData();
		} catch (Exception e) {
			//System.out.println("Erreur : " + e.getMessage());
		}
		
	}
	
	
	
	
}
