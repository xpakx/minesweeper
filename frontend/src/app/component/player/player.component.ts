import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameInfo } from 'src/app/entity/game-info';
import { GameService } from 'src/app/service/game.service';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  public invalid: boolean = false;
  public message: string = '';

  public games: GameInfo[] = [];

  constructor(private service: GameService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      this.loadPlayer(routeParams.id);
    });  
  }

  loadPlayer(id: number): void {
    this.service.getGamesByPlayerId(id).subscribe(
      (response: GameInfo[]) => {
        this.games = response;
    },
    (error: HttpErrorResponse) => {
      if(error.status === 401) {
        localStorage.removeItem("token");
      }
      this.message = error.error.message;
      this.invalid = true;
    });
  }

  toGame(id: number) {
    this.router.navigate(['/games/'+id]);
  }

}
