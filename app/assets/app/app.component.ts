import { Component }          from '@angular/core';

@Component({
//  moduleId: module.id,
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <nav>
      <a routerLink="/contracts" routerLinkActive="active">Contracts</a>
    </nav>
    <router-outlet></router-outlet>
  `,
  styleUrls: ['assets/app/app.component.css'],
})
export class AppComponent {
  public title = 'Contracts';
}

/*
Copyright 2016 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/
