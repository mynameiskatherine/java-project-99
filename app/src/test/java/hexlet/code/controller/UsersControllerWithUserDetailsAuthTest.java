package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "development")
@ExtendWith(SpringExtension.class)
public class UsersControllerWithUserDetailsAuthTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUserUpdate;
    private User testUserDelete;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        testUserUpdate = userRepository.findByEmail("test@test.com")
                .orElse(new User("test@test.com", passwordEncoder.encode("test")));
        userRepository.save(testUserUpdate);
        testUserDelete = userRepository.findByEmail("test1@test1.com")
                .orElse(new User("test1@test1.com", passwordEncoder.encode("test1")));
        userRepository.save(testUserDelete);
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@test.com")
    public void testUpdate() throws Exception {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken("test@test.com", "test"));

        testUserUpdate.setEmail("newemail@new.com");
        testUserUpdate.setPassword(passwordEncoder.encode("newpassword"));

        mockMvc.perform(put("/api/users/{id}", testUserUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserUpdate)))
                .andExpect(status().isOk());

        User user = userRepository.findById(testUserUpdate.getId()).get();

        assertThat(user.getEmail()).isEqualTo("newemail@new.com");
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@test.com")
    public void testPartialUpdate() throws Exception {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken("test@test.com", "test"));

        HashMap<String, String> userUpdate = new HashMap<>();
        userUpdate.put("email", "newemail@test.com");

        mockMvc.perform(put("/api/users/{id}", testUserUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk());

        User user = userRepository.findById(testUserUpdate.getId()).get();

        assertThat(user.getEmail()).isEqualTo(userUpdate.get("email"));
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test1@test1.com")
    public void testDelete() throws Exception {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken("test1@test1.com", "test1"));

        mockMvc.perform(delete("/api/users/{id}", testUserDelete.getId()))
                .andExpect(status().isNoContent());

        assertThat(userRepository.existsById(testUserDelete.getId())).isEqualTo(false);
    }
}
