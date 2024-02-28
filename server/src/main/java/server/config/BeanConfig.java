package server.config;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}