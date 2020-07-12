package com.kienast.jenkinstest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.repository.PeopleRepository;
import com.kienast.jenkinstest.service.PeopleService;

@WebMvcTest(controllers = PeopleController.class)
@AutoConfigureMockMvc(addFilters = false)
class PeopleControllerTest {
	

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private PeopleService peopleService;
	
	@MockBean
	private PeopleRepository peopleRepository;

	List<Person> personList;
	Person person;

	
	@Test
	void testGetPeople() throws Exception {
		
		//Init TestData
		personList = new ArrayList<>();
		personList.add(new Person("Test1"));
		personList.add(new Person("Test2"));
		
		//MockService
		Mockito.when (peopleService.getPeople()).thenReturn (personList);

		//Do Request
		this.mockMvc.perform(get("/people").accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isOk())
	       .andExpect(content().json("[{\"name\":\"Test1\"},{\"name\":\"Test2\"}]"));
	}

	@Test
	void testGetValidPerson() throws Exception {
		
		//Init TestData
		person = new Person("Test1");
		
		//MockService
		Mockito.when (peopleService.findPersonByName("Test1")).thenReturn (person);
		
		//Do Request
		this.mockMvc.perform(get("/people/Test1").accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isOk())
	       .andExpect(content().json("{\"name\":\"Test1\"}"));
	}
	
	@Test
	void testGetNotValidPerson() throws Exception {
		
		//Init TestData
		person = new Person("Test1");
		
		//MockService
		Mockito.when (peopleService.findPersonByName("Test1")).thenReturn (person);
				
		this.mockMvc.perform(get("/people/Test3").accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isNotFound());
	}
}
