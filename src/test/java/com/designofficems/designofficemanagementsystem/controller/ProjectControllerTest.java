package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.assignproject.AssignProjectDTO;
import com.designofficems.designofficemanagementsystem.dto.employee.EmployeeMapper;
import com.designofficems.designofficemanagementsystem.dto.project.*;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import com.designofficems.designofficemanagementsystem.repository.ProjectRepository;
import com.designofficems.designofficemanagementsystem.service.AssignProjectService;
import com.designofficems.designofficemanagementsystem.service.CostService;
import com.designofficems.designofficemanagementsystem.service.EmployeeRateService;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    private EmployeeRateService employeeRateService;

    @Autowired
    private CostService costService;

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
                new TypeReference<List<ProjectDTO>>() {
                });

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
        assertThat(receivedProjectDTO.getBudget()).isEqualTo(projectDTO.getBudget());
        assertThat(receivedProjectDTO.getName()).isEqualTo(projectDTO.getName());
    }

    @Test
    void shouldEditProject() throws Exception {
        Project project = createProject("S19DB", BigDecimal.valueOf(6500000), "Dobrzyniewo-Bialystok");
        ProjectDTO editedProject = ProjectMapper.mapToProjectDTO(project);
        editedProject.setBudget(BigDecimal.valueOf(5500000));
        String searchByParam = "DB";
        MvcResult putMvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/projects")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedProject))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        ProjectDTO receivedProjectDTO = objectMapper.readValue(putMvcResult.getResponse().getContentAsString(),
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
        ProjectDTO fetchedProjectDTO = fetchedProjectDTOs.stream().findFirst().orElseThrow();

        assertThat(fetchedProjectDTO.getBudget()).isEqualTo(receivedProjectDTO.getBudget());
        assertThat(fetchedProjectDTO.getBudget()).isEqualTo(editedProject.getBudget());
    }

    @Test
    void shouldGetProjectWithEmployees() throws Exception {
        Department department = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", department);
        Project project = createProject("DK8", BigDecimal.valueOf(560000), "projekt");
        assignProjectService.assignEmployeeToProject(project, employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}/employees", project.getId())
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        ProjectEmployeeDTO projectEmployeeDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ProjectEmployeeDTO.class);

        assertThat(projectEmployeeDTO.getEmployees()).isNotEmpty();
        assertThat(projectEmployeeDTO.getEmployees().size()).isEqualTo(1);
        assertThat(projectEmployeeDTO.getEmployees().get(0)).isEqualTo(EmployeeMapper.mapToEmployeeDTO(employee));
        assertThat(projectEmployeeDTO.getName()).isEqualTo(project.getName());
    }

    @Test
    void shouldGetProjectsWithEmployees() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", dept);
        Project project1 = createProject("DK91R", BigDecimal.valueOf(4500000), "koncepcja");
        Project project2 = createProject("DK8B", BigDecimal.valueOf(2300000), "projekt");
        assignProjectService.assignEmployeeToProject(project1, employee);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/employees")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<ProjectEmployeeDTO> projectEmployeeDTOs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProjectEmployeeDTO>>() {
                });

        assertThat(projectEmployeeDTOs.get(0).getEmployees().size()).isEqualTo(1);
        assertThat(projectEmployeeDTOs.get(1).getEmployees().size()).isEqualTo(0);
        assertTrue(projectEmployeeDTOs.contains(ProjectMapper.mapToProjectEmployeeDTO(project2, new ArrayList<>())));
        assertEquals(2, projectEmployeeDTOs.size());
    }

    @Test
    void shouldEditProjectEmployee() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", dept);
        Project project = createProject("S19PH", BigDecimal.valueOf(5500000), "proj");
        List<Employee> employees = new ArrayList<>();
        ProjectEmployeeDTO projectEmployeeDTO = ProjectMapper.mapToProjectEmployeeDTO(project, employees);

        MvcResult putMvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/projects/assign")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEmployeeDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", "" + employee.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        AssignProjectDTO assignProjectDTO = objectMapper.readValue(putMvcResult.getResponse().getContentAsString(),
                AssignProjectDTO.class);

        MvcResult getMvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}/employees", project.getId())
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        ProjectEmployeeDTO receivedProjectEmployeeDTO = objectMapper.readValue(getMvcResult.getResponse().getContentAsString(),
                ProjectEmployeeDTO.class);

        assertThat(assignProjectDTO.getProjectId()).isEqualTo(receivedProjectEmployeeDTO.getId());
        assertThat(assignProjectDTO.getEmployeeId()).isEqualTo(receivedProjectEmployeeDTO.getEmployees()
                .get(0).getId());
    }

    @Test
    void shouldEditProjectEmployee_Project_NoSuchElementEx() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", dept);
        List<Employee> employees = new ArrayList<>();
        ProjectEmployeeDTO projectEmployeeDTO = ProjectEmployeeDTO.builder()
                .id(1)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/projects/assign")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEmployeeDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", "" + employee.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result -> assertEquals("Project does not exist", result.getResolvedException().getMessage()));
    }

    @Test
    void shouldEditProjectEmployee_Employee_NoSuchElementEx() throws Exception {
        Random random = new Random();
        int randomId = random.nextInt(100000) + 1;
        Project project = createProject("S19PH", BigDecimal.valueOf(5500000), "proj");
        List<Employee> employees = new ArrayList<>();
        ProjectEmployeeDTO projectEmployeeDTO = ProjectMapper.mapToProjectEmployeeDTO(project, employees);

        mockMvc.perform(MockMvcRequestBuilders.put("/projects/assign")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEmployeeDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", "" + randomId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result -> assertEquals("This employee does not exist", result.getResolvedException().getMessage()));
    }

    @Test
    void shouldEditProjectEmployee_Employee_InstanceAlreadyExistsEx() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", dept);
        Project project = createProject("S19PH", BigDecimal.valueOf(5500000), "proj");
        List<Employee> employees = new ArrayList<>();
        assignProjectService.assignEmployeeToProject(project, employee);
        ProjectEmployeeDTO projectEmployeeDTO = ProjectMapper.mapToProjectEmployeeDTO(project, employees);

        mockMvc.perform(MockMvcRequestBuilders.put("/projects/assign")
                        .headers(getAuthorizedHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectEmployeeDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", "" + employee.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InstanceAlreadyExistsException))
                .andExpect(result -> assertEquals("This employee is already assigned to this project",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void shouldGetCostPerProject() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Jan", "Kowalski", dept);
        Project project = createProject("S19PH", BigDecimal.valueOf(5500000), "proj");
        List<Employee> employees = new ArrayList<>();
        assignProjectService.assignEmployeeToProject(project, employee);
        ProjectEmployeeDTO projectEmployeeDTO = ProjectMapper.mapToProjectEmployeeDTO(project, employees);
        EmployeeRate employeeRate = new EmployeeRate(1, "JanKowalski_SALARY", CategoryType.SALARY,
                60, "PLN", employee);
        employeeRateService.addEmployeeRate(employeeRate);
        costService.add(project, employee, LocalDate.of(2024, 3, 1), 120L);
        costService.add(project, employee, LocalDate.of(2024, 3, 2), 60L);

        LocalDate startDate = LocalDate.parse("2024-02-20");
        LocalDate endDate = LocalDate.now();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{id}/cost", project.getId())
                        .headers(getAuthorizedHeader())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        ProjectCostDTO projectCostDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProjectCostDTO.class);

        assertThat(projectCostDTO.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(180));
    }


}