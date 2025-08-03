package ma.enset.ebankingbackendibdcc;

import jakarta.transaction.Transactional;
import ma.enset.ebankingbackendibdcc.dtos.BankAccountDTO;
import ma.enset.ebankingbackendibdcc.dtos.CurrentBankAccountDTO;
import ma.enset.ebankingbackendibdcc.dtos.CustomerDTO;
import ma.enset.ebankingbackendibdcc.dtos.SavingBankAccountDTO;
import ma.enset.ebankingbackendibdcc.entities.*;
import ma.enset.ebankingbackendibdcc.enums.AccountStatus;
import ma.enset.ebankingbackendibdcc.enums.OperationType;
import ma.enset.ebankingbackendibdcc.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackendibdcc.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackendibdcc.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackendibdcc.repositories.AccountOperationRepository;
import ma.enset.ebankingbackendibdcc.repositories.BankAccountRepository;
import ma.enset.ebankingbackendibdcc.repositories.CustomerRepository;
import ma.enset.ebankingbackendibdcc.services.BankAccountService;
import ma.enset.ebankingbackendibdcc.services.BankAccountServiceImpl;
import ma.enset.ebankingbackendibdcc.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@SpringBootApplication
public class EbankingBackendIbdccApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendIbdccApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(BankAccountService bankAccountService) {

        return args -> {
           Stream.of("Ayman","Hanaa","ilham").forEach(name -> {
               CustomerDTO customerDTO = new CustomerDTO();
               customerDTO.setName(name);
               customerDTO.setEmail(name + "@gmail.com");
             bankAccountService.saveCustomer(customerDTO);
           });
             bankAccountService.listCustomers().forEach(customer -> {
               try {
                   bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000,customer.getId());
                   bankAccountService.saveSavingBankAccount(Math.random()*12000,5.5,customer.getId());

               } catch (CustomerNotFoundException e) {
                   e.printStackTrace();
               }
           });
            List<BankAccountDTO> bankAccounts= bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i=0;i<10;i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO){
                        accountId =((SavingBankAccountDTO) bankAccount).getId();
                    }
                    else
                    {
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*9000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*1000,"Debit");
                }
            }
        };
    }

    // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            // Création des clients
            Stream.of("Ayman", "ahmed", "mohamed").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });

            // Création des comptes
            customerRepository.findAll().forEach(customer -> {
                // CurrentAccount
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(customer);
                currentAccount.setBalance(Math.random() * 10000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(90000);
                bankAccountRepository.save(currentAccount);

                // SavingAccount
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(customer);
                savingAccount.setBalance(Math.random() * 10000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(0.05);
                bankAccountRepository.save(savingAccount);
            });

            // Création des opérations
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setDateOperation(new Date());
                    accountOperation.setAmount(Math.random() * 10000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(bankAccount);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };
    }
}
