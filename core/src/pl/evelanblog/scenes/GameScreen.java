package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.enums.GameState;
import pl.evelanblog.paxcosmica.*;
import pl.evelanblog.utilities.GameManager;
import pl.evelanblog.utilities.HUDController;
import pl.evelanblog.world.World;

public class GameScreen implements Screen {

	private static HUDController hudController; // przyciski, knob, tekst oraz informacja o stanie shieldPos i hp
	private static Background background; // tło
	private static Stage gameStage;
	private World world; // świat
	SimpleParticleEffect explodeEffect, hitEffect;

	public GameScreen(final PaxCosmica game) {
		world = new World();
		gameStage = new Stage(new StretchViewport(1920, 1080));
		hudController = new HUDController(game, new StretchViewport(1920, 1080));
		hitEffect = new SimpleParticleEffect(Assets.hitEffect);
		explodeEffect = new SimpleParticleEffect(Assets.explosionEffect);
		background = new Background(GameManager.getActivePlanet().getBackground());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		// ustawienie backgroundu zawsze na środku
		background.getBackground().setY(gameStage.getCamera().position.y - gameStage.getHeight() / 2);
		gameStage.draw(); //rysowanie stage z graczem, przeciwnikami i strzałami
		hudController.draw(); // HUD, czyli wszystkie przycsiki i inne duperele

		// jeśli stan gry jest na ONGOING to update tła i reszty obiektów, jeśli nie to będziemy mieć efekt pauzy
		if (World.getState() == GameState.ongoing) {
			hudController.setResume();
			background.update(GameManager.getActivePlanet().getRotationSpeed());
			world.update(delta);
		} else if (World.getState() == GameState.win) { // wygrana i przechodzimy do mapy galaktyki
			Assets.track2.stop();
			Assets.play(Assets.track1);
			//TODO powinno pokazać jakiś ekran że wygraliśmy i opcję do do wyjścia z gry, lub przejscia do galaktyki
			Stats.save();
			hudController.exitScreen();
		} else if (World.getState() == GameState.defeat) { // przegrana i rysujemy przycisk do wypierdalania za bramę
			World.getPlayer().setVisible(false); // przestajemy wyświetlać gracza bo już zginął
			hudController.exitScreen();
			World.getPlayer().setVisible(false);
		} else if (World.getState() == GameState.powermanager) {
			hudController.setPowerPanager();
		} else if (World.getState() == GameState.paused) {
			hudController.setPause();
		}

	}

	@Override
	public void show() {
		world.clear();
		gameStage.addActor(background);
		gameStage.addActor(background);
		gameStage.addActor(hitEffect);
		gameStage.addActor(explodeEffect);
		gameStage.addActor(World.getObjects());
		gameStage.addActor(World.getPlayer());
		hudController.show();
		Assets.play(Assets.track2);
	}

	public static float getCameraY() {
		return gameStage.getCamera().position.y;
	}

	public static void setCameraY(float y) {
		gameStage.getCamera().position.y = y;
	}

	public static Background getBackground() {
		return background;
	}

	@Override
	public void dispose() {
		hudController.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

}