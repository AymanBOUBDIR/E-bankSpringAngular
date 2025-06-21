package ma.enset.ebankingbackendibdcc.repositories;

import ma.enset.ebankingbackendibdcc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
