package com.benz.dataConsumer.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.benz.dataConsumer.Config.MQConfig;
import com.benz.dataConsumer.Model.UserData;
import com.benz.dataConsumer.Model.UserDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class MessageListener {

	@RabbitListener(queues = MQConfig.QUEUE)
	public void listener(UserData data)  {
		if(data.getFileType().equalsIgnoreCase("xml")) {
			try {
				ObjectMapper map = new XmlMapper();
				UserDataResponse userData = new UserDataResponse();
				userData.setName(data.getName());
				userData.setAge(data.getAge());
				userData.setSalary(data.getSalary());
				userData.setDob(data.getDob());
				map.writeValue(new File("./src/main/resources/XML/"+data.getName()+".xml"), userData);
				System.out.println("Data Saved " +data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			ArrayList<UserDataResponse> dataList = new ArrayList<>();
			FileWriter writer = null;
			try {
				Date date = Calendar.getInstance().getTime();  
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
				 
				
				writer = new FileWriter("./src/main/resources/CSV/"+data.getName()+".csv");
				writer.append("Name, Age, Salary, Dob");
				writer.append("\n");
				UserDataResponse userData = new UserDataResponse();
				userData.setName(data.getName());
				userData.setAge(data.getAge());
				userData.setSalary(data.getSalary());
				userData.setDob(data.getDob());
				dataList.add(userData);
				for(UserDataResponse user : dataList) {
					writer.append(user.getName());
					writer.append(",");
					writer.append(String.valueOf(user.getAge()));
					writer.append(",");
					writer.append(String.valueOf(user.getSalary()));
					writer.append(",");
					String strDate = dateFormat.format(user.getDob());
					writer.append(strDate);
					writer.append("\n");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					writer.flush();
					writer.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}


			System.out.println("Data Saved " +data);
		}
	}

}
