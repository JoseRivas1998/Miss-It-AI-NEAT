package com.tcg.missitai.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tcg.lichengine.entities.Entity;
import com.tcg.lichengine.managers.TCGValues;
import com.tcg.neat4j.ConnectionHistory;
import com.tcg.neat4j.FitnessFunction;
import com.tcg.neat4j.NeuralNetwork;

import java.util.List;

import static com.tcg.lichengine.TCGHelpers.*;
import static com.tcg.missitai.MyUtil.maxIndex;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class AIPlayer extends Entity implements FitnessFunction {

    public static class DECISIONS {
        public static final int NUM_CHOICES = 5;
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
        public static final int NONE = 4;
    }

    private NeuralNetwork brain;

    private float[] vision;
    private float[] outputs;
    private float liveTime;
    private float fitness;

    private float squareSize;

    private boolean alive;

    public static final int INPUTS = 8;
    public static final int OUTPUTS = DECISIONS.NUM_CHOICES;

    private Rectangle sensor;

    public AIPlayer() {
        super();
        vision = new float[INPUTS];
        outputs = new float[OUTPUTS];
        brain = new NeuralNetwork(INPUTS, OUTPUTS);
        this.liveTime = 0;
        this.fitness = 0;
        this.alive = true;
        squareSize = TCGValues.getFloat("square_size");
        setSize(squareSize, squareSize);
        setX(worldWidth() * .5f - squareSize * .5f);
        setY(worldHeight() * .5f - squareSize * .5f);
    }

    public void look(List<Enemy> enemies) {
        resetSensor();
        lookNorth(enemies);
        resetSensor();
        lookNorthEast(enemies);
        resetSensor();
        lookEast(enemies);
        resetSensor();
        lookSouthEast(enemies);
        resetSensor();
        lookSouth(enemies);
        resetSensor();
        lookSouthWest(enemies);
        resetSensor();
        lookWest(enemies);
        resetSensor();
        lookNorthWest(enemies);
    }

    private void lookNorthEast(List<Enemy> enemies) {
        // Look North East
        float toTopRight = distance(getX(), getY(), worldWidth(), worldHeight());
        for(int i = 0; i < toTopRight; i++) {
            sensor.y = getY() + i;
            sensor.x = getX() + i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[1] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[1] = distance(getX(), getY(), sensor.x, sensor.y);
        }
    }

    private void lookNorth(List<Enemy> enemies) {
        // Look north
        float toTop = worldHeight() - getTop();
        for(int i = 0; i < toTop; i++) {
            sensor.y = getTop() + i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[0] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[0] = sensor.y;
        }
    }

    private void lookEast(List<Enemy> enemies) {
        float toRight = worldWidth() - getRight();
        for(int i = 0; i < toRight; i++) {
            sensor.x = getRight() + i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[2] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[2] = sensor.y;
        }
    }

    private void lookSouthEast(List<Enemy> enemies) {
        float toBottomRight = distance(getX(), getY(), worldWidth(), 0);
        for(int i = 0; i < toBottomRight; i++) {
            sensor.x = getX() + i;
            sensor.y = getY() - i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[3] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[3] = sensor.y;
        }
    }

    private void lookSouth(List<Enemy> enemies) {
        float toBottom = getY();
        for(int i = 0; i < toBottom; i++) {
            sensor.y = getY() - i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[4] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[4] = sensor.y;
        }
    }

    private void lookSouthWest(List<Enemy> enemies) {
        float toBottomLeft = distance(getX(), getY(), 0, 0);
        for(int i = 0; i < toBottomLeft; i++) {
            sensor.y = getY() - i;
            sensor.x = getX() - i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[5] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[5] = sensor.y;
        }
    }

    private void lookWest(List<Enemy> enemies) {
        float toLeft = getX();
        for(int i = 0; i < toLeft; i++) {
            sensor.x = getX() - i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[6] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[6] = sensor.y;
        }
    }

    private void lookNorthWest(List<Enemy> enemies) {
        float toTopLeft = distance(getX(), getY(), 0, worldHeight());
        for(int i = 0; i < toTopLeft; i++) {
            sensor.x = getX() - i;
            sensor.y = getY() + i;
            boolean collided = false;
            for (Entity enemy : enemies) {
                if(enemy.collidingWith(sensor)) {
                    collided = true;
                    vision[7] = distance(enemy.getPosition(), new Vector2(sensor.x, sensor.y));
                    break;
                }
            }
            if(collided) break;
            vision[7] = sensor.y;
        }
    }

    private void resetSensor() {
        if(sensor == null) {
            sensor = new Rectangle(getBounds());
        } else {
            sensor.set(getBounds());
        }
    }

    public void think() {
        outputs = brain.feedForward(vision);
        int choice = maxIndex(outputs);
        switch (choice) {
            case DECISIONS.LEFT:
                setVelocity(-TCGValues.getFloat("square_speed"), 0);
                break;
            case DECISIONS.RIGHT:
                setVelocity(TCGValues.getFloat("square_speed"), 0);
                break;
            case DECISIONS.DOWN:
                setVelocity(0, -TCGValues.getFloat("square_speed"));
                break;
            case DECISIONS.UP:
                setVelocity(0, TCGValues.getFloat("square_speed"));
                break;
            case DECISIONS.NONE:
            default:
                setVelocity(0, 0);
                break;
        }

    }

    @Override
    public void update(float dt) {
        if(isAlive()) {
            think();
            addVelocityToPosition(dt);
            setX(clamp(getX(), 0, worldWidth() - getWidth()));
            setY(clamp(getY(), 0, worldHeight() - getHeight()));
            liveTime += dt;
        }
    }

    public void collisions(List<Enemy> enemies) {
        for (Entity enemy : enemies) {
            if(collidingWith(enemy)) {
                kill();
                break;
            }
        }
    }

    public float getFitness() {
        return fitness;
    }

    public NeuralNetwork getBrain() {
        return brain;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, ShapeRenderer sr, float dt) {
        sr.setColor(TCGValues.color("player_color"));
        sr.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void calcFitness() {
        this.fitness = this.liveTime * this.liveTime;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public AIPlayer crossover(AIPlayer parent) {
        AIPlayer child = new AIPlayer();
        child.brain = brain.crossover(parent.brain);
        return child;
    }

    public void mutate(List<ConnectionHistory> innovationHistory) {
        brain.mutate(innovationHistory);
    }

    public AIPlayer cloneForReplay() {
        AIPlayer child = new AIPlayer();
        child.brain = brain.clone();
        return child;
    }

}
