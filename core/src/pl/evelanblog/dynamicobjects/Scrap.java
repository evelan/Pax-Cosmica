package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Stats;

/**
 * Created by Evelan on 03-02-2015.
 */

public class Scrap extends DynamicObject {

	public Scrap(float x, float y) {
		super(x, y, 50f, 1f, 0f, 0f, "booster/booster.png");
	}

	@Override
	public void kill() {
		Stats.scrap += 4;
		dispose();
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		setX(getX() - speed * deltaTime);
	}
}
