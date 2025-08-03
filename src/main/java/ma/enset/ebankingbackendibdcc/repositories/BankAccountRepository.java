package ma.enset.ebankingbackendibdcc.repositories;

import ma.enset.ebankingbackendibdcc.entities.AccountOperation;
import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import ma.enset.ebankingbackendibdcc.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    List<BankAccount> findByCustomer(Customer customer);


}
