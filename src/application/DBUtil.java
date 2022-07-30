package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    //直连数据库
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
          return DriverManager.getConnection("jdbc:mysql://localhost:3306/sport?useSSL=false","root","990271");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }




    //关闭数据库
    public void closeConn(Connection conn){     
         try {
            conn.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("数据库关闭异常");
            e.printStackTrace();
          }
    }

}