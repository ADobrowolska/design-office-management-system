package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.cost.CostDTO;
import com.designofficems.designofficemanagementsystem.dto.cost.CostRequestDTO;
import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.EmployeeRate;
import com.designofficems.designofficemanagementsystem.model.Project;
import com.designofficems.designofficemanagementsystem.repository.DepartmentRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRateRepository;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import com.designofficems.designofficemanagementsystem.repository.ProjectRepository;
import com.designofficems.designofficemanagementsystem.service.CostService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    private CostService costService;


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

    @Test
    void shouldGetAllCosts() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Anna", "Nowak", dept);
        Project project1 = createProject("DK8", BigDecimal.valueOf(3350200.20), "droga krajowa");
        Project project2 = createProject("DK61", BigDecimal.valueOf(4000000.50), "droga krajowa");
        EmployeeRate employeeRate1 = createEmployeeRate("AN_SALARY", CategoryType.SALARY, 50, "PLN", employee);
        EmployeeRate employeeRate2 = createEmployeeRate("AN_LICENCE", CategoryType.LICENCE, 5, "USD", employee);
        costService.add(project1, employee, LocalDate.now(), 120L);
        costService.add(project2, employee, LocalDate.now(), 150L);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/costs")
                        .headers(getAuthorizedHeader()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<CostDTO> costs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<CostDTO>>() {
                });

        assertThat(costs.size()).isEqualTo(4);
        assertThat(costs.get(0).getEmployeeRate().getRate()).isEqualTo(50);
    }

    @Test
    void shouldGetCostsByDay() throws Exception {
        Department dept = createDepartment();
        Employee employee = createEmployee("Anna", "Nowak", dept);
        Project project1 = createProject("S19", BigDecimal.valueOf(8000000.00), "proj");
        Project project2 = createProject("S12", BigDecimal.valueOf(6000000.00), "proj");
        EmployeeRate employeeRateSalary = createEmployeeRate("AN_SALARY", CategoryType.SALARY, 80, "PLN", employee);
        EmployeeRate employeeRateLicence = createEmployeeRate("AN_LICENCE", CategoryType.LICENCE, 10, "EUR", employee);

        costService.add(project1, employee, LocalDate.now(), 60L);
        costService.add(project2, employee, LocalDate.now(), 360L);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/costs/param")
                        .headers(getAuthorizedHeader())
                        .param("date", LocalDate.now().toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<CostDTO> costs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<CostDTO>>() {
                });
        assertThat(costs.size()).isEqualTo(4);
        assertThat(costs.get(0).getEmployeeRate().getCurrency()).isEqualTo("PLN");
    }




}