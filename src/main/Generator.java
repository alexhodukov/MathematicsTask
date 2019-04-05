package main;

public class Generator {
	private Computer comp;
	private Builder build;
	private int[] numbers;
	private char[] availOper;
	private char[] parentheses;
	private int index;
	private int indexSymbol;
	
	
	public Generator(Computer comp) {
		this.comp = comp;
		this.availOper = new char[]{'+', '-', '*', '/'};
		this.numbers = new int[4];
	}
	
	public void rec(int[] ar, int def) {
		int size = ar.length;
		for (int i = size - def; i < size; i++) {
			if ( i != size - def | i == 0 ) {
				System.arraycopy(ar, 0, numbers, 0, ar.length);
//				show(ar);
				recSymbol(new char[]{' ', ' ', ' '}, 3);
			}
			if ( def > 2 ) {
				rec(ar, def - 1);
			}
			shiftArray(ar, size - def);
		}	
	}
	
	private int[] shiftArray(int[] ar, int pos) {
		int length = ar.length;
		int t = ar[pos];
		System.arraycopy(ar, pos + 1, ar, pos, length - 1 - pos);
		ar[length - 1] = t;
		return ar;
	}
	
	public void recSymbol(char[] ar, int def) {
		int size = ar.length;
		for (int i = 0; i < availOper.length; i++) {
			ar[size - def] = availOper[i];
			if (def > 1) {
				recSymbol(ar, def - 1);
			}			
			if (def == 1) {
//				showSymbol(ar);
				indexSymbol++;
				checkSequence(ar);
			}
		}	
	}
	
	
	
	private boolean checkSequence(char[] ch) {
		String sequence = build.buildSequence(numbers, ch);
		return comp.computeExpression(sequence);
	}
	
	private void show(int[] a) {
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + ", ");
		}
		System.out.println("");
		index++;
	}
	
	private void showSymbol(char[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ", ");
		}
		System.out.println("");
		indexSymbol++;
	}
	
	public int getIndex() {
		return index;
	}

	public int getIndexSymbol() {
		return indexSymbol;
	}

	public void setBuild(Builder build) {
		this.build = build;
	}
	
	
	

}