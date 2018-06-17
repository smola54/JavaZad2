package weatherjava;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class  DBConnect {
		
   public static void DBInsert(String city, String country, int temperature) {
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/weather",
            		"postgres", "razdwatrzy");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         
         String sql = "INSERT INTO WEATHERINFO (CITY,COUNTRY,TEMPERATURE) "
            + "VALUES (' " + city + "', ' " + country + " ', " + temperature + ");";
         stmt.executeUpdate(sql);
         stmt.close();
         c.commit();
         c.close();
      } catch (Exception e) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Records created successfully");
   }
   
   
	public static LinkedList<Weatherinfo> DBSelect(){
		LinkedList<Weatherinfo> weatherinfoList = new LinkedList<>();
	    Connection c = null;
	    Statement stmt = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/weather",
	            		"postgres", "razdwatrzy");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         
	         String sql = "SELECT * FROM WEATHERINFO";
	         ResultSet rs = stmt.executeQuery(sql);
	         Weatherinfo weatherinfo;
	         while (rs.next()) {
	        	 weatherinfo = new Weatherinfo(rs.getString("CITY"), rs.getString("COUNTRY"), rs.getInt("TEMPERATURE"),rs.getInt("ID"));
	        	 weatherinfoList.add(weatherinfo);
	         }	         
	         stmt.close();
	         c.commit();
	         c.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Selected successfully");
	      return weatherinfoList;
	}
	
	public static void DBDeleteRow(int id){
		
	    Connection c = null;
	    Statement stmt = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/weather",
	            		"postgres", "razdwatrzy");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");         
	         
	         PreparedStatement st = c.prepareStatement("DELETE FROM WEATHERINFO WHERE id = ? ");
	         st.setInt(1, id);
	         st.executeUpdate(); 
     
	         st.close();
	         c.commit();
	         c.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("deleted successfully");
	     
	}
   
public static void DBDelete(){
		
	    Connection c = null;
	    Statement stmt = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/weather",
	            		"postgres", "razdwatrzy");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");         
	         
	         PreparedStatement st = c.prepareStatement("DELETE FROM WEATHERINFO ");
	        
	         st.executeUpdate(); 
     
	         st.close();
	         c.commit();
	         c.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("deleted successfully");
	     
	}
	
   
   
}