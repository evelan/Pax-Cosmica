package pl.evelanblog.world;

import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.Booster;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.enemy.Enemy;
import pl.evelanblog.enemy.Fighter;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.scenes.GameScreen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class World {

	private Collider colider;
	private Player player;
	private static Group objects;

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
		objects = new Group();
		player = new Player();
		colider = new Collider(player);

		for (int i = 0; i < 6; i++)
			sleepTime[i] = 0;

		state = GameState.ongoing;
	}

	public void update(float delta) {
		if (Stats.kills > 10)
			state = GameState.win;

		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		spawnObjects(delta); // spawning objects like asteroids
		updateObjects(delta); // update all objects

		if (player.isAlive()) {
			colider.checkPlayerCollision(objects);
			colider.checkBulletCollision(objects);
		} else if (!player.isAlive())
			state = GameState.defeat;
	}

	// w tej funkcji także spawnują się strzały wrogów!!
	private void updateObjects(float delta) { // aktualizacja wszystkich obiektów, pozycji itd
		player.update(delta); // update position

		for (Actor obj : objects.getChildren())
		{
			if (obj instanceof Enemy)
			{
				Enemy enemy = (Enemy) obj;
				if (enemy.isAlive())
					enemy.update(delta);
				else
					enemy.remove();

			} else if (obj instanceof Asteroid)
			{
				Asteroid a = (Asteroid) obj;
				if (a.isAlive())
					a.update(delta);
				else
					a.remove();

			} else if (obj instanceof Bullet)
			{
				Bullet bullet = (Bullet) obj;
				if (bullet.isAlive()) {
					bullet.update(delta);
				} else if (!bullet.isAlive() && bullet != null)
					bullet.remove();

			} else if (obj instanceof Booster)
			{
				Booster booster = (Booster) obj;
				if (booster.isAlive())
					booster.update(delta);
				else
					booster.remove();
			}
		}
	}

	private void spawnObjects(float delta) { // spownoawnie obiektów, oprócz pocisków wrogów, to się dzieje w update
		// TODO tutaj trzeba te ify jakoś skrócić za dużo podobnego kodu
		if (GameScreen.getHit() && sleepTime[0] > player.getShootFrequency() && player.ableToShoot()) {
			objects.addActor(player.shoot());
			sleepTime[0] = 0;

		}

		if (sleepTime[1] > Asteroid.getSpawnTime()) {
			objects.addActor(new Asteroid());
			sleepTime[1] = 0;

		}

		if (sleepTime[2] > Fighter.SPAWN_TIME) {
			objects.addActor(new Fighter());
			sleepTime[2] = 0;
		}

//		for (Actor obj : objects.getChildren())
//		{
//			if (obj instanceof Enemy)
//			{
//				Enemy e = (Enemy) obj;
//				e.setLastShoot(e.getLastShoot() + delta);
//				if (e.getLastShoot() > e.getShootTime()) {
//
//					objects.addActor(e.shoot());
//					e.setLastShoot(0f);
//				}
//			}
//		}
		if (sleepTime[4] > Booster.getSpawnTime()) {
			// objectArray.add(new Booster()); // wyłączyłem dodawanie boosterów, bo tak, bo ich nie potrzebuje teraz
			// objects.add(new Booster());
			sleepTime[4] = 0;
			// wasCreated=true;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public static Group getObjects()
	{
		return objects;
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
