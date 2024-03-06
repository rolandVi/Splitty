package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import commons.dto.view.BankAccountDto;
import commons.dto.view.EventTitleDto;
import commons.dto.view.UserNameDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.repository.BankAccountRepository;
import server.repository.UserRepository;

@Service
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final BankAccountRepository bankAccountRepository;

    /**
     * Constructor
     *
     * @param modelMapper           the ModelMapper injected by Spring
     * @param userRepository        the UserRepository injected by Spring
     * @param bankAccountRepository the BankAccountRepository injected by Spring
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
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
     * @param iban iban
     * @param holder email of holder
     * @param bic bic
     * @param firstName firstName
     * @param lastName lastName
     * @param email email
     * @return the user credentials
     */
    public UserNameDto createUser(String iban, String holder, String bic, String firstName,
                                  String lastName, String email) {
        createBankAccount(iban, holder, bic);
        BankAccountEntity bankAccountEntity = new BankAccountEntity(iban, holder, bic);
        UserEntity userEntity = new UserEntity();
        userEntity.setBankAccount(bankAccountEntity);
        userEntity.setEmail(email);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        UserEntity result = this.userRepository.save(userEntity);
        return modelMapper.map(result, UserNameDto.class);
    }

    /**
     * creating new bankAccount
     * @param iban iban
     * @param holder holder
     * @param bic bic
     * @return the bankinfo of a user
     */
    public BankAccountDto createBankAccount(String iban, String holder, String bic){
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setBic(bic);
        bankAccountEntity.setIban(iban);
        bankAccountEntity.setHolder(holder);
        BankAccountEntity result = this.bankAccountRepository.save(bankAccountEntity);
        return modelMapper.map(result, BankAccountDto.class);
    }


}
