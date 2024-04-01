package server.service;

import commons.BankAccountEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import server.exception.FieldValidationException;
import server.repository.BankAccountRepository;

@Service
public class BankAccountService {

    private final ModelMapper modelMapper;

    private final BankAccountRepository bankAccountRepository;

    /**
     * @param modelMapper           the ModelMapper injected by Spring
     * @param bankAccountRepository the bankAccountRepository
     */
    public BankAccountService(ModelMapper modelMapper,
                              BankAccountRepository bankAccountRepository) {
        this.modelMapper = modelMapper;
        this.bankAccountRepository = bankAccountRepository;
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
                .orElseThrow(()-> new ObjectNotFoundException("No such bank account found")),
                BankAccountDto.class);
    }

    /**
     * Remove a banAccount on id
     * @param id id
     */
    @Transactional
    public void removeById(long id){
        this.bankAccountRepository.deleteById(id);
    }

//   todo : public BankAccountDto updateById(){}

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
     * @param bankAccountEntity the bank account information
     * @return BankAccountDto + id
     */
    public BankAccountEntity createBankAccount(BankAccountCreationDto bankAccountEntity) {
        if (this.ibanExists(bankAccountEntity.getIban())){
            throw new FieldValidationException("Iban should be unique");
        }
        BankAccountEntity newEntity = this.modelMapper
                .map(bankAccountEntity, BankAccountEntity.class);
        BankAccountEntity result = this.bankAccountRepository.save(newEntity);
        return result;
    }

    /**
     *
     * @param iban the iban to check
     * @return whether such an iban exists
     */
    public boolean ibanExists(String iban) {
        return bankAccountRepository.existsByIban(iban);
    }

    /**
     * Edits the bank account detiails
     * @param bankAccount the old bank account entity
     * @param bankAccountCreationDto the new bank account details
     * @return the new bank account
     */
    public BankAccountEntity editBankAccount(
            BankAccountEntity bankAccount,
            BankAccountCreationDto bankAccountCreationDto) {
        if (!bankAccount.getIban().equals(bankAccountCreationDto.getIban())
                && this.bankAccountRepository.existsByIban(bankAccountCreationDto.getIban())){
            throw new FieldValidationException("Iban should be unique");
        }

        bankAccount.setBic(bankAccountCreationDto.getBic());
        bankAccount.setIban(bankAccountCreationDto.getIban());
        bankAccount.setHolder(bankAccountCreationDto.getHolder());
        return this.bankAccountRepository.save(bankAccount);
    }
}
