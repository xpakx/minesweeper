import { Position } from "@angular/compiler";
import { PlayerMin } from "./player-min";

export interface Game {
    id: number;
    width: number;
    height: number;
    won: boolean;
    lost: boolean;
    player: PlayerMin;
    positions: Position[];
}