package pl.evelanblog.enemy;

import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.world.World;

/**
 * Wielkie i ociężałe statki
 * 
 * @author Evelan
 *
 */

public class Bomber extends Enemy {

	public final static float SPAWN_TIME = 10f;

	public Bomber() {
		// (float speed, hp, shield, bulletSpeed, shootTime, impactDamage, SPAWN_TIME, String texture)
		super(60f, 5f, 0f, 400f, 3f, 400f, "enemy/bomber.png");

		shootTime += ((MathUtils.random(20)) / 10); // aby nie strzelały w takim samym odstępie czasu, małe urozmaicenie
		radius = MathUtils.random(5, 20);
		startY = MathUtils.random(0, Assets.worldHeight - radius);
	}

	@Override
	public void shoot() {
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() - (getHeight() / 6), bulletSpeed, false, 2f));
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() / 6, bulletSpeed, false, 2f));
		Assets.playSound(Assets.shootSfx);
		time = 0;
	}

}
