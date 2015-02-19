package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Evelan on 19-02-2015 - 20:43
 */
public class Bar extends Image {

	private int value;

	public Bar(float x, float y, float w, float h, int value, Texture texture) {
		super(texture);
		setBounds(x, y, w, h);
		this.value = value;
	}

	public void draw() {

	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
