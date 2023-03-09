package com.infinity.department.service;

import com.infinity.department.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(Long id);
    Department add(Department department);

    Department update(Long id, Department department);
    List<Department> findByOrganizationId(Long id);
    
}
