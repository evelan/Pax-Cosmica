package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class DynamicObject extends Sprite {

	protected float hp;
	protected boolean live;
	protected float speed;
	protected float impactDamage;
	protected float shield;
	protected float temp_x, temp_y;
	public DynamicObject(float x, float y, float speed, float hp, float shield, float impactDamage, String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		setBounds(x, y, getTexture().getWidth(), getTexture().getHeight());
		setOriginCenter();
		live = true;
		this.speed = speed;
		this.impactDamage = impactDamage;
		this.shield = shield;
		this.hp = hp;
		this.temp_x=0;
		this.temp_y=0;
	}

	public void setTexture(String file)
	{
		setTexture(new Texture(Gdx.files.internal(file)));
		setSize(getTexture().getWidth(), getTexture().getHeight());
		setOriginCenter();
	}

	public float getImpactDamage()
	{
		return impactDamage;
	}

	public float getSpeed() {
		return speed;
	}

	public float getHealth() {
		return hp;
	}

	public float getShield()
	{
		return shield;
	}

	public void hurt(float damage) {

		if (shield > 0) {
			shield -= damage;
			shield = 0;
		} else {
			hp -= damage;
		}

		if (hp <= 0)
			kill();
	}

	public abstract void kill();

	public boolean isAlive() {
		return live;
	}

}
