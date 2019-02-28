package com.tcg.missitai.managers;

import com.tcg.lichengine.managers.TCGGameStateManager;
import com.tcg.missitai.gamestates.PlayState;
import com.tcg.missitai.gamestates.States;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class GameStateManager extends TCGGameStateManager<States> {

    public GameStateManager() {
        super();
        setState(States.Play);
    }

    @Override
    public void setState(States state) {
        if(currentState != null) currentState.dispose();
        switch (state) {
            case Play:
                this.currentState = new PlayState(this);
                break;
        }
    }
}
