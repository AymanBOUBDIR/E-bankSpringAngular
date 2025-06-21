package ma.enset.ebankingbackendibdcc.services;


import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import ma.enset.ebankingbackendibdcc.entities.CurrentAccount;
import ma.enset.ebankingbackendibdcc.entities.SavingAccount;
import ma.enset.ebankingbackendibdcc.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
  private BankAccountRepository bankAccountRepository;
    public void consulter() {
        BankAccount bankAccount = bankAccountRepository.findById("2332ca1c-f2e6-4eaa-a943-64cbeda24da7").orElse(null);
        if (bankAccount != null) {
            System.out.println("====================================================================");
            System.out.println("Balance: " + bankAccount.getBalance());
            System.out.println("Status: " + bankAccount.getStatus());
            System.out.println("Date de création: " + bankAccount.getCreatedAt());
            System.out.println("Client: " + bankAccount.getCustomer().getName());
            System.out.println("Type de compte: " + bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Overdraft: " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Interest Rate: " + ((SavingAccount) bankAccount).getInterestRate());
            }

            System.out.println("================ Opérations =========================");
            bankAccount.getAccountOperations().forEach(accountOperation -> {
                System.out.println(accountOperation.getDateOperation() + " " +
                        accountOperation.getAmount() + " " +
                        accountOperation.getType());
            });
        }

    }
}