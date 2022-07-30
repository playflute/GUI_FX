package application;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;

public class ExerciseRecordController implements Initializable
{

    @FXML
    private ListView<String> detailList;

    @FXML
    private Slider durationSlider;

    @FXML
    private DatePicker excerciseDate;

    @FXML
    private ChoiceBox<String> excerciseType;

    @FXML
    private Button recordBtn;
    
    @FXML
    private Label sliderAmount;
    
    private double amount;
    
    
    
    public void recordEntry(ActionEvent event) throws Exception
    { 
    	
    	//step1显示在控件ListView上
    	detailList.getItems().add(excerciseDate.getValue().toString()+"***"+excerciseType.getValue()+"***"+durationSlider.getValue());
    	//step2写入records.xml 借助XMLHelper的wirteRecord函数
    	//To be continued...
    	Scene scene=sliderAmount.getScene();
    	SingleExerciseEntry see=new SingleExerciseEntry((String)scene.getUserData(),excerciseDate.getValue().toString(), excerciseType.getValue(), amount);
    	XMLHelper.wirteRecord(see);
    	//step3 调用MainUIController里的refresh  更新图像
    	//(String)scene.getUserData()
		FXMLLoader loader=new FXMLLoader(getClass().getResource("MainUI.fxml"));

		Parent root = loader.load();
		MainUIController mainUIController=loader.getController();
//		mainUIController.initializeBarChart((String)scene.getUserData());
//		mainUIController.initializePieChart((String)scene.getUserData());
//		mainUIController.refresh((String)scene.getUserData());
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		sliderAmount.setText("0.0 Hours");
		String[] sportTypes;
		try {
			sportTypes = XMLHelper.getSportTypes();
			System.out.println(Arrays.toString(sportTypes));
			excerciseType.getItems().addAll(sportTypes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		durationSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
			{
				amount=durationSlider.getValue();
				sliderAmount.setText(amount+" Hours");
				
				
			}
		});
		
	}

}
