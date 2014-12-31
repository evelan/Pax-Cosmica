package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.Planet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class MousePointer extends Rectangle {
	private static final long serialVersionUID = 3373269279037859745L;

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
		if(button.isVisible())
		{
			//y = Gdx.graphics.getHeight() - y;
			setPosition(x, y);

			Rectangle r = new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());

			if (this.overlaps(r))
				return true;
			else
				return false;
		}
		return false;

	}

	public boolean overlaps(Button button) {
		if(button.isVisible())
		{
			Rectangle r = new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());
			if (this.overlaps(r))
				return true;
			else
				return false;
		}
		return false;
	}


	public boolean overlaps(Planet planet, float moveValue, float x, float y) {
		if(planet.isVisible())
		{
			setPosition(x+moveValue*Gdx.graphics.getWidth(),y);
			if (this.overlaps(planet.getSprite().getBoundingRectangle()))
			{
				setPosition(x,y);
				return true;
			}
			else
			{
				setPosition(x,y);
				return false;
			}
		}
		return false;
	}

	public boolean overlaps(Button button, float moveValue, float x, float y) {
		if(button.isVisible())
		{
			setPosition(x+moveValue*Gdx.graphics.getWidth(), y);
			Rectangle r = new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());
			if (this.overlaps(r))
			{
				setPosition(x,y);
				return true;
			}
			else
			{
				setPosition(x,y);
				return false;
			}
		}
		return false;
	}
}
