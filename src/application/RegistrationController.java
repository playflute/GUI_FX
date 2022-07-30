package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class RegistrationController {

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField phoneNoTextBox;

    @FXML
    private Button photoFileChooser;

    @FXML
    private ImageView profilePhotoImageView;

    @FXML
    private TextField pwdTextBox;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField userNameTextBox;

    private String imagePath;
    private String photoName;
    @FXML
    public void chooseImage(ActionEvent event) 
    {
    	imagePath="";
    	FileChooser fc=new FileChooser();
		
		fc.setTitle("Choose your profile photo");
		//Set the selected file or null if no files has been selected
		File file=fc.showOpenDialog(null);
		if(file!=null)
		{

			imagePath=file.getPath();
			System.out.println(imagePath);
			profilePhotoImageView.setImage(new Image(file.toURI().toString()));
//			iv.setCache(false);
//			System.out.println("hello");
		}
		else
		{
			System.out.println("You haven't choose a file");
		}
    }
    public void register(ActionEvent event) throws Exception
    {	
    	
    	//step1图片文件保存在image文件夹下

		try 
		{ 
			File oldfile = new File(imagePath); 
			System.out.println(oldfile.exists());
			if (oldfile.exists()) 
			{ 
				photoName=oldfile.getName();
				System.out.println("This picture name "+oldfile.getName()); 
				//文件存在时 
				File newFile=new File("src/Images/"+oldfile.getName());
				System.out.println(newFile.getAbsolutePath());
				System.out.println(newFile.toPath());
				Files.copy(oldfile.toPath(), newFile.toPath());

			} 
		} 
		catch (Exception e) 
		{ 
			System.out.println("复制单个文件操作出错"); 
			e.printStackTrace();
		}
    	
    	//step2把值写入xml文件的user节点

		//创建解析器
		SAXReader saxReader = new SAXReader();
		//得到document
		Document document = saxReader.read("infos.xml");
		//得到根节点
		Element root = document.getRootElement();

		//获取users下的所有元素elements
		List<Element> list = root.elements();
		//创建一个user元素
		Element currentUser = DocumentHelper.createElement("user");
		//在user标签下创建ID和password标签并添加文本
		Element currentID = currentUser.addElement("ID");
		currentID.setText(userNameTextBox.getText());
		Element currentpwd= currentUser.addElement("password");
		currentpwd.setText(pwdTextBox.getText());
		Element currentBirthday = currentUser.addElement("birthday");
		currentBirthday.setText(birthdayDatePicker.getValue().toString());
		Element currentPhoneNo = currentUser.addElement("phoneNo");
		currentPhoneNo.setText(phoneNoTextBox.getText());
		Element currentProfilePhoto = currentUser.addElement("photo");
		currentProfilePhoto.setText(photoName);
		
		//在特定位置添加
		list.add(0,currentUser);
		
		//回写xml
		OutputFormat format = OutputFormat.createPrettyPrint();//格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("infos.xml"),format);
		xmlWriter.write(document);
		xmlWriter.close();
		
		Alert alert=new Alert(AlertType.CONFIRMATION,"Congratulations!Dear "+userNameTextBox.getText());
		alert.setTitle("Registration Success");
		alert.setHeaderText("Registration Success");

		alert.show();




    }
    

}
