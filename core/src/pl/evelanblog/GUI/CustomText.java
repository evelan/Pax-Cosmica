package pl.evelanblog.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CustomText extends Actor {
	BitmapFont font;
	CharSequence text;
	float x, y;

	public CustomText() {
		super();
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
	}

	public CustomText(BitmapFont font, CharSequence text, float x, float y) {
		this.font = font;
		this.text = text;
		this.x = x;
		this.y = y;
	}

	public CustomText(CharSequence text, float x, float y) {
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
		this.text = text;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		font.draw(batch, text, x, y);
	}

	public void setText(CharSequence text) {
		this.text = text;
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
