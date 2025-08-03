import {Component, OnInit} from '@angular/core';
import {CustomerService} from '../services/customer-service';
import { HttpClientModule } from '@angular/common/http';
import {AsyncPipe, JsonPipe, NgIf} from '@angular/common';
import {observableToBeFn} from 'rxjs/internal/testing/TestScheduler';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Customer} from '../model/customer.model';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-customers',
  imports: [
    AsyncPipe,
    NgIf,
    ReactiveFormsModule,
  ],
  templateUrl: './customers.html',
  styleUrl: './customers.css'
})
export class Customers implements OnInit {
  errorMessage! : object ;
  customers! :Observable<Array<Customer>>;
  searchFromGroup: FormGroup | undefined;

  constructor(private customerService: CustomerService ,private fb : FormBuilder , private router: Router) {
    this.searchFromGroup = fb.group({
      keyword : this.fb.control(''),
    })
  }
ngOnInit() {
this.customers =this.customerService.getCustomers().pipe(
 catchError(error => {
   this.errorMessage = error.message;
   return throwError(error);
 })
);

}
  handleSearchCustomers() {
  let kw =this.searchFromGroup?.value.keyword;
    this.customers =this.customerService.searchCustomers(kw).pipe(
      catchError(error => {
        this.errorMessage = error.message;
        return throwError(error);
      })
    );
  }

  handleDeleteCustomers(c :Customer) {
    let conf =confirm('Are you sure?');
    if (!conf) return;
  this.customerService.deleteCustomer(c.id).subscribe( {
      next: (resp)=> {
      this.customers = this.customers.pipe(
        map(data =>{
          let index = data.indexOf(c);
          data.slice(index,1);
          return data;
        })
      )
    },
      error: err => {
         alert('err');
      }
     })
  }

  handleCustomerAccounts(c: Customer) {
    this.router.navigateByUrl('/customer-accounts/' + c.id, { state: { customer: c } });
  }

}
