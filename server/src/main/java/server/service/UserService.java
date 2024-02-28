package server.service;

import commons.UserEntity;
import org.springframework.stereotype.Service;
import server.dto.view.EventTitleDto;
import server.repository.UserRepository;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean existsById(long id) {
        return this.userRepository.existsById(id);
    }

    public UserEntity findById(long userId) {
        return this.userRepository.getReferenceById(userId);
    }
}
