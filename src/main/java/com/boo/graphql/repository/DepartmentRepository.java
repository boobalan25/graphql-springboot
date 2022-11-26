package com.boo.graphql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.boo.graphql.entities.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {
	
}
