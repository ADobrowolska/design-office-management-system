package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateDTO;
import com.designofficems.designofficemanagementsystem.dto.employeerate.EmployeeRateMapper;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import com.designofficems.designofficemanagementsystem.util.CategoryType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class EmployeeRateControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRateRepository employeeRateRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        this.department = createDepartment();
    }

    private Department createDepartment() {
        Department department = new Department();
        department.setName("Bridge");
        return departmentRepository.save(department);
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Anna");
        employee.setLastName("Nowak");
        employee.setDepartment(department);
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

    @Test
    void shouldGetEmployeeRate() throws Exception {
        Employee employee = createEmployee();
        EmployeeRate employeeRate = createEmployeeRate("AnnaNowak_SALARY",
                CategoryType.SALARY, 100, "PLN", employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/rate")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<EmployeeRateDTO> employeeRateDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<EmployeeRateDTO>>() {
        });

        assertThat(employeeRateDTOs.size()).isEqualTo(1);
        assertThat(employeeRateDTOs.get(0)).isEqualTo(EmployeeRateMapper.mapToEmployeeRateDTO(employeeRate));
    }

    @Test
    void shouldGetEmployeeRateEmployeeId() throws Exception {
        Employee employee = createEmployee();
        EmployeeRate employeeRate1 = createEmployeeRate("AnnaNowak_SALARY",
                CategoryType.SALARY, 100, "PLN", employee);
        EmployeeRate employeeRate2 = createEmployeeRate("AnnaNowak_LICENCE",
                CategoryType.LICENCE, 5, "PLN", employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}/rate", employee.getId())
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<EmployeeRateDTO> employeeRateDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<EmployeeRateDTO>>() {
        });

        assertThat(employeeRateDTOs.size()).isEqualTo(2);
        assertThat(employeeRateDTOs.get(0).getName()).isEqualTo("AnnaNowak_SALARY");
        assertThat(employeeRateDTOs.get(1).getName()).isEqualTo("AnnaNowak_LICENCE");


    }





}