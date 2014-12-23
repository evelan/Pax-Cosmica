package pl.evelanblog.world;

import java.util.ArrayList;
import java.util.ListIterator;

import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.Booster;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Fighter;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.scenes.GameScreen;

import com.badlogic.gdx.utils.TimeUtils;

public class World {

	private Collider colider;
	private Player player;
	private ArrayList<DynamicObject> objectArray; // wszystkie ruszające się obiekty w grze, TODO: przydałoby się
													// zamienić na hashsety bo szybsze

	public static enum GameState {
		ongoing, // gra się toczy i jest elegancko, szczelanie etc
		win,
		defeat,
		menu,
		powermanager,
		paused
	}

	private GameState state;
	private long startTime;
	private long stageTime;
	private float[] sleepTime = new float[6]; // tablica gdzie trzymam czasy poszczególnych rzeczy kiedy mają się
												// pojawiać na ekranie, asteroidy etc

	public World() {
		objectArray = new ArrayList<DynamicObject>();
		player = new Player();
		colider = new Collider(player);

		for (int i = 0; i < 6; i++)
			sleepTime[i] = 0;

		startTime = TimeUtils.millis();
		stageTime = 60000;

		state = GameState.ongoing;
	}

	public void update(float delta) {

		// if (TimeUtils.timeSinceMillis(startTime) > stageTime)
		// state = GameState.win;

		if(Stats.kills > 10)
		 state = GameState.win;

		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		spawnObjects(delta); // spawning objects like asteroids
		updateObjects(delta); // update all objects

		if (player.isAlive()) {
			colider.checkPlayerCollision(objectArray);
			colider.checkBulletCollision(objectArray);
		} else if (!player.isAlive())
			state = GameState.defeat;
	}

	private void updateObjects(float delta) {
		player.update(delta); // update position

		ListIterator<DynamicObject> itr = objectArray.listIterator();
		while (itr.hasNext()) {
			DynamicObject obj = itr.next();
			if (obj instanceof Fighter)
			{
				Fighter e = (Fighter) obj;
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

		if (sleepTime[2] > Fighter.getSpawnTime()) {
			objectArray.add(new Fighter());
			sleepTime[2] = 0;
		}

		ListIterator<DynamicObject> iter = objectArray.listIterator();
		while (iter.hasNext())
		{
			DynamicObject obj = iter.next();
			if (obj instanceof Fighter)
			{
				Fighter e = (Fighter) obj;
				e.setLastShoot(e.getLastShoot() + delta);
				if (e.getLastShoot() > e.getShootTime()) {
					iter.add(e.shoot());
					e.setLastShoot(0f);
				}
			}
		}

		if (sleepTime[4] > Booster.getSpawnTime()) {
			// objectArray.add(new Booster()); // wyłączyłem dodawanie boosterów, bo tak
			sleepTime[4] = 0;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<DynamicObject> getObjects()
	{
		return objectArray;
	}

	public GameState getState()
	{
		return state;
	}

	public void setState(GameState state)
	{
		this.state = state;
	}
}
