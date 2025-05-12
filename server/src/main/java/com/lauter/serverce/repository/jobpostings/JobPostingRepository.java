package com.lauter.serverce.repository.jobpostings;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lauter.serverce.model.jobpostings.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {
}