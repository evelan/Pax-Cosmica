package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
public class Button extends Image {


    public Button(float x, float y, Texture texture)
    {
        super(texture);
        setPosition(x, y);
    }

    public Button(float x, float y, float w, float h, Texture texture)
    {
        super(texture);
        setBounds(x, y, w, h);
    }
    public Button(Texture texture)
    {
        super(texture);
    }

    public Rectangle getBoundingRectangle()
    {
        return (new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }
}