import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer.model';
import {Accounts} from '../accounts/accounts';
import {AccountDetails} from '../model/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  backendHost : string="http://localhost:8084";
  constructor(private http: HttpClient) { }

  public  getAccounts (): Observable<Array<Accounts>> {
    return this.http.get <Array<Accounts>>(this.backendHost+"/accounts"); }

  public  getAccount(accountId:string ,page :number,size:number): Observable<AccountDetails> {
    return this.http.get <AccountDetails>(this.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size);
  }
  public  debit(accountId:string ,amount :number,description:String) {
    let data = { accountId: accountId, amount: amount, description: description };
    return this.http.post (this.backendHost+"/accounts/debit",data);
  }
  public  credit(accountId:string ,amount :number,description:String) {
    let data = { accountId: accountId, amount: amount, description: description };
    return this.http.post (this.backendHost+"/accounts/credit",data);
  }
  public  transfer(accountSource:string ,accountDestination:string ,amount :number,description:String) {
    let data = { accountSource, accountDestination, amount, description };
    return this.http.post (this.backendHost+"/accounts/transfer",data);
  }

  public getCustomerAccounts(customerId: string): Observable<Array<Accounts>> {
    return this.http.get<Array<Accounts>>(this.backendHost + "/customers/" + customerId + "/accounts");
  }





}
