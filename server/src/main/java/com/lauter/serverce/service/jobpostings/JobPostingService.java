package com.lauter.serverce.service.jobpostings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lauter.serverce.model.jobpostings.JobPosting;
import com.lauter.serverce.repository.jobpostings.JobPostingRepository;

@org.springframework.stereotype.Service
public class JobPostingService {

	private static final Logger logger = LoggerFactory.getLogger(JobPostingService.class);

	@Autowired
	private JobPostingRepository jobPostRepository;

	public List<JobPosting> getAllServices() {
		logger.info("Fetching all job postings...");
		List<JobPosting> jobPostings = jobPostRepository.findAll();
		if (jobPostings.isEmpty()) {
			logger.warn("No job postings found.");
		} else {
			logger.info("Successfully fetched {} job postings.", jobPostings.size());
		}
		return jobPostings;
	}

	public Optional<JobPosting> getServiceById(UUID id) {
		logger.info("Fetching job posting by ID: {}", id);
		Optional<JobPosting> jobPosting = jobPostRepository.findById(id);
		if (jobPosting.isPresent()) {
			logger.info("Successfully fetched job posting with ID: {}", id);
		} else {
			logger.warn("Job posting with ID: {} not found.", id);
		}
		return jobPosting;
	}

	public JobPosting createService(JobPosting jobPosting) {
		logger.info("Creating a new job posting with name: {}", jobPosting.getName());
		JobPosting savedJobPosting = jobPostRepository.save(jobPosting);
		logger.info("Job posting created with ID: {}", savedJobPosting.getId());
		return savedJobPosting;
	}

	public JobPosting updateService(UUID id, JobPosting jobPosting) {
		logger.info("Updating job posting with ID: {}", id);
		if (jobPostRepository.existsById(id)) {
			jobPosting.setId(id);
			JobPosting updatedJobPosting = jobPostRepository.save(jobPosting);
			logger.info("Job posting with ID: {} successfully updated.", id);
			return updatedJobPosting;
		} else {
			logger.error("Job posting with ID: {} not found for update.", id);
			throw new RuntimeException("Job posting not found");
		}
	}

	public void deleteService(UUID id) {
		logger.info("Deleting job posting with ID: {}", id);
		if (jobPostRepository.existsById(id)) {
			jobPostRepository.deleteById(id);
			logger.info("Job posting with ID: {} successfully deleted.", id);
		} else {
			logger.error("Job posting with ID: {} not found for deletion.", id);
			throw new RuntimeException("Job posting not found");
		}
	}
}
