package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CustomSprite extends Actor {

	Sprite sprite;

	public CustomSprite(Texture texture) {
		sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
		setPosition(0, 0);
	}

	public CustomSprite(float x, float y, Texture texture) {
		sprite = new Sprite(texture, (int) x, (int) y, texture.getWidth(), texture.getHeight());
		setPosition(x, y);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY());
	}

	public Sprite getSprite() {
		return sprite;
	}

}
