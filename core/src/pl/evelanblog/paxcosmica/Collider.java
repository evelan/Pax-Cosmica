package pl.evelanblog.paxcosmica;

import java.util.ArrayList;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.booster.Booster;
import pl.evelanblog.enemy.Enemy;

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
					player.kill();
				} else if (obj instanceof Booster) {
					obj.kill();
					Assets.playSound(Assets.powerupSfx);
				} else if (obj instanceof Bullet) {
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection())
					{
						player.hurt(bullet.getImpactDamage());
						obj.kill();
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
				Bullet bullet = (Bullet) dynamicObject;
				for (DynamicObject asteroid : array)
				{
					if (asteroid instanceof Asteroid)
					{
						// kolizja asteroidiy i pocisku
						if (asteroid.getBoundingRectangle().overlaps(bullet.getBoundingRectangle())) {
							asteroid.hurt(bullet.impactDamage);
							bullet.kill();
						}
					}
				}

				for (DynamicObject enemy : array)
				{
					if (enemy instanceof Enemy)
					{
						// kolizja wroga i pocisku
						if (enemy.getBoundingRectangle().overlaps(bullet.getBoundingRectangle()) && bullet.getDirection()) {
							enemy.hurt(bullet.impactDamage);
							bullet.kill();
						}
					}
				}
			}
		}
	}
}
