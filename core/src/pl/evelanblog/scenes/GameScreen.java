package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.enums.GameState;
import pl.evelanblog.paxcosmica.*;
import pl.evelanblog.utilities.GameManager;
import pl.evelanblog.world.World;

public class GameScreen implements Screen {

	private final PaxCosmica game;
	private static Stage gameStage; // scena na której znajdują się statki, asteroidy, pociski oraz pociski
	private static HUD hud; // przyciski, knob, tekst oraz informacja o stanie shieldPos i hp
	private World world; // świat

	private static Background background; // tło

	SimpleParticleEffect explodeEffect, hitEffect;

	public GameScreen(final PaxCosmica game) {
		this.game = game;
		world = new World();

		gameStage = new Stage(game.getGameViewport());
		//hudStage = new Stage(game.getHudViewport());

		hud = new HUD(game, new StretchViewport(1920, 1080));

		background = new Background(GameManager.getActivePlanet().getBackground());

		//		hpBorder = new Button(15, 1025, 200, 40, Assets.barBorder);
		//		shieldBorder = new Button(15, 982, 0, 40, Assets.barBorder);

		hitEffect = new SimpleParticleEffect(Assets.hitEffect);
		explodeEffect = new SimpleParticleEffect(Assets.explosionEffect);


	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// ustawienie backgroundu zawsze na środku
		background.getBackground().setY(gameStage.getCamera().position.y - gameStage.getHeight() / 2);
		gameStage.draw(); //rysowanie stage z graczem, przeciwnikami i strzałami
		hud.draw(); // HUD, czyli wszystkie przycsiki i inne duperele

		// jeśli stan gry jest na ONGOING to update tła i reszty obiektów, jeśli nie to będziemy mieć efekt pauzy
		if (World.getState() == GameState.ongoing) {
			hud.setResume();
			background.update(GameManager.getActivePlanet().getRotationSpeed());
			world.update(delta);
		} else if (World.getState() == GameState.win) // wygrana i przechodzimy do mapy galaktyki
		{
			Assets.track2.stop();
			Assets.play(Assets.track1);
			//TODO powinno pokazać jakiś ekran że wygraliśmy i opcję do do wyjścia z gry, lub przejscia do galaktyki
			Stats.save();
			game.setScreen(GameManager.galaxyMap);
		} else if (World.getState() == GameState.defeat) // przegrana i rysujemy przycisk do wypierdalania za bramę
		{
			World.getPlayer().setVisible(false); // przestajemy wyświetlać gracza bo już zginął
			hud.defeatScreen();
			World.getPlayer().setVisible(false);
		} else if (World.getState() == GameState.powermanager) {
			hud.setPowerPanager();
		} else if (World.getState() == GameState.paused) {
			hud.setPause();
		}

	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		world.clear();

		Stats.levelKills = 0;

		// ADD SCENE ACTORS
		gameStage.addActor(background);
		Stats.levelKills = 0;
		//getHpBorder().setSize(200, 40);

		// ADD SCENE ACTORS
		gameStage.addActor(background);
		gameStage.addActor(hitEffect);
		gameStage.addActor(explodeEffect);
		gameStage.addActor(World.getObjects());
		gameStage.addActor(World.getPlayer());

		//		hud.addActor(hpBorder);
		//		hud.addActor(shieldBorder);

		hud.show();
		Assets.play(Assets.track2);
		World.setState(GameState.ongoing); // już wszystko zostało ustawione wiec możemy startować z grą
	}

	@Override
	public void hide() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		hud.dispose();
		gameStage.dispose();

	}

	public static Background getBackground() {
		return background;
	}

	//	public static Button getShieldBorder() {
	//		return shieldBorder;
	//	}

	public static Stage getGameStage() {
		return gameStage;
	}

	//	public static Stage getHudStage() {
	//		return hudStage;
	//	}
}