package vue_controlleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Taquin;
import utils.EcouteurModele;
import utils.ParseImage;
import utils.Position;


/**
 * TaquinGraphique !
 * The TaquinGraphique program implements an application that
 * simply adds two given integer numbers and Prints
 * the output on the screen.
 * Note: Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 * 
 * @author  diallo-djiguine-diallo-diallo
 * @version 1.0
 * @since   2022-04-03
 */

public class TaquinGrahique extends JPanel implements EcouteurModele ,MouseListener,ActionListener,KeyListener{
	private JButton [][] button;
	private int ligne;
	private int colonne;
	private Taquin modele;
	JButton bInput, bValide, bMelange, caseButton, bSave, bRestaure;
	JLabel labelValueScore;

	ArrayList<ParseImage> subImages;
	BufferedImage imageBase = null;
	JPanel panelGeneration, panelGrille;
	JFormattedTextField tRow, tCol;
	 
	/**
	 * Le constructeur
	 * @param modele est de type Taquin
	 */
	public TaquinGrahique(Taquin modele){
		this.setPreferredSize(new Dimension(650,600));
		this.modele= modele;
		this.ligne = modele.getRow();
		this.colonne = modele.getColumn();
		panelGeneration = new JPanel();
		GridLayout grid=new GridLayout(2,3,2,2);
		panelGeneration.setLayout(grid);
		panelGrille = new JPanel();
		panelGrille.setLayout(new GridLayout(ligne, colonne));
		bInput = new JButton("Importer image");
		bValide = new JButton("Valider");
		bMelange = new JButton("Melanger");
		bSave = new JButton("Sauvegarder");
		bRestaure = new JButton("Restaurer");
		
		NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
		tRow = new JFormattedTextField(integerNumberInstance);
		tCol = new JFormattedTextField(integerNumberInstance);
		tRow.setColumns(5);
		tCol.setColumns(5);
		tRow.setValue(ligne);
		tCol.setValue(colonne);
		
		bInput.addActionListener(this::chargeImage);
		bValide.addActionListener(this::valideSize);
		bMelange.addActionListener(this::melange);
		bSave.addActionListener(this::sauvegardeTaquin);
		bRestaure.addActionListener(this::restaureTaquin);
		this.setLayout(new BorderLayout());

		panelGeneration.add(new JLabel("Colone"));
		panelGeneration.add(tCol);
		panelGeneration.add(bValide);
		panelGeneration.add(new JLabel("Ligne"));
		panelGeneration.add(tRow);

		JPanel panelScore = new JPanel();
		panelScore.setBackground(Color.cyan);
		JLabel labelScore = new JLabel("SCORE");
		labelValueScore = new JLabel("0");
		panelScore.setLayout(new GridLayout(1,2,50,50));
		panelScore.add(labelScore);
		panelScore.add(labelValueScore);

		JPanel menu =new JPanel();
		menu.setLayout(new GridLayout(1,2,150,0));
		menu.add(panelGeneration);
		menu.add(panelScore);

		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new GridLayout(4,1,0,0));
		panelOptions.add(bInput);
		panelOptions.add(bMelange);
		panelOptions.add(bSave);
		panelOptions.add(bRestaure);
		panelGrille.setPreferredSize(new Dimension(50,0));
		
