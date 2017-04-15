import {Component, OnInit} from '@angular/core';
import {Router}            from '@angular/router';
import {Contract}          from './contract';
import {ContractService}   from './contract.service';

@Component({
//  moduleId: module.id,
  selector: 'my-contracts',
  templateUrl: 'assets/app/contracts.component.html',
  styleUrls: ['assets/app/contracts.component.css'],
})
export class ContractsComponent implements OnInit {
  public contracts: Contract[];
  public selectedContract: Contract;

  constructor(private contractService: ContractService,
              private router: Router) {
  }

  public ngOnInit(): void {
    this.getContracts();
  }

  public onSelect(contract: Contract) {
    this.selectedContract = contract;
  }

  public gotoDetail(): void {
    this.router.navigate(['/detail', this.selectedContract.id]);
  }

  private getContracts() {
    this.contractService
      .getContracts()
      .then(contracts => this.contracts = contracts);
  }

  private delete(contract: Contract) {
    this.contractService
      .delete(contract.id)
      .then(() => {
        this.contracts = this.contracts.filter(current => current !== contract);
        if (this.selectedContract === contract) { this.selectedContract = null; }
      });
  }
}
