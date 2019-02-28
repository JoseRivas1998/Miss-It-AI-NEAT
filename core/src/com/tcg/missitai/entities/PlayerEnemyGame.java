package com.tcg.missitai.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.lichengine.managers.ActionTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class PlayerEnemyGame {

    private AIPlayer player;
    private List<Enemy> enemies;
    private ActionTimer enemySpawner;
    private boolean isBest;

    public PlayerEnemyGame() {
        this(new AIPlayer());
    }

    public PlayerEnemyGame(AIPlayer player) {
        this.player = player;
        enemies = new ArrayList<>();
        enemySpawner = new ActionTimer("enemy_time", ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY, () -> {
            enemies.add(new Enemy());
        });
        isBest = false;
    }

    public void handleInput(float dt) {
        player.look(enemies);
    }

    public void update(float dt) {
        player.update(dt);
        player.collisions(enemies);
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }
        enemySpawner.update(dt);
    }


    public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
        player.draw(sb, sr, dt);
        for (Enemy enemy : enemies) {
            enemy.draw(sb, sr, dt);
        }
    }

    public AIPlayer getPlayer() {
        return player;
    }
}
