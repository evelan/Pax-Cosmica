package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
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

	public static Texture planet;
	public static Texture mainmenu;
	public static Texture paxCosmica;

	public static Texture pauseButton, unpauseButton;

	public static Sound hitSfx, shootSfx, explosionSfx, passingSfx, powerupSfx, clickSfx;
	public static Music track1, track2;

	public static Texture playButton;
	public static Texture optionsButton;
	public static Texture creditsButton;
	public static Texture exitButton;

	public static ParticleEffect explosionEffect;
	public static ParticleEffect hitEffect;
	public static ParticleEffect playerEngineEffect;

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

		planet = loadTexture("planet_1.png");
		mainmenu = loadTexture("mainmenu.png");
		paxCosmica = loadTexture("pax_cosmica.png");

		pauseButton = loadTexture("pauseButton.png");
		unpauseButton = loadTexture("unpauseButton.png");

		playButton = loadTexture("buttons/playButton.png");
		optionsButton = loadTexture("buttons/optionsButton.png");
		creditsButton = loadTexture("buttons/creditsButton.png");
		exitButton = loadTexture("buttons/exitButton.png");

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