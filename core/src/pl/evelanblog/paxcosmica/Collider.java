package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.Booster;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Enemy;
import pl.evelanblog.dynamicobjects.Player;

public class Collider {

	private Player player;

	public Collider(Player player)
	{
		this.player = player;
	}

	public void checkPlayerCollision(Group objects) {

		for(Actor obj: objects.getChildren())
		{
			if (player.overlaps(obj))
			{
				if (obj instanceof Enemy || obj instanceof Asteroid) {
					player.kill();
				} 

				//Co to?
				else if (obj instanceof Booster) {
					Booster booster = (Booster) obj;
					booster.kill();
					Assets.playSound(Assets.powerupSfx);

				} else if (obj instanceof Bullet) {
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection())
					{
						player.hurt(bullet.getImpactDamage());
						bullet.kill();
					}
				}
			}
		}
	}

	/**
	 * Sprawdza czy strzały przeciwnika lub gracza kolidują z czymś
	 * @param array
	 */
	public void checkBulletCollision(Group objects) {
		
		for(int i = 0; i<objects.getChildren().size;i++)
		{
			Actor obj = objects.getChildren().get(i);
			if (obj instanceof Bullet)
			{
				Bullet bullet = (Bullet) obj;
				for (Actor actor2 : objects.getChildren())
				{
					
					if (actor2 instanceof Asteroid)
					{
						Asteroid asteroid = (Asteroid) actor2;
						// kolizja asteroidy i pocisku
						if (asteroid.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle())) {
							asteroid.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
					if (actor2 instanceof Enemy)
					{
						Enemy enemy = (Enemy) actor2;
						// kolizja wroga i pocisku
						if (enemy.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle()) && bullet.getDirection()) {
							enemy.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
				}
			}
		}
	}
}
