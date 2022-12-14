package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainUIController
{
	@FXML
	Label label1;
    @FXML
    private CheckMenuItem themeMenuItem;
    
    @FXML
    private PieChart pc;
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private LineChart<String, Double> lineChart;
    
    private String thisUserID;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    
    

    @FXML
    private Button refreshBtn;
    
//    @FXML
//    private Menu sync;
    


	public void refresh(ActionEvent event) throws Exception
	{
//		Map<String,Double> map=XMLHelper.generateDictionary(thisUserID);
//		 BarChart.Series<String, Double> series1 = new BarChart.Series<>();
////		series.getData().clear();
//		series.setName("Hours");
//		for(String key : map.keySet())
//		{
//			Double value = map.get(key);
//			System.out.println(key+":"+value);
//			series1.getData().add(new XYChart.Data<>(key,value));
//
//		}
//		ObservableList<XYChart.Series<String, Double>> barChartData = FXCollections.observableArrayList();
//		barChartData.add(series1);
//		barChart.setData(barChartData);
//		
		initializeBarChart(thisUserID);
		initializePieChart(thisUserID);
		initializeLineChart(thisUserID);

	}
	
	public void displayName(String userName)
	{
		this.thisUserID=userName;
		label1.setText("hello "+userName);
	}
	public void changeTheme(ActionEvent event) throws IOException
	{
		if(themeMenuItem.isSelected()==false)
		{
			((Node)label1).getScene().getRoot().getStylesheets().remove(getClass().getResource("application.css").toExternalForm());
			System.out.println("remove css");
		}
		else
		{
			((Node)label1).getScene().getRoot().getStylesheets().clear();
			((Node)label1).getScene().getRoot().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			System.out.println("add css:"+getClass().getResource("application.css").toExternalForm());

			
		}

	}
	public void showRecordDialog(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Record.fxml"));
		Parent root = loader.load();
//		
//		//?????????????????????load??????????????????
//		RegistrationController rc=loader.getController();

//		root = FXMLLoader.load(getClass().getResource("Record.fxml"));
		Stage stage=(Stage)((Node)label1).getScene().getWindow();
		stage=new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
//
//
		Scene scene=new Scene(root);
		
		stage.setScene(scene);
		stage.setTitle("Enter Data Entry For Specified Date");
		scene.setUserData(this.thisUserID);

		stage.show();
	
	}
	public void show_CRUD_dialog(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CRUD.fxml"));
		CRUD_Controller controller=new CRUD_Controller();
		//step3
		controller.setCurrentUserName(thisUserID);
		//step4
		loader.setController(controller);
		//step5
		Parent root = loader.load();

//		
//		//?????????????????????load??????????????????
//		RegistrationController rc=loader.getController();

//		root = FXMLLoader.load(getClass().getResource("Record.fxml"));
		Stage stage=(Stage)((Node)label1).getScene().getWindow();
		stage=new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
//
//
		Scene scene=new Scene(root);
		
		stage.setScene(scene);
		stage.setTitle(this.thisUserID+" Data in remote server--->MySQL");
		scene.setUserData(this.thisUserID);

		stage.show();
	}
	
	public void initializePieChart(String ID) throws Exception
	{
		//??????XMLHelper??????????????? ??????ObservableList??????????????? 
		pc.getData().clear();

		
		ObservableList<PieChart.Data> piechartData=FXCollections.observableArrayList();
		
		Map<String,Double> map=XMLHelper.generateDictionary(ID);
//		System.out.println(piechartData.get(2).getName());
//		System.out.println(piechartData.get(2).getPieValue());
		for(String key : map.keySet())
		{
			Double value = map.get(key);
			System.out.println(key+":"+value);
			piechartData.add(new PieChart.Data(key, value));
		}
		pc.setData(piechartData);
		pc.setTitle(ID+"'s Sports Genres Pie Chart");

	}


	public void initializeBarChart(String ID) throws Exception
	{
		//??????XMLHelper??????????????? ??????ObservableList??????????????? 		
		

		Map<String,Double> map=XMLHelper.generateDictionary(ID);
		
		List<BarData> unsorted=new ArrayList<BarData>();
		for(String key : map.keySet())
		{
			BarData bd=new BarData(key, map.get(key));
			unsorted.add(bd);
		
		}
		//??????efficient bubble sort,??????flag??????
        for(int j = unsorted.size() - 1 ;j > 0;j--)
        {
        	boolean hasSwap=false;
            for(int i = 0; i < j; i++)
            {
                if(unsorted.get(i+1).getDuration()< unsorted.get(i).getDuration() )
                {
                    BarData tmp = unsorted.get(i);
                    unsorted.set(i, unsorted.get(i+1));
                    unsorted.set(i+1, tmp);
                    hasSwap=true;
                    
                   
                }
            }
            if(hasSwap==false)
            {
            	break;
            }
        }

		
		XYChart.Series<String, Double> series=new XYChart.Series<String, Double>();
		series.setName("Hours");
		for(BarData e:unsorted)
		{
			series.getData().add(new XYChart.Data<>(e.getSportName(),e.getDuration()));
		}
//		for(String key : map.keySet())
//		{
//			Double value = map.get(key);
//			System.out.println(key+":"+value);
//			series.getData().add(new XYChart.Data<>(key,value));
//
//		}
		barChart.setAnimated(true);
		barChart.setTitle(ID+"'s Sports Total Hours Statistics Bar Chart");
		barChart.getData().add(series);
		barChart.setData(FXCollections.observableArrayList(series));


	}
	public void initializeLineChart(String ID) throws Exception
	{
		XYChart.Series series = new XYChart.Series();
		series.setName("Total hours statistics"); //setting series name (appear as legends)
		Map<String,Double> map=XMLHelper.generateDateWithTotalhourDictionary(ID);
		for(String key : map.keySet())
		{
			Double value = map.get(key);
			System.out.println(key+":"+value);
			series.getData().add(new XYChart.Data<>(key,value));

		}
		barChart.setAnimated(true);
//	    series.getData().add(new XYChart.Data("2021-01-03", 23)); 
//        series.getData().add(new XYChart.Data("2021-01-04", 14));
//        series.getData().add(new XYChart.Data("2021-01-05", 15));
//        series.getData().add(new XYChart.Data("2021-01-06", 24));
//        series.getData().add(new XYChart.Data("2021-01-07", 34));
//        series.getData().add(new XYChart.Data("2021-01-08", 36));
//        series.getData().add(new XYChart.Data("2021-01-09", 22));
//        series.getData().add(new XYChart.Data("2021-01-10", 45));

        lineChart.getData().clear();
        lineChart.getData().add(series);
	}
	@FXML
	public void synchronizeDatabase(ActionEvent event) throws Exception
	{
//	    <ID>Austin</ID>
//	    <exerciseDate>2019-04-11</exerciseDate>
//	    <exerciseType>Pingpong</exerciseType>
//	    <totalDuration>2.240506329113924</totalDuration>
		//1.0 ???????????????????????????????????????????????????
		DBUtil util = new DBUtil();
        Connection  conn= util.getConnection();//?????????????????????
        String sql="drop table records;";
//        String sql = "create table records(ID int,sportType varchar(50),duration int);";//??????sql??????
        PreparedStatement pstmt;
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        
      //2.0 ?????????????????????????????????????????????
        conn= util.getConnection();//?????????????????????
        
        sql = "create table records(ID int primary key auto_increment,userName varchar(30),exerciseDate varchar(20),exerciseType varchar(30),totalDuration real);";//??????sql??????
   
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        //3.0????????????xml????????????????????????mysql?????????
		Map<String,Double> map = new TreeMap<String,Double>();
		SAXReader reader = new SAXReader();	
		// ??????xml???????????????????????????
		Document doc = reader.read("Records.xml");
		// ?????????????????????
		Element rootElement = doc.getRootElement();
		//????????????????????????Entry??????
		List<Element> entries = rootElement.elements("Entry");
		
		conn= util.getConnection();
		
		for(int i=0;i<entries.size(); i++) 
		{
			String un=entries.get(i).elementText("ID");
			String exerciseDate=entries.get(i).elementText("exerciseDate");
			String type=entries.get(i).elementText("exerciseType");
			Double duration=Double.parseDouble(entries.get(i).elementText("totalDuration"));
			sql = "insert into records(userName, exerciseDate, exerciseType, totalDuration) values(?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			
            pstmt.setString(1, un);
            pstmt.setString(2, exerciseDate);
            pstmt.setString(3, type);
            pstmt.setDouble(4, duration);;
            
            pstmt.executeUpdate();


		

		}
        pstmt.close();
        conn.close();
	}




}
