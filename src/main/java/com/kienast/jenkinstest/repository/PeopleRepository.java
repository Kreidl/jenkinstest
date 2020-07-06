package com.kienast.jenkinstest.repository;

import java.util.List;


import com.kienast.jenkinstest.model.Person;

public interface PeopleRepository {

	Person findPerson(String personName);
	
	List<Person> getPeople();
}
