import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RankedPlayer } from 'src/app/entity/ranked-player';
import { RankingService } from 'src/app/service/ranking.service';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {
  public invalid: boolean = false;
  public message: string = '';
  public ranking: RankedPlayer[] = [];

  constructor(private service: RankingService, private router: Router) { }

  ngOnInit(): void {
    this.service.getRankedPlayers().subscribe(
      (response: RankedPlayer[]) => {
        this.ranking = response;
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

  }
}
