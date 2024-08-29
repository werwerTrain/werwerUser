package com.buaa.werweruser.controller;

import com.buaa.werweruser.client.TrainClient;
import com.buaa.werweruser.entity.User;
import com.buaa.werweruser.entity.VerificationCode;
import com.buaa.werweruser.service.IEmailService;
import com.buaa.werweruser.service.IUserService;
import com.buaa.werweruser.service.IVerificationCodeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private IVerificationCodeService verificationCodeService;

    @MockBean
    private IEmailService emailService;

    @MockBean
    private TrainClient trainClient;

    // test for 用户注册
    @Test
    void testUserRegisterSuccess() throws Exception {
        // Prepare mock behavior
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(0);

        // Mock registerUser to return a new User object
        Mockito.when(userService.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new User("120105200001011234", "Test User", "Password123", "test@example.com"));

        Mockito.when(trainClient.insertPassenger(Mockito.anyString(), Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                .thenReturn(1);

        // Prepare request payload
        Map<String, Object> registerMap = new HashMap<>();
        registerMap.put("id", "120105200001011234");
        registerMap.put("name", "Test User");
        registerMap.put("password", "Password123");
        registerMap.put("email", "test@example.com");
        registerMap.put("time", 2);

        // Perform the POST request
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void testUserRegisterValidationError() throws Exception {
        // Prepare request payload with invalid email
        Map<String, Object> registerMap = new HashMap<>();
        registerMap.put("id", "120105200001011234");
        registerMap.put("name", "Test User");
        registerMap.put("password", "Password123");
        registerMap.put("email", "invalid-email");
        registerMap.put("time", 2);

        // Perform the POST request
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.reason").value("email格式错误"));
    }

    // test for 查看用户信息
    @Test
    void showUserInfo() throws Exception {
        // Prepare mock behavior
        Mockito.when(userService.findById(Mockito.anyString()))
                .thenReturn(new User("120105200001011234", "Test User", "Password123", "test@example.com"));

        // Perform the GET request
        mockMvc.perform(get("/users/120105200001011234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("120105200001011234"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }


    // test for 用户登录
    @Test
    void userLogin() throws Exception {
        // Prepare mock behavior for successful login
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(1);
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new User("120105200001011234", "Test User", "Password123", "test@example.com"));

        // Prepare request payload
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("id", "120105200001011234");
        loginMap.put("password", "Password123");

        // Perform the POST request
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("登录成功"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void userLoginUserNotRegistered() throws Exception {
        // Prepare mock behavior for user not registered
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(0);

        // Prepare request payload
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("id", "120105200001011234");
        loginMap.put("password", "Password123");

        // Perform the POST request
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("用户未注册"))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void userLoginInvalidCredentials() throws Exception {
        // Prepare mock behavior for invalid credentials
        Mockito.when(userService.findUserById(Mockito.anyString())).thenReturn(1);
        Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        // Prepare request payload
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("id", "120105200001011234");
        loginMap.put("password", "WrongPassword");

        // Perform the POST request
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("用户id或密码错误"))
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist());
    }


    // test for 更新密码
    @Test
    void updatePassword() throws Exception {
        // Prepare mock behavior for successful password update
        Mockito.when(userService.updatePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(1);

        // Prepare request payload
        Map<String, Object> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("id", "120105200001011234");
        updatePasswordMap.put("newpassword", "NewPassword123");

        // Perform the POST request
        mockMvc.perform(post("/updatepassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePasswordMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void updatePasswordFailure() throws Exception {
        // Prepare mock behavior for failed password update
        Mockito.when(userService.updatePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(0);

        // Prepare request payload
        Map<String, Object> updatePasswordMap = new HashMap<>();
        updatePasswordMap.put("id", "120105200001011234");
        updatePasswordMap.put("newpassword", "NewPassword123");

        // Perform the POST request
        mockMvc.perform(post("/updatepassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePasswordMap)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false));
    }


    // test for 忘记密码
    @Test
    void forgetPassword() throws Exception {
        // Prepare mock behavior for successful forget password request
        Mockito.when(userService.getEmail(Mockito.anyString())).thenReturn("test@example.com");
        Mockito.when(verificationCodeService.getVerificationCode(Mockito.anyString()))
                .thenReturn(new VerificationCode("test@example.com"));

        // Perform the GET request
        mockMvc.perform(get("/getbackPassword/120105200001011234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("成功发送验证码"));
    }

    @Test
    void forgetPasswordInvalidId() throws Exception {
        // Perform the GET request with invalid ID
        mockMvc.perform(get("/getbackPassword/invalid-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("身份证号格式错误"));
    }

    @Test
    void forgetPasswordUserNotRegistered() throws Exception {
        // Prepare mock behavior for unregistered user
        Mockito.when(userService.getEmail(Mockito.anyString())).thenReturn(null);

        // Perform the GET request
        mockMvc.perform(get("/getbackPassword/120105200001011234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("未注册"));
    }


    // test for 验证码
    @Test
    void idCodeByEmail() throws Exception {
        // Prepare mock behavior for valid verification code
        VerificationCode code = new VerificationCode("test@example.com");
        String pass = code.getCode();  // Generate the verification code
        Mockito.when(userService.getEmail(Mockito.anyString())).thenReturn("test@example.com");
        Mockito.when(verificationCodeService.getVerificationCode(Mockito.anyString())).thenReturn(code);

        // Perform the POST request with the 'pass' as a path variable
        mockMvc.perform(post("/id/idCode/{pass}/120105200001011234", pass))  // Pass 'pass' here
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }


    @Test
    void idCodeByEmailInvalidCode() throws Exception {
        // Prepare mock behavior for invalid verification code
        Mockito.when(userService.getEmail(Mockito.anyString())).thenReturn("test@example.com");
        Mockito.when(verificationCodeService.getVerificationCode(Mockito.anyString())).thenReturn(null);

        // Perform the POST request
        mockMvc.perform(post("/id/idCode/invalidCode/120105200001011234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(false));
    }


    // test for 获取邮箱
    @Test
    void getEmail() throws Exception {
        // Prepare mock behavior
        Mockito.when(userService.getEmail(Mockito.anyString())).thenReturn("test@example.com");

        // Perform the GET request
        mockMvc.perform(get("/getEmail/120105200001011234"))
                .andExpect(status().isOk())
                .andExpect(content().string("test@example.com"));
    }

}
