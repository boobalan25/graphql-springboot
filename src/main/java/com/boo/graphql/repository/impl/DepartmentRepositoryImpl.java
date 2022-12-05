package com.boo.graphql.repository.impl;

import java.util.List;
import java.util.Map;

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
	
	@SchemaMapping(typeName = "Query", value = "findDepartmentById")
	public Department findDepartmentById(@Argument String id) {
		return departmentRepository.findById(id).orElse(null);
	}
	
	public Department resolveDepartmentReference(Map<String, Object> reference) {
        if (reference.get("id") instanceof String fooId) {
            return findDepartmentById(fooId);
        } else {
            return null;
        }
    }
}
