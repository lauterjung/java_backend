package com.lauter.serverce.controller.jobpostings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauter.serverce.dto.ErrorResponse;
import com.lauter.serverce.model.jobpostings.JobPosting;
import com.lauter.serverce.service.jobpostings.JobPostingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/job-postings")
@Tag(name = "Job postings")
public class JobPostingController {

	private static final Logger logger = LoggerFactory.getLogger(JobPostingController.class);

	@Autowired
	private JobPostingService jobPostingService;

	@GetMapping
	@Operation(summary = "Get all job postings", description = "Fetches a list of all job postings", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the job postings", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JobPosting.class)))),
			@ApiResponse(responseCode = "204", description = "No job postings found"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<List<JobPosting>> getAllServices() {
		logger.debug("Received GET request for all job postings");
		try {
			List<JobPosting> jobPostings = jobPostingService.getAllServices();
			if (jobPostings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(jobPostings, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occurred while retrieving all job postings", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get job posting by ID", description = "Fetches a single job posting by its ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the job posting", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = JobPosting.class)) }),
			@ApiResponse(responseCode = "404", description = "Job posting not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	public ResponseEntity<JobPosting> getServiceById(
			@Parameter(description = "The unique ID of the job posting") @PathVariable UUID id) {
		logger.debug("Received GET request for job posting with ID: {}", id);
		try {
			Optional<JobPosting> jobPosting = jobPostingService.getServiceById(id);
			return jobPosting.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			logger.error("Error occurred while retrieving job posting with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	@Operation(summary = "Create a new job posting", description = "Adds a new job posting to the system", responses = {
			@ApiResponse(responseCode = "201", description = "Job posting created successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = JobPosting.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid job posting data", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	public ResponseEntity<JobPosting> createService(@RequestBody JobPosting jobPost) {
		logger.debug("Received POST request to create a new job posting: {}", jobPost);
		try {
			JobPosting createdJobPosting = jobPostingService.createService(jobPost);
			return new ResponseEntity<>(createdJobPosting, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error occurred while creating job posting", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a job posting", description = "Updates an existing job posting by ID", responses = {
			@ApiResponse(responseCode = "200", description = "Job posting updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = JobPosting.class)) }),
			@ApiResponse(responseCode = "404", description = "Job posting not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	public ResponseEntity<JobPosting> updateService(@PathVariable UUID id, @RequestBody JobPosting jobPost) {
		logger.debug("Received PUT request to update job posting with ID: {} - Data: {}", id, jobPost);
		try {
			JobPosting updatedJobPosting = jobPostingService.updateService(id, jobPost);
			return new ResponseEntity<>(updatedJobPosting, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occurred while updating job posting with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a job posting", description = "Deletes an existing job posting by ID", responses = {
			@ApiResponse(responseCode = "204", description = "Job posting deleted successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	public ResponseEntity<Void> deleteService(
			@Parameter(description = "The unique ID of the job posting to delete") @PathVariable UUID id) {
		logger.debug("Received DELETE request for job posting with ID: {}", id);
		try {
			jobPostingService.deleteService(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Error occurred while deleting job posting with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
