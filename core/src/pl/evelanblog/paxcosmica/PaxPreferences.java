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

    protected static Preferences getPrefs()
    {
        return Gdx.app.getPreferences( PREFS_NAME );
    }
    public static boolean getSoundEnabled()
    {
        return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
    }
    public static boolean getMusicEnabled()
    {
        return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
    }
    public static float getMusicVolume(){
        return getPrefs().getFloat( PREF_MUSIC_VOLUME, 0.5f );
    }
    public static float getSoundVolume(){
        return getPrefs().getFloat( PREF_SOUND_VOLUME, 0.5f );
    }
    public static int getKills(){
        return getPrefs().getInteger( PREF_KILLS );
    }
    public static int getScore(){
        return getPrefs().getInteger(PREF_SCORE );
    }
    public static int getScrap(){
        return getPrefs().getInteger(PREF_SCRAP );
    }

    public static void setMusicEnabled(boolean musicEnabled )
    {
        getPrefs().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        getPrefs().flush();
    }

    public static void setSoundEnabled(boolean soundEnabled)
    {
        getPrefs().putBoolean( PREF_SOUND_ENABLED, soundEnabled );
        getPrefs().flush();
    }

    public static void setMusicVolume(float volume )
    {
        getPrefs().putFloat( PREF_MUSIC_VOLUME, volume );
        getPrefs().flush();
    }

    public static void setSoundVolume(float volume )
    {
        getPrefs().putFloat( PREF_SOUND_VOLUME, volume );
        getPrefs().flush();
    }

    public static void setKills(int kills)
    {
        getPrefs().putInteger(PREF_KILLS, kills);
        getPrefs().flush();
    }
    public static void setScore(int score)
    {
        getPrefs().putInteger(PREF_SCORE, score);
        getPrefs().flush();
    }
    public static void setScrap(int scrap)
    {
        getPrefs().putInteger(PREF_SCRAP, scrap);
        getPrefs().flush();
    }

}