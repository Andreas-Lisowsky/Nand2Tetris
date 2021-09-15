import java.io.*;

public class Parser {
	
	static String command = null;
	BufferedReader reader = null;
	
	public Parser(File file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException fnfe) {System.err.println("Error trying to read the file.");}
	}
	
	public boolean hasMoreCommands() throws IOException {
		try {
			return reader.ready();
		}
		catch (IOException ioe) {
			System.err.println("Error trying to read the file.");
			return false;
		}
	}
	
	public void advance() {
		try {
			command = reader.readLine();
			
			//Read command and remove all whitespaces and comments
			command = command.trim();
			if(command.indexOf("//") != -1) command = command.substring(0,command.indexOf("//"));
		}
		catch (IOException ioe) {System.err.println("Error trying to read the file.");}
	}
	
	public String commandType() {
		
		String[] command_parts = command.split(" ");
		if(command.equals("add") || command.equals("sub") || command.equals("neg") || command.equals("eq") || command.equals("gt") || 
		   command.equals("lt")  || command.equals("and") || command.equals("or")  || command.equals("not")) {
			return "C_ARITHMETIC";
		}
		else if(command_parts[0].equals("push")) return "C_PUSH";
		else if(command_parts[0].equals("pop")) return "C_POP";
		else if(command_parts[0].equals("label")) return "C_LABEL";
		else if(command_parts[0].equals("goto")) return "C_GOTO";
		else if(command_parts[0].equals("if-goto")) return "C_IF";
		else if(command_parts[0].equals("function")) return "C_FUNCTION";
		else if(command.equals("return")) return "C_RETURN";
		else return "C_CALL";
	}
	
	public String arg1() {
		String[] command_parts = command.split(" ");
		if(commandType().equals("C_ARITHMETIC")) return command_parts[0]; 
		else return command_parts[1];
	}
	
	public int arg2() {
		String[] command_parts = command.split(" ");
		return Integer.parseInt(command_parts[2]);
	}
	
	public void Close() {
		try {
			reader.close();
		}
		catch (IOException ioe) {System.err.println("Error trying to close file.");}
	}
}
