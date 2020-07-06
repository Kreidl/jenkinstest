package com.kienast.jenkinstest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kienast.jenkinstest.dto.PeopleAdapter;
import com.kienast.jenkinstest.dto.PersonAdapter;
import com.kienast.jenkinstest.exception.PersonNotFoundException;
import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.rest.api.GetPeopleApi;
import com.kienast.jenkinstest.rest.api.model.PersonModel;
import com.kienast.jenkinstest.service.PeopleService;


@RestController
@RequestMapping(value= "/api")
public class PeopleController implements GetPeopleApi {
	
	@Autowired
	private PeopleService peopleService;

	@Override
	@RequestMapping(value= "/getPeople/{personname}")
	public ResponseEntity<PersonModel> getPerson(String personname) {
		
		try {
			
			Person person = peopleService.findPersonByName(personname);
			PersonModel response = new PersonAdapter(person).createJson();
			return ResponseEntity.ok(response);
			
		}catch(PersonNotFoundException e) {
			throw new PersonNotFoundException(e.getId());
		}
		
	}

	@Override
	@RequestMapping(value= "/getPeople")
	public ResponseEntity<List<PersonModel>> getPeople() {
		List<Person> people = peopleService.getPeople();	
		List<PersonModel> response = people.stream().map(PeopleAdapter::new)
				.map(PeopleAdapter::createJson).collect(Collectors.toList());
	
		return ResponseEntity.ok(response);

	}

}
