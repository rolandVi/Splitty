package server.service;

import commons.BankAccountEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.exception.FieldValidationException;
import server.exception.ObjectNotFoundException;
import server.repository.BankAccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccountService = new BankAccountService(modelMapper, bankAccountRepository);
    }

    @Test
    void existsById_withValidId_shouldReturnTrue() {
        long id = 1L;
        when(bankAccountRepository.existsById(id)).thenReturn(true);

        boolean result = bankAccountService.existsById(id);

        assertTrue(result);
    }

    @Test
    void existsById_withInvalidId_shouldReturnFalse() {
        long id = 1L;
        when(bankAccountRepository.existsById(id)).thenReturn(false);

        boolean result = bankAccountService.existsById(id);

        assertFalse(result);
    }

    @Test
    void saveBankAccount() {
        BankAccountDto bankAccountDto = new BankAccountDto();
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        when(modelMapper.map(bankAccountDto, BankAccountEntity.class)).thenReturn(bankAccountEntity);
        when(bankAccountRepository.save(bankAccountEntity)).thenReturn(bankAccountEntity);

        BankAccountEntity savedEntity = bankAccountService.saveBankAccount(bankAccountDto);

        assertNotNull(savedEntity);
        verify(modelMapper, times(1)).map(bankAccountDto, BankAccountEntity.class);
        verify(bankAccountRepository, times(1)).save(bankAccountEntity);
    }

    @Test
    void getById_withValidId_shouldReturnBankAccountDto() {
        long id = 1L;
        BankAccountDto expectedDto = new BankAccountDto();
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(new BankAccountEntity()));
        when(modelMapper.map(any(), eq(BankAccountDto.class))).thenReturn(expectedDto);

        BankAccountDto result = bankAccountService.getById(id);

        assertEquals(expectedDto, result);
    }

    @Test
    void getById_withInvalidId_shouldThrowObjectNotFoundException() {
        long id = 1L;
        when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> bankAccountService.getById(id));
    }

    @Test
    void removeById_withValidId_shouldInvokeDeleteById() {
        long id = 1L;

        bankAccountService.removeById(id);

        verify(bankAccountRepository, times(1)).deleteById(id);
    }

    @Test
    void ibanExists_withExistingIban_shouldReturnTrue() {
        String iban = "existingIban";
        when(bankAccountRepository.existsByIban(iban)).thenReturn(true);

        boolean result = bankAccountService.ibanExists(iban);

        assertTrue(result);
    }

    @Test
    void ibanExists_withNonExistingIban_shouldReturnFalse() {
        String iban = "nonExistingIban";
        when(bankAccountRepository.existsByIban(iban)).thenReturn(false);


        boolean result = bankAccountService.ibanExists(iban);

        assertFalse(result);
    }
    @Test
    public void testRemoveById_ValidId_RemovesSuccessfully() {
        // Arrange
        long accountId = 1L;

        // Act
        bankAccountService.removeById(accountId);

        // Assert
        verify(bankAccountRepository).deleteById(accountId);
    }

    // Test for saveBankAccount method
    @Test
    public void testSaveBankAccount_ValidDto_SavesSuccessfully() {
        // Arrange
        BankAccountDto bankAccountDto = new BankAccountDto();
        BankAccountEntity savedEntity = new BankAccountEntity();
        when(modelMapper.map(any(), eq(BankAccountEntity.class))).thenReturn(savedEntity);
        when(bankAccountRepository.save(any())).thenReturn(savedEntity);

        // Act
        BankAccountEntity result = bankAccountService.saveBankAccount(bankAccountDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedEntity, result);
    }

    // Test for editBankAccount method
    @Test
    public void testEditBankAccount_ValidInput_ReturnsEditedBankAccount() {
        // Arrange
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setIban("existingIBAN");

        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        bankAccountCreationDto.setIban("newIBAN");
        bankAccountCreationDto.setBic("newBIC");

        when(bankAccountRepository.existsByIban(any())).thenReturn(false);
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        // Act
        BankAccountEntity result = bankAccountService.editBankAccount(bankAccount, bankAccountCreationDto);

        // Assert
        assertNotNull(result);
        assertEquals(bankAccountCreationDto.getIban(), result.getIban());
        assertEquals(bankAccountCreationDto.getBic(), result.getBic());
    }


}
