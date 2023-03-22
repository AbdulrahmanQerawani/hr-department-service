package com.infinity.department.service;


import com.infinity.department.events.DepartmentChangeModel;
import com.infinity.department.model.Department;
import com.infinity.department.repository.DepartmentRepository;
import com.infinity.department.utils.UserContextHolder;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = log;
    private final DepartmentRepository departmentRepository;
    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.source}")
    String outputBindingName;
    private String correlationId = UserContextHolder.getContext().getCorrelationId();

    @Override
    public List<Department> findAll() {
        List<Department> departmentList = departmentRepository.findAll();
        if (departmentList.isEmpty()) {
            LOGGER.info("No departments found");
        }
        return departmentList;
    }

    @Override
    public Department findById(Long id) {
        LOGGER.info("find department with id: {}", id);
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            LOGGER.debug("Sending Kafka message for GET Department id:{}, correlationId={}", department.get().getId(), correlationId);
            DepartmentChangeModel changeModel = new DepartmentChangeModel(DepartmentChangeModel.class.getTypeName(), "GET", department.get().getId(), correlationId);
            streamBridge.send(outputBindingName, changeModel);
            return department.get();
        } else {
            LOGGER.info("department with id: {}, not found", id);
        }
        return null;
    }

    @Override
    public Department add(@NotNull Department department) {
        LOGGER.info("insert new department with id->{}", department.getName());
        assert (!(department.getName() == null) && !department.getName().equals("")) : "department id cannot be null";
        department = departmentRepository.save(department);
        LOGGER.debug("Sending Kafka message for Create Department id:{}, correlationId={}", department.getId(), correlationId);
        streamBridge.send(outputBindingName, new DepartmentChangeModel(DepartmentChangeModel.class.getTypeName(), "SAVE", department.getId(), correlationId));
        return department;

    }

    @Override
    public Department update(Long id, Department department) {
        LOGGER.info("update department with id:->{}", id);
        Department existDepartment = departmentRepository.findById(id).orElse(null);
        if (existDepartment == null) {
            return null;
        }
        existDepartment.setName(department.getName());
        department = departmentRepository.save(existDepartment);
        LOGGER.debug("Sending Kafka message for Update Department id:{}, correlationId={}", department.getId(), correlationId);
        streamBridge.send(outputBindingName, new DepartmentChangeModel(DepartmentChangeModel.class.getTypeName(), "UPDATE", department.getId(), correlationId));
        return department;
    }

    @Override
    public List<Department> findByOrganizationId(Long organizationId) {
        List<Department> departmentList = departmentRepository.findByOrganizationId(organizationId);
        return departmentList;
    }

}
