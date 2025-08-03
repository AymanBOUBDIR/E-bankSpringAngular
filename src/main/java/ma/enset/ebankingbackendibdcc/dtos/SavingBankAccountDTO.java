package ma.enset.ebankingbackendibdcc.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankingbackendibdcc.entities.AccountOperation;
import ma.enset.ebankingbackendibdcc.entities.Customer;
import ma.enset.ebankingbackendibdcc.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String  id;
    private  double balance;
    private Date createdAt;
    private AccountStatus Status;
    @ManyToOne
    private CustomerDTO customerDTO;
    private double interestRate;
}
