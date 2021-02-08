package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder(setterPrefix = "with")
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Movie {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    private Category category;

    @ElementCollection
    @CollectionTable
    @Column(name = "value")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

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
}
