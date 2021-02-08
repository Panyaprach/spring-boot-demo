package com.example.demo.model;

import com.example.demo.model.Identity.Permission;
import com.example.demo.model.Identity.Privilege;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Identity.class)
public abstract class Identity_ {

	public static volatile SingularAttribute<Identity, Date> createdAt;
	public static volatile SingularAttribute<Identity, String> password;
	public static volatile SingularAttribute<Identity, String> createdBy;
	public static volatile ListAttribute<Identity, Permission> permissions;
	public static volatile SingularAttribute<Identity, Date> modifiedAt;
	public static volatile SingularAttribute<Identity, String> name;
	public static volatile SingularAttribute<Identity, String> modifiedBy;
	public static volatile SingularAttribute<Identity, String> id;
	public static volatile SingularAttribute<Identity, Privilege> privilege;

	public static final String CREATED_AT = "createdAt";
	public static final String PASSWORD = "password";
	public static final String CREATED_BY = "createdBy";
	public static final String PERMISSIONS = "permissions";
	public static final String MODIFIED_AT = "modifiedAt";
	public static final String NAME = "name";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String ID = "id";
	public static final String PRIVILEGE = "privilege";

}

