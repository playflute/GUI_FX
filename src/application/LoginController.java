package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController 
{
	@FXML
	AnchorPane LoginAnchorPane;
	@FXML
	TextField ID;
	@FXML
	TextField password;
	private Stage stage;
	private Scene scene;
	private Parent root;

	
	@FXML
	public void verifySwitchToMainUI(ActionEvent event) throws Exception
	{
////////////////////////////////为了方便测试 comment了判断账号密码语句  把ID替换写死成了Austin*************************************************************
		

		String real_password=XMLHelper.getPassword(ID.getText());
		String input_password=password.getText();
		
		if(real_password.equals(input_password))
		{
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("MainUI.fxml"));
//			root = loader.load();
			
			FXMLLoader loader=new FXMLLoader(getClass().getResource("MainUI.fxml"));

			root = loader.load();
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("MainUI.fxml"));
//			root = loader.load();
			//////////////////////////
			MainUIController mainUIController=loader.getController();
//			mainUIController.displayName("Austin");
//			mainUIController.initializePieChart("Austin");
//			mainUIController.initializeBarChart("Austin");
//			mainUIController.initializeLineChart("Austin");
			mainUIController.displayName(ID.getText());
			mainUIController.initializePieChart(ID.getText());
			mainUIController.initializeBarChart(ID.getText());
			mainUIController.initializeLineChart(ID.getText());

			stage=(Stage)((Node)event.getSource()).getScene().getWindow();


			scene=new Scene(root);
			root.setUserData(ID.getText());
			stage.setScene(scene);
			
			stage.setTitle("Athlete Management Dashboard--- Welcome "+ID.getText());

			stage.show();
//			System.out.println("show main ui");

		}
		else
		{
			Alert alert=new Alert(AlertType.ERROR,"Password is wrong!");
			alert.setTitle("Error");
			alert.setHeaderText("OOps");

			alert.show();
		}
		

		
	}
	public void switchToRegistration(ActionEvent event) throws Exception
	{
//	    AnchorPane pane;
//	    try {
//	        // Initiate the loader for fxml 2. Using this loader, you can get the controller instance of fxml 2
//	        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
//	        pane = loader.load();
//	        LoginAnchorPane.getChildren().setAll(pane);
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
		//FXMLLoader必须使用参数初始化，否则getController会失败
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
		root = loader.load();
//		
//		//这个方法必须在load方法之后调用
//		RegistrationController rc=loader.getController();

		root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
		stage=(Stage)((Node)event.getSource()).getScene().getWindow();
		stage=new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
//
//
		scene=new Scene(root);
		
		
		stage.setScene(scene);
		stage.setTitle("Registration dialog");
		

		stage.show();
	}

}
