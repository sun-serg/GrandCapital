package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.iservice.IEmailDataService;
import by.sunserg.grandcapital.service.jwt.JwtUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(EmailDataTestConfig.class)
class EmailDataControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IEmailDataService emailDataService;

    @Test
    void addNewEmail_shouldReturn200() throws Exception {
        Long userId = 1L;
        String email = "test@test.com";
        JwtUser jwtUser = new JwtUser(userId, "test", "test");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/emails", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}")
                        .with(user(jwtUser)))
                .andExpect(status().isOk());
        
        verify(emailDataService, times(1)).addUserEmail(userId, email);
    }

    @Test
    void addNewEmail_shouldReturn403_whenWrongUser() throws Exception {
        Long pathUserId = 1L;
        Long currentUserId = 2L;
        String email = "test@test.com";
        JwtUser jwtUser = new JwtUser(currentUserId, "test", "test");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/emails", pathUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}")
                        .with(user(jwtUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateEmail_shouldReturn200() throws Exception {
        Long userId = 1L;
        Long emailDataId = 10L;
        String email = "updated@test.com";
        JwtUser jwtUser = new JwtUser(userId, "test", "test");

        mockMvc.perform(MockMvcRequestBuilders.put("/user/{userId}/emails/{emailDataId}", userId, emailDataId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}")
                        .with(user(jwtUser)))
                .andExpect(status().isOk());

        verify(emailDataService, times(1)).updateUserEmail(userId, emailDataId, email);
    }

    @Test
    void updateEmail_shouldReturn403_whenWrongUser() throws Exception {
        Long pathUserId = 1L;
        Long currentUserId = 2L;
        Long emailDataId = 10L;
        String email = "updated@test.com";
        JwtUser jwtUser = new JwtUser(currentUserId, "test", "test");
        
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{userId}/emails/{emailDataId}", pathUserId, emailDataId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}")
                        .with(user(jwtUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteEmail_shouldReturn200() throws Exception {
        Long userId = 1L;
        Long emailDataId = 10L;
        JwtUser jwtUser = new JwtUser(userId, "test", "test");
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emails/{emailDataId}", userId, emailDataId)
                        .with(user(jwtUser)))
                .andExpect(status().isOk());
        
        verify(emailDataService, times(1)).deleteUserEmail(userId, emailDataId);
    }

    @Test
    void deleteEmail_shouldReturn403_whenWrongUser() throws Exception {
        Long pathUserId = 1L;
        Long currentUserId = 2L;
        Long emailDataId = 10L;
        JwtUser jwtUser = new JwtUser(currentUserId, "test", "test");
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emails/{emailDataId}", pathUserId, emailDataId)
                        .with(user(jwtUser)))
                .andExpect(status().isForbidden());
    }
}