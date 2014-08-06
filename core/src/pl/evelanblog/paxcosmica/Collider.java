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
					obj.kill();
					Assets.playSound(Assets.powerupSfx);
				} else if (obj instanceof Bullet) {
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection())
					{
						Assets.playSound(Assets.hitSfx);
						Assets.hitEffect.setPosition(obj.getX(), obj.getY() + (obj.getHeight() / 2));
						Assets.hitEffect.start();
						player.hurt(bullet.getImpactDamage());
						obj.kill();

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
							Assets.hitEffect.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY()
									+ (dynamicObject.getHeight() / 2));
							Assets.hitEffect.start();
							asteroid.hurt(dynamicObject.impactDamage);
							dynamicObject.kill();

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
							Assets.hitEffect.setPosition(dynamicObject.getX() + dynamicObject.getWidth(), dynamicObject.getY()
									+ (dynamicObject.getHeight() / 2));
							Assets.hitEffect.start();
							enemy.hurt(dynamicObject.impactDamage);
							dynamicObject.kill();

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
