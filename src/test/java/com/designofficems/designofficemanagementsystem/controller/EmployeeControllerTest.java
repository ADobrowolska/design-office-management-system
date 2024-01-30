package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeDTO;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
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
import org.springframework.http.MediaType;
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
        assertThat(receivedEmployee.getFirstName()).isEqualTo("Anna");
    }

    @Test
    void shouldAddEmployee() throws Exception {
        EmployeeDTO newEmployee = EmployeeDTO.builder()
                .firstName("Jan")
                .lastName("kowalski")
                .departmentId(department.getId())
                .build();

        MvcResult postMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeDTO receivedEmployee = objectMapper.readValue(postMvcResult.getResponse().getContentAsString(), EmployeeDTO.class);

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeDTO fetchedEmployee = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(), EmployeeDTO.class);

        assertThat(fetchedEmployee).isNotNull();
        assertThat(fetchedEmployee.getFirstName()).isEqualTo(newEmployee.getFirstName());
        assertThat(fetchedEmployee.getDepartmentId()).isEqualTo(newEmployee.getDepartmentId());
    }

    @Test
    void shouldEditEmployee() throws Exception {
        Employee employee = createEmployee();
        EmployeeDTO employeeToEdit = EmployeeMapper.mapToEmployeeDTO(employee);
        employeeToEdit.setFirstName("Katarzyna");

        MvcResult putMvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToEdit))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeDTO receivedEmployee = objectMapper.readValue(putMvcResult.getResponse().getContentAsString(), EmployeeDTO.class);

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        EmployeeDTO fetchedEmployee = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(), EmployeeDTO.class);

        assertThat(fetchedEmployee.getFirstName()).isEqualTo(employeeToEdit.getFirstName());


    }








}