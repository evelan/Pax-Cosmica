package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
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
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	private final PaxCosmica game;
	private Sprite dimScreen;
	private DynamicObject player;
	private MySprite background;
	private MyButton attack, move, store, upgrade, exit, left, right;
	private MyFont score, scrap, fuel;
	private BitmapFont font;
	private Vector2 destiny;
	private Rectangle mousePointer;
	private float dimValue;
	private boolean portal = false;
	private ArrayList<Planet> planets;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		
		planets = new ArrayList<Planet>();
		
		background = new MySprite(Assets.mainmenu);
		
		game.getMapHud().addActor(background);
		
		attack = new MyButton("buttons/attackButton.png");
		move = new MyButton("buttons/moveButton.png");
		store = new MyButton("buttons/storeButton.png");
		upgrade = new MyButton(1520, 116, 400, 96, "buttons/upgradesButton.png");
		exit = new MyButton(1520, 20, 400, 96, "buttons/exitButton.png");
		dimScreen = new Sprite(Assets.dim, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		left = new MyButton(0,540,128,"left.png");
		right = new MyButton(1792,540,128,"right.png");
		game.getMapHud().addActor(left);
		game.getMapHud().addActor(right);
		game.getMapHud().addActor(upgrade);
		game.getMapHud().addActor(exit);
		
		
		destiny = new Vector2(-1, -1);
		mousePointer = new MousePointer();
		mousePointer.setSize(1);


		player = new Player(0, 0);
		player.getSprite().setTexture(Assets.spaceship);
		player.setScale(0.7f);

		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		
		score = new MyFont(font, "Score: "+Stats.score, 100, 1000);
		scrap = new MyFont(font, "Scrap: "+Stats.scrap, 300, 1000);
		fuel = new MyFont(font, "Fuel: "+Stats.fuel, 500, 1000);
		
		game.getMapHud().addActor(score);
		game.getMapHud().addActor(scrap);
		game.getMapHud().addActor(fuel);
		// tworzenie planet
		planets.add(new Planet(200, 200, 4, 0.10f, true, false, "Ice", "planet/ice.png", 0.05f));
		planets.add(new Planet(600, 540, 4, 0.01f, true, false, "Fire", "planet/fire.png", 0.01f));
		
		for(Planet obj: planets)
		game.getMapScene().addActor(obj);
		
		game.setActivePlanet(planets.get(0));
		
		game.getMapScene().addActor(attack);
		game.getMapScene().addActor(move);
		game.getMapScene().addActor(store);
		attack.setVisible(false);
		move.setVisible(false);
		store.setVisible(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for(Planet obj: planets)
			obj.update();
		game.getMapHud().draw();
		game.getMapScene().draw();
		dimScreen(delta);
		
	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dimScreen.draw(game.getMapScene().getBatch(), dimValue);
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


		if (game.getMouse().overlaps(attack.getButton().getBoundingRectangle()) && !portal)
		{
			destiny.set(game.getMouse().x, game.getMouse().y);
			player.setPosition(destiny.x, destiny.y);
			Assets.track1.stop();
			game.setScreen(GameStateManager.gameScreen);

		}else if(game.getMouse().overlaps(left.getButton().getBoundingRectangle())){
			game.getMapScene().getCamera().position.x-=1920;
		}
		else if(game.getMouse().overlaps(right.getButton().getBoundingRectangle())){
			game.getMapScene().getCamera().position.x+=1920;
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
		// Obsługa wyboru planet
		for (Planet obj : planets)
		{ 	
			obj.reset();
			if (game.getMouse().overlaps(obj.getSprite().getBoundingRectangle()))
			{
				attack.getButton().setPosition(obj.getX()-obj.getSprite().getWidth(), obj.getY());
				attack.setVisible(true);
				obj.setHover();
				game.setActivePlanet(obj);
			} 	 
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
