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

}
