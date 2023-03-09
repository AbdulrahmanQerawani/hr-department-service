package com.infinity.department.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Employee {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String emailAddress;
    private Long organizationId;
    private Long departmentId;
    private String position;

    public Employee(String firstName, String lastName, String gender, int age, String emailAddress, Long organizationId, Long departmentId, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.emailAddress = emailAddress;
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.position = position;
    }

}