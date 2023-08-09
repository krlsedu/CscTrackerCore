package com.csctracker.repository;

import com.csctracker.model.ServiceError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceErrorRepository extends JpaRepository<ServiceError, Long> {
}
