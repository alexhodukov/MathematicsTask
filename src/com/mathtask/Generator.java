package com.mathtask;

public class Generator {
	private Calculator calculator;
	private Builder builder;
	private int[] numbers;
	private char[] availOper;
	private Bracket[] brackets;
	private boolean isRightExpr;

	public Generator(Calculator calc) {
		this.calculator = calc;
		this.availOper = new char[]{'+', '-', '*', '/'};
		this.numbers = new int[4];
	}
	
	public void generateBrackets() {
		brackets = new Bracket[] {new Bracket(new boolean[]{false, false, false}),
				new Bracket(new boolean[]{true, false, false}),
				new Bracket(new boolean[]{false, true, false}),
				new Bracket(new boolean[]{false, false, true}),
				new Bracket(new boolean[]{true, true, false}),
				new Bracket(new boolean[]{false, true, true}),
				new Bracket(new boolean[]{true, false, true})};
	}
	
	public void generateNumbers(int[] ar, int level) {
		int size = ar.length;
		int i = size - level;
		while (i < size && !isRightExpr) {
			if (i != size - level || i == 0) {
				System.arraycopy(ar, 0, numbers, 0, ar.length);
				generateOperations(new char[]{' ', ' ', ' '}, 3);
			}
			if (level > 2) {
				generateNumbers(ar, level - 1);
			}
			shiftArray(ar, size - level);
			i++;
		}	
	}
	
	private void generateOperations(char[] ar, int level) {
		int size = ar.length;
		int i = 0;
		while (i < availOper.length && !isRightExpr) {
			ar[size - level] = availOper[i];
			if (level > 1) {
				generateOperations(ar, level - 1);
			}			
			if (level == 1) {
				int j = 0;
				while (j < brackets.length && !isRightExpr) {
					checkSequence(ar, brackets[j].getBrackets());
					j++;
				}
			}
			i++;
		}	
	}
	
	private int[] shiftArray(int[] ar, int pos) {
		int length = ar.length;
		int t = ar[pos];
		System.arraycopy(ar, pos + 1, ar, pos, length - 1 - pos);
		ar[length - 1] = t;
		
		return ar;
	}
	
	private void checkSequence(char[] ch, boolean[] brackets) {
		String sequence = builder.buildSequence(numbers, ch, brackets);
		calculator.computeExpression(sequence);
	}
	
	public void stopGenerate() {
		this.isRightExpr = true;
	}

	public void setBuild(Builder builder) {
		this.builder = builder;
	}
	
	public boolean isRightExpr() {
		return isRightExpr;
	}
}