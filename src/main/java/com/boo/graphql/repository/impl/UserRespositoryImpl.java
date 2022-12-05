package com.boo.graphql.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.boo.graphql.entities.User;
import com.boo.graphql.repository.UserRepository;

@Controller
public class UserRespositoryImpl {

	@Autowired
	private UserRepository userRepository;
	
	@SchemaMapping(typeName = "Mutation", value = "createUser")
	public User createUser(@Argument User user) {
		return userRepository.save(user);
	}
	
	@SchemaMapping(typeName = "Query", value = "findAllUsers")
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@SchemaMapping(typeName = "Query", value = "findUserById")
	public User findUserById(@Argument String id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User resolveUserReference(Map<String, Object> reference) {
        if (reference.get("id") instanceof String fooId) {
            return findUserById(fooId);
        } else {
            return null;
        }
    }
}
