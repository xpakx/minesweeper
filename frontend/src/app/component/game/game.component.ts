import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Game } from 'src/app/entity/game';
import { GameService } from 'src/app/service/game.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  public invalid: boolean = false;
  public message: string = '';

  public game!: Game;

  constructor(private service: GameService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      this.loadGame(routeParams.id);
    });  
  }

  loadGame(id: number): void {
    this.service.getGameById(id).subscribe(
      (response: Game) => {
        this.game = response;
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  toPlayer(id: number) {
    this.router.navigate(['/players/'+id]);
  }
}
