package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {

	public Sprite background;
	public Sprite stars1;
	public Sprite stars2;
	public Sprite stars3;
	public Sprite stars4;
	float speed;

	public Background(Sprite background)
	{
		stars1 = new Sprite(Assets.stars);
		stars2 = new Sprite(Assets.stars);
		stars3 = new Sprite(Assets.stars);
		stars4 = new Sprite(Assets.stars);
		this.background = background;

		stars1.setBounds(0, 150, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars2.setBounds(Assets.stars.getWidth(), 150, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars3.setBounds(0, -150, Assets.stars.getWidth(), Assets.stars.getHeight());
		stars4.setBounds(Assets.stars.getWidth(), -150, Assets.stars.getWidth(), Assets.stars.getHeight());
		background.setBounds(0, 0, Assets.worldWidth, Assets.worldHeight);
    }

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(this.background.getTexture(), this.background.getX(), this.background.getY(), this.background.getWidth(), this.background.getHeight());
		batch.draw(this.stars1.getTexture(), this.stars1.getX(), this.stars1.getY(), this.stars1.getWidth(), this.stars1.getHeight());
		batch.draw(this.stars2.getTexture(), this.stars2.getX(), this.stars2.getY(), this.stars2.getWidth(), this.stars2.getHeight());
		batch.draw(this.stars3.getTexture(), this.stars3.getX(), this.stars3.getY(), this.stars3.getWidth(), this.stars3.getHeight());
		batch.draw(this.stars4.getTexture(), this.stars4.getX(), this.stars4.getY(), this.stars4.getWidth(), this.stars4.getHeight());
	}

	public void update(float delta) {
		speed = 1;
		stars1.setX(stars1.getX() - (delta * 20 * speed));
		stars2.setX(stars2.getX() - (delta * 20 * speed));
		if (stars1.getX() + stars1.getWidth() < 0)
			stars1.setX(1920);
		if (stars2.getX() + stars2.getWidth() < 0)
			stars2.setX(1920);

		stars3.setX(stars3.getX() - (delta * 30 * speed));
		stars4.setX(stars4.getX() - (delta * 30 * speed));
		if (stars3.getX() + stars3.getWidth() < 0)
			stars3.setX(1920);
		if (stars4.getX() + stars4.getWidth() < 0)
			stars4.setX(1920);
	}

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}
}
