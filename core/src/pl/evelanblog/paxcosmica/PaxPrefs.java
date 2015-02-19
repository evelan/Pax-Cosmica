package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Dave on 2015-02-06.
 */
public class PaxPrefs {

	// constants
	public static final String PREFS_NAME = "Pax Cosmica";
	public static final String MUSIC_VOLUME = "music.volume";
	public static final String SOUND_VOLUME = "sound.volume";
	public static final String MUSIC_ENABLED = "music.enabled";
	public static final String SOUND_ENABLED = "sound.enabled";
	public static final String KILLS = "kills";
	public static final String SCORE = "score";
	public static final String SCRAP = "scrap";

	public static final String HULL_LVL = "hull.lvl";
	public static final String HULL_PWR = "hull.pwr";

	public static final String SHIELD_LVL = "shield.lvl";
	public static final String SHIELD_PWR = "shield.pwr";
	public static final String POWER_LVL = "power.lvl";
	public static final String POWER_PWR = "power.pwr";

	public static final String WEAPON_LVL = "weapon.lvl";
	public static final String WEAPON_PWR = "weapon.pwr";

	public static final String ENGINE_LVL = "engine.lvl";
	public static final String ENGINE_PWR = "engine.pwr";

	protected static Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}

	public static void putInt(String PREFS, int value) {
		getPrefs().putInteger(PREFS, value).flush();
	}

	public static int getInt(String PREFS, int defValue) {
		return getPrefs().getInteger(PREFS, defValue);
	}

	public static void putBoolean(String PREFS, boolean value) {
		getPrefs().putBoolean(PREFS, value).flush();
	}

	public static boolean getBoolean(final String PREFS, boolean defValue) {
		return getPrefs().getBoolean(PREFS, defValue);
	}
}