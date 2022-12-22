/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.game;

import com.r3nny.seabatlle.client.core.actors.EnemyGameField;
import com.r3nny.seabatlle.client.core.actors.PlayerGameField;

/**Контейнер с статусом и полями*/
public class Game {
    public static final int FIELD_Y = 426;
    public static final int PLAYER_FIELD_X = 89;
    public static final int ENEMY_FIELD_X = 633;
    public static GameStatus status = GameStatus.SHIPS_STAGE;
    public static PlayerGameField player;
    public static EnemyGameField enemy;
}
