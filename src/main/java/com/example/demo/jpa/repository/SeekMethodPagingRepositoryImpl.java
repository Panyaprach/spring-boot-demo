package com.example.demo.jpa.repository;

import com.example.demo.jpa.data.Seek;
import com.example.demo.jpa.data.SeekColumn;
import com.example.demo.jpa.data.SeekImpl;
import com.example.demo.jpa.data.Seekable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class SeekMethodPagingRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements SeekMethodPagingRepository<T, ID> {
    private final JpaEntityInformation<T, ?> entityInformation;
    private final Field seekField;
    private final SingularAttribute<? super T, ?> seekColumn;
    private final EntityManager em;

    public SeekMethodPagingRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;

        Class<T> domainClass = getDomainClass();
        ManagedType<T> managedType = em.getMetamodel().managedType(domainClass);

        seekField = Arrays.stream(domainClass.getDeclaredFields())
                .filter(this::isSeekColumn)
                .findFirst()
                .orElse(null);

        SingularAttribute<? super T, ?> seekAttribute = (seekField == null) ? null
                : managedType.getSingularAttribute(seekField.getName());

        seekColumn = seekAttribute != null ? seekAttribute : entityInformation.getIdAttribute();

        if (seekColumn == null) {
            String error = String.format("Unable to define seek criteria on entity %s, %s not found on declared fields", domainClass, SeekColumn.class);
            throw new IllegalStateException(error);
        }
    }

    @Override
    public Seek<T> findAll(Seekable seekable) {
        return findAll(null, Sort.unsorted(), seekable);
    }

    @Override
    public Seek<T> findAll(Sort sort, Seekable seekable) {
        return findAll(null, sort, seekable);
    }

    @Override
    public Seek<T> findAll(Specification<T> specification, Seekable seekable) {
        return findAll(specification, Sort.unsorted(), seekable);
    }

    @Override
    public Seek<T> findAll(Specification<T> specification, Sort sort, Seekable seekable) {
        Specification<T> seekableSpec = getSpec(seekable, specification);
        Sort seekableSort = getSort(seekable, sort);

        TypedQuery<T> query = getQuery(seekableSpec, seekableSort);
        query.setMaxResults(seekable.getPageSize());

        List<T> result = query.getResultList();
        if (result.isEmpty()) {
            return Seek.empty();
        }

        T lastEntity = result.get(result.size() - 1);
        Comparable<?> lastSeen = getLastSeenFromEntity(lastEntity);

        return new SeekImpl<>(result, seekable.next(lastSeen));
    }

    private Comparable<?> getLastSeenFromEntity(T entity) {
        if (seekField == null) {
            return (Comparable<?>) entityInformation.getId(entity);
        } else {
            try {
                seekField.setAccessible(true);
                return (Comparable<?>) seekField.get(entity);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }

    private Sort getSort(Seekable seekable, Sort sort) {

        if (sort.isUnsorted() || sort.isEmpty()) {

            return Sort.by(seekable.direction(), seekColumn.getName());
        } else {

            return sort.getOrderFor(seekColumn.getName()) == null
                    ? sort.and(Sort.by(seekable.direction(), seekColumn.getName()))
                    : sort;
        }
    }

    private Specification<T> getSpec(Seekable seekable, Specification<T> specification) {
        if (seekable.isUnseek() || !seekable.hasPrevious()) {
            return where(specification);
        }

        Specification<T> seekCondition = (root, query, builder) -> seekable.asc()
                ? builder.greaterThan(root.get(seekColumn.getName()), seekable.lastSeen())
                : builder.lessThan(root.get(seekColumn.getName()), seekable.lastSeen());

        return where(seekCondition).and(specification);
    }

    private boolean isSeekColumn(Field field) {
        return field.isAnnotationPresent(SeekColumn.class);
    }
}
