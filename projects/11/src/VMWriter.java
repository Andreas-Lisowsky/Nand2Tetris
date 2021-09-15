import java.io.*;

public class VMWriter {
	
	Writer w = null;
	private static String eol = System.getProperty("line.separator");
	
	//Setting up output file.
	public VMWriter(String output_file) {
		try {
			w = new FileWriter(output_file);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write to file.");}
	}
	
	//Method collection to write VM commands.
	public void writePush(String segment, int index) {
		
		try{
			
			w.write("push "+segment+" "+index+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write push command.");}
	}
	
	public void writePop(String segment, int index) {
		
		try{
			
			w.write("pop "+segment+" "+index+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write pop command.");}
	}
	
	public void writeArithmetic(String command) {
		
		try{
			
			w.write(command+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write arithmetic command.");}
	}
	
	public void writeLabel(String label) {

		try{
			
			w.write("label "+label+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write label command.");}
	}
	
	public void writeGoto(String label) {
		
		try{
			
			w.write("goto "+label+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write goto command.");}
	}
	
	public void writeIf(String label) {
		
		try{
			
			w.write("if-goto "+label+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write if-goto command.");}
	}
	
	public void writeCall(String name, int nArgs) {
		
		try{
			
			w.write("call "+name+" "+nArgs+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write call command.");}
	}
	
	public void writeFunction(String name, int nLocals) {
		
		try{
			
			w.write("call "+name+" "+nLocals+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write function command.");}
	}
	
	public void writeReturn() {
		
		try{
			
			w.write("return"+eol);
		}
		catch(IOException ioe) {System.err.println("VMWriter could not write function command.");}
	}
	
	public void close() {
		
		try {
			w.close();
		}
		catch(IOException ioe) {
			System.err.println("VMWriter could not close file.");
		}
	}
}
