package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Button extends Image {

	public Button(float x, float y, String file) {
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		setBounds(x, y, super.getWidth(), super.getHeight());
	}

	public Button(String file) {
		super(new Texture(Gdx.files.internal(file)));
		setBounds(getWidth(), getHeight(), super.getWidth(), super.getHeight());
	}

	public Button(Texture texture) {
		super(texture);
		setBounds(getWidth(), getHeight(), super.getWidth(), super.getHeight());
	}

	public Button(Texture texture, float x, float y) {
		super(texture);
		super.setPosition(x, y);
	}

	/**
	 * Konstruktor. Skaluje argumenty metody do danej rodzielczości, aby były dopasowane do całości ekranu niezależnie
	 * od rozdzielczości.
	 *
	 * @param isRound Czy tekstura jest okrągła/kwadratowa
	 * @param x       pozycja x
	 * @param y       pozycja y
	 * @param z       szerokosc
	 * @param v       wysokosc
	 * @param file    ścieżka do pliku
	 * @author Dave
	 * @version 1.0
	 */
	public Button(Boolean isRound, float x, float y, float h, float w, String file) {
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		if (isRound) {
			h = Gdx.graphics.getHeight() * h / 1080;
			w = Gdx.graphics.getHeight() * w / 1080;
		} else {
			h = Gdx.graphics.getWidth() * h / 1920;
			w = Gdx.graphics.getHeight() * w / 1080;
		}
		setBounds(x, y, h, w);
	}

	public Button(float x, float y, float hw, String file) {
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		hw = Gdx.graphics.getHeight() * hw / 1080;
		setBounds(x, y, hw, hw);
	}

	public float getWidth() {
		return super.getWidth() * Gdx.graphics.getWidth() / 1920;
	}

	public float getHeight() {
		return super.getHeight() * Gdx.graphics.getHeight() / 1080;
	}

	public Rectangle getBoundingRectangle() {
		return (new Rectangle(getX(), getY(), getWidth(), getHeight()));
	}
}