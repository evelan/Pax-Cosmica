package pl.evelanblog.world;

import java.util.ArrayList;
import java.util.ListIterator;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.booster.Booster;
import pl.evelanblog.enemy.fighter.Enemy;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.DynamicObject;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Player;
import pl.evelanblog.paxcosmica.control.Joystick;
import pl.evelanblog.scenes.LostScreen;
import pl.evelanblog.scenes.WinScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;

public class World {

	private final PaxCosmica game;
	public Player player;
	public Rectangle stars;
	public Rectangle stars2;
	public ParticleEffect explosion;
	public ParticleEffect hit;

	public static ArrayList<DynamicObject> objectArray = new ArrayList<DynamicObject>();

	public boolean stageFinished = false;

	public static int score = 0;

	float[] sleepTime = { 0, 0, 0, 0, 0, 0 }; // miejsce dla 6 czasów

	public World(final PaxCosmica game) {
		this.game = game;
		player = new Player();

		objectArray = new ArrayList<DynamicObject>();

		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal("data/explosion.p"), Gdx.files.internal(""));

		hit = new ParticleEffect();
		hit.load(Gdx.files.internal("data/hit.p"), Gdx.files.internal(""));

		stars = new Rectangle(0, 50, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars2 = new Rectangle(0, -50, Assets.stars2.getWidth(), Assets.stars2.getHeight());

		Assets.playMusic(Assets.track2);
	}

	public void update(float delta) {
		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		updateBackground(delta); // scrolling background
		spawnObjects(delta); // spawning objetcs like asteroids
		updateObjects(delta); // update all objects

		if (player.isAlive()) {
			checkPlayerCollision(objectArray);
			checkBulletCollision(objectArray);
		} else if (!player.isAlive())
		{
			if (Gdx.input.isTouched())
				game.setScreen(new LostScreen(game));
		} else if (stageFinished)
		{
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

	private void checkPlayerCollision(ArrayList<DynamicObject> array) {

		for (DynamicObject obj : array)
		{
			if (obj.overlaps(player))
			{
				if (obj instanceof Enemy || obj instanceof Asteroid) {
					Assets.playSound(Assets.explosion);
					explosion.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
					explosion.start();
					player.kill();
				} else if (obj instanceof Booster) {
					Assets.playSound(Assets.hit);
					hit.setPosition(obj.getX(), obj.getY() + (obj.getHeight() / 2));
					hit.start();
					obj.kill();
					player.hurt(50);

					if (!player.isAlive()) {
						Assets.playSound(Assets.explosion);
						explosion.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
						explosion.start();
					}
				} else if (obj instanceof Bullet) {
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection())
					{
						Assets.playSound(Assets.hit);
						hit.setPosition(obj.getX(), obj.getY() + (obj.getHeight() / 2));
						hit.start();
						obj.kill();
						player.hurt(50);

						if (!player.isAlive()) {
							Assets.playSound(Assets.explosion);
							explosion.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
							explosion.start();
						}
					}

				}
			}

		}
	}

	private void checkBulletCollision(ArrayList<DynamicObject> array) {
		for (DynamicObject dynamicObject : array)
		{
			if (dynamicObject instanceof Bullet)
			{
				for (DynamicObject asteroid : array)
				{
					if (asteroid instanceof Asteroid)
					{
						// kolizja asteroidiy i pocisku
						if (asteroid.overlaps(dynamicObject)) {
							Assets.playSound(Assets.hit);
							hit.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY() + (dynamicObject.getHeight() / 2));
							hit.start();
							dynamicObject.kill();
							asteroid.hurt(50);

							if (!asteroid.isAlive()) {
								Assets.playSound(Assets.explosion);
								explosion.setPosition(asteroid.getX() + (asteroid.getWidth() / 2), asteroid.getY() + (asteroid.getHeight() / 2));
								explosion.start();
								score += 10;
							}
						}
					}
				}

				for (DynamicObject enemy : array)
				{
					if (enemy instanceof Enemy)
					{
						Bullet bullet = (Bullet) dynamicObject;
						// kolizja wroga i pocisku
						if (enemy.overlaps(dynamicObject) && bullet.getDirection()) {
							Assets.playSound(Assets.hit);
							hit.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY() + (dynamicObject.getHeight() / 2));
							hit.start();
							dynamicObject.kill();
							enemy.hurt(50);

							if (!enemy.isAlive()) {
								Assets.playSound(Assets.explosion);
								explosion.setPosition(enemy.getX() + (enemy.getWidth() / 2), enemy.getY() + (enemy.getHeight() / 2));
								explosion.start();
								score += 10;
							}
						}
					}
				}
			}
		}
	}
}
