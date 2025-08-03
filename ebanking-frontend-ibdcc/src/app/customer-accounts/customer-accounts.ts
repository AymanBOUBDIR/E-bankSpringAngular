import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../model/customer.model';
import { NgForOf, NgIf} from '@angular/common';
import { Accounts } from '../accounts/accounts';
import { AccountsService } from '../services/accounts-service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-customer-accounts',
  imports: [
  ],
  templateUrl: './customer-accounts.html',
  styleUrls: ['./customer-accounts.css']  // fixed typo here
})
export class CustomerAccounts implements OnInit {
  customerID!: string;
  customer!: Customer;
  accounts: Accounts[] = [];  // store accounts array here

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountsService: AccountsService
  ) {
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras?.state && nav.extras.state['customer']) {
      this.customer = nav.extras.state['customer'] as Customer;
    }
  }

  ngOnInit(): void {
    this.customerID = this.route.snapshot.params['id'];
    this.loadAccounts(this.customerID);
  }

  loadAccounts(customerId: string): void {
    this.accountsService.getCustomerAccounts(customerId).subscribe({
      next: (data) => {
        this.accounts = data;
      },
      error: (err) => {
        console.error('Failed to load accounts', err);
      }
    });
  }
}
