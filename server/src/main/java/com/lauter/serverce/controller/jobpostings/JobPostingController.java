package com.lauter.serverce.controller.jobpostings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauter.serverce.model.jobpostings.JobPosting;
import com.lauter.serverce.service.jobpostings.JobPostingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/job-postings")
@Tag(name = "Job postings")
public class JobPostingController {

	private static final Logger logger = LoggerFactory.getLogger(JobPostingController.class);

	@Autowired
	private JobPostingService jobPostingService;

	@GetMapping
	@Operation(summary = "Get all job postings", description = "Fetches a list of all job postings")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the job postings"),
			@ApiResponse(responseCode = "404", description = "No job postings found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<JobPosting> getAllServices() {
		logger.debug("Received GET request for all job postings");
		return jobPostingService.getAllServices();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get job posting by ID", description = "Fetches a single job posting by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the job posting"),
			@ApiResponse(responseCode = "404", description = "Job posting not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Optional<JobPosting> getServiceById(@PathVariable UUID id) {
		logger.debug("Received GET request for job posting with ID: {}", id);
		return jobPostingService.getServiceById(id);
	}

	@PostMapping
	@Operation(summary = "Create a new job posting", description = "Adds a new job posting to the system")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Job posting created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid job posting data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public JobPosting createService(@RequestBody JobPosting jobPost) {
		logger.debug("Received POST request to create a new job posting: {}", jobPost);
		return jobPostingService.createService(jobPost);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a job posting", description = "Updates an existing job posting by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Job posting updated successfully"),
			@ApiResponse(responseCode = "404", description = "Job posting not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public JobPosting updateService(@PathVariable UUID id, @RequestBody JobPosting jobPost) {
		logger.debug("Received PUT request to update job posting with ID: {} - Data: {}", id, jobPost);
		return jobPostingService.updateService(id, jobPost);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a job posting", description = "Deletes an existing job posting by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Job posting deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Job posting not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void deleteService(@PathVariable UUID id) {
		logger.debug("Received DELETE request for job posting with ID: {}", id);
		jobPostingService.deleteService(id);
	}
}
