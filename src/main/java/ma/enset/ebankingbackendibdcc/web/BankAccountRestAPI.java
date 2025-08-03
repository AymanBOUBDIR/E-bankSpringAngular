package ma.enset.ebankingbackendibdcc.web;


import ma.enset.ebankingbackendibdcc.dtos.*;
import ma.enset.ebankingbackendibdcc.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackendibdcc.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackendibdcc.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackendibdcc.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;
    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable  String accountId) throws BankAccountNotFoundException {
     return  bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    List<BankAccountDTO> listBankAccounts() {
        return  bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory (@PathVariable  String accountId) throws BankAccountNotFoundException {
      return  bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory
            (@PathVariable  String accountId,
             @RequestParam(name="page", defaultValue ="0") int page,
             @RequestParam(name="size", defaultValue ="5") int size) throws BankAccountNotFoundException {
        return  bankAccountService.getaccountHistory(accountId,page,size);
    }
  @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
      this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
      return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(transferRequestDTO.getAccountSource()
                ,transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> getCustomerAccounts(@PathVariable Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomerAccounts(customerId);
    }

}
