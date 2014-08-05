package pl.evelanblog.paxcosmica;

import pl.evelanblog.scenes.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxCosmica extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;

	@Override
	public void create() {
		Assets.load();

		setScreen(new MainMenu(this));
	}
}