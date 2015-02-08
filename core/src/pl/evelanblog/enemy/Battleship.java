package pl.evelanblog.enemy;

import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPreferences;
import pl.evelanblog.world.World;

/**
 *
 */

public class Battleship extends Enemy {

	public final static float SPAWN_TIME = 14f;

	public Battleship() {
		// (float speed, hp, shield, bulletSpeed, shootTime, impactDamage, SPAWN_TIME, String texture)
		super(80f, 8f, 4f, 600f, 3f, 400f, "enemy/battleship.png");
		shootTime += ((MathUtils.random(20)) / 10); // aby nie strzelały w takim samym odstępie czasu, małe urozmaicenie
		radius = MathUtils.random(20, 50);
		startY = MathUtils.random(0, Assets.worldHeight - radius);
	}

	@Override
	public void shoot() {
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() - (getHeight() / 6), bulletSpeed, false, 2f));
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() / 6, bulletSpeed, false, 2f));
        if(PaxPreferences.getSoundEnabled())
		    Assets.playSound(Assets.shootSfx);
		time = 0;
	}

}
