package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08.Task.doesMatrixIncludeSpiralPattern;

public class SolutionTests {
	@Test
	public void testUnwrapping(){
		int[][] inputMat1 = new int[][]{
				{0, 1, 2, 3, 4},
				{15, 16, 17, 18, 5},
				{14, 23, 24, 19, 6},
				{13, 22, 21, 20, 7},
				{12, 11, 10, 9, 8}
		};
		boolean resultArrExpected1 = true;
		boolean resultArrActual1 = doesMatrixIncludeSpiralPattern(inputMat1);

		int[][] inputMat2 = new int[][]{
				{0,  1,  2,  111,  4, 5},
				{13, 14, 15, 16, 17, 6},
				{12, 11, 10,  9,  8, 7},
		};
		boolean resultArrExpected2 = false;
		boolean resultArrActual2 = doesMatrixIncludeSpiralPattern(inputMat2);

		assertEquals(resultArrExpected1, resultArrActual1);
		assertEquals(resultArrExpected2, resultArrActual2);
	}
}
