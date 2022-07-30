package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
//import org.dom4j.Node;
import org.dom4j.io.*;
public class XMLHelper 
{
	public static void cleanRecordFile() throws IOException
	{
		
        File file = new File("records.xml");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");  //写入空
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //写入xml文件root节点

   
        file =new File("records.xml");
        
        FileWriter fileWritter = new FileWriter(file,true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><DataEntries></DataEntries>");
        //bufferWritter.write("\n");
        bufferWritter.close();
        System.out.println("写入成功");


	}
	//把用户单次的运动记录项写入records.xml
	public static void wirteRecord(SingleExerciseEntry entry) throws Exception
	{
		//创建解析器
		SAXReader saxReader = new SAXReader();
		//得到document
		Document document = saxReader.read("records.xml");
		//得到根节点
		Element root = document.getRootElement();

		//获取users下的所有元素elements
		List<Element> list = root.elements();
		//创建一个Entry元素
		Element currentEntry = DocumentHelper.createElement("Entry");
		//在Entry标签下创建ID exerciseDate exerciseType totalDuration 标签并添加文本
		Element currentID = currentEntry.addElement("ID");
		currentID.setText(entry.getID());
		
		Element currentExerciseDate= currentEntry.addElement("exerciseDate");
		currentExerciseDate.setText(entry.getExerciseDate());
		
		Element currentExerciseType = currentEntry.addElement("exerciseType");
		currentExerciseType.setText(entry.getExerciseType());
		
		Element currentTotalDuration = currentEntry.addElement("totalDuration");
		currentTotalDuration.setText(entry.getTotalDuration()+"");

		
		//在特定位置添加
		list.add(0,currentEntry);
		
		//回写xml
		OutputFormat format = OutputFormat.createPrettyPrint();//格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("records.xml"),format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
	public static String getPassword(String target_id) throws Exception
	{
		SAXReader reader = new SAXReader();	
		try {
			
			// 读取xml文件，获取文档结构
			Document doc = reader.read("infos.xml");
			// 获取文件根节点
			Element rootElement = doc.getRootElement();
			//获取根节点下所有use节点
			List<Element> users = rootElement.elements("user");

			
			for(Element user : users) 
			{
				String id = user.elementText("ID");				
				String pwd= user.elementText("password");
				if(id.equals(target_id))
				{
					return pwd;
				}		
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "not found";


	}
	public static String[] getSportTypes() throws Exception
	{
		SAXReader reader = new SAXReader();	
		// 读取xml文件，获取文档结构
		Document doc = reader.read("Calorie.xml");
		// 获取文件根节点
		Element rootElement = doc.getRootElement();
		//获取根节点下所有use节点
		List<Element> sportItems = rootElement.elements("SportsItem");
		String[] results=new String[sportItems.size()];
		for(int i=0;i<sportItems.size(); i++) 
		{
			results[i]=sportItems.get(i).elementText("Name");

		}
		return results;
	}
	public static Map<String,Double> generateDateWithTotalhourDictionary(String userID) throws Exception
	{
		Map<String,Double> map = new TreeMap<String,Double>();
		SAXReader reader = new SAXReader();	
		// 读取xml文件，获取文档结构
		Document doc = reader.read("Records.xml");
		// 获取文件根节点
		Element rootElement = doc.getRootElement();
		//获取根节点下所有Entry节点
		List<Element> entries = rootElement.elements("Entry");

		for(int i=0;i<entries.size(); i++) 
		{
			if(entries.get(i).elementText("ID").equals(userID))
			{
				String type=entries.get(i).elementText("exerciseDate");
				Double duration=Double.parseDouble(entries.get(i).elementText("totalDuration"));
				if(map.get(type)==null)//当前运动类型键还未在字典中出现过
				{
					map.put(type, duration);
				}
				else//当前运动类型键已经存在
				{
					map.put(type, map.get(type)+new Double(duration));
				}
			}

		}
		return map;

		
		
	}
	public static Map<String,Double> generateDictionary(String userID) throws Exception
	{
		Map<String,Double> map = new HashMap<String,Double>();
		SAXReader reader = new SAXReader();	
		// 读取xml文件，获取文档结构
		Document doc = reader.read("Records.xml");
		// 获取文件根节点
		Element rootElement = doc.getRootElement();
		//获取根节点下所有Entry节点
		List<Element> entries = rootElement.elements("Entry");

		for(int i=0;i<entries.size(); i++) 
		{
			if(entries.get(i).elementText("ID").equals(userID))
			{
				String type=entries.get(i).elementText("exerciseType");
				Double duration=Double.parseDouble(entries.get(i).elementText("totalDuration"));
				if(map.get(type)==null)//当前运动类型键还未在字典中出现过
				{
					map.put(type, duration);
				}
				else//当前运动类型键已经存在
				{
					map.put(type, map.get(type)+new Double(duration));
				}
			}

		}
		return map;

		
		
	}

}
