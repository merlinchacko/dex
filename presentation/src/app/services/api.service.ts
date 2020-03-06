import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from "rxjs";
import {catchError, retry} from "rxjs/operators";
import {MediaInfo} from "../models/media-info";

const localUrl = 'http://localhost:8080/media-search?inputTerm=';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {
  }

  getMediaList(inputTerm): Observable<any> {
    console.log("localUrl", localUrl + inputTerm);
    return this.http.get<MediaInfo[]>(localUrl + inputTerm)
               .pipe(
                  retry(1),
                  catchError((err) => {
                    console.log('error caught in service');
                    console.error(err);
                    return throwError(err);
                  })
                );
  }

}
