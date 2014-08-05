package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DynamicObject extends Sprite {

	protected float hp;
	protected boolean live;
	protected float speed;
	protected float impactDamage;

	public DynamicObject(float x, float y, float speed, float hp, float impactDamage, String file) 
	{
		super(new Texture(Gdx.files.internal(file)));
		setBounds(x, y, getTexture().getWidth(), getTexture().getHeight());
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
		hp -= damage;
		if (hp < 0)
			kill();
	}

	public void kill() {
		live = false;
	}

	public boolean isAlive() {
		return live;
	}

}
