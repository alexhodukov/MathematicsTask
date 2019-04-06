package com.mathtask;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Calculator {
	
	private final int neededResult = 24;
	
	private Generator generator;

	private final Map<String, Integer> operations = new HashMap<>();
	{
		operations.put("+", 1);
		operations.put("-", 1);
		operations.put("*", 2);
		operations.put("/", 2);
	}
	
	public void computeExpression(String src) {
		int result = calculate(src);
		if (isRightResult(result)) {
			generator.stopGenerate();
		}
	}
	
	private boolean isRightResult(int result) {
		return result == neededResult;
	}
	
	private Stack<String> translateRpn(String src) {
		List<String> list = Arrays.asList(src.split(" "));
		Iterator<String> it = list.iterator();
		Stack<String> out = new Stack<>();
		Stack<String> oper = new Stack<>();
		while (it.hasNext()) {
			String token = it.next();
			if (operations.containsKey(token)) {
				int prior = operations.get(token);
				if (oper.isEmpty() || (!oper.isEmpty() && (!isOperator(oper.peek()) || prior > operations.get(oper.peek())))) {
					oper.push(token);
				} else {
					while (!oper.isEmpty() && isOperator(oper.peek()) && prior <= operations.get(oper.peek())) {
						out.push(oper.pop());
					}
					oper.push(token);
				}
			} else if (isLeftBracket(token)) {
				oper.push(token);
			} else if (isRightBracket(token)) {
				while (!oper.isEmpty() && !isLeftBracket(oper.peek())) {
					out.push(oper.pop());
				}
				oper.pop();
			} else {
				out.push(token);
			}
		}
		
		while (!oper.isEmpty()) {
			out.push(oper.pop());
		}
		
		return out;
	}
	
	public int calculate(String src) {
		Stack<String> stack = translateRpn(src);
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
		}
		
		if (isDivByZero | (!calc.isEmpty() && calc.peek().isResultFraction())) {
			return 0;
		} else {
			return calc.pop().getUsualNumber();	
		}
	}
	
	public boolean isOperator(String token) {
		return operations.containsKey(token);
	}
	
	public boolean isOperandZero(Operand op) {
		return op.getUsualNumber() == 0;
	}
	
	private boolean isLeftBracket(String s) {
		return "(".equals(s);
	}

	private boolean isRightBracket(String s) {
		return ")".equals(s);
	}
	
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

}
