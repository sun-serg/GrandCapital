package by.sunserg.grandcapital.service.specification;

import by.sunserg.grandcapital.repository.entity.EmailData;
import by.sunserg.grandcapital.repository.entity.PhoneData;
import by.sunserg.grandcapital.repository.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    private UserSpecification() {
    }

    public static Specification<User> userFilters(LocalDate dateOfBirth, String phone, String name, String email) {
        return (root, query, cb) -> {
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            if (dateOfBirth != null) {
                predicates.add(cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(root.get("name"), name + "%"));
            }

            if (phone != null && !phone.isEmpty()) {
                Join<User, PhoneData> phoneJoin = root.join("phoneData", JoinType.LEFT);
                predicates.add(cb.equal(phoneJoin.get("phone"), phone));
            }

            if (email != null && !email.isEmpty()) {
                Join<User, EmailData> emailJoin = root.join("emailData", JoinType.LEFT);
                predicates.add(cb.equal(emailJoin.get("email"), email));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
