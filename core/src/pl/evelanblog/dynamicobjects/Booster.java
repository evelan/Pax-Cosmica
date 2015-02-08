package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.paxcosmica.Assets;

public class Booster extends DynamicObject {

	public static final float SPAWN_TIME = 10f;

	public enum BoostType {
		shootBoost, speedBoost, healthBoost
	}

	public Booster() {
		super(Assets.worldWidth, MathUtils.random(0, Assets.worldHeight - Assets.booster.getWidth()), 120f, 1, 0, 1, "booster.png");
	}

	@Override
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
