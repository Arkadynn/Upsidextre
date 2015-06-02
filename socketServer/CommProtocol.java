package socketServer;

import upsidextre.comput.entryPoint.UpsiDextre;
import upsidextre.comput.hardware.Doigt;
import upsidextre.comput.hardware.Gant;
import upsidextre.comput.hardware.Hardware3D;
import upsidextre.comput.hardware.Pouce;

public class CommProtocol {
	
	private UpsiDextre hardware;
	
	private Gant main = null;
	private Doigt doigt = null;
	private Pouce pouce = null;
	private Hardware3D h3D = null;
	
	/**
	 * Protocole de communication entre les clients et le serveur
	 * @param hardware : la représentation virtuelle des gants 
	 */
	public CommProtocol(UpsiDextre hardware) {
		this.hardware = hardware;
	}
	
	/**
	 * Génère la réponse à la question posée par le client
	 * @param question : question posée par le client. 
	 * @return : Le reponse du serveur
	 */
	public String repondre (String question) {
		String reponse = new String("<?xml version=\"1.0\" ?>");
		
		String[] q2 = question.split("\\.");
		
		for (int i = 0; i < q2.length; i++) {
			switch (q2[i]) {
			case "mainGauche":
				main = hardware.getMainGauche();
				if (i == q2.length-1) {
					reponse += respond ("main");
				}
				break;
			case "mainDroite":
				main = hardware.getMainDroite();
				if (i == q2.length-1) {
					reponse += respond ("main");
				}
				break;
			case "index":
				doigt = main.getIndexe();
				if (i == q2.length-1) {
					reponse += "<flexion>" + doigt.getFlexion() + "</flexion>";
				}
				break;
			case "majeur":
				doigt = main.getMajeur();
				if (i == q2.length-1) {
					reponse += "<flexion>" + doigt.getFlexion() + "</flexion>";
				}
				break;
			case "pouce":
				pouce = main.getPouce();
				doigt = pouce;
				if (i == q2.length-1) {
					reponse += respond ("pouce");
				}
				break;
			case "accelerometre":
				h3D = main.getPosition().getAccelerometre();
				if (i == q2.length-1) {
					reponse += respond ("h3D");
				}
				break;
			case "magnetometre":
				h3D = main.getPosition().getMagnetometre();
				if (i == q2.length-1) {
					reponse += respond ("h3D");
				}
				break;
			case "gyrometre":
				h3D = main.getPosition().getGyroscope();
				if (i == q2.length-1) {
					reponse += respond ("h3D");
				}
				break;
			case "h3D":
				if (i != q2.length -1) {
					return reponse + "<error>Erreur dans votre demande. Requete non prise en charge</error>";
				}
				reponse += respond("h3DAll");
			case "x":
				reponse += "<x>" + h3D.getX() + "</x>";
				break;
			case "y":
				reponse += "<y>" + h3D.getY() + "</y>";
				break;
			case "z":
				reponse += "<z>" + h3D.getZ() + "</z>";
				break;
			case "flexion":
				reponse += "<flexion>" + doigt.getFlexion() + "</flexion>";
				break;
			case "opposition":
				reponse += "opposition>" + pouce.getOpposition() + "</opposition>";
				break;
			default:
				reponse += "<error>Erreur dans votre demande. Requete non prise en charge</error>";
			}
		}
		
		clear();
		
		return reponse;
	}
	
