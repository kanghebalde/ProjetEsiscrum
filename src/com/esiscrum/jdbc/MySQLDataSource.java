package com.esiscrum.jdbc;
public class MySQLDataSource extends DataSource {

	public static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQLBRIDGE = "jdbc:mysql:";

	public MySQLDataSource() {
		
 	}

	
 	public MySQLDataSource(String host, String source, String user, String password) {
		super(MYSQLDRIVER, MYSQLBRIDGE, host, source, user, password);
 	}
 	
 	public MySQLDataSource(String host, String source, String user) {
		super(MYSQLDRIVER, MYSQLBRIDGE, host, source, user, "root");
 	}
 	
 	public MySQLDataSource(String host, String source) {
		super(MYSQLDRIVER, MYSQLBRIDGE, host, source, "root", "root");
 	}
 	
 	public MySQLDataSource(String source) {
		super(MYSQLDRIVER, MYSQLBRIDGE, "localhost", source, "root", "");
 	}

	public String getURL() {
		return MYSQLBRIDGE + "//" + getHost() + "/" + getSource();
	}
	
	public char startDelimiter() {
		return '`';
	}

	public char endDelimiter() {
		return '`';
		
	}


}
