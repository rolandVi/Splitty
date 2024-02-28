package server.service;

import commons.UserEntity;
import org.springframework.stereotype.Service;
import server.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository the UserRepository injected by Spring
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * CHecks if entity exists by id
     * @param id the id
     * @return true if it exists and false otherwise
     */
    public boolean existsById(long id) {
        return this.userRepository.existsById(id);
    }

    /**
     * Get and entity by id
     * @param userId the id
     * @return the entity
     */
    public UserEntity findById(long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

}
