package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;

/**
 * Końcowy badass
 * 
 * @author Evelan
 *
 */
public class EnemyBoss extends Enemy {

	public EnemyBoss() {
		super(Gdx.graphics.getHeight() / 2, 1f, 700f, 100f, 0f, 200f, "enemy.png");
	}

}
