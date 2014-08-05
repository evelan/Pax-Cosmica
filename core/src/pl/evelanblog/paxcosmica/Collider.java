package pl.evelanblog.paxcosmica;

import java.util.ArrayList;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.booster.Booster;
import pl.evelanblog.enemy.fighter.Enemy;

public class Collider {

	private Player player;

	public Collider(Player player)
	{
		this.player = player;
	}

	//TODO: sprawdziæ czy da sie rpzekazywaæ dowolny typ objektu i potem go rozpoznaæ 
	public void testCheckCollision(ArrayList<DynamicObject> array, Class<?> coliderObject)
	{
		coliderObject.getClass();
		for(DynamicObject obj : array)
		{
			if(coliderObject.isInstance(player))
			{
				
			}
		}
	}
	
	public void checkPlayerCollision(ArrayList<DynamicObject> array) {

		for (DynamicObject obj : array)
		{
			if (obj.getBoundingRectangle().overlaps(player.getBoundingRectangle()))
			{
				if (obj instanceof Enemy || obj instanceof Asteroid) {
					Assets.playSound(Assets.explosionSfx);
					Assets.explosionEffect.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
					Assets.explosionEffect.start();
					player.kill();
				} else if (obj instanceof Booster) {
					Assets.playSound(Assets.hitSfx);
					Assets.hitEffect.setPosition(obj.getX(), obj.getY() + (obj.getHeight() / 2));
					Assets.hitEffect.start();
					obj.kill();
					player.hurt(50);

					if (!player.isAlive()) {
						Assets.playSound(Assets.explosionSfx);
						Assets.explosionEffect.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
						Assets.explosionEffect.start();
					}
				} else if (obj instanceof Bullet) {
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection())
					{
						Assets.playSound(Assets.hitSfx);
						Assets.hitEffect.setPosition(obj.getX(), obj.getY() + (obj.getHeight() / 2));
						Assets.hitEffect.start();
						obj.kill();
						player.hurt(50);

						if (!player.isAlive()) {
							Assets.playSound(Assets.explosionSfx);
							Assets.explosionEffect.setPosition(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2));
							Assets.explosionEffect.start();
						}
					}

				}
			}

		}
	}

	public void checkBulletCollision(ArrayList<DynamicObject> array) {
		for (DynamicObject dynamicObject : array)
		{
			if (dynamicObject instanceof Bullet)
			{
				for (DynamicObject asteroid : array)
				{
					if (asteroid instanceof Asteroid)
					{
						// kolizja asteroidiy i pocisku
						if (asteroid.getBoundingRectangle().overlaps(dynamicObject.getBoundingRectangle())) {
							Assets.playSound(Assets.hitSfx);
							Assets.hitEffect.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY() + (dynamicObject.getHeight() / 2));
							Assets.hitEffect.start();
							dynamicObject.kill();
							asteroid.hurt(50);

							if (!asteroid.isAlive()) {
								Assets.playSound(Assets.explosionSfx);
								Assets.explosionEffect.setPosition(asteroid.getX() + (asteroid.getWidth() / 2), asteroid.getY() + (asteroid.getHeight() / 2));
								Assets.explosionEffect.start();
								// score += 10;
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
						if (enemy.getBoundingRectangle().overlaps(dynamicObject.getBoundingRectangle()) && bullet.getDirection()) {
							Assets.playSound(Assets.hitSfx);
							Assets.hitEffect.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY() + (dynamicObject.getHeight() / 2));
							Assets.hitEffect.start();
							dynamicObject.kill();
							enemy.hurt(50);

							if (!enemy.isAlive()) {
								Assets.playSound(Assets.explosionSfx);
								Assets.explosionEffect.setPosition(enemy.getX() + (enemy.getWidth() / 2), enemy.getY() + (enemy.getHeight() / 2));
								Assets.explosionEffect.start();
								// score += 10;
							}
						}
					}
				}
			}
		}
	}

}
