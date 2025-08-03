package ma.enset.ebankingbackendibdcc.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private List<AccountOperationDTO> accountOperationDTOS;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;



}
