package upsidextre.comput.serveur;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import upsidextre.comput.utilities.FastTrigo;

public class FingerTest extends JFrame {
	private int value=512;
	private Canvas canvas = new Canvas(){
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawLine(240, 240, 0, 240);
			
			int x1 = (int)(100*(FastTrigo.cos(((value/490F)*Math.PI/2F))))+240;
			int y1 = (int)(100*(FastTrigo.sin(((value/490F)*Math.PI/2F))))+240;
			int x2 = (int)(100*(FastTrigo.cos(((value/490F)*Math.PI))))+x1;
			int y2 = (int)(100*(FastTrigo.sin(((value/490F)*Math.PI))))+y1;
			
			g.drawLine(240, 240, x1, y1);
			g.drawLine(x1, y1, x2, y2);
		}
	};
	public FingerTest() {
		super("Test Doigt");
		setSize(640,480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		add(canvas);
	}
	

	
	public void setvalue(int nvvalue) {
		value = nvvalue;
		canvas.repaint();
	}
}
