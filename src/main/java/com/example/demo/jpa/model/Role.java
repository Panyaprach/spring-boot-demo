package com.example.demo.jpa.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    /* ManyToMany map back example
        @ManyToMany(mappedBy = "roles")
        private List<User> users;
    */
    public Role(String name) {
        this.name = name;
    }
}
