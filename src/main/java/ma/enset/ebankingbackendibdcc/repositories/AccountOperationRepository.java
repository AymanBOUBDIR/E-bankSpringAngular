package ma.enset.ebankingbackendibdcc.repositories;

import ma.enset.ebankingbackendibdcc.entities.AccountOperation;
import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    List<AccountOperation> findByBankAccount_Id(String AccountId);
    Page<AccountOperation> findByBankAccount_IdOrderByDateOperationDesc(String accountId, Pageable pageable);


    
}