		this.createButtons(ligne, colonne, true);
		this.setLayout(new BorderLayout());
		this.add(menu,BorderLayout.NORTH);
		this.add(panelGrille,BorderLayout.CENTER);
		JLabel leftPanel = new JLabel();
		leftPanel.setPreferredSize(new Dimension(50,0));
		this.add(leftPanel,BorderLayout.WEST);
		this.add(panelOptions,BorderLayout.EAST);
		 
		  
		modele.initTableContent();
		modele.ajoutEcouteur(this);
		
	}
	
	/**
	 * Cette fonction est utilisée pour charger une image.
	 * @param arg C'est un parametre de type ActionEvent
	 */
	void chargeImage(ActionEvent arg) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "png", "jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        this.subImages = new ArrayList<>();
        if(returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				this.imageBase = ImageIO.read(chooser.getSelectedFile());
				int widthImage = imageBase.getWidth();
				int heightImage = imageBase.getHeight();
				int eWidth = widthImage / this.modele.getColumn();
				int eHeight = heightImage / this.modele.getRow();
				int x = 0;
				int y = 0;
				this.modele.initTableContent();
				for (int i = 0; i < this.modele.getRow(); i++) {
					y = 0;
					for (int j = 0; j < this.modele.getColumn(); j++) {
						Image subImgage = imageBase.getSubimage(y, x, eWidth, eHeight);
						if(i+1 == this.modele.getRow() && j+1 == this.modele.getColumn()) {
							this.disableButton(button[i][j]);
						} else {
							String id = modele.getText(i, j);
							ParseImage pImg = new ParseImage(id, subImgage);
							subImages.add(pImg);
							button[i][j].setEnabled(true);
							button[i][j].setBackground(null);
							button[i][j].setIcon(this.resizeIcon(pImg.getImage(), button[i][j].getSize().width, button[i][j].getSize().height));
							button[i][j].setText(id);
						}
						
						y += eWidth;
					}
					x += eHeight;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
        	
        }
	}
	
	/**
	 * Cette fonction est utilisée pour creer une imageIcon.
	 * @param image C'est le premier parametre, il est de type Image
	 * @param resizedWidth C'est la largeur de l'image et il est de type Integer
	 * @param resizedHeight  C'est la hauteur de l'image et il est de type Integer
	 * @return ImageIcon Renvoie une imageIcon avec l'image passee en paramettre tout en respectant les tailles donnees.
	 */
	ImageIcon resizeIcon(Image image, int resizedWidth, int resizedHeight) {
	    Image resizedImage = image.getScaledInstance(resizedWidth, resizedHeight-10,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
	
	/**
	 * Cette fonction est utilisée pour mettre le bouton de la case vide.
	 * @param jb  C'est le bouton de la case et il est de type JButton
	 */
 private void disableButton(JButton jb) {
		jb.setText("--");
		jb.setIcon(null);
		jb.setBackground(Color.BLACK);
		jb.setOpaque(true);
		caseButton = jb;
	}
	
	/**
	 * Cette fonction est utilisée pour changer la taille du grille.
	 * @param arg C'est un parametre de type ActionEvent.
	 */
	private void valideSize(ActionEvent arg) {
		this.subImages = new ArrayList<>();
		this.imageBase = null;
		int r = Integer.parseInt(this.tRow.getText());
		int c = Integer.parseInt(this.tCol.getText());
		if (r > 1 && c > 1) {
			this.colonne = c;
			this.ligne = r;
			this.panelGrille.setVisible(false);
			this.panelGrille.removeAll();
			this.createButtons(this.ligne, this.colonne, true);
			panelGrille.setLayout(new GridLayout(this.ligne, this.colonne));
			this.panelGrille.setVisible(true);
		}
	}
	
	/**
	 * Cette fonction est utilisée pour creer les boutons de la grille.
	 * @param row C'est le nombre de ligne de la grille, il est de type Integer.
	 * @param col C'est le nombre de colonne de la grille, il est de type Integer.
	 * @param init  C'est qui permet de verifier si nous devons recreer la grille ou non, et il est de type boolean
	 */
 private void createButtons(int row, int col, boolean init) {
		this.button = new JButton[row][col];
		if (init)
			this.modele.creatGrille(row,col);
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				this.button[i][j] = new JButton(modele.getText(i, j));
				this.button[i][j].addMouseListener(this);
				this.button[i][j].addActionListener(this);
				this.button[i][j].addKeyListener(this);
				this.panelGrille.add(button[i][j]);
			}
		}
	}
	
	/**
	 * Cette fonction est utilisée pour mettre a jour l'interface graphique.
	 * @param source C'est le parametre, il est de type Object
	 */
	@Override
	public void modeleMisAjour(Object source) {
		this.labelValueScore.setText(""+modele.getNbCout());
		for(int i =0; i < this.modele.getRow(); i++) {
			for(int j =0; j < this.modele.getColumn(); j++) {
				this.button[i][j].setText(this.modele.getGrille()[i][j]);
				if (!this.modele.getGrille()[i][j].equals("--")) {
					ParseImage pImg = this.findParseImage(i,j);
					this.button[i][j].setEnabled(true);
					this.button[i][j].setBackground(null);
					if (pImg != null && button[i][j].getSize().width > 0) {
						this.button[i][j].setIcon(resizeIcon(pImg.getImage(), button[i][j].getSize().width, button[i][j].getSize().height));
					}
				}
				else{
					this.disableButton(this.button[i][j]);
				}
			}
		}
		this.repaint();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		gestionSurvol(e);
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		for(int i =0 ; i < this.modele.getRow() ; i++) {
			for(int j =0 ; j < this.modele.getColumn() ; j++) {
					this.button[i][j].setBackground(null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		String buttonText = e.getActionCommand();
		move(buttonText);
	}
	
	/**
	 * Cette fonction mets exergue un élément si ce dernier est déplaçable quand la
	 * souris le survole
	 * @param e de type MouseEvent
	 */
	public void gestionSurvol(MouseEvent e) {
		Object o = e.getSource();
		JButton b = null;
		String buttonText = "";
		if(o instanceof JButton)
			b = (JButton) o;
		if(b != null)
			buttonText = b.getText();
		Position cord = modele.getCaseEmpty();
		if( (modele.isCorrectIndex(cord.getAbsX()-1, cord.getOrdY()) && modele.getGrille()[cord.getAbsX()-1][cord.getOrdY()].equals(buttonText))
				||(modele.isCorrectIndex(cord.getAbsX()+1, cord.getOrdY()) && modele.getGrille()[cord.getAbsX()+1][cord.getOrdY()].equals(buttonText))
		|| (modele.isCorrectIndex(cord.getAbsX(), cord.getOrdY()-1) && modele.getGrille()[cord.getAbsX()][cord.getOrdY()-1].equals(buttonText))
		|| (modele.isCorrectIndex(cord.getAbsX(), cord.getOrdY()+1) && modele.getGrille()[cord.getAbsX()][cord.getOrdY()+1].equals(buttonText)))
			b.setBackground(Color.CYAN);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Cette fonction offre la possibilité de pouvoir utilisé les quatre flèche du clavier 
	 * Pour éffectuer un déplacement
	 * @param e de type keyEvent 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int source=e.getKeyCode();
		Position pos=modele.getCaseEmpty();
		boolean checkIsOver=false;
		switch (source) {
			case KeyEvent.VK_UP: 
				if(modele.isCorrectIndex(pos.getAbsX()-1, pos.getOrdY())) {
					checkIsOver =modele.play(pos.getAbsX()-1,pos.getOrdY());
					this.button[pos.getAbsX()][pos.getOrdY()].setBackground(getBackground());
					break;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(modele.isCorrectIndex(pos.getAbsX()+1, pos.getOrdY())) {
					checkIsOver =modele.play(pos.getAbsX()+1,pos.getOrdY());
					this.button[pos.getAbsX()][pos.getOrdY()].setBackground(getBackground());
					break;
				}
				break;
			
			case KeyEvent.VK_RIGHT:
				if(modele.isCorrectIndex(pos.getAbsX(), pos.getOrdY()+1)) {
					checkIsOver =modele.play(pos.getAbsX(),pos.getOrdY()+1);
					this.button[pos.getAbsX()][pos.getOrdY()].setBackground(getBackground());
					break;
				}
				break;
			case KeyEvent.VK_LEFT:
				if(modele.isCorrectIndex(pos.getAbsX(), pos.getOrdY()-1)) {
					checkIsOver =modele.play(pos.getAbsX(),pos.getOrdY()-1);
					this.button[pos.getAbsX()][pos.getOrdY()].setBackground(getBackground());
					break;
				}
				break;
			default:
				break;
		}
		if(checkIsOver) {
			String message = "Félicitations!, vous avez gagné avec "+ this.modele.getNbCout() +" tours";
			JOptionPane.showMessageDialog(this, message);
			this.modele.setNbCout(0);
		}		
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	/**
	 * This method is used to add two integers. This is
	 * a the simplest form of a class method, just to
	 * show the usage of various javadoc Tags.
	 * @param numA This is the first paramter to addNum method
	 * @param numB  This is the second parameter to addNum method
	 * @return int This returns sum of numA and numB.
	 */
	private void move(String buttonText) {
		Position cord = modele.getCaseEmpty();
		boolean checkIsOver = false;
		if( (modele.isCorrectIndex(cord.getAbsX()-1, cord.getOrdY()) && modele.getGrille()[cord.getAbsX()-1][cord.getOrdY()].equals(buttonText)))
			checkIsOver = modele.play(cord.getAbsX()-1, cord.getOrdY());
		else if (modele.isCorrectIndex(cord.getAbsX()+1, cord.getOrdY()) && modele.getGrille()[cord.getAbsX()+1][cord.getOrdY()].equals(buttonText))
			checkIsOver = modele.play(cord.getAbsX()+1, cord.getOrdY());
				
		else if (modele.isCorrectIndex(cord.getAbsX(), cord.getOrdY()-1) && modele.getGrille()[cord.getAbsX()][cord.getOrdY()-1].equals(buttonText))
			checkIsOver = modele.play(cord.getAbsX(), cord.getOrdY()-1);
		
		else if (modele.isCorrectIndex(cord.getAbsX(), cord.getOrdY()+1) && modele.getGrille()[cord.getAbsX()][cord.getOrdY()+1].equals(buttonText))
			checkIsOver =  modele.play(cord.getAbsX(), cord.getOrdY()+1);
		if(checkIsOver) {
			String message = "Félicitations!, vous avez gagné avec "+ this.modele.getNbCout() +" coups";
			JOptionPane.showMessageDialog(this, message);
			this.modele.setNbCout(0);
		}
	}
	
	/**
	 * Cette fonction recupère l'image qui doit etre en imageIcon.
	 * @param i C'est l'indice de l'élement, il est de type int.
	 * @param j C'est l'indice de l'element, il est de type int.
	 * @return une image située à la position ij
	 */
	public ParseImage findParseImage(int i, int j) {
		if (this.imageBase != null && !modele.getGrille()[i][j].equals("--")) {
			int id = Integer.parseInt(modele.getGrille()[i][j]) -1;
			return this.subImages.get(id);
		}
		return null;
	}
	
	/**
	 * Cette fonction gère le melange sur le partie graphique
	 * @param e  de type ActionEvent
	 * @return int This returns sum of numA and numB.
	 */
 private void melange(ActionEvent e) {
		this.modele.mixTableContent();
		String [][] grille = this.modele.getGrille();
		for(int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				button[i][j].setEnabled(true);
				button[i][j].setBackground(null);
				if(grille[i][j].equals("--")) {
					this.disableButton(button[i][j]);
				}
			}
		}
	}
	
	/**
	 * Cette fonction est utilisée pour sauvegarder la partie du jeu en cours.
	 * @param e C'est le parametre, il est de type ActionEvent.
	 */
 private void sauvegardeTaquin(ActionEvent e) {
//		boolean verif = this.modele.saveTaquin(this.imageBase);
	 	boolean verif = this.modele.saveTaquin(this.imageBase, "data/file.txt");
		String message = null;
		if (verif) {
			if (this.imageBase != null) {
				for (int i = 0; i < this.modele.getRow(); i++) {
					for (int j = 0; j < this.modele.getColumn(); j++) {
						ImageIcon icon = (ImageIcon)this.button[i][j].getIcon();
						if (icon != null) {
							Image image = icon.getImage();
							BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
							Graphics2D bGr = bimage.createGraphics();
						    bGr.drawImage(image, 0, 0, null);
						    bGr.dispose();
							try {
								ImageIO.write(bimage, "png", new File("data/"+ this.modele.getText(i, j) +".png"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
			message = "La partie a ete sauvegarder";
		} else {
			message = "La partie n'a pas ete sauvegarder";
		}
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * Cette fonction est utilisée pour restaurer la partie du jeu sauvegardé.
	 * @param e C'est le parametre, il est de type ActionEvent.
	 */
	private void restaureTaquin(ActionEvent e) {
		boolean verif = this.modele.restaureTaquin("data/file.txt");
		
		this.panelGrille.setVisible(false);
		this.panelGrille.removeAll();
		this.panelGrille.setLayout(new GridLayout(this.modele.getRow(), this.modele.getColumn()));
		this.createButtons(this.modele.getRow(), this.modele.getColumn(), false);
		this.panelGrille.setVisible(true);
		
		if (verif) {
			try {
				this.imageBase = ImageIO.read(new File("data/image.png"));
				this.getSubImage();
				for (int i = 0; i < this.modele.getRow(); i++) {
					for (int j = 0; j < this.modele.getColumn(); j++) {
						if (!this.button[i][j].getText().equals("--")) {
							ParseImage pImg = this.findParseImage(i,j);
							this.button[i][j].setEnabled(true);
							this.button[i][j].setBackground(null);
							this.button[i][j].setIcon(new ImageIcon(pImg.getImage()));
						} else {
							this.disableButton(button[i][j]);
						}
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.tRow.setText(""+this.modele.getRow());
		this.tCol.setText(""+this.modele.getColumn());
		this.modele.endRestauration();
		JOptionPane.showMessageDialog(this, "La partie a ete restaurer");
	}
	
	/**
	 * Cette fonction est utilisée pour diviser une image.
	 */
 private void getSubImage() {
		this.subImages = new ArrayList<>();
		for (int i = 0; i < (this.modele.getRow() * this.modele.getColumn())-1; i++) {
			BufferedImage img;
			try {
				String id = ""+(1+i);
				img = ImageIO.read(new File("data/"+ id +".png"));
				ParseImage pImg = new ParseImage(id, img);
				subImages.add(pImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
