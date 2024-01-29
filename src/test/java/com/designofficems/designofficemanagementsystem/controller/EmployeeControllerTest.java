package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import com.designofficems.designofficemanagementsystem.repository.UserRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;


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

    @Test
    void shouldGetEmployee() throws Exception {
        Employee employee = createEmployee();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        EmployeeDTO receivedEmployee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EmployeeDTO.class);
        assertThat(receivedEmployee).isNotNull();
        assertThat(receivedEmployee.getDepartmentId()).isEqualTo(department.getId());
    }




}