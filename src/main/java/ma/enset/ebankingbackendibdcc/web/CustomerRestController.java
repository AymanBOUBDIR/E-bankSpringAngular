package ma.enset.ebankingbackendibdcc.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackendibdcc.dtos.CustomerDTO;
import ma.enset.ebankingbackendibdcc.entities.Customer;
import ma.enset.ebankingbackendibdcc.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackendibdcc.repositories.CustomerRepository;
import ma.enset.ebankingbackendibdcc.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {

    private final CustomerRepository customerRepository;
    private BankAccountService bankAccountService;
     @GetMapping("/customers")
     @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name ="keyword",defaultValue = "") String keyword) {
    List<CustomerDTO> customerDTOS= bankAccountService.searchCustomer("%"+keyword+"%");
    return customerDTOS;
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO customer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
         return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
          return  bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping ("/customer/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public  CustomerDTO updateCustomer(@PathVariable(name="id") Long customerId,@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public  void deleteCustomer( @PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
         bankAccountService.deleteCustomer(customerId);
    }


}
