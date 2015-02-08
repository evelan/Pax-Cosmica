package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CustomParticleEffect extends Actor {

	private ParticleEffect effect;

	public CustomParticleEffect(ParticleEffect effect) {
		this.effect = effect;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		effect.draw(batch, Gdx.graphics.getDeltaTime());
	}
}
