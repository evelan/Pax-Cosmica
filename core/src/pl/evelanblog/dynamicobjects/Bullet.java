package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.scenes.GameScreen;

import com.badlogic.gdx.Gdx;

public class Bullet extends DynamicObject {
	private boolean direction; // true - strza�y gracza, false - strza�y wroga

	public Bullet(float x, float y, float speed, boolean direction, float damage) {
		super(x, y, speed, 1, 0, damage, "bullet.png");
		this.direction = direction;
	}

	public void update(float deltaTime) {
		if (direction)
			getSprite().setX(getSprite().getX() + speed * deltaTime);
		else
			getSprite().setX(getSprite().getX() - speed * deltaTime);

		if (getSprite().getX() < 0 || getSprite().getX() > Gdx.graphics.getWidth())
			live = false;
	}

	public boolean getDirection()
	{
		return direction;
	}

	@Override
	public void kill()
	{
		live = false;
		Assets.playSound(Assets.hitSfx);
		Assets.hitEffect.setPosition(getSprite().getX(), getSprite().getY() + (getSprite().getHeight() / 2));
		Assets.hitEffect.start();
	}
}
