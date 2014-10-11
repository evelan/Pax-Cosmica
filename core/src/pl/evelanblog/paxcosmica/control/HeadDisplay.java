package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeadDisplay {

	private Player player;

	public HeadDisplay(Player player)
	{
		this.player = player;
	}

	public void drawHullBar(SpriteBatch batch)
	{
		for (int i = 0; i < player.getHealth(); i++)
			batch.draw(Assets.hullBar, 10 * 20, Gdx.graphics.getHeight() - 20);
	}

	public void drawShieldBar(SpriteBatch batch)
	{
		for (int i = 0; i < player.getShield(); i++)
			batch.draw(Assets.hullBar, 10 * 20, Gdx.graphics.getHeight() - 120);

	}
}
