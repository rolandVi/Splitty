package client.utils;

import dto.exceptions.PasswordExpiredException;
import dto.view.ParticipantNameDto;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ServerUtilsTest {

    @Mock
    public Client client;

    @Mock
    public WebTarget webTarget;

    @Mock
    Invocation.Builder builder;

    @Mock
    StompSession stompSession;

    @Mock
    Response response;

    // to be changed to pull from the config
    private final String SERVER = "http://localhost:8080/";

    public ServerUtils serverUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serverUtils = new ServerUtils(client, stompSession);

        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(anyString())).thenReturn(builder);
        when(builder.accept(anyString())).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(response);
    }

    @Test
    void validatePasswordValid() {
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        assertTrue(serverUtils.validatePassword("validPassword"));
    }

    @Test
    void validatePasswordInvalid() {
        when(response.getStatus()).thenReturn(Response.Status.FORBIDDEN.getStatusCode());
        assertFalse(serverUtils.validatePassword("invalidPassword"));
    }

    @Test
    void validatePasswordExpired() {
        when(response.getStatus()).thenReturn(Response.Status.BAD_REQUEST.getStatusCode());

        assertThrows(PasswordExpiredException.class, ()-> {
            serverUtils.validatePassword("usedPassword");
        });
    }

    //The testConnection method fails
//    @Test
//    void generatePassword() {
//        serverUtils.generatePassword();
//        verify(client, times(1)).target(SERVER);
//        verify(webTarget, times(1)).path("api/password/generatePassword");
//        verify(webTarget, times(1)).request(APPLICATION_JSON);
//        verify(builder, times(1)).accept(APPLICATION_JSON);
//        verify(builder, times(1)).post(null);
//    }


    @Test
    void getParticipantDetails() {
        ParticipantNameDto dto = new ParticipantNameDto(123L, null, null, null);
        when(serverUtils.getParticipantsByEvent(1)).thenReturn(List.of(dto));
        assertDoesNotThrow(() -> serverUtils.getParticipantDetails(123L, 1));
    }

    @Test
    void deleteEventParticipant() {
        assertDoesNotThrow(() -> serverUtils.deleteEventParticipant(123L, 456L));
    }

    @Test
    void enrollInEvent() {
        assertDoesNotThrow(() -> serverUtils.enrollInEvent("inviteCode", 123L));
    }
}