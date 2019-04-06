package com.mathtask;

public class Operand {
	
	private int num;
	private int denom;
	private int usualNumber;
	private Mode mode;
	
	static enum Mode {
		NORMAL, FRACTIONAL;
	}
	
	public Operand(int number) {
		this.usualNumber = number;
		this.mode = Mode.NORMAL;
	}
	
	public Operand add(Operand op) {
		if (isModeNormal(op)) {
			usualNumber += op.getUsualNumber();
		} else {
			op = createFraction(op);
			addFraction(castToFraction(op));
		}
		
		return this;
	}

	public Operand subtract(Operand op) {
		if (isModeNormal(op)) {
			usualNumber -= op.getUsualNumber();
		} else {
			op = createFraction(op);
			subtractFraction(castToFraction(op));
		}
		
		return this;
	}
	
	public Operand multiply(Operand op) {
		if (isModeNormal(op)) {
			usualNumber *= op.getUsualNumber();
		} else {
			multipleFraction(createFraction(op));
		}
		
		return this;
	}
	
	public Operand divide(Operand op) {
		if (isModeNormal(op) && usualNumber % op.getUsualNumber() == 0) {
			usualNumber /= op.getUsualNumber();
		} else {
			divideFraction(createFraction(op));
		}
		
		return this;
	}
	
	private Operand createFraction(Operand op) {
		if (mode == Mode.NORMAL) {
			mode = Mode.FRACTIONAL;
			num = usualNumber;
			denom = 1;	
		}
		
		if (op.getMode() == Mode.NORMAL) {
			op.setNum(op.getUsualNumber());
			op.setDenom(1);
		} 
		
		return op;
	}
	
	private void addFraction(Operand op) {
		num += op.getNum();
	}
	
	private void subtractFraction(Operand op) {
		num -= op.getNum();
	}
	
	private void multipleFraction(Operand op) {
		num = num * op.getNum();
		denom = denom * op.getDenom();
	}
	
	private void divideFraction(Operand op) {
		num = num * op.getDenom();
		denom = denom * op.getNum();
	}
	
	private Operand castToFraction(Operand op) {
		int lcm = lcm(denom, op.getDenom());
		num *= lcm / denom;
		op.setNum(op.getNum() * lcm / op.getDenom());
		denom = lcm;
		
		return op;
	}
	
	private int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	
	private int lcm(int a, int b) {
		return a / gcd(a,b) * b;
	}
	
	public static Operand parseOperand(String string) {
		return new Operand(Integer.parseInt(string));
	}
	
	public boolean isResultFraction() {
		if (denom == 0) {
			return false;
		}
		
		return num % denom != 0;
	}
	
	private boolean isModeNormal(Operand op) {
		return mode == Mode.NORMAL && op.getMode() == Mode.NORMAL;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getDenom() {
		return denom;
	}

	public void setDenom(int denom) {
		this.denom = denom;
	}

	public int getUsualNumber() {
		return usualNumber;
	}

	public void setUsualNumber(int number) {
		this.usualNumber = number;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return "usual " + usualNumber + ", fraction " + num + "/" + denom;
	}

}
