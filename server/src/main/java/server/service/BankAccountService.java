package server.service;

import commons.BankAccountEntity;
import commons.dto.view.BankAccountDto;
import commons.dto.view.EventDetailsDto;
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

    public BankAccountService(ModelMapper modelMapper, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public boolean existsById(long id){
        return this.bankAccountRepository.existsById(id);
    }

    public BankAccountDto getById(long id){
        return this.modelMapper.map(this.bankAccountRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new), BankAccountDto.class);
    }

    @Transactional
    public void removeById(long id){
        this.bankAccountRepository.deleteById(id);
    }

//   Todo : public BankAccountDto updateById(){}

    public BankAccountEntity saveBankAccount(BankAccountDto bankAccount){
        BankAccountEntity bankAccountEntity = this.modelMapper.map(bankAccount, BankAccountEntity.class);
        return this.bankAccountRepository.save(bankAccountEntity);
    }

    public BankAccountDto createBankAccount(String iban, String holder, String bic){
        BankAccountEntity newEntity = new BankAccountEntity();
        newEntity.setHolder(holder);
        newEntity.setBic(bic);
        newEntity.setIban(iban);
        BankAccountEntity result = this.bankAccountRepository.save(newEntity);
        return modelMapper.map(result, BankAccountDto.class);
    }
}
