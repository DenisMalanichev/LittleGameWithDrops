package com.denis.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

class MainMenuScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;

    public MainMenuScreen(Drop gam){
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        camera.update();

        game.mBatch.setProjectionMatrix(camera.combined);
        game.mBatch.begin();
        game.font.draw(game.mBatch, "Catch all drops!", 300, 250);
        game.font.draw(game.mBatch, "Tap to continue...", 300, 210);
        game.mBatch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameArea(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
