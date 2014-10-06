package pl.evelanblog.booster;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class Booster extends DynamicObject {

	private static final float spawnTime = 10f;
	
	public enum BoostType {
        shootBoost, speedBoost, healthBoost;
    }

	public Booster() {
		super(Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - Assets.booster.getWidth()), 120f, 1, 0, 1, "booster.png");
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;
		setX(getX() - speed * deltaTime);
	}
	
	public void dispose() {
		live = false;
	}

	public static float getSpawnTime() {
		return spawnTime;
	}
}
