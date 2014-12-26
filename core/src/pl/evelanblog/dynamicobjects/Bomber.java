package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;

public class Bomber extends Enemy {

	public Bomber() {
		// Enemy(float y, float speed, float bulletSpeed, float hp, float shield, float impactDamage, String texture)
		super(0, 20f + (MathUtils.random(6) * 10), 400f, 6f, 0f, 250f, "enemy/bomber.png");
		spawnTime = 8f;
		shootTime = 1f + ((MathUtils.random(20)) / 10);
		radius = MathUtils.random(10, 40);
		startY = MathUtils.random(0, Gdx.graphics.getHeight() - radius);
		engine = new ParticleEffect();
		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));

	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		// pos x, pos y, float speed, boolean direction, float damage
		return new Bullet(getX(), getY() + (getHeight() / 2) - 4, bulletSpeed, false, 1f);
	}

}
