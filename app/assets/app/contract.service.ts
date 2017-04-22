import {Injectable}       from '@angular/core';
import { Headers, Http }  from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Contract }       from './contract';

@Injectable()
export class ContractService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private contractUrl = 'api/contracts';

  constructor(private http: Http) {}

  public getContracts(): Promise<Contract[]> {
    return this.http.get(this.contractUrl)
      .toPromise()
      .then(response => response.json() as Contract[])
      .catch(this.handleError);
  }

  public delete(id: number): Promise<void> {
    const url = `${this.contractUrl}/${id}`;
    return this.http.delete(url, { headers: this.headers })
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    return Promise.reject(error.message || error);
  }
}