	/**
	 * Génère une réponse pour un élément complèxe;</br>i.e. possédant un noeud enfant dans l'arborescence XML 
	 * @param rootAsk : le type d'élément complexe à construire
	 * @return : l'XML à faire parvenir au client sans header
	 */
	private String respond (String rootAsk) {
		StringBuffer sb = new StringBuffer ();
		
		
		switch (rootAsk) {
		case "upsiDextre":
			sb.append("<upsiDextre>");
			sb.append("<gant lat=\"gauche\">");
			sb.append("<index>");
			sb.append("<flexion>" + hardware.getMainGauche().getIndexe().getFlexion() + "</flexion>");
			sb.append("</index>");
			sb.append("<majeur>");
			sb.append("<flexion>" + hardware.getMainGauche().getMajeur().getFlexion() + "</flexion>");
			sb.append("</majeur>");
			sb.append("<pouce>");
			sb.append("<flexion>" + hardware.getMainGauche().getPouce().getFlexion() + "</flexion>");
			sb.append("<opposition>" + hardware.getMainGauche().getPouce().getOpposition() + "</opposition>");
			sb.append("</pouce>");
			sb.append("<accelerometre>");
			sb.append("<x>" + hardware.getMainGauche().getPosition().getAccelerometre().getX() + "</x>");
			sb.append("<y>" + hardware.getMainGauche().getPosition().getAccelerometre().getY() + "</y>");
			sb.append("<z>" + hardware.getMainGauche().getPosition().getAccelerometre().getZ() + "</z>");
			sb.append("</accelerometre>");
			sb.append("<gyroscope>");
			sb.append("<x>" + hardware.getMainGauche().getPosition().getGyroscope().getX() + "</x>");
			sb.append("<y>" + hardware.getMainGauche().getPosition().getGyroscope().getY() + "</y>");
			sb.append("<z>" + hardware.getMainGauche().getPosition().getGyroscope().getZ() + "</z>");
			sb.append("</gyroscope>");
			sb.append("<magnetometre>");
			sb.append("<x>" + hardware.getMainGauche().getPosition().getMagnetometre().getX() + "</x>");
			sb.append("<y>" + hardware.getMainGauche().getPosition().getMagnetometre().getY() + "</y>");
			sb.append("<z>" + hardware.getMainGauche().getPosition().getMagnetometre().getZ() + "</z>");
			sb.append("</magnetometre>");
			sb.append("</gant>");
			sb.append("<gant lat=\"droit\">");
			sb.append("<index>");
			sb.append("<flexion>" + hardware.getMainDroite().getIndexe().getFlexion() + "</flexion>");
			sb.append("</index>");
			sb.append("<majeur>");
			sb.append("<flexion>" + hardware.getMainDroite().getMajeur().getFlexion() + "</flexion>");
			sb.append("</majeur>");
			sb.append("<pouce>");
			sb.append("<flexion>" + hardware.getMainDroite().getPouce().getFlexion() + "</flexion>");
			sb.append("<opposition>" + hardware.getMainDroite().getPouce().getOpposition() + "</opposition>");
			sb.append("</pouce>");
			sb.append("<accelerometre>");
			sb.append("<x>" + hardware.getMainDroite().getPosition().getAccelerometre().getX() + "</x>");
			sb.append("<y>" + hardware.getMainDroite().getPosition().getAccelerometre().getY() + "</y>");
			sb.append("<z>" + hardware.getMainDroite().getPosition().getAccelerometre().getZ() + "</z>");
			sb.append("</accelerometre>");
			sb.append("<gyroscope>");
			sb.append("<x>" + hardware.getMainDroite().getPosition().getGyroscope().getX() + "</x>");
			sb.append("<y>" + hardware.getMainDroite().getPosition().getGyroscope().getY() + "</y>");
			sb.append("<z>" + hardware.getMainDroite().getPosition().getGyroscope().getZ() + "</z>");
			sb.append("</gyroscope>");
			sb.append("<magnetometre>");
			sb.append("<x>" + hardware.getMainDroite().getPosition().getMagnetometre().getX() + "</x>");
			sb.append("<y>" + hardware.getMainDroite().getPosition().getMagnetometre().getY() + "</y>");
			sb.append("<z>" + hardware.getMainDroite().getPosition().getMagnetometre().getZ() + "</z>");
			sb.append("</magnetometre>");
			sb.append("</gant>");
			break;
		case "main":
			sb.append("<gant>");
			sb.append("<index>");
			sb.append("<flexion>" + main.getIndexe().getFlexion() + "</flexion>");
			sb.append("</index>");
			sb.append("<majeur>");
			sb.append("<flexion>" + main.getMajeur().getFlexion() + "</flexion>");
			sb.append("</majeur>");
			sb.append("<pouce>");
			sb.append("<flexion>" + main.getPouce().getFlexion() + "</flexion>");
			sb.append("<opposition>" + main.getPouce().getOpposition() + "</opposition>");
			sb.append("</pouce>");
			sb.append("<accelerometre>");
			sb.append("<x>" + main.getPosition().getAccelerometre().getX() + "</x>");
			sb.append("<y>" + main.getPosition().getAccelerometre().getY() + "</y>");
			sb.append("<z>" + main.getPosition().getAccelerometre().getZ() + "</z>");
			sb.append("</accelerometre>");
			sb.append("<gyroscope>");
			sb.append("<x>" + main.getPosition().getGyroscope().getX() + "</x>");
			sb.append("<y>" + main.getPosition().getGyroscope().getY() + "</y>");
			sb.append("<z>" + main.getPosition().getGyroscope().getZ() + "</z>");
			sb.append("</gyroscope>");
			sb.append("<magnetometre>");
			sb.append("<x>" + main.getPosition().getMagnetometre().getX() + "</x>");
			sb.append("<y>" + main.getPosition().getMagnetometre().getY() + "</y>");
			sb.append("<z>" + main.getPosition().getMagnetometre().getZ() + "</z>");
			sb.append("</magnetometre>");
			sb.append("</gant>");
			break;
		case "pouce":
			sb.append("<pouce>");
			sb.append("<flexion>" + doigt.getFlexion() + "</flexion>");
			sb.append("<opposition>" + pouce.getOpposition() + "</opposition>");
			sb.append("</pouce>");
			break;
		case "h3D":
			sb.append("<h3D>");
			sb.append("<x>" + h3D.getX() + "</x>");
			sb.append("<y>" + h3D.getY() + "</y>");
			sb.append("<z>" + h3D.getZ() + "</z>");
			sb.append("</h3D>");
			break;
		}
		
		return sb.toString();
	}
	
	/**
	 * Oublie les références de cette requête pour repartir de zéro. 
	 */
	private void clear () {
		main = null;
		doigt =  null;
		pouce = null;
		h3D = null;
	}
}
