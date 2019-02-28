package com.tcg.missitai.entities;

import com.tcg.neat4j.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class Species {

    private List<AIPlayer> players;
    private float bestFitness;

    private AIPlayer champ;
    private float averageFitness;

    private int staleness;
    private NeuralNetwork rep;

    public Species() {
        players = new ArrayList<>();
        bestFitness = 0;
        averageFitness = 0;
        staleness = 0;
    }

    public Species(AIPlayer player) {
        this();
        players.add(player);
        bestFitness = player.getFitness();
        rep = player.getBrain().clone();
        champ = player.cloneForReplay();
    }

}
