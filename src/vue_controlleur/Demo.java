package vue_controlleur;

import model.Taquin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.*;

public class Demo {
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("+---------------- MENU -------------+");
		System.out.println("+                                   +");
		System.out.println("+ 1 - pour jouer en mode graphique  +");
		System.out.println("+-----------------------------------+");
		System.out.println("+ 2 - pour jouer en mode console    +");
		System.out.println("+-----------------------------------+");
		System.out.println("+ 3 - pour quitter                  +");
		System.out.println("+-----------------------------------+");

		String key = "";

		do {
			System.out.println("Veuillez entrer votre choix(1..3)  ");
			key = scan.nextLine();
		}while(!key.equals("1") && !key.equals("2") && !key.equals("3"));
	
		Taquin taquin;
		switch (key) {
			case "3":
				System.out.println("Bye Bye!");
				break;
			case "1":
				System.out.println("Vous etes en mode graphique");
				taquin = new Taquin(3, 3);
				new TaquinFenGUI(taquin);
				break;
			case "2":
				System.out.println("Vous etes en mode console");
				System.out.println("Veuillez entrer le nombre des lignes:  \t");
				String ligne = scan.nextLine();
				System.out.println("Veuillez entrer le nombre des colonnes:\t");
				String colonne = scan.nextLine();
				
				int row = Integer.parseInt(ligne);
				int col = Integer.parseInt(colonne);
	
				taquin = new Taquin(row, col);
				taquin.initTableContent();
				System.out.println("#ETAT FINAL");
				taquin.displayTable(); 
				taquin.mixTableContent();
				taquin.displayTable();
				do {
					System.out.println("entrer un caractere (w,z,a et d)  ");
					taquin.move();
				} while(!taquin.isOver());
				System.out.println("BRAVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");	
				break;
			default:
				break;
		}
		
		
		
	}
}
 