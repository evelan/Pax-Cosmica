package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyEffect extends Actor {

	private ParticleEffect effect;
	
	public MyEffect()
	{
		effect = new ParticleEffect();
	}
	
	public MyEffect(ParticleEffect _effect)
	{
		setEffect(_effect);
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		getEffect().draw(batch, Gdx.graphics.getDeltaTime());
	}

	public ParticleEffect getEffect() {
		return effect;
	}

	public void setEffect(ParticleEffect effect) {
		this.effect = effect;
	}
	
}
