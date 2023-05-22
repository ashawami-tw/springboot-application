package org.example.integration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.integration.config.PostgresqlInit;
import org.example.user.handler.UserDto;
import org.example.user.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateUserIntTest extends PostgresqlInit {
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "test123@T";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception{
        UserDto userDto = new UserDto(EMAIL, PASSWORD);
        mockMvc.perform(post("/user")
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertEquals(1, userRepo.findAll().size());
    }
}
