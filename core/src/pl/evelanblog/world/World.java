package pl.evelanblog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import pl.evelanblog.dynamicobjects.Asteroid;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.enemy.Battleship;
import pl.evelanblog.enemy.Bomber;
import pl.evelanblog.enemy.EnemyBoss;
import pl.evelanblog.enemy.Fighter;
import pl.evelanblog.paxcosmica.Collider;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.scenes.GameScreen;

public class World {

	private static Group objects; //wszystkie obiekty na scenie (bez gracza)
	private Collider collider; // zderzacz hadronów XD
	private Player player; // gracz
	private EnemyBoss enemyBoss; // boss
	private GameState state; // stany gry
	private float[] sleepTime = new float[6]; // tablica gdzie trzymam czasy poszczególnych rzeczy kiedy mają się asteroidy etc.
	private boolean enemyBossExists = false;
    private final PaxCosmica game;

	public static enum GameState {
		ongoing, // gra się toczy i jest elegancko, szczelanie etc
		paused, //wcisniety przycisk pauzy -> wyswietlenie przycisków coś al'a menu
		win, // wygrana
		defeat, // przegrana
		powermanager // power manager + pauza
	}

	public World(PaxCosmica game) {
		this.game = game;
        objects = new Group();
		player = new Player();
		collider = new Collider(player, game);

		for (int i = 0; i < 6; i++) //zerujemy tablicę z czasami
			sleepTime[i] = 0;

		enemyBoss = new EnemyBoss();
	}

	public void update(float delta) {

		//jeśli gracz zabije podczas gry więcej niż 10 przeciwników I nie ma bossa na ekranie, to go dodaje
		if (Stats.levelKills > 0 && !enemyBossExists) {
			//TODO zmiana muzyki na jakąś poważniejszą
			objects.addActor(enemyBoss);
			enemyBossExists = true;
            GameScreen.getHudStage().addActor(enemyBoss.getBossHp());
            GameScreen.getHudStage().addActor(enemyBoss.getBossHpBorder());
			Gdx.app.log("STATE", "Dodano do sceny EnemyBoss!");
            enemyBoss.getBossHp().setVisible(true);
            enemyBoss.getBossHpBorder().setVisible(true);
		}

		//jeśli zabijemy bossa wygrywamy można jeszcze dodać jakieś 3 sekund odstępu zanim pokaże się ekran że wygraliśmy
		if (!enemyBoss.isAlive()) {
            state = GameState.win;
            Stats.kills += Stats.levelKills;
			Gdx.app.log("STATE", "EnemyBoss nie żyje " + state);
		}

		// adding delta time to array
		for (int i = 0; i < sleepTime.length; i++)
			sleepTime[i] += delta;

		spawnObjects(delta); // spawning objects like asteroids
		updateObjects(delta); // update all objects

		if (player.isAlive()) {
			collider.checkPlayerCollision(objects);
			collider.checkBulletCollision(objects);
		} else if (!player.isAlive()) {
            Stats.kills += Stats.levelKills;
			state = GameState.defeat;
			Gdx.app.log("STATE", "Gracz zginął, " + state);
		}
	}

	// w tej funkcji także spawnują się strzały wrogów!!!!!!!!!
	private void updateObjects(float delta) { // aktualizacja wszystkich obiektów, pozycji itd
		player.update(delta); // update position

		for (Actor obj : objects.getChildren())
			((DynamicObject) obj).update(delta);

		if (sleepTime[5] > 10f) {
			Gdx.app.log("COUNTER", "Liczba obiektów na scenie: " + objects.getChildren().size);
			sleepTime[5] = 0;
		}
	}

	private void spawnObjects(float delta) { // spownoawnie obiektów, oprócz pocisków wrogów, to się dzieje w update!!!!!!!!!!
		// TODO tutaj trzeba te ify jakoś skrócić za dużo podobnego kodu
		if (sleepTime[0] > player.getShootFrequency() && player.ableToShoot() && GameScreen.getHit()) {
			objects.addActor(player.shoot());
			sleepTime[0] = 0;
		}

		if (sleepTime[1] > Asteroid.SPAWN_TIME) {
			objects.addActor(new Asteroid());
			sleepTime[1] = 0;
		}

		if (sleepTime[2] > Fighter.SPAWN_TIME) {
			objects.addActor(new Fighter());
			sleepTime[2] = 0;
		}

		if (sleepTime[3] > Bomber.SPAWN_TIME) {
			objects.addActor(new Bomber());
			sleepTime[3] = 0;
		}

		if (sleepTime[4] > Battleship.SPAWN_TIME) {
			objects.addActor(new Battleship(game));
			sleepTime[4] = 0;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public static Group getObjects() {
		return objects;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
