package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CustomSprite extends Actor {
	Sprite sprite;

	public CustomSprite(Texture foo){
		sprite = new Sprite(foo, 0, 0, foo.getWidth(), foo.getHeight());
		setPosition(0, 0);
	}
	public CustomSprite(Texture foo, float x, float y){
		sprite = new Sprite(foo, (int)x, (int)y, foo.getWidth(), foo.getHeight());
		setPosition(x, y);
	}
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(sprite.getTexture(),sprite.getX(),sprite.getY());
	}
}
