package com.mathtask;

public class Builder {
	private Generator generator;
	
	public Builder(Generator generator) {
		this.generator = generator;
	}
	
	public void runGeneration(int[] nums) {
		generator.generateNumbers(nums, nums.length);
	}
	
	public String buildSequence(int[] ar, char[] ch, boolean[] brackets) {
		StringBuilder build = new StringBuilder();
		boolean isBracket = false;
		
		for (int i = 0; i < ch.length; i++) {
			if (brackets[i] && !isBracket) {
				if (i > 0) {
					build.delete(build.length() - 2, build.length());
				}
				build.append("(").append(" ");
				if (i > 0) {
					build.append(ar[i]).append(" ");
				}
				isBracket = true;
			}
			if (i == 0) {
				build.append(ar[i]).append(" ");
			}
			build.append(ch[i]).append(" ");
			build.append(ar[i + 1]).append(" ");
			if (isBracket && ((i == ch.length - 1) || (i < ch.length - 1 && !brackets[i + 1]))) {
				build.append(")").append(" ");
				isBracket = false;
			}
		}
		
		return new String(build);
	}

}
