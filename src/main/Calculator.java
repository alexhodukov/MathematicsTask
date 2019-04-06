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
		Stack<Operand> calc = new Stack<>();
		boolean isDivByZero = false;
		while (!stack.isEmpty() && !isDivByZero) {
			String token = stack.pop();
			if (isOperator(token)) {
				Operand x1 = calc.pop();
				Operand x2 = calc.pop();
				switch (token) {
				case "+" : calc.push(x2.add(x1)); break;
				case "-" : calc.push(x2.subtract(x1)); break;
				case "*" : calc.push(x2.multiply(x1)); break;
				case "/" : {
					if (!isOperandZero(x1)) {
						calc.push(x2.divide(x1));
					} else {
						isDivByZero = true;
					}
				} break;
				} 
			} else {
				calc.push(Operand.parseOperand(token));
			}
			System.out.println("stack " + stack + ",    calc " + calc + ",   token " + token);
		}
		
		if (isDivByZero | (!calc.isEmpty() && calc.pop().isResultFraction())) {
			return 0;
		} else {
			return calc.pop().getUsual();	
		}
	}
	
	public boolean isOperator(String token) {
		return operations.containsKey(token);
	}
	
	public boolean isOperandZero(Operand op) {
		return op.getUsual() == 0;
	}
			

	public static void main(String[] args) {
		System.out.println(new Calculator().calculate("8 + 3 / ( 7 - 5 )"));

	}

}
