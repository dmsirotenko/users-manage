package com.sirotenkod.test.usersmanage.repository.specification;

import com.sirotenkod.test.usersmanage.dao.UserDAO;
import com.sirotenkod.test.usersmanage.dto.UserDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class UserDAOSpecification implements Specification<UserDAO> {
    private UserDTO filter;

    public UserDAOSpecification(UserDTO filter) {
        super();

        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<UserDAO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = builder.disjunction();

        if (Objects.nonNull(filter.getId())) {
            predicate.getExpressions()
                    .add(builder.equal(root.get("id"), filter.getId()));
        }

        if (Objects.nonNull(filter.getName())) {
            predicate.getExpressions()
                    .add(builder.like(
                            builder.lower(root.get("name")),
                            builder.lower(builder.literal("%" + filter.getName() + "%"))
                    ));
        }

        if (Objects.nonNull(filter.getAge())) {
            predicate.getExpressions()
                    .add(builder.equal(root.get("age"), filter.getAge()));
        }

        return predicate;
    }
}
