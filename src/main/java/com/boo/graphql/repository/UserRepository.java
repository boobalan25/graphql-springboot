package com.boo.graphql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.boo.graphql.entities.User;

@Service
public interface UserRepository extends MongoRepository<User, String> {

}
