import {Component, OnInit} from '@angular/core';
import {Client} from './client';
import {ClientService} from './client.service';
import {HttpErrorResponse} from '@angular/common/http';
import {NgForm} from '@angular/forms';
import {AccountType} from './account.type';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    public clients: Client[];
    public editClient: Client;
    public deleteClient: Client;
    public acceptClient: Client;
    public accType: AccountType;
    public stringAccountType: string;
    /*public accType: AccountType = new AccountType {
        accType: string[];
    };*/
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

    public onAddEmloyee(addForm: NgForm): void {
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

    public onUpdateEmloyee(client: Client): void {
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

    public onDeleteEmloyee(clientId: string | undefined): void {
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

    public onAcceptEmloyee(clientId: string, acctString: any): void {
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
        if (mode === 'edit') {
            this.editClient = client;
            button.setAttribute('data-target', '#updateClientModal');
        }
        if (mode === 'delete') {
            this.deleteClient = client;
            button.setAttribute('data-target', '#deleteClientModal');
        }
        if (mode === 'accept') {
            this.acceptClient = client;
            button.setAttribute('data-target', '#acceptClientModal');
        }
        container.appendChild(button);
        button.click();
    }
}
