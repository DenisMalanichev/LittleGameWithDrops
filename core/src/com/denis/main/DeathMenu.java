package com.denis.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

class DeathMenu implements Screen{
    final Drop game;
    int score;
    OrthographicCamera camera;
    public DeathMenu(final Drop game, int score) {
        this.game = game;
        this.score = score;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //setting background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
        camera.update();

        game.mBatch.setProjectionMatrix(camera.combined);
        //starting drawing
        game.mBatch.begin();

        game.font.draw(game.mBatch, "You died", 300, 250);
        game.font.draw(game.mBatch, "Tap to retry...", 300, 210);
        game.font.draw(game.mBatch, "Score is " + score ,  300, 170);
        game.mBatch.end();

        //restart game
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

    public void setScore(int score) {
        this.score = score;
    }
}
