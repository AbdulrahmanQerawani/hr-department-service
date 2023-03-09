package com.infinity.department.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";
    public static final String DEPARTMENT_ID = "tmx-department-id";
    public static final String EMPLOYEE_ID = "tmx-employee-id";

    private String correlationId= "";
    private String authToken= "";
    private String userId = "";
    private String organizationId = "";
    private String departmentId = "";
    private String employeeId = "";

}