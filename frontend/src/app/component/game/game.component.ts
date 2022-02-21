import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Game } from 'src/app/entity/game';
import { MoveRequest } from 'src/app/entity/move-request';
import { Position } from 'src/app/entity/position';
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
  public position: number[][] = [];

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
        this.createBoard();
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  toPlayer(id: number): void {
    this.router.navigate(['/players/'+id]);
  }

  move(x: number, y: number): void {
    let request: MoveRequest = {x: x, y: y};
    this.service.move(this.game.id, request).subscribe(
      (response: Position[]) => {
        //TODO
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  createBoard(): void {
    this.position = new Array<[number, number]>(this.game.height);
    for(let i=0;i<this.game.height;i++) {
      this.position[i] = new Array<number>(this.game.width);
    }

    this.game.positions.forEach((a) => this.position[a.x][a.y] = a.number);
  }
}
