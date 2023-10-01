package test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import model.Taquin;
import utils.Position;

public class TestTaquin {
	private Taquin taquin;
	private int row = 3;
	private int col = 3;

	@Test
	public void isCorrectIndexTest() {
		taquin = new Taquin(row, col);
		taquin.creatGrille(row, col);
		assertTrue(taquin.isCorrectIndex(0, 0));
		assertTrue(taquin.isCorrectIndex(1, 1));
		assertTrue(taquin.isCorrectIndex(row-1, col-1));
		assertFalse(taquin.isCorrectIndex(-2, -1));
	}
	
	@Test
	public void isOverTest() {
		taquin = new Taquin(row, col);
		taquin.creatGrille(row, col);
		taquin.mixTableContent();
		assertFalse(taquin.isOver());
	}
	
	@Test
	public void getEmptyCaseTest() {
		taquin = new Taquin(row, col);
		taquin.creatGrille(row, col);
		Position p = taquin.getCaseEmpty();
		assertTrue((p.getAbsX() == (row-1)) && (p.getOrdY() == (col-1)));
		assertFalse((p.getAbsX() == 0) && (p.getOrdY() == 0));
	}
	
	@Test
	public void mixTableDataTest() {
		taquin = new Taquin(row, col);
		taquin.creatGrille(row, col);
		Position p = taquin.getCaseEmpty();
		assertTrue((p.getAbsX() == (row-1)) && (p.getOrdY() == (col-1)));
		assertFalse((p.getAbsX() == 0) && (p.getOrdY() == 0));
		taquin.mixTableContent();
		assertFalse((p.getAbsX() == 0) && (p.getOrdY() == 0));
	}
	
	@Test
	public void playTest() {
		taquin = new Taquin(row, col);
		taquin.creatGrille(row, col);
		assertFalse(taquin.play((row-1), (col-2)));
		assertTrue(taquin.play((row-1), (col-1)));
	}
	
	@Test
	public void sauveAndResetTest() {
		Taquin taquinBase = new Taquin(row, col);
		Taquin taquinFinal = new Taquin(row, col);
		taquinBase.creatGrille(row, col);
		taquinBase.mixTableContent();
		String file = "fileTest.txt";
		taquinBase.saveTaquin(null, file);
		taquinFinal.restaureTaquin(file);
		assertTrue(Arrays.deepEquals(taquinBase.getGrille(), taquinFinal.getGrille()));
	}
}
