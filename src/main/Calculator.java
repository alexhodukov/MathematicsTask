package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Calculator {
	
	private final Map<String, Integer> operations = new HashMap<>();
	{
		operations.put("+", 1);
		operations.put("-", 1);
		operations.put("*", 2);
		operations.put("/", 2);
	}
	
	public Stack<String> translate(String src) {
		List<String> list = Arrays.asList(src.split(" "));
		Iterator<String> it = list.iterator();
		Stack<String> out = new Stack<>();
		Stack<String> op = new Stack<>();
		while (it.hasNext()) {
			String token = it.next();
			if (operations.containsKey(token)) {
				int pr = operations.get(token);
				if (op.isEmpty() | (!op.isEmpty() && (!isOperator(op.peek()) || pr > operations.get(op.peek())))) {
					op.push(token);
				} else {
					while (!op.isEmpty() && pr <= operations.get(op.peek())) {
						out.push(op.pop());
					}
					op.push(token);
				}
			} else if ("(".equals(token)) {
				op.push(token);
			} else if (")".equals(token)) {
				while (!op.isEmpty() && !op.peek().equals("(")) {
					out.push(op.pop());
				}
				op.pop();
			} else {
				out.push(token);
			}
		}
		
		while (!op.isEmpty()) {
			out.push(op.pop());
		}
		
		return out;
	}
	
	public int calculate(String src) {
		Stack<String> stack = translate(src);
		Collections.reverse(stack);
		Stack<String> calc = new Stack<>();
		while (!stack.isEmpty()) {
			String token = stack.pop();
			if (isOperator(token)) {
				Integer x1 = Integer.parseInt(calc.pop());
				Integer x2 = Integer.parseInt(calc.pop());
				switch (token) {
				case "+" : calc.push(String.valueOf(x2 + x1)); break;
				case "-" : calc.push(String.valueOf(x2 - x1)); break;
				case "*" : calc.push(String.valueOf(x2 * x1)); break;
				case "/" : calc.push(String.valueOf(x2 / x1)); break;
				}
			} else {
				calc.push(token);
			}
		}
		
		return Integer.parseInt(calc.pop());
	}
	
	public boolean isOperator(String token) {
		return operations.containsKey(token);
	}
			

	public static void main(String[] args) {
		System.out.println(new Calculator().calculate("8 + 2 * ( 7 - 5 )"));

	}

}
