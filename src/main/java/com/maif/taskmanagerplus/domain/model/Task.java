package com.maif.taskmanagerplus.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.maif.taskmanagerplus.core.validation.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Task {

	@NotNull(groups = Groups.TaskId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull
    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean completed = false;

    // This annotation is used to mark a field that represents the moment when the entity was created. When the entity is first persisted in the database, 
    // the value of this field is automatically set to the current timestamp of the creation moment.
    @CreationTimestamp 
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private OffsetDateTime createdAt;

    // This annotation is used to mark a field that represents the moment when the entity was last updated. 
    // The value of this field is automatically updated to the current timestamp whenever the entity is updated in the database.
	@UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private OffsetDateTime updatedAt;
    

    
}