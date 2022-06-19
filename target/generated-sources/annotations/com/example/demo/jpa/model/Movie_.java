package com.example.demo.jpa.model;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Movie.class)
public abstract class Movie_ {

	public static volatile SingularAttribute<Movie, Instant> createdAt;
	public static volatile SingularAttribute<Movie, String> createdBy;
	public static volatile SingularAttribute<Movie, Instant> modifiedAt;
	public static volatile SingularAttribute<Movie, String> name;
	public static volatile SingularAttribute<Movie, String> modifiedBy;
	public static volatile SingularAttribute<Movie, String> id;
	public static volatile SingularAttribute<Movie, Category> category;
	public static volatile ListAttribute<Movie, String> tags;

	public static final String CREATED_AT = "createdAt";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_AT = "modifiedAt";
	public static final String NAME = "name";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String TAGS = "tags";

}

