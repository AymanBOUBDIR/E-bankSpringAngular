package ma.enset.ebankingbackendibdcc;

import ma.enset.ebankingbackendibdcc.entities.*;
import ma.enset.ebankingbackendibdcc.enums.AccountStatus;
import ma.enset.ebankingbackendibdcc.enums.OperationType;
import ma.enset.ebankingbackendibdcc.repositories.AccountOperationRepository;
import ma.enset.ebankingbackendibdcc.repositories.BankAccountRepository;
import ma.enset.ebankingbackendibdcc.repositories.CustomerRepository;
import ma.enset.ebankingbackendibdcc.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendIbdccApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendIbdccApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BankService bankService) {
        return args -> {
            bankService.consulter();
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
