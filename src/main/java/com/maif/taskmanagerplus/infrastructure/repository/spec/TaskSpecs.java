package com.maif.taskmanagerplus.infrastructure.repository.spec;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.maif.taskmanagerplus.domain.filter.TaskFilter;
import com.maif.taskmanagerplus.domain.model.Task;

public class TaskSpecs {

	public static Specification<Task> withFilter(TaskFilter filter) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getTitle() != null) {
				predicates.add(builder.like(root.get("title"), "%" + filter.getTitle() + "%"));
			}
			
			if (filter.getDescription() != null) {
				predicates.add(builder.equal(root.get("description"), filter.getDescription()));
			}
			
			if (filter.getDueDate() != null) {
			    OffsetDateTime dueDate = OffsetDateTime.parse(filter.getDueDate());
			    predicates.add(builder.equal(root.get("dueDate"), dueDate));
			}

			if (filter.getCompleted() != null) {
				predicates.add(builder.equal(root.get("completed"), filter.getCompleted()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
