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

  get username(): String | null {
    return localStorage.getItem("user_id");
  }

  get isLogged(): boolean {
    return localStorage.getItem("token") ? true : false;
  }

  newGame(width: number, height: number) {
    let request: GameRequest = {width: width, height: height};
    let username: String | null = localStorage.getItem("user_id");
    if(username) {
      this.service.newGame(username, request).subscribe(
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

  toLogin() {
    this.router.navigate(["/login"]);
  }
}
