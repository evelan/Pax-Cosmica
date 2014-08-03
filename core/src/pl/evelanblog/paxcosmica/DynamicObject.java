package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.math.Rectangle;

public class DynamicObject extends Rectangle {

	protected float hp;
	protected boolean live;
	protected float speed;
	protected float impactDamage;

	//player
	public DynamicObject(float xVal, float yVal, float speed, float hp, float impactDamage, float wVal, float hVal) {
		setPosition(xVal, yVal);
		setSize(wVal, hVal);
		live = true;
		this.speed = speed;
		this.hp = hp;
	}


	public float getSpeed() {
		return speed;
	}
	
	public float getHealth() {
		return hp;
	}

	public void hurt(float damage) {
		if (hp > 0)
			hp -= damage;
		else
			kill();
	}

	public void kill() {
		live = false;
	}


	public boolean isAlive() {
		return live;
	}

}
