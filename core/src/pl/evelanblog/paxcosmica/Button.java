package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button extends Sprite {

	public Button(int x, int y, String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		setBounds(x, y, getTexture().getWidth(), getTexture().getHeight());
	}

	public Button(String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		setBounds(0, 0, getTexture().getWidth(), getTexture().getHeight());
	}

	public Button(Texture texture)
	{
		super(texture);
		setBounds(0, 0, getTexture().getWidth(), getTexture().getHeight());
	}

	/**
	 * Konstruktor. Skaluje argumenty metody do danej rodzielczości, aby były dopasowane do całości ekranu
	 * niezależnie od rozdzielczości.
	 * 
	 * @author Dave
	 * @version 1.0
	 * @param isRound
	 *            Czy tekstura jest okrągła/kwadratowa
	 * @param x
	 *            pozycja x
	 * @param y
	 *            pozycja y
	 * @param z
	 *            szerokosc
	 * @param v
	 *            wysokosc
	 * @param file
	 *            ścieżka do pliku *
	 * */
	public Button(Boolean isRound, float x, float y, float z, float v, String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		if (isRound) {
			z = Gdx.graphics.getHeight() * z / 1080;
			v = Gdx.graphics.getHeight() * v / 1080;
		}
		else {
			z = Gdx.graphics.getWidth() * z / 1920;
			v = Gdx.graphics.getHeight() * v / 1080;
		}

		setBounds(x, y, z, v);
	}
}