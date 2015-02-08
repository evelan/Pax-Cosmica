package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import pl.evelanblog.dynamicobjects.*;
import pl.evelanblog.enemy.Enemy;

/**
 * bardzo brzydkie sprawdzanie kolizji, szkoda czasu na zagłębianie się w to skoro działa
 */

public class Collider {

	private Player player;

	public Collider(Player player) {
		this.player = player;
	}

	/**
	 * Sprawdza czy jakiś inny obiekt koliduje z graczem
	 *
	 * @param objects wszystkie obiekty z world (oprócz gracza)
	 */
	public void checkPlayerCollision(Group objects) {

		for (Actor obj : objects.getChildren()) {
			if (player.overlaps(obj)) {
				if (obj instanceof Scrap) { //kolizja ze scrapem
					Scrap scrap = (Scrap) obj;
					scrap.kill();
                    if(PaxPreferences.getSoundEnabled())
					    Assets.playSound(Assets.powerupSfx);
					Gdx.app.log("PLAYER", "Gracz wziął SCRAP");
				} else if (obj instanceof Booster) { // kolizja z boosterem
					Booster booster = (Booster) obj;
					booster.kill();
                    if(PaxPreferences.getSoundEnabled())
					    Assets.playSound(Assets.powerupSfx);
					Gdx.app.log("PLAYER", "Gracz wziął BOOSTERa");
				} else if (obj instanceof Bullet) { // kolizja ze "szczałom"
					Bullet bullet = (Bullet) obj;
					if (!bullet.getDirection()) { //pytanie czy ta "szczała" leci w kierunku gracza czy w drugą stronę
						player.hurt(bullet.getImpactDamage());
						bullet.kill();
						Gdx.app.log("PLAYER", "Kolizja gracza i \"szczały\" przeciwnika");
					}
				} else //wszystko inne może tylko zabić gracza
				{
					//TODO mamy coś takiego jak IMPACTDAMAGE, które powinno być tutaj wykorzystane
					//poniewaz w przyszłości gracz będzie takim kozakiem że będzie mógł wlecieć w asteroidę
					// i mimo to przeżyje ale ubędzie mu życia, REALISTYCZNOSC MOTZNO
					player.kill();
					Gdx.app.log("PLAYER", "Gracz zderzył się z jakimś obiektem i \"umar\"");
				}
			}
		}
	}

	/**
	 * Sprawdza czy strzały przeciwnika lub gracza kolidują z czymś
	 *
	 * @param objects wszystkie obiekty świata
	 */
	public void checkBulletCollision(Group objects) {

		for (int i = 0; i < objects.getChildren().size; i++) {
			Actor obj = objects.getChildren().get(i);
			if (obj instanceof Bullet) {
				Bullet bullet = (Bullet) obj;
				for (Actor actor2 : objects.getChildren()) {

					if (actor2 instanceof Asteroid) {
						Asteroid asteroid = (Asteroid) actor2;
						// kolizja asteroidy i pocisku
						if (asteroid.overlaps(bullet)) {
							asteroid.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
					if (actor2 instanceof Enemy) {
						Enemy enemy = (Enemy) actor2;
						// kolizja wroga i pocisku
						if (enemy.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle()) && bullet.getDirection()) {
							enemy.hurt(bullet.getImpactDamage());
							bullet.kill();
						}
					}
				}
			}
		}
	}
}
