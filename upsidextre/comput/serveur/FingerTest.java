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
		System.out.println("painted");
		g.setColor(Color.BLACK);
		g.drawLine(240, 240, 640, 240);
		
		int x = (int)(200*(Math.cos(((value/490F)*Math.PI))))+240;
		int y = (int)(200*(Math.sin(((value/490F)*Math.PI))))+240;
		
		g.drawLine(240, 240, x, y);
	}
	
	public void setvalue(int nvvalue) {
		value = nvvalue;
		invalidate();
		validate();
		repaint();
	}
}
