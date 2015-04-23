package upsidextre.comput.utilities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;

public class NodeAd {
	private Node node;
	private ModelInstance instance;
	private float x;
	private float y;
	private float z;
	
	public NodeAd(ModelInstance instance ,String id) {
	this.instance = instance;
		this.node = instance.getNode(id);
		this.x = node.rotation.getYaw();
		this.y = node.rotation.getPitch();
		this.z = node.rotation.getRoll();
	}
	public void setrotation(float x,float y,float z){
		node.rotation.setEulerAngles(this.x+x, this.y+y, this.z+z);
	}
}
