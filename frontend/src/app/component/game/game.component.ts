import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Flag } from 'src/app/entity/flag';
import { Game } from 'src/app/entity/game';
import { MoveRequest } from 'src/app/entity/move-request';
import { MoveResponse } from 'src/app/entity/move-response';
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
  public positions: number[][] = [];
  public flags: Flag[] = [];

  public ended: boolean = false;
  public gameMessage: string = '';

  constructor(private service: GameService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      this.loadGame(routeParams.id);
    });  
  }

  loadGame(id: number): void {
    this.flags = [];
    this.service.getGameById(id).subscribe(
      (response: Game) => {
        this.game = response;
        if(this.game.player.username) {
          this.loadFlags(id);
        }
        this.createBoard();
        this.testGameEnd(response.lost, response.won);
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  toPlayer(username: String): void {
    this.router.navigate(['/players/'+username]);
  }

  move(x: number, y: number): void {
    if(this.isFlagged(x,y)) {
      return;
    }
    let request: MoveRequest = {x: x, y: y};
    let username: String | null = localStorage.getItem("user_id");
    if(username) {
      this.service.move(username, this.game.id, request).subscribe(
        (response: MoveResponse) => {
          this.redrawBoard(response.positions);
          this.testGameEnd(response.lost, response.won);
      },
      (error: HttpErrorResponse) => {
        if(error.status === 401) {
          localStorage.removeItem("token");
        }
        this.message = error.error.message;
        this.invalid = true;
      });
    }
  }

  createBoard(): void {
    this.positions = new Array<[number, number]>(this.game.height);
    for(let i=0;i<this.game.height;i++) {
      this.positions[i] = new Array<number>(this.game.width);
      for(let j=0;j<this.game.width;j++) {
        this.positions[i][j] = -1;
      }
    }

    this.game.positions.forEach((a) => this.positions[a.x][a.y] = a.number);
  }

  redrawBoard(positions: Position[]): void {
    positions.forEach((a) => this.positions[a.x][a.y] = a.number);
  }

  getColor(num: number): String {
    if(num == 1) {
      return "blue";
    }
    if(num == 2) {
      return "green";
    }
    if(num == 3) {
      return "red";
    }
    if(num == 4) {
      return "dark-blue";
    }
    if(num == 5) {
      return "brown";
    }
    if(num == 6) {
      return "sea-blue";
    }
    if(num == 7) {
      return "black";
    }
    if(num == 8) {
      return "grey";
    }
    if(num == 9) {
      return "bomb";
    }
    if(num == 10) {
      return "boom";
    }
    if(num == -1) {
      return "unclicked";
    }
    return "";
  }

  loadFlags(id: number): void {
    let username: String | null = localStorage.getItem("user_id");
    if(username) {
      this.service.getFlagsByGameId(username, id).subscribe(
        (response: Flag[]) => {
          this.flags = response;
      },
      (error: HttpErrorResponse) => {
        if(error.status === 401) {
          localStorage.removeItem("token");
        }
        this.message = error.error.message;
        this.invalid = true;
      });
    }
  }

  isFlagged(x: number, y: number): boolean {
    return this.flags.filter((a) => a.x == x && a.y == y).length > 0
  }

  flag(event: MouseEvent, x: number, y: number) {
    event.preventDefault();
    let username: String | null = localStorage.getItem("user_id");
    if(!username) {
      return;
    }
    let flags = this.flags.filter((a) => a.x == x && a.y == y);
    if(flags.length == 0) {
      let request: MoveRequest = {x: x, y: y};
      this.service.addFlag(username, this.game.id, request).subscribe(
        (response: Flag) => {
          this.flags.push(response);
      },
      (error: HttpErrorResponse) => {
        if(error.status === 401) {
          localStorage.removeItem("token");
        }
        this.message = error.error.message;
        this.invalid = true;
      });
      return;
    }
    let flag = flags[0];

    this.service.deleteFlag(username, this.game.id, flag.id).subscribe(
      (response: any, id: number = flag.id) => {
        this.flags = this.flags.filter((a) => a.id != id);
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  testGameEnd(lost: boolean, won: boolean): void {
    if(won) {
      this.ended = true;
      this.gameMessage = "You won!";
    }
    if(lost) {
      this.ended = true;
      this.gameMessage = "You lost!";
    }
  }
 }
