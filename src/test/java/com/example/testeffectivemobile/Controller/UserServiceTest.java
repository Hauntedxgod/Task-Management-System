package com.example.testeffectivemobile.Controller;

import com.example.testeffectivemobile.TestEffectiveMobileApplication;
import com.example.testeffectivemobile.controllers.UserController;
import com.example.testeffectivemobile.dto.AuthDto;
import com.example.testeffectivemobile.jwt.JWTUtil;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.UserRepository;
import com.example.testeffectivemobile.service.UserEncoderService;
import com.example.testeffectivemobile.service.UserService;
import com.example.testeffectivemobile.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = TestEffectiveMobileApplication.class)
@AutoConfigureMockMvc
public class UserServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserEncoderService userEncoderService;

    @MockBean
    private UserValidator userValidator;

    @Mock
    private JWTUtil jwtUtils;



    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }



    @Test
    void userRegistration() throws Exception {
        AuthDto userDto = new AuthDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("testPassword");

        User user = new User();
        user.setEmail("test@example.com");

        String token = "generatedToken";


        when(jwtUtils.generateToken(user.getEmail())).thenReturn(token);


        mockMvc.perform(post("/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt-token").isNotEmpty());


        Mockito.verify(userValidator, Mockito.times(1)).validate(any(), any());
        Mockito.verify(userEncoderService, Mockito.times(1)).encoderUser(any());
    }



    @Test
    public void userRegister_ShouldReturnError_WhenAuthenticationFails() throws Exception {

        AuthDto userDto = new AuthDto("test@example.com", "password");
        doThrow(new AuthenticationException("Authentication failed") {}).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        mockMvc.perform(post("/user/login")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(userDto)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.message").value("500"));

    }
}
