package upsidextre.comput.serveur;

import java.awt.Color;
import java.awt.Graphics;

import javax.jws.Oneway;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FingerTest extends JFrame {
	private int value=512;
	
	public FingerTest() {
		super("Test Doigt");
		setSize(640,480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		g.setColor(Color.BLACK);
		g.drawLine(240, 240, 640, 240);
		g.drawLine(240, 240, (int)(100*Math.cos((value/512)*Math.PI)), (int)(100*Math.sin(value/512)*Math.PI));
	}
	
	public void setvalue(int nvvalue) {
		value = nvvalue;
		repaint();
	}
	public static void main(String Args[]) {
		new FingerTest();
	}
}
