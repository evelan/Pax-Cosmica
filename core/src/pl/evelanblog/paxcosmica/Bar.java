package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Evelan on 19-02-2015 - 20:43
 */
public class Bar extends Image {

	private int value;

	public Bar(float x, float y, float w, float h, Texture texture) {
		super(texture);
		setBounds(x, y, w, h);
		value = 0;
	}

	public void draw(Batch batch) {
		batch.begin();
		draw(batch, 1);
		batch.end();
	}

	public void setValue(int value, int maxValue) {
		setHeight(value / maxValue);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
