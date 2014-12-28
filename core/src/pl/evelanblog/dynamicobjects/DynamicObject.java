package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class DynamicObject extends Actor {

	protected float hp;
	protected boolean live;
	protected float speed;
	protected float impactDamage;
	protected float shield;
	protected Sprite sprite;
	public DynamicObject(float x, float y, float speed, float hp, float shield, float impactDamage, String file)
	{
		sprite = new Sprite(new Texture(Gdx.files.internal(file)));
		sprite.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		sprite.setOriginCenter();
		live = true;
		this.speed = speed;
		this.impactDamage = impactDamage;
		this.shield = shield;
		this.hp = hp;
	}

	public void setTexture(String file)
	{
		sprite.setTexture(new Texture(Gdx.files.internal(file)));
		setSize(sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
		sprite.setOriginCenter();
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

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void draw(Batch batch, float alpha){
	    batch.draw(sprite.getTexture(), sprite.getX(),sprite.getY());
	}
	
	public void draw(SpriteBatch batch, float delta) {
		batch.draw(this.sprite.getTexture(),this.sprite.getX(),this.sprite.getY(), this.sprite.getWidth(), this.sprite.getHeight());
	}

}
