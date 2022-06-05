package com.example.demo.jpa.repository;

import com.example.demo.jpa.data.Seek;
import com.example.demo.jpa.data.Seekable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@NoRepositoryBean
public interface SeekMethodPagingRepository<T, ID> extends JpaRepository<T, ID> {
    Seek<T> findAll(@NonNull Seekable seekable);

    Seek<T> findAll(@NonNull Sort sort, @NonNull Seekable seekable);

    Seek<T> findAll(@Nullable Specification<T> specification, @NonNull Seekable seekable);

    Seek<T> findAll(@Nullable Specification<T> specification, @NonNull Sort sort, @NonNull Seekable seekable);
}
