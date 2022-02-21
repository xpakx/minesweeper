import { PlayerMin } from "./player-min";
import { Position } from "./position";

export interface Game {
    id: number;
    width: number;
    height: number;
    won: boolean;
    lost: boolean;
    player: PlayerMin;
    positions: Position[];
}