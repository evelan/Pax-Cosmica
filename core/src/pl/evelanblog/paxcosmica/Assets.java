package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture galaxy, stars, stars2;
	public static Texture spaceship;
	public static Texture knob;
	public static Texture bullet;
	public static Texture asteroid_1, asteroid_2, asteroid_3;
	public static Texture button;
	public static Texture booster;
	public static Texture enemy;
	public static Texture hullBar;
	public static Texture shieldBar;
	public static Sound hit, shoot, explosion, passing, powerup;
	public static Music track1, track2;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		galaxy = loadTexture("galaxy.png");
		stars = loadTexture("stars-pow2.png");
		stars2 = loadTexture("stars-pow2.png");
		knob = loadTexture("knob.png");
		bullet = loadTexture("bullet.png");
		button = loadTexture("button.png");
		asteroid_1 = loadTexture("asteroid_1.png");
		asteroid_2 = loadTexture("asteroid_2.png");
		asteroid_3 = loadTexture("asteroid_3.png");
		booster = loadTexture("booster.png");
		enemy = loadTexture("enemy.png");
		hullBar = loadTexture("hull.png");
		shieldBar = loadTexture("shield.png");

		spaceship = loadTexture("spaceship.png");
		hit = Gdx.audio.newSound(Gdx.files.internal("sfx/hit.wav"));
		shoot = Gdx.audio.newSound(Gdx.files.internal("sfx/shoot.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion.wav"));
		passing = Gdx.audio.newSound(Gdx.files.internal("sfx/spaceship-passing.wav"));
		powerup = Gdx.audio.newSound(Gdx.files.internal("sfx/powerup.wav"));

		track1 = Gdx.audio.newMusic(Gdx.files.internal("music/track01.mp3"));
		track2 = Gdx.audio.newMusic(Gdx.files.internal("music/track02.mp3"));
	}

	public static void playSound(Sound sound) {
		sound.play();
	}

	public static void playMusic(Music music) {
		music.play();
	}
}