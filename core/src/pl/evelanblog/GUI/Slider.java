package pl.evelanblog.GUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.evelanblog.paxcosmica.Assets;

/**
 * Created by Dave on 2015-02-07.
 */

public class Slider extends Actor {

    private float value;
    private Boolean isEnabled, isPressed;
    private Rectangle knob;

    public Slider(float x, float y, float scale, float val) {
        super();
        setBounds(x, y, Assets.volumeBarBorder.getWidth()*scale, Assets.volumeBarBorder.getHeight()*scale);
        this.value = val*getWidth();
        knob = new Rectangle(x + value - Assets.volumeBarKnob.getWidth() / 2, y - Assets.volumeBarKnob.getHeight() / 4, Assets.volumeBarKnob.getWidth()*scale, Assets.volumeBarKnob.getHeight()*scale);
        isEnabled = false;
        isPressed = false;
    }

    public void not() {
        isEnabled = !isEnabled;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Assets.volumeBarBorder, getX(), getY(), getWidth(), getHeight());
        batch.draw(Assets.volumeBarCore, getX(), getY(), (knob.getX() + knob.getWidth() / 2) - getX(), getHeight());
        batch.draw(Assets.volumeBarKnob, knob.getX(), knob.getY(), knob.getWidth(), knob.getHeight());
    }

    public Boolean isEnabled() {
        return isEnabled;
    }
    public Rectangle getKnob() {
        return knob;
    }
    public float getVolume() {
        return value / getWidth();
    }

    public void setIsEnabled(Boolean foo) {
        this.isEnabled = foo;
    }
    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
    public Boolean isPressed() {
        return isPressed;
    }
    public void setValue(int val) {
        this.value = val;
        this.knob.setX(getX() + val - knob.getWidth() / 2);
    }

}

