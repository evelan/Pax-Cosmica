package pl.evelanblog.utilities;

/**
 * Created by Evelan on 18-02-2015 - 21:25
 */
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Planet;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.scenes.*;

public class GameManager {

	public static MainMenu mainMenu;
	public static GameScreen gameScreen;
	public static GalaxyMap galaxyMap;
	public static UpgradeScreen upgradeScreen;
	public static OptionsScreen options;
	public static CreditsScreen credits;
	private static Planet activePlanet;
	public static int levelKills = 1;
	private static MousePointer mousePointer;

	public GameManager(final PaxCosmica game) {

		mousePointer = new MousePointer();
		mainMenu = new MainMenu(game);
		galaxyMap = new GalaxyMap(game);
		gameScreen = new GameScreen(game);
		upgradeScreen = new UpgradeScreen(game);
		options = new OptionsScreen(game);
		credits = new CreditsScreen(game);
	}

	public static MousePointer getMouse() {
		return mousePointer;
	}

	public static Planet getActivePlanet() {
		return activePlanet;
	}

	public static void setActivePlanet(Planet activePlanet) {
		GameManager.activePlanet = activePlanet;
	}

}