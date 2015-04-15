package upsidextre.comput.hardware;

public class Positionnement {

	private Accelerometre accelerometre;
	private Magnetometre magnetometre;
	private Gyroscope gyroscope;
	
	private int[] tmp = new int[9];
	
	public Accelerometre getAccelerometre() {
		return accelerometre;
	}
	
	
	public void computNextPosition() {
		
	}
	
	
	
	public void setAccelerometre(Accelerometre accelerometre) {
		this.accelerometre = accelerometre;
	}
	public Magnetometre getMagnetometre() {
		return magnetometre;
	}
	public void setMagnetometre(Magnetometre magnetometre) {
		this.magnetometre = magnetometre;
	}
	public Gyroscope getGyroscope() {
		return gyroscope;
	}
	public void setGyroscope(Gyroscope gyroscope) {
		this.gyroscope = gyroscope;
	}
	
	public void setTmp(int i, int v) {
		if (i >= tmp.length || i < 0) return;
		tmp[i] = v;
	}
}
