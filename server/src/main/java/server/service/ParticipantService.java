package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.ParticipantEntity;
import dto.BankAccountCreationDto;
import dto.ParticipantCreationDto;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import server.repository.ParticipantRepository;

@Service
public class ParticipantService {
    private final ModelMapper modelMapper;

    private final ParticipantRepository participantRepository;

    private final BankAccountService bankAccountService;


    /**
     * Constructor
     *
     * @param modelMapper        the ModelMapper injected by Spring
     * @param userRepository     the UserRepository injected by Spring
     * @param bankAccountService the bank account service
     */
    public ParticipantService(ModelMapper modelMapper, ParticipantRepository userRepository,
                              BankAccountService bankAccountService) {
        this.modelMapper = modelMapper;
        this.participantRepository = userRepository;
        this.bankAccountService = bankAccountService;
    }

    /**
     * Checks if entity exists by id
     * @param id the id
     * @return true if it exists and false otherwise
     */
    public boolean existsById(long id) {
        return this.participantRepository.existsById(id);
    }

    /**
     * Get and entity by id
     * @param userId the id
     * @return the entity
     */
    public ParticipantEntity findById(Long userId) {
        return this.participantRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("No such participant found"));
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
        // Checks if there is a user to link the bank account to
        ParticipantEntity currentUser = this.participantRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException("No such user found"));

        // Create bankAccountEntity
        BankAccountEntity entity;
        if (currentUser.getBankAccount() == null) entity = new BankAccountEntity();
        else entity = currentUser.getBankAccount();
        entity.setIban(bankAccountCreationDto.getIban());
        entity.setBic(bankAccountCreationDto.getBic());
        entity.setHolder(currentUser.getFirstName() + currentUser.getLastName());

        // Add bankAccountEntity to database using service
        BankAccountEntity bankAccount = this.bankAccountService.createBankAccount(entity);

        currentUser.setBankAccount(bankAccount);
        this.participantRepository.save(currentUser);
        return this.modelMapper.map(bankAccount, BankAccountDto.class);
    }

    /**
     * Returns the bank account of the user with the id
     * @param id the user id
     * @return the bank account info
     */
    public BankAccountDto getBankAccount(Long id) {
        ParticipantEntity user=this.participantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No such user found"));
        if (user.getBankAccount()==null){
            return new BankAccountDto();
        }

        return this.modelMapper.map(user.getBankAccount(), BankAccountDto.class);
    }

    /**
     * Edits the bank account info
     * @param bankAccountCreationDto the new bank account
     * @param userId the user id
     * @return the new bank account
     */
    public BankAccountDto editBankAccount(
            BankAccountCreationDto bankAccountCreationDto, Long userId) {
        ParticipantEntity user=this.participantRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("No such user found"));

        if (user.getBankAccount() == null) {
            System.out.println("nOT NULL");
            user.setBankAccount(new BankAccountEntity());
            user.getBankAccount().setHolder(user.getFirstName()+user.getLastName());
        }
        BankAccountEntity bankAccount=this.bankAccountService
                .editBankAccount(user.getBankAccount(), bankAccountCreationDto);
        user.setBankAccount(bankAccount);
        this.participantRepository.save(user);
        return this.modelMapper.map(bankAccount, BankAccountDto.class);
    }

    /**
     * Creates a new participant for a specified event
     * @param participantInfo the participant information
     * @param event the event
     * @return the saved participant info
     */
    public ParticipantNameDto createParticipant(
            ParticipantCreationDto participantInfo,
            EventEntity event) {
        ParticipantEntity participant=new ParticipantEntity();
        participant.setFirstName(participantInfo.getFirstName());
        participant.setLastName(participantInfo.getLastName());
        participant.setEmail(participantInfo.getEmail());
        participant.setEvent(event);
        participant= this.participantRepository.save(participant);
        return this.modelMapper.map(participant, ParticipantNameDto.class);
    }

    /**
     * Deletes a participant
     * @param participantId the participant id
     */
    @Transactional
    public void deleteParticipant(Long participantId) {
        this.participantRepository.deleteById(participantId);
    }

    /**
     * Edit the participant information
     * @param participantNameDto the participant information
     * @param userId the participant ID
     * @return the saved participant info
     */
    public ParticipantNameDto editParticipant(ParticipantNameDto participantNameDto, Long userId) {
        ParticipantEntity participantEntity = this.participantRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("No such participant found"));

        participantEntity.setFirstName(participantNameDto.getFirstName());
        participantEntity.setLastName(participantNameDto.getLastName());
        participantEntity.setEmail(participantNameDto.getEmail());

        participantEntity = this.participantRepository.save(participantEntity);
        return this.modelMapper.map(participantEntity, ParticipantNameDto.class);
    }

    @Transactional
    public void deleteBankAccount(Long userId) {
        ParticipantEntity participant=this.participantRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("No such participant."));
        if (participant.getBankAccount()!=null){
            BankAccountEntity bank=participant.getBankAccount();
            participant.setBankAccount(null);
            this.bankAccountService.deleteBankAccount(bank);
        }
    }
}
