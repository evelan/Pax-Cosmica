package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyFont extends Actor {
	BitmapFont font;
	CharSequence foo;
	float x,y;
	public MyFont(BitmapFont _font, CharSequence _foo, float _x, float _y){
		font=_font;	
		foo=_foo;
		x=_x;
		y=_y;
	}
	@Override
	public void draw(Batch batch, float alpha){
		font.draw(batch, foo, x, y);
	}
	public void setText(CharSequence _foo)
	{
		foo = _foo;
	}
}
