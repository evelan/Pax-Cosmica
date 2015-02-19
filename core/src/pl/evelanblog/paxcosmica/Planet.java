package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.evelanblog.GUI.CustomText;

public class Planet extends Actor {

	private Sprite sprite, background;
	private CustomText tekst;
	private float count;
	private float size;
	private float rotationSpeed;
	private boolean clockwise;
	private boolean hover = false;
	private boolean store = false;
	boolean discovered = false;
	String name;
	float speed;

	public Planet(float x, float y, float size, float speed, boolean clockwise, String name, String filename, float rotationSpeed, boolean store) {
		setSprite(new Sprite(new Texture(Gdx.files.internal(filename))));
		sprite.setPosition(x, y);
		setPosition(x, y);
		getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		getSprite().setScale(size);
		getSprite().setOriginCenter();
		this.size = size;
		this.speed = speed;
		this.clockwise = clockwise;
		this.store = store;
		this.name = name;
		this.rotationSpeed = rotationSpeed;
		if (!store) {
			this.background = new Sprite(new Texture(Gdx.files.internal("planetbg/" + name.toLowerCase() + ".jpg")));
			this.background.setSize(Assets.worldWidth, Assets.worldHeight);
		}
		tekst = new CustomText(name, getX(), getY());

	}

	public float getX() {
		return sprite.getX();
	}

	public float getY() {
		return sprite.getY();
	}

	public float getWidth() {
		return sprite.getWidth();
	}

	public float getHeight() {
		return sprite.getHeight();
	}

	public void update() {

		if (count < 0.0f)
			count = 360.0f;
		else {
			if (clockwise)
				count -= rotationSpeed;
			else
				count += rotationSpeed;
		}

		getSprite().setRotation(count);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		getSprite().setScale(size);

		if (hover) {
			getSprite().setScale(size + 0.2f);
		}
		getSprite().draw(batch);
		tekst.draw(batch, alpha);
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public boolean isStore() {
		return store;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover() {
		hover = true;
	}

	public void reset() {
		hover = false;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void setBackground(Sprite bg) {
		this.background = bg;
	}

	public Sprite getBackground() {
		return background;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}
}
