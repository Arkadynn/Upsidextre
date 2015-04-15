package upsidextre.comput.entryPoint;

import upsidextre.comput.hardware.Gant;
import upsidextre.comput.serveur.FingerTest;
import upsidextre.comput.serveur.ServeurGants;


public class UpsiDextre {

	private Gant mainDroite;
	private Gant mainGauche;
	
	private FingerTest fingerTest;
	
	public UpsiDextre() {
		fingerTest = new FingerTest();
	}
	
	public static void main(String[] args) {
		new ServeurGants(new UpsiDextre()).run();
	}

	public Gant getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(Gant mainDroite) {
		this.mainDroite = mainDroite;
	}

	public Gant getMainGauche() {
		return mainGauche;
	}

	public void setMainGauche(Gant mainGauche) {
		this.mainGauche = mainGauche;
	}

	public void feedFinger (int feed) {
		this.fingerTest.setvalue(feed);
	}
}
