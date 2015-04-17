package upsidextre.comput.hardware;

public abstract class Hardware3D {
	
	private int x;
	private int y;
	private int z;
	
	private int oldX;
	private int oldY;
	private int oldZ;
	
	public abstract void computNext();
	
	public void setX (int x) {
		this.x = x;
	}
	
	public int getX () {
		return this.oldX;
	}
	
	public void setY (int y) {
		this.y = y;
	}
	
	public int getY () {
		return this.oldY;
	}
	
	public void setZ (int z) {
		this.z = z;
	}
	
	public int getZ () {
		return this.oldZ;
	}
}
