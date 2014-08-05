package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;

public class Bullet extends DynamicObject {
	private boolean direction; //true - strza³y gracza, false - strza³y wroga

	public Bullet(float x, float y, float speed, boolean direction, float damage) {
		super(x, y, speed, 1, damage, "bullet.png");
		this.direction = direction;
	}

	public void update(float deltaTime) {
		if (direction)
			setX(getX() + speed * deltaTime);
		else
			setX(getX() - speed * deltaTime);

		if (getX() < 0 || getX() > Gdx.graphics.getWidth())
			live = false;
	}
	
	public boolean getDirection()
	{
		return direction;
	}
}
