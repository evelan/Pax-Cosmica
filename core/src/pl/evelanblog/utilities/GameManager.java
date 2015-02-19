package pl.evelanblog.utilities;

/**
 * Created by Evelan on 18-02-2015.
 */

import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Planet;
import pl.evelanblog.scenes.*;

public class GameManager {

	public static MainMenu mainMenu;
	public static GameScreen gameScreen;
	public static GalaxyMap galaxyMap;
	public static UpgradeScreen upgradeScreen;
	public static OptionsScreen options;
	public static CreditsScreen credits;
	private static Planet activePlanet;
	public static int levelKills = 20;

	public GameManager(final PaxCosmica game) {

		mainMenu = new MainMenu(game);
		galaxyMap = new GalaxyMap(game);
		gameScreen = new GameScreen(game);
		upgradeScreen = new UpgradeScreen(game);
		options = new OptionsScreen(game);
		credits = new CreditsScreen(game);
	}

	public static Planet getActivePlanet() {
		return activePlanet;
	}

	public static void setActivePlanet(Planet activePlanet) {
		GameManager.activePlanet = activePlanet;
	}

}