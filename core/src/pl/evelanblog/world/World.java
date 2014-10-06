package pl.evelanblog.world;

import java.util.ArrayList;
import java.util.ListIterator;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.booster.Booster;
import pl.evelanblog.enemy.Enemy;
import pl.evelanblog.paxcosmica.Background;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.DynamicObject;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Player;
import pl.evelanblog.scenes.GameScreen;
import pl.evelanblog.scenes.LostScreen;
import pl.evelanblog.scenes.WinScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class World {

	private final PaxCosmica game;
	private Collider colider;
	private Background background;
	private Player player;
	private ArrayList<DynamicObject> objectArray;

	private long startTime;
	private long stageTime;
	private long pauseTime;
	private boolean stageFinished;
	private float[] sleepTime = new float[6];

	public World(final PaxCosmica game) {
		this.game = game;
		background = new Background();
	}

	public void prepare(int time)
	{
		objectArray = new ArrayList<DynamicObject>();
		player = new Player();
		colider = new Collider(player);

		for (int i = 0; i < 6; i++)
			sleepTime[i] = 0;

		pauseTime = 0;
		startTime = TimeUtils.millis();
		if (time == 0)
			stageTime = 30000;
		else
			stageTime = time * (1000 * 60);

		stageFinished = false;
	}

	public void update(float delta) {

		if (TimeUtils.timeSinceMillis(startTime) + pauseTime > stageTime)
			stageFinished = true;

		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		background.update(delta);
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
			game.setScreen(new WinScreen(game));
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

		if (GameScreen.getHit() && sleepTime[0] > player.getShootFrequency() && player.ableToShoot()) {
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
			//objectArray.add(new Booster());
			sleepTime[4] = 0;
		}
	}

	public Background getBackground() {
		return background;
	}

	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<DynamicObject> getObjects()
	{
		return objectArray;
	}
}
