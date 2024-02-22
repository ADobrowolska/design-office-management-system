package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.department.DepartmentDTO;
import com.designofficems.designofficemanagementsystem.dto.department.DepartmentEmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.department.DepartmentMapper;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DepartmentControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    private Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);
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

    @Test
    void shouldGetDepartments() throws Exception {
        Department dept1 = createDepartment("Road");
        Department dept2 = createDepartment("Bridge");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        List<DepartmentDTO> departmentDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<DepartmentDTO>>() {
                });
        assertThat(departmentDTOs).isNotNull();
        assertThat(departmentDTOs.size()).isEqualTo(2);
    }

    @Test
    void shouldGetDepartmentsByParam() throws Exception {
        Department dept1 = createDepartment("Road");
        Department dept2 = createDepartment("Bridge");
        String param = "idg";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/departments/param")
                        .param("searchBy", param)
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<DepartmentDTO> departmentDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<DepartmentDTO>>() {
        });
        assertThat(departmentDTOs.get(0).getName()).isEqualTo(dept2.getName());
        assertThat(departmentDTOs.size()).isEqualTo(1);
        assertTrue(departmentDTOs.contains(DepartmentMapper.mapToDepartmentDTO(dept2)));
    }

    @Test
    void shouldGetDepartmentByIdWithEmployees() throws Exception {
        Department dept = createDepartment("Road");
        Employee emp1 = createEmployee("Anna", "Nowak", dept);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/departments/" + dept.getId() + "/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        DepartmentEmployeeDTO receivedDept = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                DepartmentEmployeeDTO.class);
        assertThat(receivedDept.getEmployees().size()).isEqualTo(1);
        assertThat(receivedDept.getName()).isEqualTo("Road");
    }

    @Test
    void shouldGetDepartmentsWithEmployees() throws Exception {
        Department dept1 = createDepartment("Road");
        Department dept2 = createDepartment("Geodesy");
        Employee employee = createEmployee("Anna", "Nowak", dept1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/departments/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<DepartmentEmployeeDTO> receivedDepartments = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<DepartmentEmployeeDTO>>() {
                });
        assertThat(receivedDepartments.size()).isEqualTo(2);
        assertThat(receivedDepartments.get(0).getEmployees()).isNotNull();
        assertThat(receivedDepartments.get(1).getEmployees().size()).isEqualTo(0);
    }






}