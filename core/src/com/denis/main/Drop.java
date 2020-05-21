package com.denis.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    SpriteBatch mBatch;
    BitmapFont font; //shows text

    @Override
    public void create() {
            mBatch = new SpriteBatch();
            font = new BitmapFont();
            font.setColor(0f, 0f, 1f, 0.5f);
            font.getData().setScale(2);//setting text size
            this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBatch.dispose();
        font.dispose();
    }
}
