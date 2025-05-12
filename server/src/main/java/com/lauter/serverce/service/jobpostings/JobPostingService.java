package com.lauter.serverce.service.jobpostings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.lauter.serverce.model.jobpostings.JobPosting;
import com.lauter.serverce.repository.jobpostings.JobPostingRepository;

@org.springframework.stereotype.Service
public class JobPostingService {

	@Autowired
	private JobPostingRepository jobPostRepository;

	public List<JobPosting> getAllServices() {
		return jobPostRepository.findAll();
	}

	public Optional<JobPosting> getServiceById(UUID id) {
		return jobPostRepository.findById(id);
	}

	public JobPosting createService(JobPosting jobPosting) {
		return jobPostRepository.save(jobPosting);
	}

	public JobPosting updateService(UUID id, JobPosting jobPosting) {
		if (jobPostRepository.existsById(id)) {
			jobPosting.setId(id);
			return jobPostRepository.save(jobPosting);
		} else {
			throw new RuntimeException("Service not found");
		}
	}

	public void deleteService(UUID id) {
		if (jobPostRepository.existsById(id)) {
			jobPostRepository.deleteById(id);
		} else {
			throw new RuntimeException("Service not found");
		}
	}
}
