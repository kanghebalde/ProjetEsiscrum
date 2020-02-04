package com.esiscrum.jdbc;

import java.util.Vector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class DbCassandra {

	private Cluster cluster;
	private Session session;
	
	
	public DbCassandra() {
	     cluster = Cluster.builder()
	             .addContactPoint("localhost")
	             //.withCredentials(USER, PWD)
	             .withLoadBalancingPolicy(
	             DCAwareRoundRobinPolicy.builder()
	                 .withLocalDc("dc1")
	                 .withUsedHostsPerRemoteDc(6)
	                 .allowRemoteDCsForLocalConsistencyLevel()
	                 .build())
	             .build();
	     session = cluster.connect("esiscrum");
 	}
	

	
	public String delimit(String name) {
		if(name.contains(" ")) {
			name = startDelimiter()+name + endDelimiter();
		}
		return name;
	}
	
	public String[][] select(String tableName){
		String query = "SELECT * FROM "+ delimit(tableName);
		return excuteQuery(query);
	}
	
	public String[][] select(String tableName,String key,Object value){
		String query = "SELECT * FROM "+ delimit(tableName)+" WHERE " + delimit(key) + " = '"+value+"' ALLOW FILTERING";
		return excuteQuery(query);
	}
	
	
	public String[][] selectLike(String tableName,String key,Object value){
		String query = "SELECT * FROM "+ delimit(tableName)+" WHERE " + delimit(key) + " Like '%"+value+"%' ALLOW FILTERING";
		return excuteQuery(query);
	}
	
	public String[][] excuteQuery(String query){
		//System.out.println("Query : " + query);
 		try {

			
			
	        ResultSet rst = session.execute(query);

	        int cols =rst.getColumnDefinitions().size();
			int rows = rst.all().size();
			String data[][] = new String[rows+1][cols+1];
			//System.out.println("Cols : " + cols);
			//System.out.println("Rows : " + rows);
			
			int r = 0; 
			rst = session.execute(query);
	        for(Row row : rst) {
	        	r++;
	        	for (int i = 0; i < cols; i++) {
					// data[r][i] = row.getString(i);
	        		data[r][i] = row.getObject(i).toString();
					// System.out.println("data["+r+"]["+i+"]" + data[r][i]);
				}
				 

	        }

			return data;
		} catch (Exception e) {
			System.out.println("Error : "  + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(String tableName,Object ...row ) {
		try {
			StringBuffer query = new StringBuffer("INSERT INTO " + delimit(tableName)+ColumnNames(tableName)
					+ " VALUES('" + row[0] + "'");
			for (int i = 1; i < row.length; i++) {

				if (row[i].getClass().getTypeName().equals("java.lang.Integer")) {
					query.append(","+row[i]+"");
				}
				else {
					query.append(",'"+row[i]+"'");
				}
				
			}
			query.append(")");
			
			
			//System.out.println("DatabaseNosql.insert()");
			//System.out.println(query);
			
			ResultSet rst = session.execute(query.toString());
			return 1;
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return 0;
		}
	}
	
	public int update(String tableName,Object ...row) {
		tableName = delimit(tableName);
		try {
			
			String cols[] = getColumnNames(tableName);

			StringBuffer query = new StringBuffer("UPDATE " + tableName );
			
			if (row[1].getClass().getTypeName().equals("java.lang.Integer")) {
				query.append(" SET "+ delimit(cols[1]) + " = "+row[1]+"");
			}
			else {
				query.append(" SET "+ delimit(cols[1]) + " = '"+row[1]+"'");
			}
			
			for (int i = 2; i < row.length; i++) {
				if (row[i].getClass().getTypeName().equals("java.lang.Integer")) {
					query.append(", "+delimit(cols[i])+" = " +row[i]+"");
				}
				else {
					query.append(", "+delimit(cols[i])+" = '" +row[i]+"'");
				}
				
				
			}
			
			if (row[0].getClass().getTypeName().equals("java.lang.Integer")) {
				query.append(" WHERE " + delimit(cols[0]) + " = " + row[0]+ "");
			}
			else {
				query.append(" WHERE " + delimit(cols[0]) + " = '" + row[0]+ "'");
			}
			
			
			
			//System.out.println(query);
			ResultSet rst = session.execute(query.toString());
			return 1;
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return -1;
		}
	}
	

	public String[] getColumnNames(String tableName) {
		try {
			
			String query = "SELECT * FROM system_schema.columns WHERE keyspace_name = 'esiscrum' AND table_name = '"+tableName+"';";
			ResultSet rst = session.execute(query);
			Vector<String> v = new Vector<>();
	        for(Row row : rst) {
				v.add(row.getString("column_name"));				
	        }

				String t[] = new String[v.size()];
				v.toArray(t);
				return t;
			} 
			catch (Exception e) {
				System.out.println("Erreur : " + e.getMessage());
				return null;
			}
	
			
		}
	
	
	public String ColumnNames(String tableName) {
		try {
			
			String query = "SELECT * FROM system_schema.columns WHERE keyspace_name = 'esiscrum' AND table_name = '"+tableName+"';";
			ResultSet rst = session.execute(query);
			Vector<String> v = new Vector<>();
			
	        for(Row row : rst) {
				v.add(row.getString("column_name"));
	        }

				String t[] = new String[v.size()];
				v.toArray(t);
				StringBuffer sb = new StringBuffer("(");
				for (int i = 0; i < t.length-1; i++) {
					sb.append(t[i]+",");
				}
				sb.append(t[t.length-1]+")");
				return sb.toString();
			} 
			catch (Exception e) {
				System.out.println("Erreur : " + e.getMessage());
				return null;
			}
	
			
		}
	
	public int delete(String tableName,String key,Object value) {
		try {
			String query = "DELETE FROM " + delimit(tableName) + " WHERE " + delimit(key) + " = '"+value+"'";
			ResultSet rst = session.execute(query.toString());
			return 1;
			
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return -1;
		}
	}
	

	

	public char startDelimiter() {
		return '`';
	}

	public char endDelimiter() {
		return '`';
		
	}
	

}
