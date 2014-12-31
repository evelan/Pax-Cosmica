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
	private float x;
	private float y;
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
	private MyFont tekst;

	public Planet(float x, float y, float size, float speed, boolean clockwise, boolean store, String _name, String filename, float _rotationSpeed)
	{
		setSprite(new Sprite(new Texture(Gdx.files.internal(filename))));
		x=Gdx.graphics.getWidth()*x/1920;
		y=Gdx.graphics.getHeight()*y/1080;
		sprite.setBounds(x, y, Gdx.graphics.getHeight()*300/1080, Gdx.graphics.getHeight()*300/1080);
		setBounds(x, y, Gdx.graphics.getHeight()*300/1080, Gdx.graphics.getHeight()*300/1080);
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
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		tekst = new MyFont(font, portal ? "P:" + name : name, getX(), getY());
		
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
