package pl.evelanblog.paxcosmica;

public class Stats {

	public static int score = PaxPreferences.getScore();
	public static int scrap = PaxPreferences.getScrap();
	public static float fuel = 100f;
	public static int kills = PaxPreferences.getKills();
	public static int levelKills = 0;

	public static void clear() {
		levelKills = 0;
		kills = 0;
		score = 0;
		scrap = 0;
		fuel = 100f;
	}

	public static void save() {
		PaxPreferences.setKills(kills);
		PaxPreferences.setScore(score);
		PaxPreferences.setScrap(scrap);
	}
}
