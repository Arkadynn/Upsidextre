package upsidextre.exemple;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main3DLauncher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	      cfg.title = "Main 3D";
	      cfg.useGL30 = false;
	      cfg.width = 480;
	      cfg.height = 320;

	      new LwjglApplication(new Main3D(), cfg);
	}

}
