package pl.evelanblog.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.world.World;

/**
 * Created by Dave on 2015-02-08 - 21:25
 */
public class PowerManager extends Stage {

	private float hover = -1; // mówi o tym co wcisnęliśmy aby pokazać przycisk upgrade
	private float powerPos = 200, hullPos = 400, shieldPos = 600, weaponPos = 800, enginePos = 1000;
	float yPos = 200;
	private Button upPwr, downPwr;
	private CustomText powerLabel, hullLabel, shieldLabel, weaponLabel, engineLabel;

	public PowerManager() {
		super(new StretchViewport(1920, 1080));
		upPwr = new Button(0, 100, 200, 60, Assets.up);
		downPwr = new Button(0, 0, 200, 60, Assets.down);

		hullLabel = new CustomText(hullPos, yPos);
		powerLabel = new CustomText(powerPos, yPos);
		shieldLabel = new CustomText(shieldPos, yPos);
		weaponLabel = new CustomText(weaponPos, yPos);
		engineLabel = new CustomText(enginePos, yPos);

		addActor(upPwr);
		addActor(downPwr);

		addActor(powerLabel);
		addActor(hullLabel);
		addActor(shieldLabel);
		addActor(weaponLabel);
		addActor(engineLabel);
	}

	public void draw() {
		createBar(hullPos, World.getPlayer().hullPwr);
		createBar(powerPos, World.getPlayer().powerPwr);
		createBar(shieldPos, World.getPlayer().shieldPwr);
		createBar(weaponPos, World.getPlayer().weaponPwr);
		createBar(enginePos, World.getPlayer().enginePwr);

		hullLabel.setText("Hull " + World.getPlayer().hullPwr + " L" + World.getPlayer().hullLvl);
		powerLabel.setText("Power " + World.getPlayer().powerPwr + " L" + World.getPlayer().powerLvl);
		shieldLabel.setText("Shield " + World.getPlayer().shieldPwr + " L" + World.getPlayer().shieldLvl);
		weaponLabel.setText("Weapon " + World.getPlayer().weaponPwr + " L" + World.getPlayer().weaponLvl);
		engineLabel.setText("Engine " + World.getPlayer().enginePwr + " L" + World.getPlayer().engineLvl);

		super.draw();
	}

	public void createBar(float x, float level) {
		getBatch().begin();
		for (int i = 0; i < level; i++)
			getBatch().draw(Assets.upgradeBar, x, 200 + i * 30);
		getBatch().end();
	}

	public void touchDown(MousePointer mousePointer) {

		if (mousePointer.overlaps(upPwr)) {
			if (Stats.scrap >= 5 && World.getPlayer().powerLvl > 0) {
				if (hover == hullPos && World.getPlayer().hullLvl > World.getPlayer().hullPwr) {
					World.getPlayer().hullPwr++;
					World.getPlayer().powerPwr--;
					Stats.scrap -= 5;
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

		if (mousePointer.getX() > hullPos && mousePointer.getX() < hullPos + Assets.upgradeBar.getWidth())
			hover = hullPos;
		else if (mousePointer.getX() > weaponPos && mousePointer.getX() < weaponPos + Assets.upgradeBar.getWidth())
			hover = weaponPos;
		else if (mousePointer.getX() > shieldPos && mousePointer.getX() < shieldPos + Assets.upgradeBar.getWidth())
			hover = shieldPos;
		else if (mousePointer.getX() > enginePos && mousePointer.getX() < enginePos + Assets.upgradeBar.getWidth())
			hover = enginePos;
		else
			hover = -1;

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
		}
	}
}
