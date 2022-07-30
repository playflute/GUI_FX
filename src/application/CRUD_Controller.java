package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;

public class CRUD_Controller implements Initializable
{
	private String currentUserName;
	
	
    public String getCurrentUserName() 
    {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) 
	{
		this.currentUserName = currentUserName;
	}

	@FXML
    private Label label;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Record, String> duration_column;

    @FXML
    private TableColumn<Record, String> exerciseDate_column;

    @FXML
    private TableColumn<Record, String> exerciseType_column;

    @FXML
    private TableColumn<Record, String> id_column;

    @FXML
    private TableView<Record> table;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtExercciseDate;

    @FXML
    private TextField txtExerciseType;
    
    @FXML
    private Button btnSync;
    
    @FXML
    void add(ActionEvent event) 
    {
        String exercciseDate,exerciseType,duration;
        exercciseDate = txtExercciseDate.getText();
        exerciseType = txtExerciseType.getText();
        duration = txtDuration.getText();
	    try 
	    {
	        pst = con.prepareStatement("insert into records(userName,exerciseDate,exerciseType,totalDuration)values(?,?,?,?)");
	        pst.setString(1, this.getCurrentUserName());
	        pst.setString(2, exercciseDate);
	        pst.setString(3, exerciseType);
	        pst.setDouble(4, Double.parseDouble(duration));
	        pst.executeUpdate();
	      
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Sport Record Entered");
	
		
	        alert.setHeaderText("Sport Record Entered");
	        alert.setContentText("Record Addedddd!");
	
	        alert.showAndWait();
	
	        table();
	        
	        txtExercciseDate.setText("");
	        txtExerciseType.setText("");
	        txtDuration.setText("");
	        txtExercciseDate.requestFocus();
	    } 
	    catch (SQLException ex)
	    {
	        Logger.getLogger(CRUD_Controller.class.getName()).log(Level.SEVERE, null, ex);
	    }
    }

    @FXML
    void delete(ActionEvent event) 
    {
        myIndex = table.getSelectionModel().getSelectedIndex();
		 
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                     

        try 
        {
            pst = con.prepareStatement("delete from records where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sports Data Management");

		
			alert.setHeaderText("Delete Record");
			alert.setContentText("Deletedd!");

			alert.showAndWait();
            table();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(CRUD_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void update(ActionEvent event) 
    {
    	String exercciseDate,exerciseType,duration;
        
       myIndex = table.getSelectionModel().getSelectedIndex();
		 
       id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
          
       exercciseDate = txtExercciseDate.getText();
       exerciseType = txtExerciseType.getText();
       duration = txtDuration.getText();
       try 
       {
           pst = con.prepareStatement("update records set exerciseDate = ?,exerciseType = ? ,totalDuration = ? where id = ? ");
           pst.setString(1, exercciseDate);
           pst.setString(2, exerciseType);
           pst.setDouble(3, Double.parseDouble(duration));
           pst.setInt(4, id);
           pst.executeUpdate();
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Update Record");

		
           alert.setHeaderText("Update Record");
           alert.setContentText("Updateddd!");

           alert.showAndWait();
              table();
       } 
       catch (SQLException ex)
       {
           Logger.getLogger(CRUD_Controller.class.getName()).log(Level.SEVERE, null, ex);
       }

    }
    
    @FXML
    void synchronize(ActionEvent event) throws Exception 
    {
    	System.out.println("sync start..........................");
    	//根据远程mysql数据库 生成本地records.xml文件
    	String sql="select * from records";
    	Statement statement=con.createStatement();
    	ResultSet rs=statement.executeQuery(sql);
    	
    	//先清空本地records.xml文件
    	XMLHelper.cleanRecordFile();
    	//根据mysql数据写回records.xml文件
		while (rs.next())
		{
			String un=rs.getString("userName");
			String exerciseDate=rs.getString("exerciseDate");
			String exerciseType=rs.getString("exerciseType");
			String totalDuration=rs.getString("totalDuration");
			System.out.println("----------------------------------------------");
			SingleExerciseEntry entry=new SingleExerciseEntry(un, exerciseDate, exerciseType, Double.parseDouble(totalDuration));
			XMLHelper.wirteRecord(entry);
		}
    	
    	

    }


    public void table()
    {
        Connect();
        ObservableList<Record> records = FXCollections.observableArrayList();
        try 
        {
//        	System.out.println(label.getText());
//        	Scene scene=label.getScene();
//        	System.out.println((String)scene.getUserData());
        	pst = con.prepareStatement("select id,userName,exerciseDate,exerciseType,totalDuration from records where userName=\'"+this.getCurrentUserName()+"\'");  
        	ResultSet rs = pst.executeQuery();
        	{
        		while (rs.next())
        		{
		    	  Record r = new Record();
		          r.setId(rs.getString("id"));
		          r.setUserName(rs.getString("userName"));
		          r.setExerciseDate(rs.getString("exerciseDate"));
		          r.setExerciseType(rs.getString("exerciseType"));
		          r.setDuration(rs.getString("totalDuration"));
		          records.add(r);
        		}
        	} 
             table.setItems(records);
             id_column.setCellValueFactory(f -> f.getValue().idProperty());
             exerciseDate_column.setCellValueFactory(f -> f.getValue().exerciseDateProperty());
             exerciseType_column.setCellValueFactory(f -> f.getValue().exerciseTypeProperty());
             duration_column.setCellValueFactory(f -> f.getValue().durationProperty());
              
             

     }
     
     catch (SQLException ex) 
     {
         Logger.getLogger(CRUD_Controller.class.getName()).log(Level.SEVERE, null, ex);
     }

     table.setRowFactory( tv -> {
		     TableRow<Record> myRow = new TableRow<>();
		     myRow.setOnMouseClicked (event -> 
		     {
		        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
		        {
		            myIndex =  table.getSelectionModel().getSelectedIndex();
		 
		           id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
		           txtExercciseDate.setText(table.getItems().get(myIndex).getExerciseDate());
		           txtExerciseType.setText(table.getItems().get(myIndex).getExerciseType());
                   txtDuration.setText(table.getItems().get(myIndex).getDuration());
                         
                       
                         
		        }
		     });
		        return myRow;
		        });
  
  
    }
    
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;
    
    public void Connect()
   {
       try {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sport?useSSL=false","root","990271");
       } 
       catch (ClassNotFoundException ex) 
       {
         
       } 
       catch (SQLException ex) 
       {
           ex.printStackTrace();
       }
   }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		System.out.println("-----------------------"+this.getCurrentUserName());
        Connect();
        table();
		
	}
}



