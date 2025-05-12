package com.lauter.serverce.model.jobpostings;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "job_postings")
@Data
@Schema(description = "Represents a job post in the system")
public class JobPosting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	@Schema(description = "The unique identifier of the job post")
	private UUID id;

	@Schema(description = "The name of the job post")
	private String name;

	public JobPosting() {
	}

	public JobPosting(String name) {
		this.name = name;
	}
}
