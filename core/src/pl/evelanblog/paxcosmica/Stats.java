package pl.evelanblog.paxcosmica;

public class Stats {

	public static int score;
	public static int scrap;
	public static float fuel;
	public static int kills;
	public static int levelKills = 0;

	public static void clear() {
		levelKills = 0;
		kills = 0;
		score = 0;
		scrap = 100; //do testów
		fuel = 100f;
	}

	public static void save() {
		PaxPrefs.putInt(PaxPrefs.KILLS, kills);
		PaxPrefs.putInt(PaxPrefs.SCORE, score);
		PaxPrefs.putInt(PaxPrefs.SCRAP, scrap);
	}

	public static void load() {
		PaxPrefs.getInt(PaxPrefs.KILLS, 0);
		PaxPrefs.getInt(PaxPrefs.SCORE, 0);
		PaxPrefs.getInt(PaxPrefs.SCRAP, 0);

	}
}
