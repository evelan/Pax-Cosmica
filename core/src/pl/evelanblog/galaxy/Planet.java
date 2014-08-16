package pl.evelanblog.galaxy;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Planet extends Sprite {

	private float x;
	private float y;
	private float count;
	private float rotation;
	private float size;
	private float speed;
	private boolean clockwise;
	private boolean hover = false;
	private boolean store = false;
	private String name;
	private BitmapFont font;

	public Planet(float x, float y, float size, float speed, boolean clockwise, boolean store, String name)
	{
		super(Assets.galaxyPlanet);
		setBounds(x, y, Assets.galaxyPlanet.getWidth(), Assets.galaxyPlanet.getHeight());
		setScale(size);
		setOriginCenter();
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		this.clockwise = clockwise;
		this.store = store;
		this.name = name;
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);

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

		setRotation(count);
	}

	public void draw(SpriteBatch batch, float delta)
	{
		draw(batch);
		setScale(size);
		font.draw(batch, name, getX(), getY());
		if (hover)
		{
			setScale(size + 0.2f);
			drawOptions();
		}
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
}
