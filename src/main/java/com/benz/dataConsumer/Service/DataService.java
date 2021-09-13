package com.benz.dataConsumer.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.benz.dataConsumer.Model.UserDataResponse;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class DataService {

	public List<UserDataResponse> getData() throws JsonGenerationException, JsonMappingException, IOException {
		 File xmlPath = new File("./src/main/resources/XML");
		 ArrayList<UserDataResponse> dataList = new ArrayList<>();
		
	      File xmlFilesList[] = xmlPath.listFiles();
	      ObjectMapper map = new XmlMapper();
	      for(File file : xmlFilesList) {
	    	  UserDataResponse data = map.readValue(new File(file.getAbsolutePath()), UserDataResponse.class);
	    	 dataList.add(data);
	      }
	      File csvPath = new File("./src/main/resources/CSV");
		      File csvFilesList[] = csvPath.listFiles();
		      for(File file : csvFilesList) {
		    	  try{
		              CSVReader reader=
		                      new CSVReaderBuilder(new FileReader(file.getAbsolutePath())).
		                              withSkipLines(1). // Skiping firstline as it is header
		                              build();
		              List<UserDataResponse> userDataList=reader.readAll().stream().map(data-> {
		            	  UserDataResponse csvObject= new UserDataResponse();
		            	  csvObject.setName(data[0]);
		            	  csvObject.setAge(Integer.parseInt(data[1]));
		            	  csvObject.setSalary(Double.parseDouble(data[2]));
						try {
							 Date  date = new SimpleDateFormat("dd/MM/yyyy").parse(data[3]);
							 csvObject.setDob(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}
		            	 
		            	  dataList.add(csvObject);
		                  return csvObject;
		              }).collect(Collectors.toList());
		              userDataList.forEach(System.out::println);
		          } catch (IOException e) {
		              e.printStackTrace();
		          }
		      }
	   
		return dataList;
	}

	
}
