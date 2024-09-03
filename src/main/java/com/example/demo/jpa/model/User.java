package com.example.demo.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roles")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false, length = 100)
    private String modifiedBy;

    @Basic
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Basic
    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    /* Example enum mapping
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Privilege privilege;

    @Column(name = "permission_id")
    @ElementCollection(targetClass = Permission.class)
    @CollectionTable(joinColumns = @JoinColumn(name = "identity_id"))
    private List<Permission> permissions;

    public enum Permission {
        READ, WRITE;

        public boolean canWrite() {
            return this.equals(WRITE);
        }
    }

    public enum Privilege {
        SILVER, GOLD, PLATINUM
    }
     */
}
