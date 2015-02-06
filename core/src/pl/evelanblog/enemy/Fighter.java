package pl.evelanblog.enemy;

import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.world.World;

/**
 * To ma być typowe mięso armatnie wroga, jakieś tam typowe stateczki co latajo
 * 
 * @author Evelan
 *
 */
public class Fighter extends Enemy {

	public final static float SPAWN_TIME = 6f;

	public Fighter() { // wiem mogłem wszystko dać do super ale nie chciałem zbyt długiego i pokręconego konstruktra
		// (float speed, hp, shield, bulletSpeed, shootTime, impactDamage, SPAWN_TIME, String texture)
		super(120f, 3f, 0, 600f, 2f, 150f, "enemy/fighter.png");

		shootTime += ((MathUtils.random(20)) / 10); // aby nie strzelały w takim samym odstępie czasu, małe urozmaicenie
		radius = MathUtils.random(30, 100);
		startY = MathUtils.random(0, Assets.worldHeight - radius);
	}
	
	public void shoot() {
		World.getObjects().addActor(new Bullet(getX(), getY() + (getHeight() / 2) - 4, bulletSpeed, false, 1f));
		Assets.playSound(Assets.shootSfx);
		time = 0;
	}
}
