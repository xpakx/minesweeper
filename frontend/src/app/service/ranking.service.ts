import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RankedPlayer } from '../entity/ranked-player';

@Injectable({
  providedIn: 'root'
})
export class RankingService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public getRankedPlayers():  Observable<RankedPlayer[]> {
    return this.http.get<RankedPlayer[]>(`${this.apiServerUrl}/ranking`);
  }
}
