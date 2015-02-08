package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Planet extends Actor {

	private Sprite sprite, background;
	private float count;
	private float size;
	private float speed;
	private float rotationSpeed;
	private boolean clockwise;
	private boolean hover = false;
	private boolean store = false;
	private boolean portal = false;
	private String name;
	private boolean discovered = false;
	private BitmapFont font;
	private CustomText tekst;

	public Planet(float x, float y, float size, float speed, boolean clockwise, boolean store, String _name, String filename, float _rotationSpeed)
	{
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
		this.name = _name;
		this.rotationSpeed = _rotationSpeed;
		this.background = new Sprite(new Texture(Gdx.files.internal("planetbg/"+name.toLowerCase()+".jpg")));
        this.background.setSize(Assets.worldWidth, Assets.worldHeight);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
		tekst = new CustomText(font, portal ? "P:" + name : name, getX(), getY());
		
	}

	public float getX()
	{
		return sprite.getX();
	}

	public float getY()
	{
		return sprite.getY();
	}

	public float getWidth()
	{
		return sprite.getWidth();
	}
	public float getHeight()
	{
		return sprite.getHeight();
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

	public void setBackground(Sprite bg) {
		this.background = bg;
		
	}

	public Sprite getBackground() {
		// TODO Auto-generated method stub
		return background;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
}
