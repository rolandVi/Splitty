package client;

import com.google.inject.Provider;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

public class ClientProvider implements Provider<Client> {
    /**
     * @return new Client instance
     */
    @Override
    public Client get(){
        return ClientBuilder.newClient(new ClientConfig());
    }
}
