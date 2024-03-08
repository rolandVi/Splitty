package server.service;

import commons.BankAccountEntity;
import commons.dto.view.BankAccountDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.repository.BankAccountRepository;
import server.repository.UserRepository;

@Service
public class BankAccountService {

    private final ModelMapper modelMapper;

    private final BankAccountRepository bankAccountRepository;

    private final UserRepository userRepository;

    /**
     *
     * @param modelMapper the ModelMapper injected by Spring
     * @param bankAccountRepository the bankAccountRepository
     * @param userRepository the UserRepository
     */
    public BankAccountService(ModelMapper modelMapper,
                              BankAccountRepository bankAccountRepository,
                              UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    /**
     * checker if bankAccount exists
     * @param id id
     * @return true if exists, false if it doesn't
     */
    public boolean existsById(long id){
        return this.bankAccountRepository.existsById(id);
    }

    /**
     * Getter for bankINfo on id
     * @param id id
     * @return bankAccount which corresponds to given id
     */
    public BankAccountDto getById(long id){
        return this.modelMapper.map(this.bankAccountRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new), BankAccountDto.class);
    }

    /**
     * Remove a banAccount on id
     * @param id id
     */
    @Transactional
    public void removeById(long id){
        this.bankAccountRepository.deleteById(id);
    }

//   Todo : public BankAccountDto updateById(){}

    /**
     * persist bankAccount to the database
     * @param bankAccount bankAccount
     * @return BankAccountEntity
     */
    public BankAccountEntity saveBankAccount(BankAccountDto bankAccount){
        BankAccountEntity bankAccountEntity =
                this.modelMapper.map(bankAccount, BankAccountEntity.class);
        return this.bankAccountRepository.save(bankAccountEntity);
    }

    /**
     * create a new bankAccount given credentials
     * @param iban iban
     * @param holder holder
     * @param bic bic
     * @return BankAccountDto + id
     */
    public BankAccountDto createBankAccount(String iban, String holder, String bic){
        BankAccountEntity newEntity = new BankAccountEntity();
        newEntity.setHolder(holder);
        newEntity.setBic(bic);
        newEntity.setIban(iban);
        BankAccountEntity result = this.bankAccountRepository.save(newEntity);
        return modelMapper.map(result, BankAccountDto.class);
    }
}
