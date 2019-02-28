package com.tcg.missitai.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.lichengine.gamestates.GameState;
import com.tcg.lichengine.graphics.TCGViewport;
import com.tcg.lichengine.managers.ActionTimer;
import com.tcg.lichengine.managers.TCGGameStateManager;
import com.tcg.missitai.entities.AIPlayer;
import com.tcg.missitai.entities.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class PlayState extends GameState {

    private TCGViewport viewport;
    private AIPlayer player;
    private List<Enemy> enemies;
    private ActionTimer enemySpawner;

    public PlayState(TCGGameStateManager tcgGameStateManager) {
        super(tcgGameStateManager);
    }

    @Override
    protected void init() {
        viewport = new TCGViewport();
        player = new AIPlayer();
        enemies = new ArrayList<>();
        enemySpawner = new ActionTimer("enemy_time", ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY, () -> {
            enemies.add(new Enemy());
        });
    }

    @Override
    public void handleInput(float dt) {
        player.look(enemies);
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        player.collisions(enemies);
        for (Enemy enemy : enemies) {
            enemy.update(dt);
        }
        enemySpawner.update(dt);
    }

    @Override
    public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getProjectionMatrix());
        player.draw(sb, sr, dt);
        for (Enemy enemy : enemies) {
            enemy.draw(sb, sr, dt);
        }
        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.resize(width, height);
    }

    @Override
    public void dispose() {

    }
}
