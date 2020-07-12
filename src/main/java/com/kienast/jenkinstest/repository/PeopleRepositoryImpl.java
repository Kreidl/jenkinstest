package com.kienast.jenkinstest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

import com.kienast.jenkinstest.exception.PersonNotFoundException;
import com.kienast.jenkinstest.model.Person;

@Component
public class PeopleRepositoryImpl implements PeopleRepository {

	private static List<Person> people; 
	
	private void initializeDummyData() {
		
		people = new ArrayList<>();
		Person person1 = new Person("Test1");
		Person person2 = new Person("Test2");
		Person person3 = new Person("Test3");
		
		
		people.add(person1);
		people.add(person2);
		people.add(person3);

	}
	
	@Override
	public Person findPerson(String personName) {
		initializeDummyData();
		return people.stream().filter(findByName(personName)).findFirst()
				.orElseThrow(() -> new PersonNotFoundException(personName));
	}
	
	private Predicate<Person> findByName(String personName) {
		return item -> item.getName().equals(personName);
	}

	@Override
	public List<Person> getPeople() {
		initializeDummyData();
		return people.stream().collect(Collectors.toList());
	}

}
