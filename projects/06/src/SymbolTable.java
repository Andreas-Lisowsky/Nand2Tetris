import java.util.*;

public class SymbolTable {
	public SymbolTable() {
		//Set the predefined values
		symbol_table.put("SP", 0);
		symbol_table.put("LCL", 1);
		symbol_table.put("ARG", 2);
		symbol_table.put("THIS", 3);
		symbol_table.put("THAT", 4);
		for(int i = 0; i <= 15; i++) symbol_table.put("R"+Integer.toString(i), i);
		symbol_table.put("SCREEN", 16384);
		symbol_table.put("KBD", 24576);	
	}
	
	static Map<String,Integer> symbol_table = new HashMap<String,Integer>();
	
	public static void main(String args[]) {
		
	}

	public static void addEntry(String symbol, int address) {
		symbol_table.put(symbol,address);
	}
	
	public static boolean contains(String symbol) {
		if(symbol_table.get(symbol) == null) return false;
		else return true;
	}
	
	public static int getAddress(String symbol) {
		return symbol_table.get(symbol);
	}
}
