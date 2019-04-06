package main;

public class Operand {
	
	private int num;
	private int denom;
	private int usual;
	private Mode mode;
	
	public Operand(int number) {
		this.usual = number;
		this.mode = Mode.NORMAL;
	}
	
	public Operand add(Operand op) {
		if (mode == Mode.NORMAL && op.getMode() == Mode.NORMAL) {
			usual += op.getUsual();
		} else {
			op = createFraction(op);
			addFraction(castFraction(op));
		}
		
		return this;
	}

	public Operand subtract(Operand op) {
		if (mode == Mode.NORMAL && op.getMode() == Mode.NORMAL) {
			usual -= op.getUsual();
		} else {
			op = createFraction(op);
			subtractFraction(castFraction(op));
		}
		
		return this;
	}
	
	public Operand multiply(Operand op) {
		if (mode == Mode.NORMAL && op.getMode() == Mode.NORMAL) {
			usual *= op.getUsual();
		} else {
			multipleFraction(createFraction(op));
		}
		
		return this;
	}
	
	public Operand divide(Operand op) {
		if (mode == Mode.NORMAL && op.getMode() == Mode.NORMAL && usual % op.getUsual() == 0) {
			usual /= op.getUsual();
		} else {
			divideFraction(createFraction(op));
		}
		
		return this;
	}
	
	private Operand createFraction(Operand op) {
		if (mode == Mode.NORMAL) {
			mode = Mode.FRACTIONAL;
			num = usual;
			denom = 1;	
		}
		
		if (op.getMode() == Mode.NORMAL) {
			op.setNum(op.getUsual());
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
	
	private void divideFraction(Operand op) {
		num = num * op.getDenom();
		denom = denom * op.getNum();
	}
	
	private void multipleFraction(Operand op) {
		num = num * op.getNum();
		denom = denom * op.getDenom();
	}
	
	private Operand castFraction(Operand op) {
		int lcm = lcm(denom, op.getDenom());
		num *= lcm / denom;
		op.setNum(op.getNum() * lcm / op.getDenom());
		denom = lcm;
		return op;
	}

	public static void main(String[] args) {		
		Operand o1 = new Operand(0);
		o1.setNum(3);
		o1.setDenom(4);
		o1.setMode(Mode.FRACTIONAL);
		Operand o2 = new Operand(3);
		o2.setMode(Mode.NORMAL);
		
		o1.add(o2);
		System.out.println(o1);

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
		return num % denom != 0;
	}
	
	static enum Mode {
		NORMAL, FRACTIONAL;
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

	public int getUsual() {
		return usual;
	}

	public void setUsual(int usual) {
		this.usual = usual;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		return "usual " + usual + ", fraction " + num + "/" + denom;
	}

}
