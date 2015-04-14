package upsidextre.comput.entryPoint;

import upsidextre.comput.hardware.Gant;
import upsidextre.comput.serveur.ServeurGants;


public class UpsiDextre {

	private Gant mainDroite;
	private Gant mainGauche;
	
	public UpsiDextre() {
		
	}
	
	public static void main(String[] args) {
		new ServeurGants().run();

	}

}
