package com.kienast.jenkinstest.command;

import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.repository.PeopleRepository;

public class GetPerson {

	private PeopleRepository peopleRepository;
	private String personName;

	public GetPerson(PeopleRepository peopleRepository, String personName) {
		this.peopleRepository = peopleRepository;
		this.personName = personName;
	}

	public Person getPerson() {
		return peopleRepository.findPerson(personName);
	}
}
