package com.mathtask;

public class Main {
	private Builder builder;
	private Calculator calculator;
	private Generator generator;
	
	public Main() {
		this.calculator = new Calculator();
		
		generator = new Generator(calculator);
		generator.generateBrackets();
		
		calculator.setGenerator(generator);
		
		builder = new Builder(generator);
		generator.setBuild(builder);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		System.out.println(main.canBeEqualTo24(new int[]{4, 1, 3, 2}));
	}
	
	public boolean canBeEqualTo24(int[] nums) {
		builder.runGeneration(nums);
		
		return generator.isRightExpr();
	}
}
