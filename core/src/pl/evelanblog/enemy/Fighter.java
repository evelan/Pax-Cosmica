package pl.evelanblog.enemy;

import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * To ma być typowe mięso armatnie wroga, jakieś tam typowe stateczki co latajo
 * 
 * @author Evelan
 *
 */
public class Fighter extends Enemy {

	public Fighter() { // wiem mogłem wszystko dać do super ale nie chciałem zbyt długiego i pokręconego konstruktra
		// (float speed, hp, shield, bulletSpeed. impactDamage, SPAWN_TIME, String texture)
		super(80f, 3f, 0, 600f, 150f, 4f, "enemy/fighter.png");

		shootTime = 1f + ((MathUtils.random(20)) / 10);
		radius = MathUtils.random(30, 100);
		startY = MathUtils.random(0, Gdx.graphics.getHeight() - radius);

		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		// pos x, pos y, float speed, boolean direction, float damage
		return new Bullet(getX(), getY() + (getHeight() / 2) - 4, bulletSpeed, false, 1f);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

}
