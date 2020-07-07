package com.kienast.jenkinstest.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kienast.jenkinstest.model.Person;

public interface PeopleService {

	Person findPersonByName(String personName);
	
	List<Person> getPeople();
}
