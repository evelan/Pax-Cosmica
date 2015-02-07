package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.scenes.GameStateManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxCosmica extends Game {

	private GameStateManager gsm;
	private MousePointer mousePointer;
	private Planet activePlanet;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;

	@Override
	public void create() {
		Assets.load();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Assets.worldWidth, Assets.worldHeight);
		mousePointer = new MousePointer();

		gsm = new GameStateManager(this);
		setScreen(GameStateManager.mainMenu);

        try{PaxPreferences.getSoundEnabled();} catch(Exception e){PaxPreferences.setSoundEnabled(true);}
        try{PaxPreferences.getMusicEnabled();} catch(Exception e){PaxPreferences.setMusicEnabled(true);}
        try{PaxPreferences.getScore();} catch(Exception e){PaxPreferences.setScore(0);}
        try{PaxPreferences.getKills();} catch(Exception e){PaxPreferences.setKills(0);}
        try{PaxPreferences.getScrap();} catch(Exception e){PaxPreferences.setScrap(0);}
        try{PaxPreferences.getMusicVolume();} catch(Exception e){PaxPreferences.setMusicVolume(100);}
        try{PaxPreferences.getSoundVolume();} catch(Exception e){PaxPreferences.setSoundVolume(100);}

	}

	public MousePointer getMouse()
	{
		return mousePointer;
	}

	public GameStateManager getGsm()
	{
		return gsm;
	}

	public Planet getActivePlanet() {
		return activePlanet;
	}

	public void setActivePlanet(Planet activePlanet) {
		this.activePlanet = activePlanet;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}