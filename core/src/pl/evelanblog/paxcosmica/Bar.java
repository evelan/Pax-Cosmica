package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Evelan on 19-02-2015 - 20:43
 */
public class Bar extends Image {

	private float maxValue;
	private float maxWidth;
	//private Bar border;

	public Bar(float x, float y, float w, float h, Texture texture) {
		super(texture);
		setBounds(x, y, w, h);
		//border = new Bar(x, y, w, h, Assets.bossHpBorder);
		maxWidth = w;
	}

	public void draw(Batch batch) {
		if (getWidth() >= 0) {
			batch.begin();
			//border.draw(batch, 1);
			draw(batch, 1);
			batch.end();
		}
	}

	/**
	 * domyślna funkcja, wyskaluje od razu pasek
	 *
	 * @param value ustawia maksymalną wartośc i obecną za jednym razem
	 */
	public void setValue(float value) {
		setWidth(maxWidth * (value / maxValue));
	}

	/**
	 * @param value    obecna wartość
	 * @param maxValue maksymalna wartość paska
	 */
	public void setValue(float value, float maxValue) {
		this.maxValue = maxValue;
		setWidth(value / maxValue);
	}
}
