package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MySprite extends Actor {
Sprite sprite;

public MySprite(Texture foo){
	sprite = new Sprite(foo);
	sprite.setBounds(0, 0, foo.getWidth(), foo.getHeight());
	}
public MySprite(Texture foo, float x, float y){
	sprite = new Sprite(foo);
	sprite.setBounds(x, y, foo.getWidth(), foo.getHeight());
	}
@Override
public void draw(Batch batch, float alpha){
    batch.draw(sprite.getTexture(),this.getX(),getY());
}
}
