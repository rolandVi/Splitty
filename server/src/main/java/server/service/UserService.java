package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import dto.UserCreationDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.UserNameDto;
import server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EventService eventService;

    private final BankAccountService bankAccountService;


    /**
     * Constructor
     *
     * @param modelMapper        the ModelMapper injected by Spring
     * @param userRepository     the UserRepository injected by Spring
     * @param eventService       the event service
     * @param bankAccountService the bank account service
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository,
                       EventService eventService, BankAccountService bankAccountService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.eventService = eventService;
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
    public UserEntity findById(Long userId) {
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
     * Get user ID by email
     * @param userEmail the email
     * @return the id
     */
    public Long findIdByEmail(String userEmail) {
        return this.userRepository.getUserIdByUserEmail(userEmail);
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
        var result=this.userRepository.getEventsByUserId(id);
        return result.stream().map(e-> this.modelMapper.map(e, EventOverviewDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Checks if such an email exists
     * @param email the email
     * @return true if it exists and false otherwise
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Leaves a particular event
     * @param eventId the event id
     * @param userId the user id
     * @return  true if the operation was successful
     */
    @Transactional
    public boolean leave(long eventId, long userId) {
        EventEntity event=eventService.findEntityById(eventId);
        UserEntity user= this.userRepository.findById(userId)
                        .orElseThrow(IllegalArgumentException::new);
        user.leave(event);
        this.userRepository.saveAndFlush(user);
        return true;
    }


    /**
     * Adding a participant in the event
     * @param inviteCode the event invite code
     * @param userId the user id
     * @return  true if the operation was successful
     */
    public boolean join(String inviteCode, long userId) {
        UserEntity user=this.userRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException("No such user found"));
        EventEntity event = eventService.findEntityByInviteCode(inviteCode);
        user.join(event);
        userRepository.save(user);
        return true;
    }

    /**
     * Creates and event and joins the creator
     * @param title the title of the event
     * @param id the id of the creator
     * @return the created event
     */
    public EventTitleDto createEvent(String title, Long id) {
        EventEntity event = this.eventService.createEvent(title);
        this.join(event.getInviteCode(), id);
        return this.modelMapper.map(event, EventTitleDto.class);
    }

    /**
     * Creates a bank account
     *
     * @param bankAccountCreationDto the bank account info
     * @param userId
     * @return the new bank account
     */
    public BankAccountDto createBankAccount(BankAccountCreationDto bankAccountCreationDto,
                                            Long userId) {
        BankAccountEntity bankAccount=this.bankAccountService
                .createBankAccount(bankAccountCreationDto);
        UserEntity currentUser=this.userRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException("No such user found"));
        currentUser.setBankAccount(bankAccount);
        this.userRepository.save(currentUser);
        return this.modelMapper.map(bankAccount, BankAccountDto.class);
    }

    public BankAccountDto getBankAccount(Long id) {
        UserEntity user=this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No such user found"));
        if (user.getBankAccount()==null){
            return new BankAccountDto();
        }

        return this.modelMapper.map(user.getBankAccount(), BankAccountDto.class);
    }

    public BankAccountDto editBankAccount(
            BankAccountCreationDto bankAccountCreationDto, Long userId) {
        UserEntity user=this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("No such user found"));
        BankAccountEntity bankAccount=this.bankAccountService
                .editBankAccount(user.getBankAccount(), bankAccountCreationDto);
        user.setBankAccount(bankAccount);
        this.userRepository.save(user);
        return this.modelMapper.map(bankAccount, BankAccountDto.class);
    }
}
