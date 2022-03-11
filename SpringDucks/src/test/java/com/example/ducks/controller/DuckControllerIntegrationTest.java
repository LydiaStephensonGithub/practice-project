package com.example.ducks.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.ducks.entity.Duck;
import com.fasterxml.jackson.databind.ObjectMapper;


//boots the context
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //create the MockMVC object
@ActiveProfiles("test") //sets current profile to test
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:duck-schema.sql", "classpath:duck-data.sql"})
public class DuckControllerIntegrationTest {
	
	@Autowired //tells Spring to insert this object into the class
	private MockMvc mvc; // object for running fake requests
	
	@Autowired
	private ObjectMapper mapper; //the object Spring uses to convert JSON <-> Java

	@Test
	public void test() {
		assertEquals(2, 1 + 1);
	}
	
	@Test
	public void testCreate() throws Exception {
		//URL, body, method, header
		Duck testDuck = new Duck(12, "Donald", "Disneyworld", "Male");
		String testDuckAsJson = this.mapper.writeValueAsString(testDuck);
		RequestBuilder req = post("/duck/create").content(testDuckAsJson).contentType(MediaType.APPLICATION_JSON);
		
		Duck testSavedDuck = new Duck(2, 12, "Donald", "Disneyworld", "Male");
		String testSavedDuckAsJson = this.mapper.writeValueAsString(testSavedDuck);
		//this will check the status code of the response
		ResultMatcher checkStatus = status().isCreated();
		//this will check the body of the response
		ResultMatcher checkBody = content().json(testSavedDuckAsJson);
		
		//run the request and check both matchers
		this.mvc.perform(req).andExpect(checkStatus).andExpectAll(checkBody);
	}
	
	@Test
	public void testReadById() throws Exception {
		RequestBuilder req = get("/duck/readById/1");
		
		ResultMatcher checkStatus = status().isOk();
		
		Duck savedDuck = new Duck(1, 15, "Duck Dodgers", "space", "male");
		String savedDuckAsJSON = this.mapper.writeValueAsString(savedDuck);
		
		ResultMatcher checkBody = content().json(savedDuckAsJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpectAll(checkBody);
	}
}
