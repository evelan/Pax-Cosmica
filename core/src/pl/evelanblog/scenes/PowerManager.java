package pl.evelanblog.scenes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.world.World;

/**
 * Created by Dave on 2015-02-08.
 */
public class PowerManager extends Actor {

    public int hullPwrBf, shieldPwrBf, weaponPwrBf, enginePwrBf;
    public static boolean toSave = true; //czy PowerManager jest zmieniany, czy nie
    private int cost = 5; //Koszt potrzebny do wyliczenia zwrotu scrapu przy zmianie punktów
    private float hover = -1; // mówi o tym co wcisnęliśmy aby pokazać przycisk upgrade
    public Button upPwr, downPwr;
    private int hullPos, shieldPos, weaponPos, enginePos;
    private CustomText hullLabel, shieldLabel, weaponLabel, engineLabel;

    public PowerManager()
    {
        hullPos = 410;
        shieldPos = 620;
        weaponPos = 830;
        enginePos = 1040;
        upPwr = new Button(hullPos, 100, 200, 60, Assets.up);
        downPwr = new Button(hullPos, 0, 200, 60, Assets.down);
        save();
        GameScreen.getHudStage().addActor(upPwr);
        GameScreen.getHudStage().addActor(downPwr);
        hullLabel = new CustomText(); //TODO użyć tu innego konstruktora
        shieldLabel = new CustomText();
        weaponLabel = new CustomText();
        engineLabel = new CustomText();
    }

   @Override
   public void draw(Batch batch, float parentAlpha)
   {
        showActors(true);

        //createBar(power, Player.powerGenerator, "Power " + (int) Player.powerGenerator + "  L" + (int) Player.powerLvl);
        createBar(hullPos, World.getPlayer().hullPwr, hullPwrBf, batch);
        createBar(shieldPos, Player.shieldPwr, shieldPwrBf, batch);
        createBar(weaponPos, Player.weaponPwr, weaponPwrBf, batch);
        createBar(enginePos, Player.enginePwr, enginePwrBf, batch);

       hullLabel.setText("Hull " + Player.hullPwr + " L" + (int) Player.hullLvl);
       hullLabel.setPosition(hullPos + 10, 190);

       shieldLabel.setText("Shield " +  Player.shieldPwr + "  L" + (int) Player.shieldLvl);
       shieldLabel.setPosition(shieldPos + 10, 190);

       weaponLabel.setText("Weapon " + Player.weaponPwr + "  L" + (int) Player.weaponLvl);
       weaponLabel.setPosition(weaponPos + 10, 190);

       engineLabel.setText("Engine " + Player.enginePwr + "  L" + (int) Player.engineLvl);
       engineLabel.setPosition(enginePos + 10, 190);


    }
    public void createBar(float x, float level, float prevLevel, Batch batch) {
        int i;

        for (i = 0; i < level; i++)
        {
            if(i>=prevLevel)
                batch.draw(Assets.upgradeBar, x, 200 + i * 30);
            else
                batch.draw(Assets.upgradeBar2, x, 200 + i * 30);
        }
    }

    public void touchDown(MousePointer mousePointer)
    {
        if (mousePointer.overlaps(upPwr)) {


            if (Stats.scrap > 0) {
                cost = 5;
                if (hover == hullPos && Player.hullLvl > Player.hullPwr) {
                    World.getPlayer().hullPwr++;
                    Stats.scrap-=cost;
                    World.getPlayer().setHp(Player.hullPwr * 3);
                }
                else if (hover == weaponPos && Player.weaponLvl > Player.weaponPwr) {
                    Player.weaponPwr++;
                    Stats.scrap-=cost;
                }
                else if (shieldPos == hover && Player.shieldLvl > Player.shieldPwr) {
                    Player.shieldPwr++;
                    Stats.scrap-=cost;
                }
                else if (enginePos == hover && Player.engineLvl > Player.enginePwr) {
                    Player.enginePwr++;
                    Stats.scrap-=cost;
                }
                Player.setStats();
            }

        } else if (mousePointer.overlaps(downPwr)) {

            System.out.println(hullPwrBf + " " + Player.hullPwr);
            if (hover == hullPos && Player.hullPwr > 0) {
                Player.hullPwr--;
                cost = Player.hullPwr>=hullPwrBf ? 5 : 4;
                Stats.scrap+=cost;
            }
            else if (hover == weaponPos && Player.weaponPwr > 0) {
                Player.weaponPwr--;
                cost = Player.weaponPwr>=weaponPwrBf ? 5 : 4;
                Stats.scrap+=cost;
            }
            else if (shieldPos == hover && Player.shieldPwr > 0) {
                Player.shieldPwr--;
                cost = Player.shieldPwr>=shieldPwrBf ? 5 : 4;
                Stats.scrap+=cost;
            }
            else if (enginePos == hover && Player.enginePwr > 0) {
                Player.enginePwr--;
                cost = Player.enginePwr>=enginePwrBf ? 5 : 4;
                Stats.scrap+=cost;

            }
            Player.setStats();

        }


        if (mousePointer.x > hullPos   && mousePointer.getX() < hullPos + 200)
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
    public void save()
    {
        hullPwrBf = Player.hullPwr;
        shieldPwrBf = Player.shieldPwr;
        weaponPwrBf = Player.weaponPwr;
        enginePwrBf = Player.enginePwr;
    }
    public void addActors(Stage stage)
    {
        stage.addActor(hullLabel);
        stage.addActor(shieldLabel);
        stage.addActor(weaponLabel);
        stage.addActor(engineLabel);
        stage.addActor(upPwr);
        stage.addActor(downPwr);
    }
    public void showActors(boolean show)
    {
        if(show)
        {
            hullLabel.setVisible(true);
            shieldLabel.setVisible(true);
            weaponLabel.setVisible(true);
            engineLabel.setVisible(true);
            upPwr.setVisible(true);
            downPwr.setVisible(true);
        }
        else
        {
            hullLabel.setVisible(false);
            shieldLabel.setVisible(false);
            weaponLabel.setVisible(false);
            engineLabel.setVisible(false);
            upPwr.setVisible(false);
            downPwr.setVisible(false);
        }
    }

}
