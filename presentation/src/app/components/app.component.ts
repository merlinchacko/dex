import {HttpErrorResponse} from "@angular/common/http";
import {Component, OnInit} from '@angular/core';
import {MediaInfo} from "../models/media-info";
import {ApiService} from "../services/api.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  inputTerm: string;
  mediaList: MediaInfo[] = [];
  loading: boolean = false;
  loaded: boolean = false;
  errorMessage;

  constructor(private api: ApiService) {
  }

  ngOnInit() {
  }

  getMediaList() {
    console.log('inside--')
    this.loading = true;
    this.errorMessage = "";
    this.api.getMediaList(this.inputTerm)
        .subscribe(resp => {
            Object.assign(this.mediaList, resp);
            this.loaded = true;
          },
          err => {
            console.error('error caught in component')
            this.errorMessage = err.error.message;
            this.loading = false;
            throw err;
          });
  }

}
