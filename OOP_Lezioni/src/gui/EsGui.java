package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EsGui {
	
	static class View extends JFrame{
		
		private Counter model;
		
		JButton piu = new JButton("+");
		JButton meno = new JButton("-");
		JLabel valore = new JLabel("?",JLabel.CENTER);

		public View(Counter model) {
			this.model = model;
			model.addObserver( (subject,parametro) -> {
				update();
			});
			//this.setLayout(new FlowLayout());
			//this.setLayout(new GridLayout(3,1));
			this.setLayout(new BorderLayout());
			
			this.add(piu,BorderLayout.NORTH);
			this.add(valore,BorderLayout.CENTER);
			this.add(meno,BorderLayout.SOUTH);
			
			this.setSize(400,400); // pixel
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			
			update();

		}
		
		public void update() {
			int v = model.getValue();
			this.valore.setText(String.valueOf(v));
		}
	}
	
	static class View2 extends JFrame{
		
		private Counter model;
		
		JLabel valore = new JLabel("",JLabel.LEFT);

		public View2(Counter model) {
			this.model = model;
			model.addObserver( (subject,parametro) -> {
				update();
			});
			this.add(valore);
			
			this.setSize(600,200); // pixel
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			
			update();

		}
		
		public void update() {
			int v = model.getValue();
			this.valore.setText((v==0?"":String.format("%"+v+"s", "").replaceAll(" ", "*")));
		}
	}

	static class Controller {
		
		private Counter model;
		private View view;
		
		public Controller( View v, Counter m) {
			this.model = m;
			this.view = v;
			
			view.piu.addActionListener( e -> {
				model.increment();
				//view.update();
			});

			view.meno.addActionListener( e -> {
				model.decrement();
				//view.update();
			});
			
		}
		
	}

	public static void main(String[] args) {
		
		Counter model = new Counter();
		
		View view = new View(model); // la view conosce il modello
		View2 view2 = new View2(model);
		
		Controller ctrl = new Controller(view,model);

	}

}
