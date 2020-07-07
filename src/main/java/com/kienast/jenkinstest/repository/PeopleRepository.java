package com.kienast.jenkinstest.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kienast.jenkinstest.model.Person;

public interface PeopleRepository {

	Person findPerson(String personName);
	
	List<Person> getPeople();
}
