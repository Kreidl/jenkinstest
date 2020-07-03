package com.kienast.jenkinstest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kienast.jenkinstest.rest.api.GetPeopleApi;
import com.kienast.jenkinstest.rest.api.GetPersonApi;
import com.kienast.jenkinstest.rest.api.model.PersonModel;

@RestController
@RequestMapping(value= "/api")
public class PeopleController implements GetPeopleApi, GetPersonApi {
	
	

	@Override
	@RequestMapping(value= "/getPerson/{personname}")
	public ResponseEntity<PersonModel> getPerson(String personname) {
		PersonModel pm = new PersonModel();
		
		pm.setName(personname);
				
		return ResponseEntity.ok(pm);
	}

	@Override
	@RequestMapping(value= "/getPeople")
	public ResponseEntity<List<PersonModel>> getPeople() {
		List<PersonModel> pmList = new ArrayList<PersonModel>();
		
		pmList.add(new PersonModel());
		pmList.add(new PersonModel());
		
		for (PersonModel pm : pmList) {
			pm.setName("Person in List");
		}
		return ResponseEntity.ok(pmList);
	}

}
