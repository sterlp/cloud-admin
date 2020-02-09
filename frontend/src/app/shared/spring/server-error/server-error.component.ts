import { Component, OnInit, Input } from '@angular/core';
import { SpringErrorWrapper } from '../api/spring-error.model';

@Component({
  selector: 'app-server-error',
  templateUrl: './server-error.component.html'
})
export class ServerErrorComponent implements OnInit {

    @Input() error?: SpringErrorWrapper;
    constructor() { }

    ngOnInit(): void {
    }
}
