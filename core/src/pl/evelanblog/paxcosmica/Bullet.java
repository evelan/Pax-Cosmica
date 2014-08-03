package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;

/*
 * Bullet - obiekt dynamiczny tak jak statek, asteroida czy gracz
 * posiada kierunek, poniewa� strza�y mogl� lecie� z prawej strony lub z lewej strony
 * */

public class Bullet extends DynamicObject {
	private boolean direction; //true - strza�y gracza, false - strza�y wroga

	public Bullet(float x, float y, float speed, boolean direction, float damage) {
		super(x, y, speed, 1, damage, Assets.bullet.getWidth(), Assets.bullet.getHeight());
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
