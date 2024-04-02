package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.ParticipantEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import dto.ParticipantCreationDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.ParticipantNameDto;
import server.repository.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(IllegalArgumentException::new);
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
        ParticipantEntity currentUser=this.participantRepository.findById(userId)
                .orElseThrow(()-> new ObjectNotFoundException("No such user found"));
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
    public ParticipantNameDto createParticipant(ParticipantCreationDto participantInfo, EventEntity event) {
        ParticipantEntity participant=new ParticipantEntity();
        participant.setFirstName(participantInfo.getFirstName());
        participant.setLastName(participantInfo.getLastName());
        participant.setEmail(participantInfo.getEmail());
        participant.setEvent(event);
        participant= this.participantRepository.save(participant);
        return this.modelMapper.map(participant, ParticipantNameDto.class);
    }

    @Transactional
    public void deleteParticipant(Long participantId) {
        this.participantRepository.deleteById(participantId);
    }
}
