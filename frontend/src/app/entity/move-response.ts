import { Position } from "./position";

export interface MoveResponse {
    positions: Position[];
    won: boolean;
    lost: boolean;
}