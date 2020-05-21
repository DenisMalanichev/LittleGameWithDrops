package com.denis.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameArea implements Screen {

	private final Drop game;
	private SpriteBatch batch;//class for drawing sprites
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;//sounds less 10sec
	private Music rainMusic;//sounds more 10sec
	private OrthographicCamera camera;
	private Rectangle bucket;//size of texture of bucket
	private Rectangle drop;
	private Vector3 touchPosition;
	private Array<Rectangle> rainDrops;
	private long lastDrop;
	public int dropsGot;
	private int hp = 3;



	public GameArea(final Drop gam) {
		this.game = gam;

		camera =  new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		dropImage = new Texture("droplet.png");
		bucketImage = new Texture("bucket.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		rainDrops = new Array<>();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
		camera.update();

		game.mBatch.setProjectionMatrix(camera.combined);
		game.mBatch.begin();
		game.font.draw(game.mBatch, "Drops collected: " + dropsGot, 0, 480);
		game.font.draw(game.mBatch, "Heals points: " + hp, 600, 480);
		game.mBatch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle rainDrop: rainDrops){
			game.mBatch.draw(dropImage, rainDrop.x, rainDrop.y);

		}
		game.mBatch.end();

		if(Gdx.input.isTouched()){
			touchPosition = new Vector3();
			touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPosition);
			bucket.x = touchPosition.x - 64 / 2;

			if(bucket.x < 0){
				bucket.x = 0;
			}else if(bucket.x > 800 - 64 ){
				bucket.x = 800-64;
			}

		}


		if(TimeUtils.nanoTime() - lastDrop > 1000000000){
			spawnRainDrop();
		}
		Iterator<Rectangle> iterator = rainDrops.iterator();
		while(iterator.hasNext()){
			Rectangle rainDrop = iterator.next();
			rainDrop.y -= (200 + dropsGot*3) * Gdx.graphics.getDeltaTime();
			if(rainDrop.y + 64 < 0){
				hp -= 1;
				if(hp == 0){
					game.setScreen(new DeathMenu(game, dropsGot));
				}
				iterator.remove();
			}
			if(rainDrop.overlaps(bucket)){
				dropSound.play();
				dropsGot++;
				iterator.remove();
			}
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
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	private void spawnRainDrop(){
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0,  800 - 64);
		rainDrop.y = 480;
		rainDrop.width = 64;
		rainDrop.height = 64;
		rainDrops.add(rainDrop);
		lastDrop = TimeUtils.nanoTime();

	}


	@Override
	public void show() {
		rainMusic.play();
	}
}
