/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.game;

import com.r3nny.seabatlle.client.core.actors.EnemyGameField;
import com.r3nny.seabatlle.client.core.actors.PlayerGameField;
import com.r3nny.seabatlle.client.core.controller.Bot;

public class SingleGame extends Game {
    private final Bot bot;

    public SingleGame() {
        player = new PlayerGameField(PLAYER_FIELD_X, FIELD_Y);
        enemy = new EnemyGameField(ENEMY_FIELD_X, FIELD_Y);
        bot = new Bot();
    }

    public boolean isShipsReady() {
        return player.isShipsReady() && enemy.isShipsReady();
    }

    public  void  update(){
        bot.update();
    }

}
