package com.maif.taskmanagerplus.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.maif.taskmanagerplus.domain.filter.ProvinceFilter;
import com.maif.taskmanagerplus.domain.model.Province;

public class ProvinceSpecs {

	public static Specification<Province> withFilter(ProvinceFilter filter) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
			
			if (filter.getName() != null) {
				predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
			}
			
			if (filter.getAbbreviation() != null) {
				predicates.add(builder.equal(root.get("abbreviation"), filter.getAbbreviation()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
