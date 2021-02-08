package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "identities")
@EntityListeners(AuditingEntityListener.class)
public class Identity {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Privilege privilege;

    @Column(name = "permission_id")
    @ElementCollection(targetClass = Permission.class)
    @CollectionTable( joinColumns = @JoinColumn(name = "identity_id"))
    private List<Permission> permissions;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false, length = 100)
    private String modifiedBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Date modifiedAt;

    public enum Permission {
        READ, WRITE;

        public boolean canWrite() {
            return this.equals(WRITE);
        }
    }

    public enum Privilege {
        SILVER, GOLD, PLATINUM
    }
}
