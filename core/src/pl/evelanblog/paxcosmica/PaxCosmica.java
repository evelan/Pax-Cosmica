package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.scenes.GameStateManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxCosmica extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;
	public Sprite dim;
	public GameStateManager gsm;

	@Override
	public void create() {
		Assets.load();
		dim = new Sprite(Assets.dim, 0, 0, 1280, 768);
		gsm = new GameStateManager(this);
		setScreen(GameStateManager.mainMenu);
	}
}