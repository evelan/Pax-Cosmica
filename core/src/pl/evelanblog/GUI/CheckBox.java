package pl.evelanblog.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.evelanblog.paxcosmica.Assets;

/**
 * Created by Dave on 2015-02-06 - 21:31
 */
public class CheckBox extends Actor {

    Boolean isChecked;



    public CheckBox(float x, float y, float scale)
    {
        super();
        setBounds(x, y, Assets.checkboxBorder.getWidth()*scale,  Assets.checkboxBorder.getHeight()*scale);

        isChecked = false;
    }

    public Rectangle getBoundingRectangle()
    {
        return (new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    public void setIsChecked(Boolean foo){
        isChecked = foo;
    }
    public void not(){ isChecked = !isChecked; }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        batch.draw(Assets.checkboxBorder, getX(), getY(), getWidth(), getHeight());
        if(isChecked)
            batch.draw(Assets.checkboxTick, getX(), getY(), getWidth(), getHeight());
    }

    public boolean getIsChecked() { return isChecked; }
}
