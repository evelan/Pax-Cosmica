package pl.evelanblog.world;

import java.util.ArrayList;
import java.util.ListIterator;

import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.Booster;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.enemy.Bomber;
import pl.evelanblog.enemy.Fighter;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.scenes.GameScreen;

public class World {

	private Collider colider;
	private Player player;
	private ArrayList<DynamicObject> objectArray; // wszystkie ruszające się obiekty w grze, TODO: przydałoby się
	private static ListIterator<DynamicObject> objIterator;

	// zamienić na hashsety bo szybsze czy coś

	public static enum GameState {
		ongoing, // gra się toczy i jest elegancko, szczelanie etc
		win,
		defeat,
		menu,
		powermanager,
		paused
	}

	private GameState state;
	private float[] sleepTime = new float[6]; // tablica gdzie trzymam czasy poszczególnych rzeczy kiedy mają się
												// pojawiać na ekranie, asteroidy etc

	public World() {
		objectArray = new ArrayList<DynamicObject>();
		player = new Player();
		colider = new Collider(player);

		for (int i = 0; i < 6; i++)
			sleepTime[i] = 0;

		state = GameState.ongoing;
	}

	public void update(float delta) {

		// if (TimeUtils.timeSinceMillis(startTime) > stageTime)
		// state = GameState.win;

		if (Stats.kills > 10)
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

	private void updateObjects(float delta) { // aktualizacja wszystkich obiektów, pozycji itd
		player.update(delta); // update position

		// TODO tutaj też trzeba te ify jakoś ogarnąć na bardziej czytelne
		objIterator = objectArray.listIterator();
		while (objIterator.hasNext()) {
			DynamicObject obj = objIterator.next();
			if (obj instanceof Fighter)
			{
				Fighter e = (Fighter) obj;
				if (e.isAlive())
					e.update(delta);

			} else if (obj instanceof Bomber)
			{
				Bomber e = (Bomber) obj;
				if (e.isAlive())
					e.update(delta);
			} else if (obj instanceof Asteroid)
			{
				Asteroid a = (Asteroid) obj;
				if (a.isAlive())
					a.update(delta);
				else
					objIterator.remove();
			} else if (obj instanceof Bullet)
			{
				Bullet bullet = (Bullet) obj;
				if (bullet.isAlive()) {
					bullet.update(delta);
				} else if (!bullet.isAlive() && bullet != null) {
					objIterator.remove();
				}
			} else if (obj instanceof Booster)
			{
				Booster booster = (Booster) obj;
				if (booster.isAlive())
					booster.update(delta);
				else
					objIterator.remove();
			}
		}
	}

	private void spawnObjects(float delta) { // spownoawnie obiektów

		// TODO tutaj trzeba te ify jakoś skrócić za dużo podobnego kodu
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

		// TODO: To jest spawnowanie pocisków od wrogów i chyba powinniśmy przenieść to do update() w klasie u wrogów
		// ListIterator<DynamicObject> iter = objectArray.listIterator();
		// while (iter.hasNext())
		// {
		// DynamicObject obj = iter.next();
		// if (obj instanceof Fighter)
		// {
		// Fighter e = (Fighter) obj;
		// e.setLastShoot(e.getLastShoot() + delta);
		// if (e.getLastShoot() > e.getShootTime()) {
		// iter.add(e.shoot());
		// e.setLastShoot(0f);
		// }
		// } else if (obj instanceof Bomber)
		// {
		// Bomber e = (Bomber) obj;
		// e.setLastShoot(e.getLastShoot() + delta);
		// if (e.getLastShoot() > e.getShootTime()) {
		// iter.add(e.shoot());
		// e.setLastShoot(0f);
		// }
		// }
		// }

		if (sleepTime[3] > Bomber.getSpawnTime()) {
			objectArray.add(new Bomber());
			sleepTime[3] = 0;
		}

		if (sleepTime[4] > Booster.getSpawnTime()) {
			// objectArray.add(new Booster()); // wyłączyłem dodawanie boosterów, bo tak
			sleepTime[4] = 0;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public static ListIterator<DynamicObject> getIterator()
	{
		return objIterator;
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
