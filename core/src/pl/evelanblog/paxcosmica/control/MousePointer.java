package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MousePointer extends Rectangle {

	public MousePointer()
	{

	}

	public void set(float x, float y)
	{
		setPosition(x, y);
		setSize(1);
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
