import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Game } from './entity/game';
import { GameRequest } from './entity/game-request';
import { GameService } from './service/game.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'minesweeper';
  
  constructor(private service: GameService, private router: Router) { }

  newGame(width: number, height: number) {
    let request: GameRequest = {width: width, height: height};
    let playerId: number = Number(localStorage.getItem("user_id"));
    this.service.newGame(playerId, request).subscribe(
      (response: Game) => {
        this.router.navigate(['/games/'+response.id]);
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      //TODO
    });
  }
}
