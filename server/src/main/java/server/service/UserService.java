package server.service;

import commons.BankAccountEntity;
import commons.UserEntity;
import commons.dto.UserCreationDto;
import commons.dto.view.BankAccountDto;
import commons.dto.view.EventOverviewDto;
import commons.dto.view.UserNameDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.repository.BankAccountRepository;
import server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;


    /**
     * Constructor
     *
     * @param modelMapper           the ModelMapper injected by Spring
     * @param userRepository        the UserRepository injected by Spring
     * @param bankAccountService    the BankAccountService injected by Spring
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository,
                       BankAccountService bankAccountService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
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
     * Create new user given credentials
     * @param user The user details
     * @return the user credentials
     */
    public UserNameDto createUser(UserCreationDto user) {
        UserEntity result = this.userRepository.save(this.modelMapper.map(user, UserEntity.class));
        return modelMapper.map(result, UserNameDto.class);
    }

    /**
     * Get the events of a specific user
     * @param id the id of the user
     * @return the events of the user
     */
    public List<EventOverviewDto> getUserEvents(long id) {
        return this.userRepository.getEventsByUserId(id).stream()
                .map(e-> this.modelMapper.map(e, EventOverviewDto.class))
                .collect(Collectors.toList());
    }
}
