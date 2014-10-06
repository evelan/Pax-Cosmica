package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxCosmica extends Game {

	public OrthographicCamera camera;
	public GameStateManager gsm;
	public SpriteBatch batch;
	public Sprite dim;


	@Override
	public void create() {
		Assets.load();
		dim = new Sprite(Assets.dim, 0, 0, 1280, 768);
		gsm = new GameStateManager(this);
		setScreen(GameStateManager.mainMenu);
	}
}