package ma.enset.ebankingbackendibdcc.repositories;

import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import ma.enset.ebankingbackendibdcc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    
}
