package pl.evelanblog.paxcosmica;

import java.util.ArrayList;

import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.Booster;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.enemy.Fighter;

/**
 * bardzo brzydkie sprawdzanie kolizji, szkoda czasu na zagłębianie się w to skoro działa 
 */

public class Collider {

	private Player player;

	public Collider(Player player)
	{
		this.player = player;
	}

	/**
	 * Sprawdza czy jakiś inny obiekt koliduje z graczem
	 * @param array
	 */
	public void checkPlayerCollision(ArrayList<DynamicObject> array) {

		for (DynamicObject obj : array)
		{
			if (obj.getBoundingRectangle().overlaps(player.getBoundingRectangle()))
			{
				if (obj instanceof Fighter || obj instanceof Asteroid) {
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

	/**
	 * Sprawdza czy strzały przeciwnika lub gracza kolidują z czymś
	 * @param array
	 */
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
							asteroid.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
				}

				for (DynamicObject enemy : array)
				{
					if (enemy instanceof Fighter)
					{
						// kolizja wroga i pocisku
						if (enemy.getBoundingRectangle().overlaps(bullet.getBoundingRectangle()) && bullet.getDirection()) {
							enemy.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
				}
			}
		}
	}
}
