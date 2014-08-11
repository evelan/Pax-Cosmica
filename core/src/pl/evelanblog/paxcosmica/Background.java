package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

	public static Sprite galaxy;
	public static Sprite stars1;
	public static Sprite stars2;
	public static Sprite stars3;
	public static Sprite stars4;
	private static float speed;

	public Background()
	{
		stars1 = new Sprite(Assets.stars);
		stars2 = new Sprite(Assets.stars);
		stars3 = new Sprite(Assets.stars);
		stars4 = new Sprite(Assets.stars);
		galaxy = new Sprite(Assets.galaxy);

		stars1.setBounds(0, 50, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars2.setBounds(Assets.stars.getWidth(), 50, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars3.setBounds(0, -50, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars4.setBounds(Assets.stars.getWidth(), -50, Assets.stars.getWidth(), Assets.stars.getHeight());
		galaxy.setBounds(0, 0, Assets.galaxy.getWidth(), Assets.galaxy.getHeight());
	}

	public static void draw(SpriteBatch batch, float delta)
	{
		updateBackground(delta);
		galaxy.draw(batch);
		stars1.draw(batch);
		stars2.draw(batch);
		stars3.draw(batch);
		stars4.draw(batch);
	}

	public static void updateBackground(float delta) {
		speed = 1;
		stars1.setX(stars1.getX() - (delta * 20 * speed));
		stars2.setX(stars2.getX() - (delta * 20 * speed));
		if (stars1.getX() + stars1.getWidth() < 0)
			stars1.setX(Gdx.graphics.getWidth());
		if (stars2.getX() + stars2.getWidth() < 0)
			stars2.setX(Gdx.graphics.getWidth());

		stars3.setX(stars3.getX() - (delta * 30 * speed));
		stars4.setX(stars4.getX() - (delta * 30 * speed));
		if (stars3.getX() + stars3.getWidth() < 0)
			stars3.setX(Gdx.graphics.getWidth());
		if (stars4.getX() + stars4.getWidth() < 0)
			stars4.setX(Gdx.graphics.getWidth());
	}
}
