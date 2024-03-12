package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.cost.CostDTO;
import com.designofficems.designofficemanagementsystem.dto.cost.CostRequestDTO;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.*;
import com.designofficems.designofficemanagementsystem.util.CategoryType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CostControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRateRepository employeeRateRepository;


    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    private Department createDepartment() {
        Department department = new Department();
        department.setName("Geodesy");
        return departmentRepository.save(department);
    }

    private Employee createEmployee(String firstName, String lastName, Department dept) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(dept);
        employee.setUser(getCurrentUser());
        return employeeRepository.save(employee);
    }

    private EmployeeRate createEmployeeRate(String name, CategoryType categoryType, double rate,
                                            String currency, Employee employee) {
        EmployeeRate employeeRate = new EmployeeRate();
        employeeRate.setName(name);
        employeeRate.setCategory(categoryType);
        employeeRate.setRate(rate);
        employeeRate.setCurrency(currency);
        employeeRate.setEmployee(employee);
        return employeeRateRepository.save(employeeRate);
    }

    private Project createProject(String name, BigDecimal budget, String desc) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(desc);
        project.setBudget(budget);
        return projectRepository.save(project);
    }

    @Test
    void shouldAddCost() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Anna", "Nowak", dept);
        Project project = createProject("DK8", BigDecimal.valueOf(3350200.20), "droga krajowa");
        EmployeeRate employeeRate1 = createEmployeeRate("AN_SALARY", CategoryType.SALARY, 50, "PLN", employee);
        EmployeeRate employeeRate2 = createEmployeeRate("AN_LICENCE", CategoryType.LICENCE, 5, "USD", employee);
        CostRequestDTO costRequest = CostRequestDTO.builder()
                .projectId(project.getId())
                .quantity(240L)
                .occurrenceDate(LocalDate.now())
                .creationDate(LocalDate.now())
                .build();
        MvcResult postMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/costs")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(costRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        LocalDate date = LocalDate.now();
        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/costs/param")
                        .headers(getAuthorizedHeader())
                        .param("date", date.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<CostDTO> fetchedCost = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                new TypeReference<List<CostDTO>>() {
                });

        assertThat(fetchedCost.get(0).getQuantity()).isEqualTo(costRequest.getQuantity());
        assertThat(fetchedCost.size()).isEqualTo(2);
        assertThat(fetchedCost.get(1).getEmployeeRate().getRate()).isEqualTo(5);
    }


}