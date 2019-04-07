package com.mathtask;

public class Main {
	private Generator generator;
	private Builder builder;
	private Calculator calculator;
	
	public Main() {
		this.generator = new Generator();
		this.calculator = new Calculator();
		this.builder = new Builder();
		
		generator.setBuilder(builder);
		generator.generateBrackets();
		builder.setCalculator(calculator);
		calculator.setGenerator(generator);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		System.out.println(main.canBeEqualTo24(new int[]{4, 1, 8, 7}));
	}
	
	public boolean canBeEqualTo24(int[] nums) {
		generator.runGeneration(nums);
		
		return generator.isRightExpr();
	}
}
