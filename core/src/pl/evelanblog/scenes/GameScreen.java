package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.enums.GameState;
import pl.evelanblog.paxcosmica.*;
import pl.evelanblog.utilities.GameManager;
import pl.evelanblog.world.World;

public class GameScreen extends Stage implements Screen {

	private final PaxCosmica game;
	private static HUD hud; // przyciski, knob, tekst oraz informacja o stanie shieldPos i hp
	private static Background background; // tło
	private World world; // świat
	SimpleParticleEffect explodeEffect, hitEffect;
	private static Vector2 cameraPos;

	public GameScreen(final PaxCosmica game) {
		super(new StretchViewport(1920, 1080));
		this.game = game;
		world = new World();
		hud = new HUD(game, new StretchViewport(1920, 1080));
		hitEffect = new SimpleParticleEffect(Assets.hitEffect);
		explodeEffect = new SimpleParticleEffect(Assets.explosionEffect);
		cameraPos = new Vector2(getCamera().position.x, getCamera().position.y);
		background = new Background(GameManager.getActivePlanet().getBackground());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cameraPos.set(getCamera().position.x, getCamera().position.y);

		// ustawienie backgroundu zawsze na środku
		background.getBackground().setY(getCamera().position.y - getHeight() / 2);
		draw(); //rysowanie stage z graczem, przeciwnikami i strzałami
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
	public void show() {
		world.clear();
		addActor(background);
		addActor(background);
		addActor(hitEffect);
		addActor(explodeEffect);
		addActor(World.getObjects());
		addActor(World.getPlayer());
		hud.show();

		Assets.play(Assets.track2);
		World.setState(GameState.ongoing); // już wszystko zostało ustawione wiec możemy startować z grą
	}

	public static Background getBackground() {
		return background;
	}

	public static Vector2 getCameraPos() {
		return cameraPos;
	}

	@Override
	public void dispose() {
		hud.dispose();
		dispose();
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