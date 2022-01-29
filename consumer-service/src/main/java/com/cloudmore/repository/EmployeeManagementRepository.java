package com.cloudmore.repository;

import com.cloudmore.entity.EmployeeWageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeManagementRepository extends JpaRepository<EmployeeWageEntity, String> {

}
