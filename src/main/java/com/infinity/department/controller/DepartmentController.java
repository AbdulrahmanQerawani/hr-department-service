package com.infinity.department.controller;

import com.infinity.department.client.EmployeeClient;
import com.infinity.department.client.GatewayClientService;
import com.infinity.department.model.Department;
import com.infinity.department.model.Employee;
import com.infinity.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private static final Logger LOGGER = log;

    private final DepartmentService departmentService;
    private EmployeeClient employeeClient;
    private final GatewayClientService clientService;

    @GetMapping("/")
    public List<Department> findAll() {
        LOGGER.debug("find all Departments");
        return departmentService.findAll();
    }

    @GetMapping("/{departmentId}")
    public Department findById(@PathVariable("departmentId") Long id) {
        LOGGER.debug("find Department with specified id={}", id);
        return departmentService.findById(id);
    }

    /**
     * Get All Departments for specified organization
     *
     * @param organizationId specified organization ID
     * @return {@return List} of departments
     */
    @GetMapping("/api/v2/organization/{organizationId}")
    public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.debug("find Department with specified organization id -> organizationId={}", organizationId);
        return departmentService.findByOrganizationId(organizationId);
    }


    /**
     * Get All Employees for specified organization
     *
     * @param organizationId specified organization ID
     * @return {@return List} of departments contains list of employees
     * @apiNote This method receives Down Stream calls from the organization service
     * and sends Down Stream request to the employee service
     */
    @GetMapping("/api/v2/organization/{organizationId}/with-employees")
    public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId) {
        LOGGER.debug("Department find: organizationId={}", organizationId);
        List<Department> departmentList = departmentService.findByOrganizationId(organizationId);
        List<Employee> employeeList = clientService.findAllEmployees(organizationId);
        departmentList.forEach(
                department -> {
                    List<Employee> departmentEmployees = employeeList.stream().filter(
                                    employee -> department.getId().equals(employee.getDepartmentId()))
                            .collect(Collectors.toList());
                    department.setEmployees(departmentEmployees);
                });
        return departmentList;
    }

    @PostMapping("/")
    public Department add(@RequestBody Department department) {
        LOGGER.debug("add department, request body -> {}", department);
        return departmentService.add(department);
    }

    @PutMapping("/{departmentId}")
    public Department update(@PathVariable Long departmentId,
                          @RequestBody Department department) {
        LOGGER.debug("Update department, request body -> {}", department);
        return departmentService.update(departmentId,department);
    }


}
