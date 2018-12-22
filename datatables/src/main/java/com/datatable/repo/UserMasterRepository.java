package com.datatable.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datatable.model.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
	Page<UserMaster> findByFirstnameContainsOrLastnameContainsAllIgnoreCase(String firstname, String lastname, Pageable pageReguest);
	
	
}
