package ma.enset.ebankingbackendibdcc.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankingbackendibdcc.entities.BankAccount;
import ma.enset.ebankingbackendibdcc.enums.OperationType;

import java.util.Date;
@Data
public class AccountOperationDTO {
    private  Long id;
    private Date dateOperation;
    private  double amount;
    private OperationType type;
    private String description;
}
