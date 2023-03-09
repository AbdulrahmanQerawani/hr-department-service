package com.infinity.department.repository;

import com.infinity.department.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findById(Long id);

    List<Department> findAll();

    default List<Department> findByOrganizationId(Long organizationId) {
        final List<Department> departmentList = findAll();
            return departmentList
                    .stream()
                    .filter(department -> department.getId().equals(organizationId)).toList();
    }
}


