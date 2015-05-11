package upsidextre.comput.entryPoint;

import upsidextre.comput.hardware.Gant;
import upsidextre.comput.serveur.ServeurGants;
import upsidextre.comput.utilities.HardwareViewer;


public class UpsiDextre {

	private Gant mainDroite;
	private Gant mainGauche;
	
	
	private HardwareViewer hardwareViewer;
	
	public UpsiDextre() {
		setMainDroite(new Gant());
		setMainGauche(new Gant());
		hardwareViewer = new HardwareViewer();
	}
	
	public static void main(String[] args) {
		new ServeurGants(new UpsiDextre()).run();;
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
	}
	
	public HardwareViewer getHardwareViewer () {
		return hardwareViewer;
	}

	public void refreshViewer() {
		
		getMainDroite().getPosition().computNextPosition();
		getMainGauche().getPosition().computNextPosition();
		
		StringBuffer sb = new StringBuffer();

		sb.append("Majeur : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainGauche().getMajeur().getFlexion() + "\n");
		sb.append("Index : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainGauche().getIndexe().getFlexion() + "\n");
		sb.append("Pouce : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainGauche().getPouce().getFlexion() + "\n");
		sb.append("\tOpposition : ");
		sb.append(getMainGauche().getPouce().getOpposition() + "\n\n");
		
		sb.append("Magnetometre : \n");
		sb.append("\tX : ");
		sb.append(getMainGauche().getPosition().getMagnetometre().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainGauche().getPosition().getMagnetometre().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainGauche().getPosition().getMagnetometre().getZ() + "\n");
		
		sb.append("Gyroscope : \n");
		sb.append("\tX : ");
		sb.append(getMainGauche().getPosition().getGyroscope().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainGauche().getPosition().getGyroscope().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainGauche().getPosition().getGyroscope().getZ() + "\n");
		
		sb.append("Accelerometre : \n");
		sb.append("\tX : ");
		sb.append(getMainGauche().getPosition().getAccelerometre().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainGauche().getPosition().getAccelerometre().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainGauche().getPosition().getAccelerometre().getZ() + "\n");
		
		hardwareViewer.getMainGauche().setText(sb.toString());
		
		sb = new StringBuffer();
		
		sb.append("Majeur : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainDroite().getMajeur().getFlexion() + "\n");
		sb.append("Index : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainDroite().getIndexe().getFlexion() + "\n");
		sb.append("Pouce : \n");
		sb.append("\tFlexion : ");
		sb.append(getMainDroite().getPouce().getFlexion() + "\n");
		sb.append("\tOpposition : ");
		sb.append(getMainDroite().getPouce().getOpposition() + "\n\n");
		
		sb.append("Magnetometre : \n");
		sb.append("\tX : ");
		sb.append(getMainDroite().getPosition().getMagnetometre().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainDroite().getPosition().getMagnetometre().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainDroite().getPosition().getMagnetometre().getZ() + "\n");
		
		sb.append("Gyroscope : \n");
		sb.append("\tX : ");
		sb.append(getMainDroite().getPosition().getGyroscope().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainDroite().getPosition().getGyroscope().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainDroite().getPosition().getGyroscope().getZ() + "\n");
		
		sb.append("Accelerometre : \n");
		sb.append("\tX : ");
		sb.append(getMainDroite().getPosition().getAccelerometre().getX() + "\n");
		sb.append("\tY : ");
		sb.append(getMainDroite().getPosition().getAccelerometre().getY() + "\n");
		sb.append("\tZ : ");
		sb.append(getMainDroite().getPosition().getAccelerometre().getZ() + "\n");
		
		hardwareViewer.getMainDroite().setText(sb.toString());
	}
}
