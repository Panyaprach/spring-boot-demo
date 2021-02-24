package com.example.demo.model;

import com.example.demo.model.binding.View;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.User.class)
    private String id;

    @JsonView(View.User.class)
    @Column(nullable = false, length = 100)
    private String name;

    @JsonView(View.User.class)
    private Category category;

    @ElementCollection
    @CollectionTable
    @Column(name = "value")
    @Builder.Default
    @JsonView(View.User.class)
    private List<String> tags = new ArrayList<>();

    @CreatedBy
    @JsonView(View.Admin.class)
    @Column(name = "created_by", updatable = false, nullable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @JsonView(View.Admin.class)
    @Column(name = "modified_by", nullable = false, length = 100)
    private String modifiedBy;

    @Basic
    @CreatedDate
    @JsonView(View.Admin.class)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Basic
    @LastModifiedDate
    @JsonView(View.Admin.class)
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
