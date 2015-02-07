package pl.evelanblog.enemy;

import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxPreferences;
import pl.evelanblog.world.World;

/**
 * Końcowy badass
 * 
 * @author Evelan
 *
 */
public class EnemyBoss extends Enemy {
    private Button bossHp = new Button(390, 1025, 410, 20, Assets.bossHp);
    private Button bossHpBorder = new Button(390, 1025, 410, 20,  Assets.bossHpBorder);

    public EnemyBoss() {
		// (float speed, hp, shield, bulletSpeed, shootTime, impactDamage, SPAWN_TIME, String texture)
		super(10f, 50f, 0f, 400f, 3f, 400f, "enemy/boss.png");
        bossHp.setVisible(false);
        bossHpBorder.setVisible(false);

		shootTime += 1f; // aby nie strzelały w takim samym odstępie czasu, małe urozmaicenie
		radius = 120;
		startY = MathUtils.random(0, Assets.worldHeight / 2);
	}
    @Override
    public void hurt(float damage) {
        hp -= damage;
        bossHp.setSize(bossHp.getWidth()-8*damage, 20);
        if (hp <= 0){
            kill();
           bossHp.setVisible(false);
            bossHpBorder.setVisible(false);
        }

    }
	@Override
	public void shoot() {
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() - (getHeight() / 6), bulletSpeed, false, 2f));
		World.getObjects().addActor(new Bullet(getX(), getY() + getHeight() / 6, bulletSpeed, false, 2f));
        if(PaxPreferences.getSoundEnabled())
		    Assets.playSound(Assets.shootSfx);
		time = 0;
	}

    public Button getBossHp() {return bossHp;};

    public Button getBossHpBorder() {
        return bossHpBorder;
    }
}
