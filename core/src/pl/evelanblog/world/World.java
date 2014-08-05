package pl.evelanblog.world;

import java.util.ArrayList;
import java.util.ListIterator;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.booster.Booster;
import pl.evelanblog.enemy.fighter.Enemy;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.DynamicObject;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Player;
import pl.evelanblog.paxcosmica.control.Joystick;
import pl.evelanblog.scenes.LostScreen;
import pl.evelanblog.scenes.WinScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class World {

	private final PaxCosmica game;
	public Player player;
	public Rectangle stars;
	public Rectangle stars2;
	private Collider colider;

	public static ArrayList<DynamicObject> objectArray = new ArrayList<DynamicObject>();

	public boolean stageFinished = false;

	public static int score = 0;

	long startTime = TimeUtils.millis(), currentTime;
	long stageTime = 1 * (1000 * 60);

	float[] sleepTime = { 0, 0, 0, 0, 0, 0 }; // miejsce dla 6 czas�w

	public World(final PaxCosmica game) {
		this.game = game;
		player = new Player();
		
		colider = new Collider(player);

		objectArray = new ArrayList<DynamicObject>();

		stars = new Rectangle(0, 50, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars2 = new Rectangle(0, -50, Assets.stars2.getWidth(), Assets.stars2.getHeight());

		Assets.playMusic(Assets.track2);
	}

	public void update(float delta) {
		if (TimeUtils.timeSinceMillis(startTime) > stageTime) // end of stage
			stageFinished = true;

		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		updateBackground(delta); // scrolling background
		spawnObjects(delta); // spawning objetcs like asteroids
		updateObjects(delta); // update all objects

		if (player.isAlive()) {
			colider.checkPlayerCollision(objectArray);
			colider.checkBulletCollision(objectArray);
		} else if (!player.isAlive())
		{
			if (Gdx.input.isTouched())
				game.setScreen(new LostScreen(game));
		}

		if (stageFinished)
		{
			Gdx.app.log("Stage:", "finished");
			game.setScreen(new WinScreen(game));
		}
	}

	private void updateObjects(float delta) {
		player.update(delta); // update position

		ListIterator<DynamicObject> itr = objectArray.listIterator();
		while (itr.hasNext()) {
			DynamicObject obj = itr.next();
			if (obj instanceof Enemy)
			{
				Enemy e = (Enemy) obj;
				if (e.isAlive())
					e.update(delta);
				else
					itr.remove();
			} else if (obj instanceof Asteroid)
			{
				Asteroid a = (Asteroid) obj;
				if (a.isAlive())
					a.update(delta);
				else
					itr.remove();
			} else if (obj instanceof Bullet)
			{
				Bullet bullet = (Bullet) obj;
				if (bullet.isAlive()) {
					bullet.update(delta);
				} else if (!bullet.isAlive() && bullet != null) {
					itr.remove();
				}
			} else if (obj instanceof Booster)
			{
				Booster booster = (Booster) obj;
				if (booster.isAlive())
					booster.update(delta);
				else
					itr.remove();
			}

		}
	}

	private void spawnObjects(float delta) {

		if (Joystick.getHit() && sleepTime[0] > Player.getShootSpeed()) {
			objectArray.add(player.shoot());
			sleepTime[0] = 0;
		}

		if (sleepTime[1] > Asteroid.getSpawnTime()) {
			objectArray.add(new Asteroid());
			sleepTime[1] = 0;
		}

		if (sleepTime[2] > Enemy.getSpawnTime()) {
			objectArray.add(new Enemy());
			sleepTime[2] = 0;
		}

		ListIterator<DynamicObject> iter = objectArray.listIterator();
		while (iter.hasNext())
		{
			DynamicObject obj = iter.next();
			if (obj instanceof Enemy)
			{
				Enemy e = (Enemy) obj;
				e.setLastShoot(e.getLastShoot() + delta);
				if (e.getLastShoot() > e.getShootTime()) {
					iter.add(e.shoot());
					e.setLastShoot(0f);
				}
			}
		}

		if (sleepTime[4] > Booster.getSpawnTime()) {
			objectArray.add(new Booster());
			sleepTime[4] = 0;
		}
	}

	private void updateBackground(float delta) {
		stars.setX(stars.getX() - (delta * 20));
		if (stars.getX() + stars.getWidth() < 0)
			stars.setX(Gdx.graphics.getWidth());

		stars2.setX(stars2.getX() - (delta * 70));
		if (stars2.getX() + stars2.getWidth() < 0)
			stars2.setX(Gdx.graphics.getWidth());
	}
}
