package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employeerate.CreateEmployeeRateDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Test
    void shouldGetEmployeeRateEmployeeId_AccessDeniedEx() throws Exception {
        Random random = new Random();
        int randomId = random.nextInt(1000) + 1;
        Employee employee = createEmployee();
        EmployeeRate employeeRate1 = createEmployeeRate("AnnaNowak_SALARY",
                CategoryType.SALARY, 100, "PLN", employee);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}/rate", randomId)
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andReturn();
    }

    @Test
    void shouldAddEmployeeRate_CurrencyUSD() throws Exception {
        Employee employee = createEmployee();
        CreateEmployeeRateDTO employeeRate = CreateEmployeeRateDTO.builder()
                .name("AnnaNowak_LICENCE")
                .category(CategoryType.LICENCE)
                .employeeId(employee.getId())
                .rate(5)
                .currency("USD")
                .build();
        MvcResult postMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employee/rate")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRate))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeRateDTO receivedEmployeeRate = objectMapper.readValue(postMvcResult.getResponse().getContentAsString(),
                EmployeeRateDTO.class);

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}/rate", employee.getId())
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeRateDTO fetchedEmployeeRate = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                new TypeReference<List<EmployeeRateDTO>>() {
                }).stream().findFirst().orElseThrow();

        assertThat(receivedEmployeeRate.getName()).isEqualTo(fetchedEmployeeRate.getName());
        assertThat(receivedEmployeeRate.getId()).isEqualTo(fetchedEmployeeRate.getId());
    }

    @Test
    void shouldAddEmployeeRate_CurrencyPLN() throws Exception {
        Employee employee = createEmployee();
        CreateEmployeeRateDTO employeeRate = CreateEmployeeRateDTO.builder()
                .name("AnnaNowak_LICENCE")
                .category(CategoryType.LICENCE)
                .employeeId(employee.getId())
                .rate(5)
                .currency("PLN")
                .build();
        MvcResult postMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employee/rate")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRate))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeRateDTO receivedEmployeeRate = objectMapper.readValue(postMvcResult.getResponse().getContentAsString(),
                EmployeeRateDTO.class);

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}/rate", employee.getId())
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeRateDTO fetchedEmployeeRate = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                new TypeReference<List<EmployeeRateDTO>>() {
                }).stream().findFirst().orElseThrow();

        assertThat(receivedEmployeeRate.getName()).isEqualTo(fetchedEmployeeRate.getName());
        assertThat(receivedEmployeeRate.getId()).isEqualTo(fetchedEmployeeRate.getId());
    }

    @Test
    void shouldAddEmployeeRate_whenInstanceAlreadyExists() throws Exception {
        Employee employee = createEmployee();
        EmployeeRate employeeRate1 = createEmployeeRate(
                "AnnaNowak_SALARY", CategoryType.SALARY, 20, "USD", employee);
        CreateEmployeeRateDTO employeeRate = CreateEmployeeRateDTO.builder()
                .name("AnnaNowak_SALARY")
                .category(CategoryType.SALARY)
                .employeeId(employee.getId())
                .rate(20)
                .currency("USD")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/rate")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRate))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InstanceAlreadyExistsException))
                .andExpect(result -> assertEquals("Employee rate exists", result.getResolvedException().getMessage()));
    }


}