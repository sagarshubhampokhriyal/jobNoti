package com.jobhelp.newopening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// Update the import path below if VisaJobEntity is in a different package, or create the class if it does not exist.
import com.jobhelp.newopening.entity.VisaJobEntity;

public interface JobRepository extends JpaRepository<VisaJobEntity, String> {
}