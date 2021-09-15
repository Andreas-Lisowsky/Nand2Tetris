import java.io.*;
import java.util.*;

public class JackTokenizer {
	
	private static String token = "";
	static Scanner scanner = null;

	//Setting up input stream.
	public JackTokenizer(File file) {
		
		try {
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException fnfe) {
			System.err.println("Tokenizer could not open file.");
		}
	}	
	
	public boolean hasMoreTokens() {
		if(scanner.hasNext()) return true;
		else return false;
	}
	
	public void advance() {
		
	}
	
	public String tokenType() {
		
		if(token.equals("class") || token.equals("constructor") || token.equals("function") || token.equals("method") || token.equals("field") ||
		   token.equals("static") || token.equals("var") || token.equals("int") || token.equals("char") || token.equals("boolean") ||
		   token.equals("void") || token.equals("true") || token.equals("false") || token.equals("null") || token.equals("this") ||
		   token.equals("let") || token.equals("do") || token.equals("if") || token.equals("else") || token.equals("while") || token.equals("return")) {
			
			return "keyword";
		}
		else if(token.equals("{") || token.equals("}") || token.equals("(") || token.equals(")") || token.equals("[") || token.equals("]") ||
				token.equals(".") || token.equals(",") || token.equals(";") || token.equals("+") || token.equals("-") || token.equals("*") ||
				token.equals("/") || token.equals("&") || token.equals("|") || token.equals("<") || token.equals(">") || token.equals("=") ||
				token.equals("-")) {
			return "symbol";
		}
		else if(isInt(token)) {
			
			if(Integer.parseInt(token) >= 0 && Integer.parseInt(token) <= 32767) {
				
				return "integerConstant";
			}
		}
		else if(!(token.contains("\"")) && !(token.contains("\\n")) && token.charAt(0) == '"' && token.charAt(token.length()-1) == '"') {
			
			return "StringConstant";
		}
		else if(isInt(Character.toString(token.charAt(0)))) {
			
			return "identifier";
		}
		
		return "invalid";
	}
	
	public String keyWord() {
		
		return token;
	}
	
	public char symbol() {
		
		return token.charAt(0);
	}
	
	public String identifier() {
		
		return token;
	}
	
	public int intVal() {
		
		return Integer.parseInt(token);
	}
	
	public String stringVal() {
		
		return token.substring(1,token.length()-1);
	}
	
	//Method to check if a given string is an integer.
	public boolean isInt(String string) {
		
		try {
			
			Integer.parseInt(string);
			return true;
		}
		catch (NumberFormatException e) {
			
			return false;
		}
	}
}
