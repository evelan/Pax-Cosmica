package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Dave on 2015-02-06.
 */
public class PaxPreferences {

    // constants
    private static final String PREFS_NAME = "Pax Cosmica";
    private static final String PREF_MUSIC_VOLUME = "music.volume";
    private static final String PREF_SOUND_VOLUME = "sound.volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_KILLS = "kills";
    private static final String PREF_SCORE = "score";
    private static final String PREF_SCRAP = "scrap";
    private static final String PREF_HULL_LVL = "hull.lvl";
    private static final String PREF_HULL_PWR = "hull.pwr";
    private static final String PREF_SHIELD_LVL = "shield.lvl";
    private static final String PREF_SHIELD_PWR = "shield.pwr";
    private static final String PREF_POWER_LVL = "power.lvl";
    private static final String PREF_POWER_GENERATOR = "power.generator";
    private static final String PREF_WEAPON_LVL = "weapon.lvl";
    private static final String PREF_WEAPON_PWR = "weapon.pwr";
    private static final String PREF_ENGINE_LVL = "engine.lvl";
    private static final String PREF_ENGINE_PWR = "engine.pwr";


    protected static Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public static boolean getSoundEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPrefs().flush();
    }

    public static boolean getMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public static void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public static float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 1);
    }

    public static void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public static float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOLUME, 1);
    }

    public static void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOLUME, volume);
        getPrefs().flush();
    }

    public static int getKills() {
        return getPrefs().getInteger(PREF_KILLS, 0);
    }

    public static void setKills(int kills) {
        getPrefs().putInteger(PREF_KILLS, kills);
        getPrefs().flush();
    }

    public static int getScore() {
        return getPrefs().getInteger(PREF_SCORE, 0);
    }

    public static void setScore(int score) {
        getPrefs().putInteger(PREF_SCORE, score);
        getPrefs().flush();
    }

    public static int getScrap() {
        return getPrefs().getInteger(PREF_SCRAP, 0);
    }

    public static void setScrap(int scrap) {
        getPrefs().putInteger(PREF_SCRAP, scrap);
        getPrefs().flush();
    }

    public static float getHullLvl() {
        return getPrefs().getFloat(PREF_HULL_LVL, 1);
    }

    public static void setHullLvl(float hullLvl) {
        getPrefs().putFloat(PREF_HULL_LVL, hullLvl);
        getPrefs().flush();
    }

    public static int getHullPwr() {
        return getPrefs().getInteger(PREF_HULL_PWR, 1);
    }

    public static void setHullPwr(int hullPwr) {
        getPrefs().putInteger(PREF_HULL_PWR, hullPwr);
        getPrefs().flush();
    }

    public static float getShieldLvl() {
        return getPrefs().getFloat(PREF_SHIELD_LVL, 1);
    }

    public static void setShieldLvl(float shieldLvl) {
        getPrefs().putFloat(PREF_SHIELD_LVL, shieldLvl);
        getPrefs().flush();
    }

    public static int getShieldPwr() {
        return getPrefs().getInteger(PREF_SHIELD_PWR, 1);
    }

    public static void setShieldPwr(int shieldPwr) {
        getPrefs().putInteger(PREF_SHIELD_PWR, shieldPwr);
        getPrefs().flush();
    }

    public static float getPowerLvl() {
        return getPrefs().getFloat(PREF_POWER_LVL, 4);
    }

    public static void setPowerLvl(float powerLvl) {
        getPrefs().putFloat(PREF_POWER_LVL, powerLvl);
        getPrefs().flush();
    }

    public static float getPowerGenerator() {
        return getPrefs().getFloat(PREF_POWER_GENERATOR, 1);
    }

    public static void setPowerGenerator(float powerGenerator) {
        getPrefs().putFloat(PREF_POWER_GENERATOR, powerGenerator);
        getPrefs().flush();
    }

    public static float getWeaponLvl() {
        return getPrefs().getFloat(PREF_WEAPON_LVL, 1);
    }

    public static void setWeaponLvl(float weaponLvl) {
        getPrefs().putFloat(PREF_WEAPON_LVL, weaponLvl);
        getPrefs().flush();
    }

    public static int getWeaponPwr() {
        return getPrefs().getInteger(PREF_WEAPON_PWR, 1);
    }

    public static void setWeaponPwr(int weaponPwr) {
        getPrefs().putInteger(PREF_WEAPON_PWR, weaponPwr);
        getPrefs().flush();
    }

    public static float getEngineLvl() {
        return getPrefs().getFloat(PREF_ENGINE_LVL, 1);
    }

    public static void setEngineLvl(float engineLvl) {
        getPrefs().putFloat(PREF_ENGINE_LVL, engineLvl);
        getPrefs().flush();
    }

    public static int getEnginePwr() {
        return getPrefs().getInteger(PREF_ENGINE_PWR, 1);
    }

    public static void setEnginePwr(int enginePwr) {
        getPrefs().putInteger(PREF_ENGINE_PWR, enginePwr);
        getPrefs().flush();
    }

}