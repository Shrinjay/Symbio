import { Component, OnInit } from '@angular/core';
import {ApiService} from '../api-service.service';
import {Sponsor} from '../Sponsor';
import {DomSanitizer} from '@angular/platform-browser'
import Plotly from 'plotly.js-dist'
@Component({
  selector: 'app-stats-view',
  templateUrl: './stats-view.component.html',
  styleUrls: ['./stats-view.component.css']
})
export class StatsViewComponent implements OnInit {
  established: Number
  negotiating: Number
  contacted: Number
  identified: Number

  constructor(private apiService: ApiService,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
     this.apiService.getSponsorNum({_status: "Established"}).subscribe(established => this.established = established)
      this.apiService.getSponsorNum({_status: "Negotiating"}).subscribe(negotiating => this.negotiating = negotiating)
      this.apiService.getSponsorNum({_status: "Contacted"}).subscribe(contacted => this.contacted=contacted)
      this.apiService.getSponsorNum({_status: "Identified"}).subscribe(identified=>this.identified=identified)
      this.generateCards()
  }

  generateCards()
  {
    let establishedData = [
      {
        type: "indicator",
        mode: "number",
        value: 400
      }
    ]
    let contactedData = [
      {
        type: "indicator",
        mode: "number",
        value: this.contacted,
      }
    ]
    
    let negotiatingData ={
      type: "indicator",
      mode: "number",
      value: this.negotiating,
      domain: { x: [0, 1], y: [0, 1] }
    }
    let identifiedData = {
      type: "indicator",
      mode: "number",
      value: this.negotiating,
      domain: { x: [0, 1], y: [0, 1] }
    }
    let layout = {
      paper_bgcolor: "lightgray",
      margin: { t: 0, b: 0, l: 0, r: 0 }
    }
    let config = {responsive: true}
    Plotly.newPlot('established', establishedData, layout, config)
  }

}
