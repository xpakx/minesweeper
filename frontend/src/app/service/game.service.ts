import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Game } from '../entity/game';
import { GameInfo } from '../entity/game-info';
import { GameRequest } from '../entity/game-request';
import { MoveRequest } from '../entity/move-request';
import { Position } from '../entity/position';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public getGameById(id: number):  Observable<Game> {
    return this.http.get<Game>(`${this.apiServerUrl}/games/${id}`);
  }

  public getGamesByPlayerId(id: number):  Observable<GameInfo[]> {
    return this.http.get<GameInfo[]>(`${this.apiServerUrl}/players/${id}/games`);
  }

  public newGame(id: number, request: GameRequest):  Observable<Game> {
    return this.http.post<Game>(`${this.apiServerUrl}/players/${id}/games`, request);
  }

  public move(gameId: number, request: MoveRequest):  Observable<Position[]> {
    return this.http.post<Position[]>(`${this.apiServerUrl}/games/${gameId}/moves`, request);
  }
}