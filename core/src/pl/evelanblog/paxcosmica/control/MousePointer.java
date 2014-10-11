package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MousePointer extends Rectangle {

	public MousePointer()
	{
		setPosition(0, 0);
		setSize(1);
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public boolean overlaps(float x, float y, Button button)
	{
		y = Gdx.graphics.getHeight() - y;
		setPosition(x, y);
		setSize(1);

		if (this.overlaps(button.getBoundingRectangle()))
			return true;
		else
			return false;
	}

}
