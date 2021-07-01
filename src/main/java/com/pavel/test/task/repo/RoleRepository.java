package com.pavel.test.task.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavel.test.task.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
