package ma.enset.ebankingbackendibdcc.dtos;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import ma.enset.ebankingbackendibdcc.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDTO  extends BankAccountDTO {
    private String  id;
    private  double balance;
    private Date createdAt;
    private AccountStatus Status;
    @ManyToOne
    private CustomerDTO customerDTO;
    private double OverDraft;
}
