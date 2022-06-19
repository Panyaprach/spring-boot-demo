package com.example.demo.jpa.model;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Instant> createdAt;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> createdBy;
	public static volatile SingularAttribute<User, Instant> modifiedAt;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> modifiedBy;
	public static volatile SingularAttribute<User, String> id;
	public static volatile SingularAttribute<User, String> username;

	public static final String CREATED_AT = "createdAt";
	public static final String PASSWORD = "password";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_AT = "modifiedAt";
	public static final String ROLES = "roles";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

