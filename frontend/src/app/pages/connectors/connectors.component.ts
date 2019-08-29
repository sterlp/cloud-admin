import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Resources } from 'src/app/common/api/hateoas'

@Component({
  selector: 'app-connectors',
  templateUrl: './connectors.component.html',
  styleUrls: ['./connectors.component.scss']
})
export class ConnectorsComponent implements OnInit {

  constructor(private http: HttpClient) { }

  supportedConnectors;

  ngOnInit() {
    this.getSupported().subscribe(result => {
      this.supportedConnectors = result._embedded.supportedConnectors;
      console.info(this, "init", this.supportedConnectors);
    });
  }

  getSupported (): Observable<Resources<Object>> {
    return this.http.get("api/connectors/supported");
  }
}
