package pl.evelanblog.dynamicobjects;

import java.util.ArrayList;
import java.util.Iterator;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.math.Vector2;

/**
 * Prędkość ma rosnąć ale nie liniowo, ma się rozpędzać do pewnej wartości i im większa prędkość tym większy
 * impactDamage z tym można zrobić dużo ulepszeń. Rakieta powinna lecieć po łuku a nie linii prostej, pewnie
 * circumference z Ellipse się przyda, im ostrzejszy zakręty tym niejsza powinna być prędkosć, to już pochodnie by się
 * przydały :v jak prędkośc spadnie poniżej jakiejs wartości to wybucha jak leci przez dłuższy czas i nie trafia to też
 * wybucha 1. Szukamu najblizego celu {@link #targetting(ArrayList, Player)}
 * 
 * @author Evelan
 *
 */

public class AutoPilotMissile extends DynamicObject {

	protected AutoPilotMissile(float x, float y, float speed, float hp, float shield, float impactDamage, String texture) {
		super(x, y, speed, hp, shield, impactDamage, texture);
	}

	/**
	 * 1. Szukanie najbliżego celu
	 * 
	 * @param arr
	 *            lista obiektów
	 * @param playerVector
	 *            vector x i y pozycji gracza
	 * @return zwraca wektor x i y z pozycją najbliższego celu
	 */
	public Vector2 targetting(ArrayList<DynamicObject> arr, Vector2 playerVector) {
		Iterator<DynamicObject> iter = arr.iterator();
		Vector2 vector = new Vector2(-1, -1);
		float distanceFloat = Float.MAX_VALUE;
		while (iter.hasNext())
		{
			DynamicObject obj = iter.next();
			if (obj.getVector().dst(playerVector) < distanceFloat) {
				distanceFloat = obj.getVector().dst(playerVector);
				vector.set(obj.getVector());
			}
		}
		return vector;
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void kill()
	{
		live = false;
		Assets.hitEffect.setPosition(getX(), getY() + (getHeight() / 2));
		Assets.playSound(Assets.hitSfx);
		Assets.hitEffect.start();
	}

}
