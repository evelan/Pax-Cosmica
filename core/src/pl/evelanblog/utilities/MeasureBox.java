package pl.evelanblog.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Taka popierdółka żeby łatwiej było mierzyć czas, oczywiście trochę bardziej zakłamane wyniki wyjdą przez to że to
 * nowy obiekt i tkaie tam ale tak jest łatwiej i szybciej
 * 
 * Dodany fpslogger żeby spełniało to od razu kilka funkcji ;)
 * 
 * @author Evelan
 * @version 1.2
 *
 */

public class MeasureBox extends FPSLogger {

	private long startTime;
	private long elapsedTime; // w nanosekundach
	private long avgTime;
	private long counter;

	public MeasureBox()
	{
		counter = 0;
		avgTime = 0;
		startTime = 0;
		elapsedTime = -1;
	}

	/**
	 * Startuje licznik
	 */
	public void start()
	{
		elapsedTime = -1;
		startTime = System.nanoTime(); // haj rezoluszyn tajm bijacz
	}

	/**
	 * stopuje licznik
	 */
	public void stop()
	{
		elapsedTime = System.nanoTime() - startTime;
		avgTime += (elapsedTime / (++counter));
	}

	/**
	 * Zwraca czas w sekundach, można używać po zakończeniu mierzenia jak i w trakcie, w trakcie mierzenia zwróci
	 * oczywiście aktualny czas
	 * 
	 * @return
	 */
	public int getSec()
	{
		if (elapsedTime > 0)
			return (int) (elapsedTime / 1000000000); // 9 zer
		else
			return (int) ((System.nanoTime() - startTime) / 1000000000);
	}

	public long getNano()
	{
		if (elapsedTime > 0)
			return elapsedTime;
		else
			return (System.nanoTime() - startTime) / 1000000000;
	}

	public void logAvg(long counts)
	{
		if (counter % counts == 0)
			Gdx.app.log("MeasueBox", "AvgTime" + avgTime);
	}

}
