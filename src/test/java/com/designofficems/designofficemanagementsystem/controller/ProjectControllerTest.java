package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.project.CreateProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.project.ProjectMapper;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import com.designofficems.designofficemanagementsystem.repository.ProjectRepository;
import com.designofficems.designofficemanagementsystem.service.AssignProjectService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AssignProjectService assignProjectService;

    private Department department;


    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        this.department = createDepartment();
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

    private Project createProject(String name, BigDecimal budget, String desc) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(desc);
        project.setBudget(budget);
        return projectRepository.save(project);
    }

    @Test
    void shouldGetProjects() throws Exception {
        Project project1 = createProject("S19DB", BigDecimal.valueOf(6500000), "Dobrzyniewo-Bialystok");
        Project project2 = createProject("S19PH", BigDecimal.valueOf(5000000), "Ploski-Hacki");

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<ProjectDTO> receivedProjectDTOs = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProjectDTO>>() {});

        assertThat(receivedProjectDTOs.size()).isEqualTo(2);
        assertThat(receivedProjectDTOs.get(0)).isEqualTo(ProjectMapper.mapToProjectDTO(project1));
    }

    @Test
    void shouldAddProject() throws Exception {
        CreateProjectDTO project = CreateProjectDTO.builder()
                .name("DK8")
                .budget(BigDecimal.valueOf(1500000))
                .build();
        String searchByParam = "K8";
        MvcResult postMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/projects")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        ProjectDTO receivedProjectDTO = objectMapper.readValue(postMvcResult.getResponse().getContentAsString(),
                ProjectDTO.class);
        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/param")
                        .headers(getAuthorizedHeader())
                        .param("searchBy", searchByParam))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<ProjectDTO> fetchedProjectDTOs = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProjectDTO>>() {
                });
        ProjectDTO projectDTO = fetchedProjectDTOs.stream().findFirst().orElseThrow();

        assertThat(projectDTO.getName()).isEqualTo(project.getName());
        assertThat(projectDTO.getBudget()).isEqualTo(project.getBudget());
    }





}