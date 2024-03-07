package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.user.UserMapper;
import com.designofficems.designofficemanagementsystem.model.User;
import com.designofficems.designofficemanagementsystem.repository.*;
import com.designofficems.designofficemanagementsystem.service.AuthenticationService;
import com.designofficems.designofficemanagementsystem.service.UserService;
import com.designofficems.designofficemanagementsystem.util.RegisterRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;

@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRateRepository employeeRateRepository;

    @Autowired
    private AssignProjectRepository assignProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private UserMapper userMapper;

    private User currentUser;
    private String token;

    protected void setUp() throws Exception {
        costRepository.deleteAll();
        employeeRateRepository.deleteAll();
        assignProjectRepository.deleteAll();
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
        userRepository.deleteAll();
        generateToken();
    }

    protected RegisterRequest registerRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName(RandomStringUtils.randomAlphabetic(10));
        request.setLastName(RandomStringUtils.randomAlphabetic(10));
        request.setEmail("%s@no.domain".formatted(RandomStringUtils.randomAlphabetic(10)));
        request.setPassword("123456");
        return request;
    }

    protected void generateToken() throws Exception {
        User user = userMapper.mapRegisterRequestToUserModel(registerRequest());
        this.token = authenticationService.register(user).getToken();
        this.currentUser = user;
    }

    protected HttpHeaders getAuthorizedHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer %s".formatted(getToken()));
        return httpHeaders;
    }

    protected User getCurrentUser() {
        return currentUser;
    }

    protected String getToken() {
        return token;
    }
}
