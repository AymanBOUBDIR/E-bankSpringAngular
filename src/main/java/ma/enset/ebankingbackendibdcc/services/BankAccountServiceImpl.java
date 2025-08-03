package ma.enset.ebankingbackendibdcc.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackendibdcc.dtos.*;
import ma.enset.ebankingbackendibdcc.entities.*;
import ma.enset.ebankingbackendibdcc.enums.OperationType;
import ma.enset.ebankingbackendibdcc.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackendibdcc.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackendibdcc.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackendibdcc.mappers.BankAccountMapperImpl;
import ma.enset.ebankingbackendibdcc.repositories.AccountOperationRepository;
import ma.enset.ebankingbackendibdcc.repositories.BankAccountRepository;
import ma.enset.ebankingbackendibdcc.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j

public class BankAccountServiceImpl implements BankAccountService {

    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private  CustomerRepository customerRepository;
    private  BankAccountMapperImpl DtoMapper;



    public BankAccountServiceImpl(AccountOperationRepository accountOperationRepository, BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.accountOperationRepository = accountOperationRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.DtoMapper = new BankAccountMapperImpl();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving customer ");
        Customer customer = DtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return DtoMapper.fromCustomer(savedCustomer);

    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found");

        CurrentAccount currentAccount = new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overdraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return DtoMapper.fromCurrentBankAccount(savedBankAccount);

    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found");

        SavingAccount savingBankAccount= new SavingAccount();

        savingBankAccount.setId(UUID.randomUUID().toString());
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setInterestRate(interestRate);
        savingBankAccount.setBalance(initialBalance);
        savingBankAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingBankAccount);
        return DtoMapper.fromSavingBankAccount(savedBankAccount);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> DtoMapper
                .fromCustomer(customer))
                .collect(Collectors.toList());
     return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(
                ()-> new BankAccountNotFoundException("bank account not found ")
        );
        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return DtoMapper.fromSavingBankAccount(savingAccount);
        }
        else
        {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return DtoMapper.fromCurrentBankAccount(currentAccount);
        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(
                ()-> new BankAccountNotFoundException("bank account not found ")
        );
        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setDateOperation(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);



    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(
                ()-> new BankAccountNotFoundException("bank account not found ")
        );
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setDateOperation(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

      debit(accountIdSource,amount,"Transfer"+accountIdDestination);
      credit(accountIdDestination,amount,"Transfer"+"From"+accountIdSource+"To"+accountIdDestination);

    }
  @Override
  public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS =bankAccounts.stream().map(bankAccount ->{
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return DtoMapper.fromSavingBankAccount(savingAccount);
             }
            else
            {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return DtoMapper.fromCurrentBankAccount(currentAccount);
            }



      }).collect(Collectors.toList());
      return bankAccountDTOS;
    }
  @Override
  public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
   Customer customer =customerRepository.findById(customerId)
           .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
      return DtoMapper.fromCustomer(customer);
   }
   @Override
   public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("updating customer ");
        Customer customer = DtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return DtoMapper.fromCustomer(savedCustomer);

    }
   @Override
   public void  deleteCustomer(Long customerId) throws CustomerNotFoundException {
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String account_id){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(account_id);
        return accountOperations.stream().map(op->DtoMapper.fromAccountOperation(op)
        ).collect(Collectors.toList());

   }

    @Override
    public AccountHistoryDTO getaccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null)  throw  new BankAccountNotFoundException("Bank Account not found");
      Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccount_IdOrderByDateOperationDesc(accountId, PageRequest.of(page, size));
      AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
      List<AccountOperationDTO> accountOperationDTOS =accountOperations.getContent().stream().map(op->DtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
      accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
      accountHistoryDTO.setAccountId(bankAccount.getId());
      accountHistoryDTO.setBalance(bankAccount.getBalance());
      accountHistoryDTO.setCurrentPage(page);
      accountHistoryDTO.setPageSize(size);
      accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
      return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
     List<Customer> customers = customerRepository.searchCustomer(keyword);
     List<CustomerDTO> customerDTOS = customers.stream().map(customer ->DtoMapper.fromCustomer(customer)).collect(Collectors.toList());
     return customerDTOS;
    }

    @Override
    public List<BankAccountDTO> getCustomerAccounts(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomer(customer);

        return bankAccounts.stream()
                .map(DtoMapper::fromBankAccount)
                .toList();
    }


}
