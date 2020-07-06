package com.kienast.jenkinstest.command;

import java.util.List;

import com.kienast.jenkinstest.model.Person;
import com.kienast.jenkinstest.repository.PeopleRepository;

public class GetPeople {
	private PeopleRepository peopleRepository;

	public GetPeople(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	public List<Person> getPeople() {
		return peopleRepository.getPeople();
	}
}
