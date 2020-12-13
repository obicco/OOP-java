package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EsGraphics {
	
	static class Finestra extends JFrame {
		private Pannello pannello=new Pannello();
		private JButton su = new JButton("SU");
		private JButton giu = new JButton("GIU");
		private JButton sinistra = new JButton("SX");
		private JButton destra = new JButton("DX");
		public Finestra() {
			setSize(400,400);
			
			setLayout(new BorderLayout());
			add(pannello,BorderLayout.CENTER);
			add(su,BorderLayout.NORTH);
			add(giu,BorderLayout.SOUTH);
			add(sinistra,BorderLayout.WEST);
			add(destra,BorderLayout.EAST);
			
			su.addActionListener( e -> {
				pannello.y-=10;
				repaint();
			});
			giu.addActionListener( e -> { 
				pannello.y+=10;
				repaint();
			});
			sinistra.addActionListener( e -> {
				pannello.x-=10;
						repaint();
			});
			destra.addActionListener( e -> {
				pannello.x+=10;
						repaint();
			});
			
			pannello.setBackground(Color.WHITE);
			this.setBackground(Color.WHITE);

			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		

	}
	
	static class Pannello extends JPanel {
		int x=100;
		int y=100;
		@Override
		public void paint(Graphics g) {
			//System.out.println("painting..");
			g.setColor(Color.orange);
			g.drawRect(50, 50, 300, 300);
			g.drawLine(0, 0, 50, 50);
			g.fillOval(x, y, 20, 20);
		}	
	}

	public static void main(String[] args) {
		
		new Finestra();

	}

}

