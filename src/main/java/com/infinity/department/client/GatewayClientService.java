package com.infinity.department.client;

import com.infinity.department.model.Department;
import com.infinity.department.model.Employee;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GatewayClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayClientService.class);
    private static final String AUTHORIZATION = "Authorization";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${infinity.gateway.baseUrl}")
    private String baseUrl = "";

    /**
     * find All employees with specified Organization ID
     *
     * @param organizationId specified ID
     * @return List of employees
     * @apiNote this method sends down stream call to employee-service throw gateway server
     */
    public List<Employee> findAllEmployees(@NotNull Long organizationId) {
        List<Employee> employeeList = doRestExchange(baseUrl + "/employee/organization/{organizationId}", organizationId);
        return employeeList;
    }

    private <T> List<T> doRestExchange(@NotNull String clientUrl, Long params) {
        /*** get Authentication token */
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Bearer " + jwt.getTokenValue());
        LOGGER.info("down stream call url:{}", clientUrl);
        ParameterizedTypeReference<ResponseEntity<T>> responseType = new ParameterizedTypeReference<>() {};
        try {
            ResponseEntity<T> response = restTemplate.exchange(clientUrl, HttpMethod.GET, new HttpEntity<>(headers), responseType, params).getBody();
            return (List<T>) response.getBody();
        } catch (ResourceAccessException e) {
            LOGGER.error("Failed to access resource: " + e.getMessage(), e);
            ResponseEntity.internalServerError().build();
        }
        return new ArrayList<>();
    }
}
