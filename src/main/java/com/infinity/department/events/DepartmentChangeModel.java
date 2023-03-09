package com.infinity.department.events;

public record DepartmentChangeModel(String type, String action, Long departmentId, String correlationId) {
}

