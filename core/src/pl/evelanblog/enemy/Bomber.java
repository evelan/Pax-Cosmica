package pl.evelanblog.enemy;

import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * Wielkie i ociężałe statki
 * 
 * @author Evelan
 *
 */

public class Bomber extends Enemy {

	public final static float SPAWN_TIME = 20f;
	
	public Bomber() {
		// (float speed, hp, shield, bulletSpeed, shootTime, impactDamage, SPAWN_TIME, String texture)
		super(30f, 5f, 0f, 400f, 3f, 400f, "enemy/bomber.png");

		shootTime += ((MathUtils.random(20)) / 10); // aby nie strzelały w takim samym odstępie czasu, małe urozmaicenie
		radius = MathUtils.random(5, 20);
		startY = MathUtils.random(0, Gdx.graphics.getHeight() - radius);

		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
	}

	@Override
	public void shoot() {
//		World.getIterator().add(new Bullet(getX(), getY() + getHeight() - (getHeight() / 6), bulletSpeed, false, 2f));
//		World.getIterator().add(new Bullet(getX(), getY() + getHeight() / 6, bulletSpeed, false, 2f));
//		Assets.playSound(Assets.shootSfx);
//		time = 0;
	}

}
