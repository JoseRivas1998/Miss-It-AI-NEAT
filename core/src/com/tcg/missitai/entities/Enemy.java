package com.tcg.missitai.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.lichengine.entities.Entity;
import com.tcg.lichengine.managers.TCGValues;

import static com.tcg.lichengine.TCGHelpers.*;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class Enemy extends Entity {

    private String[] colors = {
        "enemy_1",
        "enemy_2",
        "enemy_3",
        "enemy_4",
        "enemy_5",
        "enemy_6",
    };

    private int color;

    public Enemy() {
        super();
        setSize(TCGValues.getFloat("square_size"), TCGValues.getFloat("square_size"));
        setX(MathUtils.random(0, worldWidth() - getWidth()));
        setY(0);
        float radians = MathUtils.random(MathUtils.PI / 6.0f, 5.0f * MathUtils.PI / 6.0f);
        float speed = TCGValues.getFloat("square_speed");
        setVelocity(radToVector2(radians, speed));
        color = MathUtils.random(colors.length - 1);
    }

    @Override
    public void update(float dt) {
        checkBounce();
        addVelocityToPosition(dt);
    }

    private void checkBounce() {
        if(getX() < 0) {
            setX(0);
            setVelocityX(-getVelocityX());
        }
        if(getRight() > worldWidth()) {
            setX(worldWidth() - getWidth());
            setVelocityX(-getVelocityX());
        }
        if(getY() < 0) {
            setY(0);
            setVelocityY(-getVelocityY());
        }
        if(getTop() > worldHeight()) {
            setY(worldHeight() - getHeight());
            setVelocityY(-getVelocityY());
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float dt) {
        shapeRenderer.setColor(TCGValues.color(colors[color]));
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {

    }
}
