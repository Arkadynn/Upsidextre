package upsidextre.comput.utilities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class PhysicsObject {
private final float GRAVITY = 0.005f;
private ModelInstance object;
private float ground;
private float x;
private float y;
private float z;
private float vx=0;
private float vy=0;
private float vz=0;
public boolean grabbed = false;
public PhysicsObject(ModelInstance object,float x,float y,float z,float ground) {
	this.object = object;
	this.ground = ground;
	this.x = x;
	this.y = y;
	this.z = z;
	this.object.transform.translate(x, y, z);
}
public void deltaphy() {
	if (grabbed) {
	
	} else if (y<=ground){
		vx =0;
		vy = 0;
		vz = 0;
	} else {
		vy-=GRAVITY;
		object.transform.translate(vx, vy, vz);
		x+=vx;
		y+=vy;
		z+=vz;
	}
}
public ModelInstance getobject() {
	return object;
}
}
