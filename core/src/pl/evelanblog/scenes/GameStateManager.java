package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.PaxCosmica;

public class GameStateManager {

	public static MainMenu mainMenu;
	public static GameScreen gameScreen;
	public static GalaxyMap galaxyMap;
	public static UpgradeScreen upgradeScreen;

	public GameStateManager(final PaxCosmica game)
	{
		mainMenu = new MainMenu(game);
		galaxyMap = new GalaxyMap(game);
		gameScreen = new GameScreen(game);
		upgradeScreen = new UpgradeScreen(game);
	}

}
