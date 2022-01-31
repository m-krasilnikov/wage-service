package com.cloudmore.repository;

import com.cloudmore.entity.EmployeeWageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeManagementRepository extends JpaRepository<EmployeeWageEntity, String> {

}
