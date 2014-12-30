package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {
	public static Texture galaxy, stars, stars2;
	public static Texture spaceship;
	public static Texture knob;
	public static Texture bullet;
	public static Texture asteroid_1, asteroid_2, asteroid_3;
	public static Texture buttonA;
	public static Texture buttonB;
	public static Texture booster;
	public static Texture enemy;
	public static Texture hullBar;
	public static Texture shieldBar;
	public static Texture bubbleShield;
	public static Texture leftArrow;
	public static Texture rightArrow;
	
	public static Texture galaxyPlanet;
	public static Texture firePlanet;
	public static Texture icePlanet;
	public static Texture goldPlanet;
	public static Texture purplePlanet;
	public static Texture marsPlanet;
	public static Texture mars2Planet;
	public static Texture mars3Planet;
	public static Texture fire2Planet;
	public static Texture fire3Planet;
	public static Texture coldPlanet;
	public static Texture cold2Planet;
	public static Texture neptunePlanet;
	public static Texture staturnPlanet;
	public static Texture venusPlanet;
	public static Texture rockPlanet;
	public static Texture snowPlanet;
	
	

	public static Texture planet;
	public static Texture mainmenu;
	public static Texture paxCosmica;
	public static Texture down, up;
	
	public static Texture upgradeBar;
	public static Texture upgradeBtn;

	public static Texture pauseButton, unpauseButton;

	public static Sound hitSfx, shootSfx, explosionSfx, passingSfx, powerupSfx, clickSfx;
	public static Music track1, track2;

	public static Texture playButton;
	public static Texture optionsButton;
	public static Texture creditsButton;
	public static Texture attackButton;
	public static Texture exitButton;
	public static Texture discardButton;
	public static Texture applyButton;
	public static Texture continueButton;
	public static Texture powerButton;

	public static ParticleEffect explosionEffect;
	public static ParticleEffect hitEffect;
	public static ParticleEffect playerEngineEffect;
	public static ParticleEffect enemyEngineEffect;
	public static Texture dim;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {

		explosionEffect = new ParticleEffect();
		explosionEffect.load(Gdx.files.internal("data/explosion.p"), Gdx.files.internal(""));

		hitEffect = new ParticleEffect();
		hitEffect.load(Gdx.files.internal("data/hit.p"), Gdx.files.internal(""));

		playerEngineEffect = new ParticleEffect();
		playerEngineEffect.load(Gdx.files.internal("data/engine.p"), Gdx.files.internal(""));
		enemyEngineEffect = new ParticleEffect();
		enemyEngineEffect.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));

		galaxy = loadTexture("galaxy.png");
		stars = loadTexture("stars-pow2.png");
		stars2 = loadTexture("stars-pow2.png");
		knob = loadTexture("knob.png");
		bullet = loadTexture("bullet.png");
		buttonA = loadTexture("buttonA.png");
		buttonB = loadTexture("buttonB.png");
		asteroid_1 = loadTexture("asteroid_1.png");
		asteroid_2 = loadTexture("asteroid_2.png");
		asteroid_3 = loadTexture("asteroid_3.png");
		booster = loadTexture("booster.png");
		enemy = loadTexture("enemy.png");
		hullBar = loadTexture("hull.png");
		shieldBar = loadTexture("shield.png");
		spaceship = loadTexture("spaceship.png");
		bubbleShield = loadTexture("bubble_shield.png");
		dim = loadTexture("dim.png");
		
		firePlanet = loadTexture("planet/fire.png");
		icePlanet = loadTexture("planet/ice.png");
		coldPlanet = loadTexture("planet/cold.png");
		goldPlanet = loadTexture("planet/gold.png");
		purplePlanet = loadTexture("planet/purple.png");
		galaxyPlanet = loadTexture("planet/galaxy_planet.png");
		upgradeBar = loadTexture("power_bar.png");
		down = loadTexture("down.png");
		up = loadTexture("up.png");
		upgradeBtn = loadTexture("upgrade_button.png");

		planet = loadTexture("planet/planet_1.png");
		mainmenu = loadTexture("mainmenu.png");
		paxCosmica = loadTexture("pax_cosmica.png");

		pauseButton = loadTexture("pauseButton.png");
		unpauseButton = loadTexture("unpauseButton.png");

		playButton = loadTexture("buttons/playButton.png");
		optionsButton = loadTexture("buttons/optionsButton.png");
		creditsButton = loadTexture("buttons/creditsButton.png");
		attackButton = loadTexture("buttons/attackButton.png");
		exitButton = loadTexture("buttons/exitButton.png");
		continueButton = loadTexture("buttons/continueButton.png");
		discardButton = loadTexture("buttons/discardButton.png");
		applyButton = loadTexture("buttons/applyButton.png");
		powerButton = loadTexture("buttons/powerButton.png");
		
		hitSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/hit.wav"));
		shootSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/shoot.wav"));
		explosionSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion.wav"));
		passingSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/spaceship-passing.wav"));
		powerupSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/powerup.wav"));
		clickSfx = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.wav"));

		track1 = Gdx.audio.newMusic(Gdx.files.internal("music/zero_project.mp3"));
		track2 = Gdx.audio.newMusic(Gdx.files.internal("music/track02.mp3"));
	}

	public static void playSound(Sound sound) {
		sound.play();
	}
}