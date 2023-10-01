package model;

import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import utils.AbstractModeleEcoutable;
import utils.Position;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Arrays;


/**
 * Taquin cette classe est le model du projet !
 * 
 * @author  diallo-djiguine-diallo-diallo
 * @version 1.0
 * @since   2022-04-03
 */
public class Taquin extends AbstractModeleEcoutable{

	private String [][] grille;
	private String [][] oldGrille;
	private int row ;
	private int column;
	private int nbCout;
	
	/**
	 * Le constructeur du Taquin.
	 * @param row le nombre de ligne, type Integer.
	 * @param column le nombre de colonne, type Integer.
	 */
	public Taquin(int row ,int column){
		this.creatGrille(row,column);
		this.row=row;
		this.column=column;
		this.nbCout=0;
	}

	/**
	 * Cette fonction crée une grille à deux dimensions
	 * @param row le nombre de ligne, type Integer.
	 * @param column le nombre de colonne, type Integer.
	 */
	public void creatGrille(int row, int column) {
		this.grille=new String[row][column];
		this.row = row;
		this.column = column;
		this.initTableContent();
	}

	/**
	 * La fonction qui permet de recuperer la ligne.
	 * @return int.
	 */
	public int getRow(){
		return this.row;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public String [][] getGrille() {
		return this.grille;
	}
	
	public void setGrille(String [][] table){
		this.grille=table;
	}
	
	public int getNbCout(){
		return this.nbCout;
	}
	
	public void setNbCout(int n){
		this.nbCout = 0;
	}
	
	/**
	 * Cette fonction permet d'initialiser la grille
	 */
	public void initTableContent(){
		this.oldGrille = new String[this.row][this.column];
		int i=0,j=0;
		for (i=0 ; i < grille.length ; i++){
			for(j=0 ; j < grille[i].length ;j++){
				grille[i][j] = String.valueOf(1+(i*row)+j);
				this.oldGrille[i][j] = String.valueOf(1+(i*row)+j);
			}
		}
		this.grille[i-1][j-1]="--";
		this.oldGrille[i-1][j-1]="--";
	}
	
	/**
	 * Cette fonction  permet de récuperer un =élément de la grille à la postion r c.
	 * @param r l'indice de la ligne, type Int.
	 * @param c l'indice de la colonne, type Int.
	 * @return String .
	 */
	public String getText(int r ,int c) {
		return this.grille[r][c];
	}
	
	/**
	 * La fonction qui permet d'affiche la grille sur le terminal.
	 */
	public void displayTable(){
		for (int i=0 ; i < this.grille.length ; i++){
			for(int j=0 ; j < grille[i].length ;j++){
				if (grille[i][j].equals("--")) {
					System.out.print("| -  ");
				}else if(Integer.parseInt(grille[i][j])>+9) {
					System.out.print("| "+grille[i][j]+" ");
				}
				else {
					System.out.print("| "+grille[i][j]+"  ");
				}
			}
			System.out.println("| ");
		}
		System.out.println("");
	}
	
	/**
	 * Cette fonction teste si le jeu est fini
	 * Par comparaison de deux tableaux 
	 * @return true si les deux grilles sont identique et faux sinon
	 */
	public boolean isOver(){
		return Arrays.deepEquals(this.grille,this.oldGrille) ? true : false;
	}
	
	
	/**
	 * La fonction qui permet de mélanger la grille.
	 * @return void.
	 */
	public void mixTableContent(){
		String tmp;
		for (int i =0 ; i< this.row ; i++){
			for(int j=0; j < this.column ; j++){
				int i1=(int)(Math.random()*this.row);
				int j1=(int)(Math.random()*this.column);
				tmp=this.grille[i][j];
				this.grille[i][j]=this.grille[i1][j1];
				this.grille[i1][j1]=tmp;
			}
		}
		this.fireChangement();
	}
	
	/**
	 *Cette fonction vérifie si un index est dans la grille ou pas
	 * @param r de type int correspondant à la ligne 
	 * @param c de type int correspondant à la colonne
	 * @return true si l'index est dans la grille et faux sinon.
	 */
	public boolean isCorrectIndex(int r ,int c){
		return r < this.row && r >=0 && c < this.column && c >=0;
	}
	
	/**
	 * Cette fonction recupère la case vide est la grille.
	 * @return Position.
	 */
	public Position getCaseEmpty() {
		for (int i =0 ; i< this.row ; i++){
			for(int j=0; j < this.column ; j++){
				if(this.grille[i][j].equals("--")) {
					Position pos=new Position(i,j);
					return pos;
				}
			}
		}
		return null;
	}
	
	/**
	 * Cette fonction vérifie si la permitation est posible et l'execute.
	 * 
	 * @return boolean.
	 */
	public boolean move() {
		Scanner sc=new Scanner(System.in);
		String k=sc.nextLine();
		k=k.toLowerCase();
		switch (k) {
		
			case "q":  {
				if(isCorrectIndex(getCaseEmpty().getAbsX(), getCaseEmpty().getOrdY()-1)) 
					return play(getCaseEmpty().getAbsX(), getCaseEmpty().getOrdY()-1);	
				break;
			}
			case "z":{
				if(isCorrectIndex(getCaseEmpty().getAbsX()-1, getCaseEmpty().getOrdY()))
					return play(getCaseEmpty().getAbsX()-1, getCaseEmpty().getOrdY());
				break;
			}
			case "w":{
				if(isCorrectIndex(getCaseEmpty().getAbsX()+1, getCaseEmpty().getOrdY()))
					return play(getCaseEmpty().getAbsX()+1, getCaseEmpty().getOrdY());
				break;
			}
			case "d":{
				if(isCorrectIndex(getCaseEmpty().getAbsX(), getCaseEmpty().getOrdY()+1))
					return play(getCaseEmpty().getAbsX(),getCaseEmpty().getOrdY()+1);
				break;
			}
			default:{
				break;
			}
		}
		return false;
	}
	
	/**
	 * Cette fonction éffectue une permutation du carreau vide vers un autre déplaçable
	 * @param x de type int correspondant à la ligne
	 * @param y de tyype int correspondant à la 
	 * @return boolean 
	 */
	public boolean play(int x, int y) {
		Position cord = this.getCaseEmpty();
		String tmp = this.grille[x][y];
		this.grille[x][y]=this.grille[cord.getAbsX()][cord.getOrdY()];
		this.grille[cord.getAbsX()][cord.getOrdY()]=tmp;
		this.nbCout+=1;
		displayTable();

		fireChangement();
		return this.isOver();
	}
	
	/**
	 * Cette fonction sauvegarde le jeu.
	 * @param images la largeur du panel, type Integer.
	 * @param file le nom du fichier pour le sauvegarde.
	 * 
	 * @return boolean.
	 */
	public boolean saveTaquin(BufferedImage images, String file) {
		boolean result = false;
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			result = true;
			writer.println(this.row);
			writer.println(this.column);
			writer.println(this.nbCout);
			for (String [] s : this.grille) {
				for (String c : s) {
					writer.println(c);
				}
			}
			if(images != null) {
				result = ImageIO.write(images, "png", new File("data/image.png"));
				if (result) {
					writer.println("yes");
				}
			} else {
				writer.println("no");
			}
			writer.close();
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * Cette fonction restaure une partie déjà sauvegardée
	 * @param file le nom du fichier pour le sauvegarde.
	 * @return boolean.
	 */
	public boolean restaureTaquin(String file) {
		boolean result = false;
		try {
			BufferedReader read = new BufferedReader(new FileReader(file));
			this.row = Integer.parseInt(read.readLine());
			this.column = Integer.parseInt(read.readLine());
			this.nbCout = Integer.parseInt(read.readLine());
			this.grille = new String[this.row][this.column];
			this.oldGrille = new String[this.row][this.column];
			for (int i = 0; i < this.row; i++) {
				for (int j = 0; j < this.column; j++) {
					this.grille[i][j] = read.readLine();
					this.oldGrille[i][j] = String.valueOf(1+(i*this.row)+j);
					if ((i+1) == this.row && (j+1) == this.column) {
						this.oldGrille[i][j] = String.valueOf("--");
					}
				}
			}
			if(read.readLine().equals("yes")) {
				result = true;
			}
			read.close();
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * Cette fonction mets à jour l'interface après restauration.
	 */
	public void endRestauration() {
		this.fireChangement();
	}
}
