/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.actors;

/**Игровое поле противника*/
public class EnemyGameField extends GameField {
    public EnemyGameField(float x, float y) {
        super(x, y);
        createShipsAutomaticaly();
        super.setShipsReady(true);
    }
}
