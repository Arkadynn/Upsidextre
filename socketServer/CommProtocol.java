package socketServer;

import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.hardware.Doigt;
import upsidextre.comput.hardware.Gant;
import upsidextre.comput.hardware.Hardware3D;
import upsidextre.comput.hardware.Pouce;

public class CommProtocol {
	
	private UpsiDextre hardware;
	
	private Gant main;
	private Doigt doigt;
	private Pouce pouce;
	private Hardware3D h3D;
	
	public CommProtocol(UpsiDextre hardware) {
		this.hardware = hardware;
	}
	
	public String repondre (String question) {
		String reponse = new String();
		
		String[] q2 = question.split("\\.");
		
		for (int i = 0; i < q2.length; i++) {
			switch (q2[i]) {
			case "mainGauche":
				main = hardware.getMainGauche();
				break;
			case "mainDroite":
				main = hardware.getMainDroite();
				break;
			case "index":
				doigt = main.getIndexe();
				break;
			case "majeur":
				doigt = main.getMajeur();
				break;
			case "pouce":
				pouce = main.getPouce();
				doigt = pouce;
				break;
			case "accelerometre":
				h3D = main.getPosition().getAccelerometre();
				break;
			case "magnetometre":
				h3D = main.getPosition().getMagnetometre();
				break;
			case "gyrometre":
				h3D = main.getPosition().getGyroscope();
				break;
			case "x":
				reponse = h3D.getX() + "";
				break;
			case "y":
				reponse = h3D.getY() + "";
				break;
			case "z":
				reponse = h3D.getZ() + "";
				break;
			case "flexion":
				reponse = doigt.getFlexion() + "";
				break;
			case "opposition":
				reponse = pouce.getOpposition() + "";
			}
		}
		
		
		
		return reponse;
	}
	
}
