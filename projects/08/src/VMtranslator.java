import java.io.*;

public class VMtranslator {
	
	static String file_separator = System.getProperty("file.separator");
	
	public static void main(String args[]) {
		
		//Convert path format from windows to java
		String file_name = args[0].replace("\\", file_separator);
		File file = new File(file_name);
		
		//Single file translation
		if(!file.isDirectory()) {
			CodeWriter c_writer = new CodeWriter(file_name.substring(0, file_name.length()-2) + "asm");
			
			c_writer.setFileName(file.getName());
			Parser parser = new Parser(file);
			translate(parser,c_writer);
			c_writer.Close();
		}
		
		//Multiple files translation
		else {
			File file_list[] = file.listFiles();
			CodeWriter c_writer = new CodeWriter(file.getAbsolutePath() + file_separator + file.getName() + ".asm");	
			
			for(int i = 0; i < file_list.length; i++) {
				
				//Only read VM files
				String f = file_list[i].getName();
				String f_ending = f.substring(f.length()-3,f.length());
				if(f_ending.equals(".vm")) {
					
					c_writer.setFileName(file_list[i].getName());
					Parser parser = new Parser(file_list[i]);
					translate(parser,c_writer);
				}
			}
			c_writer.Close();
		}	
	}
	
	public static void translate(Parser parser, CodeWriter c_writer) {
		
		try {
			//Initialize SP and call the main function
			c_writer.writeInit();
			
			//Read VM file line by line
			while(parser.hasMoreCommands()) {
				parser.advance();
				String arg1 = null;
				int arg2 = 0;
				
				//Skip empty lines
				if(!Parser.command.isEmpty()) {
					
					//Determine command type and corresponding arguments
					if(!parser.commandType().equals("C_RETURN")) {
						arg1 = parser.arg1();
					}
					if(parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP") ||
							parser.commandType().equals("C_FUNCTION") || parser.commandType().equals("C_CALL")) {
						arg2 = parser.arg2();
					}
					
					//Use the command and arguments to write the corresponding assembly code
					String command_parts[] = Parser.command.split(" ");
					if(parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
						c_writer.writePushPop(command_parts[0],arg1,arg2);
					}
					else if(parser.commandType().equals("C_ARITHMETIC")) {
						c_writer.writeArithmetic(arg1);
					}
					else if(parser.commandType().equals("C_LABEL")) {
						c_writer.writeLabel(arg1);
					}
					else if(parser.commandType().equals("C_GOTO")) {
						c_writer.writeGoto(arg1);
					}
					else if(parser.commandType().equals("C_IF")) {
						c_writer.writeIf(arg1);
					}
					else if(parser.commandType().equals("C_CALL")) {
						c_writer.writeCall(arg1,arg2);
					}
					else if(parser.commandType().equals("C_RETURN")) {
						c_writer.writeReturn();
					}
					else if(parser.commandType().equals("C_FUNCTION")) {
						c_writer.writeFunction(arg1,arg2);
					}
				}
			}
		}
		catch (IOException ioe) {System.err.println("Error trying to translate the VM file.");}
		finally {
			parser.Close();
		}
	}
}
