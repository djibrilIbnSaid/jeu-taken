package vue_controlleur;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import model.Taquin;


public class TaquinFenGUI extends JFrame  {

	Dimension tailleEcran=Toolkit.getDefaultToolkit().getScreenSize();
	int screenLength=tailleEcran.width*2/3;
	int screenHeight=(tailleEcran.height*2/2)-100;

	public  TaquinFenGUI(Taquin model) {
		this.setTitle("Taquin");
		TaquinGrahique taquinGraphique = new TaquinGrahique(model);
		this.add(taquinGraphique);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setSize(screenLength,screenHeight);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
	}
}
