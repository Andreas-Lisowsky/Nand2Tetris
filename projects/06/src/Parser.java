import java.io.*;

public class Parser {
	
	static String command = null;
	
	public static void main (String args[]) {
		
		//Initialise hash-table
		SymbolTable init = new SymbolTable();
		
		//Setup Reader and Writer
		Writer writer = null;
		
		try {
			
			//Get the right file path format
			String file_name = args[0];
			file_name = file_name.replace("\\", "/");
			BufferedReader reader = new BufferedReader(new FileReader(file_name));
			BufferedReader reader_table = new BufferedReader(new FileReader(file_name));
				
			//Set name for output file
			file_name = file_name.substring(0, file_name.length()-3);
			file_name = file_name + "hack";
			writer = new FileWriter(file_name);
			
			//First run to replace symbols			
			int PC = 0;
			while(hasMoreCommands(reader_table) == true) {
				
				//Read command and remove all whitespaces and comments
				advance(reader_table);
				command = command.replaceAll("[\\s&&[^\\n]]+", "");
				if(command.indexOf("//") != -1) command = command.substring(0,command.indexOf("//"));
				
				//Skip empty lines
				if(!command.isEmpty()) {
					String symbol = symbol();
	
					//Look for labels, to put them into the hash-table
					if(commandType().equals("A_COMMAND")) PC++;
					else if(commandType().equals("L_COMMAND")) {
						if(!isInteger(symbol)) SymbolTable.addEntry(symbol,PC);
					}	
					//C_Command
					else PC++;
				}		
			}
			reader_table.close();
			
			int variable_addr = 16;
			//Second run to assemble the code
			while(hasMoreCommands(reader) == true) {
			
				//Read command and remove all whitespaces and comments
				advance(reader);
				command = command.replaceAll("[\\s&&[^\\n]]+", "");
				if(command.indexOf("//") != -1) command = command.substring(0,command.indexOf("//"));
				
				//Skip empty lines
				if(!command.isEmpty()) {
					if(commandType().equals("A_COMMAND")) {
						
						String symbol = symbol();
						//Symbol = Label
						if(SymbolTable.contains(symbol)) symbol = Integer.toString(SymbolTable.getAddress(symbol));
						//Symbol = Variable
						else if(!isInteger(symbol)) {
							SymbolTable.addEntry(symbol, variable_addr);
							symbol = Integer.toString(variable_addr);
							variable_addr++;					
						}
						symbol = Integer.toBinaryString(Integer.parseInt(symbol));
						for(int i = 16-symbol.length();i>0;i--) symbol = "0" + symbol;	
						writer.write(symbol);
						writer.write(System.getProperty("line.separator"));
					}
					else if(commandType().equals("C_COMMAND")){
						String c_instleadingbits,c_instc,c_instd,c_instj;
						c_instleadingbits = "111";
						c_instc = Code.comp(comp());
						c_instd = Code.dest(dest());
						c_instj = Code.jump(jump());
				
						String c_inst = c_instleadingbits + c_instc + c_instd + c_instj;
				
						writer.write(c_inst);
						writer.write(System.getProperty("line.separator"));					
					}	
				}
			}
			reader.close();
		}
		catch (IOException e){
			System.err.println("Error trying to read the .asm file.1");
		}
		finally {
			try {
				writer.close();
			} 
			catch (Exception e) {}
		}		
	}
	
	public static boolean hasMoreCommands(BufferedReader reader) {
		try {
			return reader.ready();			
		}
		catch (IOException e){
			System.err.println("Error trying to read the .asm file.2");
			return false;
		}		
	}
	
	public static void advance(BufferedReader reader) {
		try {			
			command = reader.readLine();
		}
		catch (IOException e){
			System.err.println("Error trying to read the .asm file.3");
		}
	}
	
	public static String commandType() {
			if (command.charAt(0) == '@') return "A_COMMAND";
			else if (command.charAt(0) == '(') return "L_COMMAND";
			else return "C_COMMAND";
	}
	
	public static String symbol() {
		if (command.charAt(0) == '@') return command.substring(1);
		else return command.substring(1,command.length()-1);
	}
	
	public static String dest() {
		int index = command.indexOf('=');
		if(index != -1) return command.substring(0,index);
		else return "";
	}
	
	public static String comp() {
		int index_d = command.indexOf('=');
		int index_j = command.indexOf(';');
		if((index_d != -1) && (index_j != -1)) return command.substring(index_d+1,index_j);
		else if((index_d != -1)) return command.substring(index_d+1);
		else if((index_j != -1)) return command.substring(0,index_j);
		else return command;
	}
	
	public static String jump() {
		int index = command.indexOf(';');
		if(index != -1) return command.substring(index+1);
		else return "";
	}
	
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch(NumberFormatException nfe) {
			return false;
		}
	}
}
