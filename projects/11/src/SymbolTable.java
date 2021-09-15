import java.util.*;

public class SymbolTable {
	
	static Map<String,String[]> symbol_table_global = null;
	static Map<String,String[]> symbol_table_local = null;
	
	private int index_static = 0;
	private int index_field = 0;
	private int index_arg = 0;
	private int index_var = 0;
	
	public SymbolTable() {
		
		symbol_table_global = new HashMap<String,String[]>();
		symbol_table_local = new HashMap<String,String[]>();
	}
	
	//Resetting the local scope symbol table.
	public void startSubroutine() {
		
		symbol_table_local.clear();
		index_arg = 0;
		index_var = 0;
	}
	
	//Adding a variable entry to the corresponding symbol table.
	public void Define(String name, String type, String kind) {
		
		String[] type_kind_index = new String[3];
		type_kind_index[0] = type;
		type_kind_index[1] = kind;
		
		if(kind.equals("STATIC")) {
			
			type_kind_index[2] = Integer.toString(index_static);
			symbol_table_global.put(name,type_kind_index);
			index_static++;
		}		
		else if(kind.equals("FIELD")) {
			
			type_kind_index[2] = Integer.toString(index_field);
			symbol_table_global.put(name,type_kind_index);
			index_field++;
		}
		else if(kind.equals("ARG")) {
			
			type_kind_index[2] = Integer.toString(index_arg);
			symbol_table_local.put(name,type_kind_index);
			index_arg++;
		}
		else if(kind.equals("VAR")) {
			
			type_kind_index[2] = Integer.toString(index_var);
			symbol_table_local.put(name,type_kind_index);
			index_var++;
		}	
	}
	
	//Returns number of variables of given kind.
	public int VarCount(String kind) {
		
		if(kind.equals("STATIC")) {
						
			return index_static;
		}		
		else if(kind.equals("FIELD")) {
						
			return index_field;
		}
		else if(kind.equals("ARG")) {
					
			return index_arg;
		}
		else if(kind.equals("VAR")) {
			
			return index_var;
		}	
		else return -1;
	}
	
	//Returns kind of a variable.
	public String KindOf(String name) {
		
		if(!(symbol_table_global.get(name).equals(null))) {
			
			return symbol_table_global.get(name)[1];
		}		
		else if(!(symbol_table_local.get(name).equals(null))) {
			
			return symbol_table_local.get(name)[1];
		}
		else return "NONE";
	}
	
	//Returns type of a variable.
	public String TypeOf(String name) {
		
		if(!(symbol_table_global.get(name).equals(null))) {
			
			return symbol_table_global.get(name)[0];
		}			
		else if(!(symbol_table_local.get(name).equals(null))) {
			
			return symbol_table_local.get(name)[0];
		}
		else return "NONE";
	}
	
	//Returns index of a variable.
	public int IndexOf(String name) {
		
		if(!(symbol_table_global.get(name).equals(null))) {
			
			return Integer.parseInt(symbol_table_global.get(name)[2]);
		}			
		else if(!(symbol_table_local.get(name).equals(null))) {
			
			return Integer.parseInt(symbol_table_local.get(name)[2]);
		}
		else return -1;
	}
}
