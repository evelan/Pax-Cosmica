package pl.evelanblog.scenes;

import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.MyButton;
import pl.evelanblog.paxcosmica.MyFont;
import pl.evelanblog.paxcosmica.MySprite;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Planet;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	private final PaxCosmica game;
	private Sprite player, dimScreen;
	private MySprite background;
	private MyButton attack, move, store, upgrade, exit, left, right;
	private MyFont score, scrap, fuel;
	private BitmapFont font;
	private Vector2 destiny;
	private Rectangle mousePointer;
	private Planet fire, ice;
	private float dimValue;
	private boolean portal = false;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		
		background = new MySprite(Assets.mainmenu);
		
		game.getHud().addActor(background);
		
		attack = new MyButton("buttons/attackButton.png");
		move = new MyButton("buttons/moveButton.png");
		store = new MyButton("buttons/storeButton.png");
		upgrade = new MyButton(1520, 116, 400, 96, "buttons/upgradesButton.png");
		exit = new MyButton(1520, 20, 400, 96, "buttons/exitButton.png");
		dimScreen = new Sprite(Assets.dim, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		left = new MyButton(0,540,128,"left.png");
		right = new MyButton(1792,540,128,"right.png");
		game.getHud().addActor(left);
		game.getHud().addActor(right);
		game.getHud().addActor(upgrade);
		game.getHud().addActor(exit);
		
		
		destiny = new Vector2(-1, -1);
		mousePointer = new MousePointer();
		mousePointer.setSize(1);


		player = new Player(0, 0);
		player.setTexture(Assets.spaceship);
		player.setScale(0.7f);

		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		
		score = new MyFont(font, "Score: "+Stats.score, 100, 1000);
		scrap = new MyFont(font, "Scrap: "+Stats.scrap, 300, 1000);
		fuel = new MyFont(font, "Fuel: "+Stats.fuel, 500, 1000);
		
		game.getHud().addActor(score);
		game.getHud().addActor(scrap);
		game.getHud().addActor(fuel);
		
		ice = new Planet(200, 200, 4, (float)0.05, true, false, "Ice", "planet/ice.png");
		fire = new Planet(600, 540, 4, (float)0.10, true, false, "Fire", "planet/fire.png");
		game.getScene().addActor(ice);
		game.getScene().addActor(fire);
		
		game.getScene().addActor(attack);
		game.getScene().addActor(move);
		game.getScene().addActor(store);
		attack.setVisible(false);
		move.setVisible(false);
		store.setVisible(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getHud().act(Gdx.graphics.getDeltaTime());
		game.getHud().draw();	
		ice.update();
		fire.update();
		game.getScene().draw();
		dimScreen(delta);
		
	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dimScreen.draw(game.getBatch(), dimValue);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		if (destiny.x != -1)
			player.setPosition(destiny.x, destiny.y);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Assets.playSound(Assets.clickSfx);
		screenY = Gdx.graphics.getHeight() - screenY;
		game.getMouse().setPosition(screenX, screenY);

//zrobić pętlę na liście planet ... rozwiazanie na szybko do sprawdzenia czy dzialalo :P
		if (game.getMouse().overlaps(ice.getSprite().getBoundingRectangle()))
		{
			attack.getButton().setPosition(ice.getX()-ice.getSprite().getWidth(), ice.getY());
			attack.setVisible(true);
			fire.reset();
			ice.setHover();
		}
		if (game.getMouse().overlaps(fire.getSprite().getBoundingRectangle()))
		{
			attack.getButton().setPosition(fire.getX()-fire.getSprite().getWidth(), fire.getY());
			attack.setVisible(true);
			ice.reset();
			fire.setHover();
		}
//////////////////////////////////////////////////////////////////////
		if (game.getMouse().overlaps(attack.getButton().getBoundingRectangle()) && !portal)
		{
			destiny.set(game.getMouse().x, game.getMouse().y);
			player.setPosition(destiny.x, destiny.y);
			game.setScreen(GameStateManager.gameScreen);

		}else if(game.getMouse().overlaps(left.getButton().getBoundingRectangle())){
			game.getScene().getCamera().position.x-=1920;
		}
		else if(game.getMouse().overlaps(right.getButton().getBoundingRectangle())){
			game.getScene().getCamera().position.x+=1920;
		}
		
		else if (game.getMouse().overlaps(move.getButton().getBoundingRectangle())) {
			destiny.set(game.getMouse().x, game.getMouse().y);
			player.setPosition(destiny.x, destiny.y);
		} else if (game.getMouse().overlaps(store.getButton().getBoundingRectangle()))
		{
			// TODO: displayStore();
		} else if (game.getMouse().overlaps(upgrade.getButton().getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.upgradeScreen);
			dispose();
		} else if (game.getMouse().overlaps(exit.getButton().getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.mainMenu);
			dispose();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
