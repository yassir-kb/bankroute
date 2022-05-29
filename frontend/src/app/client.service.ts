import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Client} from './client';
import {environment} from 'src/environments/environment';
import {AccountType} from './account.type';
import {Account} from './account';
import {History} from './history';
import {Credit} from './credit';
import {Loan} from './loan';
import {Accounts} from './accounts';

@Injectable({providedIn: 'root'})
export class ClientService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {
    }

    public getClients(): Observable<Client[]> {
        return this.http.get<Client[]>(`${this.apiServerUrl}`);
    }

    public getClient(clientId: string): Observable<Client> {
        return this.http.get<Client>(`${this.apiServerUrl}/${clientId}`);
    }

    public addClient(client: Client): Observable<Client> {
        client.status = 'Prospect';
        return this.http.post<Client>(`${this.apiServerUrl}`, client);
    }

    public updateClient(client: Client): Observable<Client> {
        return this.http.put<Client>(`${this.apiServerUrl}/${client.id}`, client);
    }

    public deleteClient(clientId: string): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/${clientId}`);
    }

    public acceptClient(clientId: string, accType: AccountType): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/${clientId}/account`, accType);
    }

    public applyLoan(loan: Loan): Observable<void> {
        console.log(loan.amountloan);
        const credit: Credit = {amount: loan.amountloan, taxes: loan.taxes, delay: loan.delay};
        return this.http.post<void>(`${this.apiServerUrl}/${loan.idclientloan}/account/${loan.ibanloan}`, credit);
    }

    public getAccounts(clientId: string): Observable<Account[]> {
        return this.http.get<Account[]>(`${this.apiServerUrl}/${clientId}/account`);
    }

    public getHistories(clientId: string): Observable<History[]> {
        return this.http.get<History[]>(`${this.apiServerUrl}/${clientId}/account/history`);
    }
}
