package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class Booster extends DynamicObject {

	public static final float SPAWN_TIME = 10f;

	public enum BoostType {
		shootBoost, speedBoost, healthBoost;
	}

	public Booster() {
		super(Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - Assets.booster.getWidth()), 120f, 1, 0, 1, "booster.png");
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		setX(getX() - speed * deltaTime);
	}

	public void dispose() {
		live = false;
	}

	@Override
	public void kill() {
	}
}
