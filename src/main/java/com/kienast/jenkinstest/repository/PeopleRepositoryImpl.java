package com.kienast.jenkinstest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.kienast.jenkinstest.exception.PersonNotFoundException;
import com.kienast.jenkinstest.model.Person;

@Service
public class PeopleRepositoryImpl implements PeopleRepository {

	private List<Person> people = new ArrayList<>();
	
	@PostConstruct
	private void initalizeDummyData() {
		Person person1 = new Person("Test1");
		Person person2 = new Person("Test2");
		Person person3 = new Person("Test3");
		
		
		people.add(person1);
		people.add(person2);
		people.add(person3);

	}
	
	@Override
	public Person findPerson(String personName) {
		return people.stream().filter(findByName(personName)).findFirst()
				.orElseThrow(() -> new PersonNotFoundException(personName));
	}
	
	private Predicate<Person> findByName(String personName) {
		return item -> item.getName().equals(personName);
	}

	@Override
	public List<Person> getPeople() {
		return people.stream().collect(Collectors.toList());
	}

}
