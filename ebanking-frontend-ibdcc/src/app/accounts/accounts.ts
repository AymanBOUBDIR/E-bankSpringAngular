import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AccountsService} from '../services/accounts-service';
import {catchError, Observable, throwError} from 'rxjs';
import {AccountDetails} from '../model/account.model';
import {AsyncPipe, DatePipe, DecimalPipe, JsonPipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {AuthService} from '../services/auth-service';


@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AsyncPipe,
    NgIf,
    DecimalPipe,
    NgForOf,
    DatePipe,
    NgClass,
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css'
})
export class Accounts implements OnInit {
  accountFormGroup! : FormGroup;
  currentPage: number =0;
  pageSize: number =5;
  accountObservable!: Observable<AccountDetails>;
  operationFormGroup!: FormGroup;
  errorMessage!: String
  constructor(private fb : FormBuilder,private accountService:AccountsService ,public authService:AuthService) {

  }
  ngOnInit() {
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control(''),
    });
    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control(null),
      amount: this.fb.control(0),
      description : this.fb.control(null),
      accountDestination : this.fb.control(null),
    })
  }

  handleSearchAccount() {
    let accountId: string = this.accountFormGroup.value.accountId;
    this.accountObservable =this.accountService.getAccount(accountId,this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err)
      }),
    );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
   let accountID = this.accountFormGroup.value.accountId;
   let operationType = this.operationFormGroup.value.operationType;
   let amount:number = this.operationFormGroup.value.amount;
   let description:String = this.operationFormGroup.value.description;
   let accountDestination:string=this.operationFormGroup.value.accountDestination;
   if(operationType === 'DEBIT') {
  this.accountService.debit(accountID,amount,description).subscribe({
    next: (data) => {
      alert('debit operation has been performed successfully.');
      this.handleSearchAccount()
      this.operationFormGroup.reset();
    },
    error :(err) =>{
      alert(err);
    }
    }
  );
   }else if(operationType === 'CREDIT') {
  this.accountService.credit(accountID,amount,description).subscribe({
      next: (data) => {
        alert('credit operation has been performed successfully.');
        this.handleSearchAccount()
        this.operationFormGroup.reset();

      },
      error :(err) =>{
        alert(err);
      }
    }
  );
   }else if(operationType === 'TRANSFER') {

  this.accountService.transfer(accountID,accountDestination,amount,description).subscribe({
    next: (data) => {
      alert('transfer operation has been performed successfully.');
      this.handleSearchAccount()
      this.operationFormGroup.reset();
    },
    error :(err) =>{
      alert(err);
    }
  })
   }

  }
}
