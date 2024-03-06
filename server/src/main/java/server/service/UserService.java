package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import commons.dto.view.EventTitleDto;
import commons.dto.view.UserNameDto;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.repository.UserRepository;

import java.util.Set;

@Service
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param modelMapper the ModelMapper injected by Spring
     * @param userRepository the UserRepository injected by Spring
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    /**
     * Checks if entity exists by id
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

    /**
     * Persist a user to the database
     * @param user the id of the user in a UserNameDto
     * @return the created UserEntity
     */
    public UserEntity saveUserByID(UserNameDto user) {
        UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
        return this.userRepository.save(userEntity);
    }

    /**
     * Create a new event given a title
     * @param title the title
     * @return the title and id of the event
     */
    public EventTitleDto createEvent(String title) {
        UserEntity userEntity = new UserEntity();
        newEntity.setTitle(title);
        EventEntity result=this.eventRepository.save(newEntity);
        return modelMapper.map(result, EventTitleDto.class);
    }

    /**
     * Creating a bankaccount entity
     */
    public


}
