package com.kienast.jenkinstest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kienast.jenkinstest.command.GetPeople;
import com.kienast.jenkinstest.command.GetPerson;
import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.repository.PeopleRepository;


@Service
public class PeopleServicesImpl implements PeopleService {

	@Autowired
	private PeopleRepository peopleRepository;
	
	@Override
	public Person findPersonByName(String personName) {
		return new GetPerson(peopleRepository, personName).getPerson();
	}

	@Override
	public List<Person> getPeople() {
		return new GetPeople(peopleRepository).getPeople();
	}

}
