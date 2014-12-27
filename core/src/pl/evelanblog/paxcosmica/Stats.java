package pl.evelanblog.paxcosmica;

/**
 * Statystyki dostępne bez konieczności tworzenia obiektu, bo to byłoby bez sensu.
 * @author Evelan
 *
 */

public class Stats {

	public static int score = 0;
	public static int scrap = 0;
	public static float fuel = 100f;
	public static int kills = 0;

	public static void clear()
	{
		kills = 0;
		score = 0;
		scrap = 0;
		fuel = 100f;
	}
}
