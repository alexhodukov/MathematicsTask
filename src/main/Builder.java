package main;

public class Builder {
	private Generator gen;
	private String strExpression;
	
	public Builder(Generator gen) {
		this.gen = gen;
	}
	
	public void runGen(int[] nums) {
		gen.rec(nums, nums.length);
	}
	
	public String buildSequence(int[] ar, char[] ch) {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < ar.length; i++) {
			build.append(ar[i]);
			if (i < ch.length) {
				build.append(ch[i]);
			}
		}
		return new String(build);
	}

}
