package pl.evelanblog.scenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.world.World;

/**
 * Created by Dave on 2015-02-08.
 */
public class PowerManager extends Actor {

	private float hover = -1; // mówi o tym co wcisnęliśmy aby pokazać przycisk upgrade
	private float powerPos, hullPos, shieldPos, weaponPos, enginePos;
	private Button upPwr, downPwr;
	private CustomText powerLabel, hullLabel, shieldLabel, weaponLabel, engineLabel;

	public PowerManager() {

		powerPos = 200;
		hullPos = 410;
		shieldPos = 620;
		weaponPos = 830;
		enginePos = 1040;

		upPwr = new Button(hullPos, 100, 200, 60, Assets.up);
		downPwr = new Button(hullPos, 0, 200, 60, Assets.down);

		GameScreen.getHudStage().addActor(upPwr);
		GameScreen.getHudStage().addActor(downPwr);

		powerLabel = new CustomText();
		hullLabel = new CustomText();
		shieldLabel = new CustomText();
		weaponLabel = new CustomText();
		engineLabel = new CustomText();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		showActors(true);

		createBar(powerPos, World.getPlayer().powerPwr, batch);
		createBar(hullPos, World.getPlayer().hullPwr, batch);
		createBar(shieldPos, World.getPlayer().shieldPwr, batch);
		createBar(weaponPos, World.getPlayer().weaponPwr, batch);
		createBar(enginePos, World.getPlayer().enginePwr, batch);

		powerLabel.setText("Power " + World.getPlayer().powerPwr + " L" + World.getPlayer().powerLvl);
		powerLabel.setPosition(powerPos + 10, 190);

		hullLabel.setText("Hull " + World.getPlayer().hullPwr + " L" + World.getPlayer().hullLvl);
		hullLabel.setPosition(hullPos + 10, 190);

		shieldLabel.setText("Shield " + World.getPlayer().shieldPwr + "  L" + World.getPlayer().shieldLvl);
		shieldLabel.setPosition(shieldPos + 10, 190);

		weaponLabel.setText("Weapon " + World.getPlayer().weaponPwr + "  L" + World.getPlayer().weaponLvl);
		weaponLabel.setPosition(weaponPos + 10, 190);

		engineLabel.setText("Engine " + World.getPlayer().enginePwr + "  L" + World.getPlayer().engineLvl);
		engineLabel.setPosition(enginePos + 10, 190);
	}

	public void createBar(float x, float level, Batch batch) {
		for (int i = 0; i < level; i++)
			batch.draw(Assets.upgradeBar, x, 200 + i * 30);
	}

	public void touchDown(MousePointer mousePointer) {

		if (mousePointer.overlaps(upPwr)) {

			if (Stats.scrap >= 5 && World.getPlayer().powerLvl > 0) {
				if (hover == hullPos && World.getPlayer().hullLvl > World.getPlayer().hullPwr) {
					World.getPlayer().hullPwr++;
					World.getPlayer().powerPwr--;
					Stats.scrap -= 5;
					World.getPlayer().setHp(World.getPlayer().hullLvl * 3);
				} else if (hover == weaponPos && World.getPlayer().weaponLvl > World.getPlayer().weaponPwr) {
					World.getPlayer().weaponPwr++;
					World.getPlayer().powerPwr--;
					Stats.scrap -= 5;
				} else if (shieldPos == hover && World.getPlayer().shieldLvl > World.getPlayer().shieldPwr) {
					World.getPlayer().shieldPwr++;
					World.getPlayer().powerPwr--;
					Stats.scrap -= 5;
				} else if (enginePos == hover && World.getPlayer().engineLvl > World.getPlayer().enginePwr) {
					World.getPlayer().enginePwr++;
					World.getPlayer().powerPwr--;
					Stats.scrap -= 5;
				}
				World.getPlayer().setStats();
			}
		} else if (mousePointer.overlaps(downPwr)) {
			if (World.getPlayer().powerLvl > World.getPlayer().powerPwr) {
				if (hover == hullPos && World.getPlayer().hullPwr > 0) {
					World.getPlayer().hullPwr--;
					Stats.scrap += 5;
					World.getPlayer().powerPwr++;
				} else if (hover == weaponPos && World.getPlayer().weaponPwr > 0) {
					World.getPlayer().weaponPwr--;
					Stats.scrap += 5;
					World.getPlayer().powerPwr++;
				} else if (shieldPos == hover && World.getPlayer().shieldPwr > 0) {
					World.getPlayer().shieldPwr--;
					Stats.scrap += 5;
					World.getPlayer().powerPwr++;
				} else if (enginePos == hover && World.getPlayer().enginePwr > 0) {
					World.getPlayer().enginePwr--;
					Stats.scrap += 5;
					World.getPlayer().powerPwr++;
				}
				World.getPlayer().setStats();
			}
		}

		if (mousePointer.x > hullPos && mousePointer.getX() < hullPos + 200)
			hover = hullPos;

		else if (mousePointer.getX() > weaponPos && mousePointer.getX() < weaponPos + 200)
			hover = weaponPos;

		else if (mousePointer.getX() > shieldPos && mousePointer.getX() < shieldPos + 200)
			hover = shieldPos;

		else if (mousePointer.getX() > enginePos && mousePointer.getX() < enginePos + 200)
			hover = enginePos;

		else
			hover = -1;

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
		}
	}

	public void addActors(Stage stage) {
		stage.addActor(hullLabel);
		stage.addActor(shieldLabel);
		stage.addActor(weaponLabel);
		stage.addActor(engineLabel);
		stage.addActor(upPwr);
		stage.addActor(downPwr);
	}

	public void showActors(boolean show) {
		if (show) {
			hullLabel.setVisible(true);
			shieldLabel.setVisible(true);
			weaponLabel.setVisible(true);
			engineLabel.setVisible(true);
			upPwr.setVisible(true);
			downPwr.setVisible(true);
		} else {
			hullLabel.setVisible(false);
			shieldLabel.setVisible(false);
			weaponLabel.setVisible(false);
			engineLabel.setVisible(false);
			upPwr.setVisible(false);
			downPwr.setVisible(false);
		}
	}

}
