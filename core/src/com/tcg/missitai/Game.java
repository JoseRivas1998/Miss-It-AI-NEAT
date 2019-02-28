package com.tcg.missitai;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.lichengine.LichengineGame;
import com.tcg.missitai.managers.GameStateManager;

public class Game extends ApplicationAdapter {

	private GameStateManager gsm;

	@Override
	public void create () {
		LichengineGame.init();
		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		gsm.handleInput(dt);
		gsm.update(dt);
		gsm.draw(dt);
	}

	@Override
	public void resize(int width, int height) {
		LichengineGame.resize(width, height);
		gsm.resize(width, height);
	}

	@Override
	public void dispose () {
		LichengineGame.dispose();
		gsm.dispose();
	}
}
