package hexlet.code.service.filter;

import hexlet.code.dto.TaskFilterSearchParameters;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.ArrayList;

@AllArgsConstructor
public class TaskFilterSearchParametersSpecification implements Specification<Task> {

    private final TaskFilterSearchParameters parameters;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (parameters.getAssigneeId() != null) {
            Join<Task, User> tasksUsers = root.join("user");
            predicates.add(criteriaBuilder.equal(tasksUsers.get("id"), parameters.getAssigneeId()));
        }

        if (parameters.getStatus() != null) {
            Join<Task, TaskStatus> tasksTaskStatuses = root.join("taskStatus");
            predicates.add(criteriaBuilder.equal(tasksTaskStatuses.get("slug"), parameters.getStatus()));
        }

        if (parameters.getLabelId() != null) {
            Join<Task, Label> tasksLabels = root.join("labels");
            predicates.add(criteriaBuilder.equal(tasksLabels.get("id"), parameters.getLabelId()));
        }

        if (parameters.getTitleCont() != null) {
            predicates.add(criteriaBuilder.like(root.<String>get("name"), "%" + parameters.getTitleCont() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
