import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameComponent } from './component/game/game.component';
import { LoginComponent } from './component/login/login.component';
import { PlayerComponent } from './component/player/player.component';
import { RankingComponent } from './component/ranking/ranking.component';
import { RegisterComponent } from './component/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'players/:username', component: PlayerComponent },
  { path: 'games/:id', component: GameComponent },
  { path: '', component: RankingComponent },
  { path: 'ranking', component: RankingComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
