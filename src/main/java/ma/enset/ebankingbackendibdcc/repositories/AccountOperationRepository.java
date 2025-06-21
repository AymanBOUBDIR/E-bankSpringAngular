package ma.enset.ebankingbackendibdcc.repositories;

import ma.enset.ebankingbackendibdcc.entities.AccountOperation;
import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    
}
