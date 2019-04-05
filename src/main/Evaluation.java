package main;

public class Evaluation {
	public static void main(String[] args) {
		Computer comp = new Computer();
		
		Generator gen = new Generator(comp);
		
		Builder build = new Builder(gen);
		gen.setBuild(build);
		build.runGen(new int[]{2, 2, 2, 2});



	}
}
