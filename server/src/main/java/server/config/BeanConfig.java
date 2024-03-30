package server.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class BeanConfig {
    /**
     *
     * @return random Result
     */
    @Bean
    public Random getRandom() {
        return new Random();
    }

    /**
     *
     * @return a ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /**
     * @return a SecureRandom instance
     */
    @Bean
    public SecureRandom getSecureRandom(){
        return new SecureRandom();
    }
}