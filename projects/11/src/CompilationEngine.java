import java.io.*;
import java.util.*;

public class CompilationEngine {
	
	Scanner scanner = null;
	FileWriter writer = null;
	
	public CompilationEngine(File input, File output) {
		
		try {
			
			scanner = new Scanner(input);
			writer = new FileWriter(output);
		}
		catch (FileNotFoundException fnfe) {System.err.println("CompilationEngine could not open input file.");}
		catch (IOException ioe) {System.err.println("CompilationEngine could not open output file.");}
	}
	
	public void compileClass() {
		
		
	}
	
	public void compileClassVarDec() {
		
		
	}
	
	public void compileSubroutine() {
		
		
	}
	
	public void compileParameterList() {
		
		
	}
	
	public void compileVarDec() {
		
		
	}
	
	public void compileStatements() {
		
		
	}
	
	public void compileDo() {
		
		
	}
	
	public void compileLet() {
		
		
	}
	
	public void compileWhile() {
		
		
	}
	
	public void compileReturn() {
		
		
	}
	
	public void compileIf() {
		
		
	}
	
	public void compileExpression() {
		
		
	}
	
	public void compileTerm() {
		
		
	}
}
