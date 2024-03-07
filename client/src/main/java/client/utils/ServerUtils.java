package client.utils;

import commons.dto.view.EventDetailsDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Get the event details of a specific event with the given id
     * @param id the id of the event
     * @return the event details
     */
    public EventDetailsDto getEventDetails(long id){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/events/"+id)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(EventDetailsDto.class);
    }

}
