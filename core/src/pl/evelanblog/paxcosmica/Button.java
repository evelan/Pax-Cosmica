package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Button extends Image {

	// pisałem długi komentarz próbujący wyrazić moją ciekawość na temat tych konstruktorów ale zapytam wprost:
	// Co tu sie odpierdala? :v
	// to krótko
	public Button(float x, float y, String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		setBounds(x, y, Gdx.graphics.getWidth() * getWidth() / 1920, Gdx.graphics.getHeight() * getHeight() / 1080);
	}

	public Button(String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		setBounds(Gdx.graphics.getWidth() * super.getX() / 1920, Gdx.graphics.getHeight() * super.getX() / 1080, Gdx.graphics.getWidth() * getWidth() / 1920,
				Gdx.graphics.getHeight() * getHeight() / 1080);
	}

	public Button(Texture texture)
	{
		super(texture);
		setBounds(Gdx.graphics.getWidth() * super.getX() / 1920, Gdx.graphics.getHeight() * super.getX() / 1080, Gdx.graphics.getWidth() * getWidth() / 1920,
				Gdx.graphics.getHeight() * getHeight() / 1080);
	}

	public Button(Texture texture, float x, float y)
	{
		super(texture);
		super.setPosition(x, y);
	}

	/**
	 * Konstruktor. Skaluje argumenty metody do danej rodzielczości, aby były dopasowane do całości ekranu niezależnie
	 * od rozdzielczości.
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
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		z = Gdx.graphics.getWidth() * z / 1920;
		v = Gdx.graphics.getHeight() * v / 1080;

		setBounds(x, y, z, v);
	}

	public Button(float x, float y, float z, String file)
	{
		super(new Texture(Gdx.files.internal(file)));
		x = Gdx.graphics.getWidth() * x / 1920;
		y = Gdx.graphics.getHeight() * y / 1080;
		z = Gdx.graphics.getHeight() * z / 1080;
		setBounds(x, y, z, z);
	}

	public float getWidth()
	{
		return super.getWidth() * Gdx.graphics.getWidth() / 1920;
	}

	public float getHeight()
	{
		return super.getHeight() * Gdx.graphics.getHeight() / 1080;
	}

	public Rectangle getBoundingRectangle()
	{
		return (new Rectangle(getX(), getY(), getWidth(), getHeight()));
	}
}