package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;

/**
 * To ma być typowe mięso armatnie wroga, jakieś tam typowe stateczki co latajo
 * 
 * @author Evelan
 *
 */
public class Fighter extends Enemy {

	public Fighter() {
		// Enemy(float y, float speed, float bulletSpeed, float hp, float shield, float impactDamage, String texture)
		super(0, 80f + (MathUtils.random(6) * 10), 600f, 3f, 0f, 150f, "enemy/fighter.png");
		spawnTime = 4f;
		shootTime = 1f + ((MathUtils.random(20)) / 10);
		radius = MathUtils.random(30, 100);
		startY = MathUtils.random(0, Gdx.graphics.getHeight() - radius);
		engine = new ParticleEffect();
		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
	}

}
