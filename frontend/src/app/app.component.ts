import {Component, OnInit} from '@angular/core';
import {Client} from './client';
import {ClientService} from './client.service';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {NgForm} from '@angular/forms';
import {AccountType} from './account.type';
import {Account} from './account';
import {History} from './history';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    public clients: Client[];
    public accounts: Account[];
    public histories: History[];
    public editClient: Client;
    public deleteClient: Client;
    public acceptClient: Client;
    public consultClient: Client;
    public historyClient: Client;
    public client: Client;
    public stringAccountType: string;
    title: 'BankRoute';

    constructor(private clientService: ClientService) {
    }

    ngOnInit() {
        this.getClients();
    }

    public getClients(): void {
        this.clientService.getClients().subscribe(
            (response: Client[]) => {
                this.clients = response;
                console.log(this.clients);
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public getClient(clientId: string): void {
        this.clientService.getClient(clientId).subscribe(
            (response: Client) => {
                this.client = response;
                console.log(this.client);
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onAddClient(addForm: NgForm): void {
        document.getElementById('add-client-form').click();
        this.clientService.addClient(addForm.value).subscribe(
            (response: Client) => {
                console.log(response);
                this.getClients();
                addForm.reset();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
                addForm.reset();
            }
        );
    }

    public onUpdateClient(client: Client): void {
        this.clientService.updateClient(client).subscribe(
            (response: Client) => {
                console.log(response);
                this.getClients();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onDeleteClient(clientId: string | undefined): void {
        this.clientService.deleteClient(clientId).subscribe(
            (response: void) => {
                console.log(response);
                this.getClients();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onAcceptClient(clientId: string, acctString: any): void {
        const arrayName = acctString.toto.split(',');
        const test: AccountType = {accType: arrayName};
        this.clientService.acceptClient(clientId, test).subscribe(
            (response: void) => {
                console.log(response);
                this.getClients();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onConsultAccounts(clientId: string): void {
        this.clientService.getAccounts(clientId).subscribe((response: Account[]) => {
                this.accounts = response.accounts;
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onConsultHistories(clientId: string): void {
        console.log(clientId);
        this.clientService.getHistories(clientId).subscribe(
            (response: History[]) => {
                console.log(response);
                this.histories = response.histories;
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }

    public onLoan(loanForm: NgForm): void {
        document.getElementById('loan-client-form').click();
        console.log(loanForm.value);
        this.clientService.applyLoan(loanForm.value).subscribe(
            (response: void) => {
                console.log(response);
                this.getClients();
                loanForm.reset();
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
                loanForm.reset();
            }
        );
    }

    public searchClients(key: string): void {
        const results: Client[] = [];
        for (const client of this.clients) {
            if (client.id.toLowerCase().indexOf(key.toLowerCase()) !== -1
                || client.fname.toLowerCase().indexOf(key.toLowerCase()) !== -1
                || client.lname.toLowerCase().indexOf(key.toLowerCase()) !== -1
                || client.dob.toLowerCase().indexOf(key.toLowerCase()) !== -1
            ) {
                results.push(client);
            }
        }
        this.clients = results;
        if (results.length === 0 || !key) {
            this.getClients();
        }
    }

    public onOpenModal(client: Client, mode: string): void {
        const container = document.getElementById('main-container');
        const button = document.createElement('button');
        button.type = 'button';
        button.style.display = 'none';
        button.setAttribute('data-toggle', 'modal');
        if (mode === 'add') {
            button.setAttribute('data-target', '#addClientModal');
        }
        if (mode === 'account') {
            button.setAttribute('data-target', '#getAccountsModal');
        }
        if (mode === 'history') {
            button.setAttribute('data-target', '#historyModal');
        }
        if (mode === 'loan') {
            button.setAttribute('data-target', '#loanModal');
        }
        if (mode === 'edit') {
            this.editClient = client;
            button.setAttribute('data-target', '#updateClientModal');
        }
        if (mode === 'consultclient') {
            this.consultClient = client;
            button.setAttribute('data-target', '#consultClientModal');
        }
        if (mode === 'delete') {
            this.deleteClient = client;
            button.setAttribute('data-target', '#deleteClientModal');
        }
        if (mode === 'accept') {
            this.acceptClient = client;
            button.setAttribute('data-target', '#acceptClientModal');
        }
        if (mode === 'consultaccounts') {
            this.onConsultAccounts(client.id);
            button.setAttribute('data-target', '#consultAccountsModal');
        }
        if (mode === 'consulthistory') {
            this.onConsultHistories(client.id);
            button.setAttribute('data-target', '#consultHistoryModal');
        }
        container.appendChild(button);
        button.click();
    }
}
