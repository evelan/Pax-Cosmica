package pl.evelanblog.paxcosmica.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloatingText extends BitmapFont {

	private float x, y;
	private String text;
	private int count;

	public FloatingText(float x, float y, String text)
	{
		super(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		this.text = text;
		count = 0;
	}

	public void draw(SpriteBatch batch)
	{
		draw(batch, text, x, y);
	}

}
