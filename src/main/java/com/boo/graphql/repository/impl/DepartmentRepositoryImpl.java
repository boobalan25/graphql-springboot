package com.boo.graphql.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.boo.graphql.entities.Department;
import com.boo.graphql.repository.DepartmentRepository;

@Controller
public class DepartmentRepositoryImpl {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@SchemaMapping(typeName = "Mutation", value = "createDepartment")
	public Department createDepartment(@Argument Department department) {
		return departmentRepository.save(department);
	}
	
	@SchemaMapping(typeName = "Query", value = "findAllDepartments")
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}
}