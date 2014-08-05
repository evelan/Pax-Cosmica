package pl.evelanblog.paxcosmica.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import pl.evelanblog.paxcosmica.PaxCosmica;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useImmersiveMode = true;
		config.useWakelock = true;
		initialize(new PaxCosmica(), config);
	}
}
