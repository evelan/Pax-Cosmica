package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Planet extends Actor {

	private Sprite sprite;
	private float x;
	private float y;
	private float count;
	private float size;
	private float speed;
	private boolean clockwise;
	private boolean hover = false;
	private boolean store = false;
	private boolean portal = false;
	private String name;
	private boolean discovered = false;
	private BitmapFont font;
	private MyFont tekst;

	public Planet(float x, float y, float size, float speed, boolean clockwise, boolean store, String name, String filename)
	{
		setSprite(new Sprite(new Texture(Gdx.files.internal(filename))));
		getSprite().setBounds(x, y, Assets.galaxyPlanet.getWidth(), Assets.galaxyPlanet.getHeight());

		getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		getSprite().setScale(size);
		getSprite().setOriginCenter();
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		this.clockwise = clockwise;
		this.store = store;
		this.name = name;
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		tekst = new MyFont(font, portal ? "P:" + name : name, getX(), getY());
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public void setAsPorta()
	{
		portal = true;
	}

	public void update()
	{

		if (count < 0.0f)
			count = 360.0f;
		else
		{
			if (clockwise)
				count -= speed;
			else
				count += speed;
		}

		getSprite().setRotation(count);
	}
	@Override
    public void draw(Batch batch, float alpha){
		getSprite().setScale(size);
		
		if (hover)
		{
			getSprite().setScale(size + 0.2f);
			drawOptions();
		}
		getSprite().draw(batch);
		tekst.draw(batch, alpha);
    }

	public boolean isDiscovered()
	{
		return discovered;
	}

	public boolean isStore()
	{
		return store;
	}

	public boolean isHover()
	{
		return hover;
	}

	public void setHover()
	{
		hover = true;
	}

	public void reset()
	{
		hover = false;
	}

	private void drawOptions()
	{
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
