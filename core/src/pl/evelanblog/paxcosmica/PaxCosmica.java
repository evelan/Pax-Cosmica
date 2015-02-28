package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Game;
import pl.evelanblog.utilities.GameManager;

public class PaxCosmica extends Game {

	@Override
	public void create() {
		Assets.load();
		new GameManager(this);
		setScreen(GameManager.mainMenu);
	}
}