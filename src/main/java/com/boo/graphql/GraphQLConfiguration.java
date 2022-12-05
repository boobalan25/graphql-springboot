package com.boo.graphql;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.apollographql.federation.graphqljava.tracing.FederatedTracingInstrumentation;
import com.boo.graphql.entities.Department;
import com.boo.graphql.entities.User;
import com.boo.graphql.repository.impl.DepartmentRepositoryImpl;
import com.boo.graphql.repository.impl.UserRespositoryImpl;

@Configuration
public class GraphQLConfiguration {
	
	@Autowired
	UserRespositoryImpl userRespositoryImpl;
	
	@Autowired
	DepartmentRepositoryImpl departmentRespositoryImpl;

	@Bean
	public FederatedTracingInstrumentation federatedTracingInstrumentation() {
		return new FederatedTracingInstrumentation();
	}

	@Bean
	public GraphQlSourceBuilderCustomizer federationTransform() {
		return builder -> builder.schemaFactory((registry, wiring) -> 
				Federation.transform(registry, wiring)
					.fetchEntities(env -> 
						env.<List<Map<String, Object>>>getArgument(_Entity.argumentName).stream().map(reference -> {
							final String typeName = (String) reference.get("__typename");
							return switch (typeName) {
								case "User" -> userRespositoryImpl.resolveUserReference(reference);
								case "Department" -> departmentRespositoryImpl.resolveDepartmentReference(reference);
								default -> null;
							};
						}).collect(Collectors.toList())).resolveEntityType(env -> {
							final Object src = env.getObject();
							if (src instanceof User) {
								return env.getSchema().getObjectType("User");
							} else if(src instanceof Department){
								return env.getSchema().getObjectType("Department");
							} else {
								return null;
							}
						}).build());
	}

}
