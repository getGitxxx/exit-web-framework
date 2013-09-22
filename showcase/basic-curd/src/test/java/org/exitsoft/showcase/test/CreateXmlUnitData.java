package org.exitsoft.showcase.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

public class CreateXmlUnitData {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException {
		Class.forName("com.mysql.jdbc.Driver");  
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?autoReconnect=true", "root", "root");  
          
        IDatabaseConnection connection = new DatabaseConnection(conn);  
        IDataSet dataSet = connection.createDataSet();
        
        OutputStream out = new FileOutputStream("src/test/resources/unit-data.xml");
        
        FlatChineseXmlWriter datasetWriter = new FlatChineseXmlWriter(out);
        datasetWriter.setIncludeEmptyTable(true);
        datasetWriter.write(dataSet);
        
	}
	
	
}
