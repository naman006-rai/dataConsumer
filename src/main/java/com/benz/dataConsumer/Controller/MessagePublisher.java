package com.benz.dataConsumer.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benz.dataConsumer.Model.UserDataResponse;
import com.benz.dataConsumer.Service.DataService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class MessagePublisher {
	
	@Autowired
	private DataService service;
	

	@GetMapping("/read")
	public ResponseEntity <List<UserDataResponse>> getData() throws JsonGenerationException, JsonMappingException, IOException  {
			return new ResponseEntity<List<UserDataResponse>>(service.getData(), HttpStatus.OK);
		
	}
}

