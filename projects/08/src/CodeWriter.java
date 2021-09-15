import java.io.*;

public class CodeWriter {
	
	static String fileName = null;
	private static Writer writer = null;
	static String eol = System.getProperty("line.separator");
	
	public CodeWriter(String output_file) {
		try {
			writer = new FileWriter(output_file);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void setFileName(String file_name) {
		fileName = file_name;
	}
	
	//For each eq, gt and lt command we need labels, which we give unique names by adding an incremental integer as a string
	private static int label_count_eq_true = 0;
	private static int label_count_eq_false = 0;
	private static int label_count_gt_true = 0;
	private static int label_count_gt_false = 0;
	private static int label_count_lt_true = 0;
	private static int label_count_lt_false = 0;
	public void writeArithmetic(String command) {
		try {	
			if(command.equals("add")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("M=D+M"+eol);	
			}
			else if(command.equals("sub")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("M=M-D"+eol);	
			}
			else if(command.equals("neg")) {
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=-M"+eol);
			}
			else if(command.equals("eq")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("D=M-D"+eol);
				writer.write("@TRUE_EQ"+Integer.toString(label_count_eq_true)+eol);
				writer.write("D;JEQ"+eol);
				
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=0"+eol);	
				writer.write("@FALSE_EQ"+Integer.toString(label_count_eq_false)+eol);
				writer.write("0;JMP"+eol);
				
				writer.write("(TRUE_EQ"+Integer.toString(label_count_eq_true)+")"+eol);
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=-1"+eol);
				
				writer.write("(FALSE_EQ"+Integer.toString(label_count_eq_false)+")"+eol);
				
				label_count_eq_true++;
				label_count_eq_false++;
			}	
			else if(command.equals("gt")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("D=M-D"+eol);
				writer.write("@TRUE_GT"+Integer.toString(label_count_gt_true)+eol);
				writer.write("D;JGT"+eol);
				
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=0"+eol);	
				writer.write("@FALSE_GT"+Integer.toString(label_count_gt_false)+eol);
				writer.write("0;JMP"+eol);
				
				writer.write("(TRUE_GT"+Integer.toString(label_count_gt_true)+")"+eol);
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=-1"+eol);
				
				writer.write("(FALSE_GT"+Integer.toString(label_count_gt_false)+")"+eol);
				
				label_count_gt_true++;
				label_count_gt_false++;
			}
			else if(command.equals("lt")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("D=M-D"+eol);
				writer.write("@TRUE_LT"+Integer.toString(label_count_lt_true)+eol);
				writer.write("D;JLT"+eol);
				
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=0"+eol);	
				writer.write("@FALSE_LT"+Integer.toString(label_count_lt_false)+eol);
				writer.write("0;JMP"+eol);
				
				writer.write("(TRUE_LT"+Integer.toString(label_count_lt_true)+")"+eol);
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=-1"+eol);
				
				writer.write("(FALSE_LT"+Integer.toString(label_count_lt_false)+")"+eol);
				
				label_count_lt_true++;
				label_count_lt_false++;
			}
			else if(command.equals("and")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("M=D&M"+eol);	
			}
			else if(command.equals("or")) {
				writer.write("@SP"+eol);
				writer.write("AM=M-1"+eol);
				writer.write("D=M"+eol);
				writer.write("A=A-1"+eol);
				writer.write("M=D|M"+eol);	
			}
			else if(command.equals("not")) {
				writer.write("@SP"+eol);
				writer.write("A=M-1"+eol);
				writer.write("M=!M"+eol);
			}
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}	
	}
	
	public void writePushPop(String command, String segment, int index) {
		try {
			String index_string = Integer.toString(index);
			
			if(command.equals("push")) {
				if(segment.equals("argument") || segment.equals("local") || segment.equals("this") || segment.equals("that")) {
					
					if(segment.equals("argument")) writer.write("@ARG"+eol);
					else if(segment.equals("local")) writer.write("@LCL"+eol);
					else if(segment.equals("this")) writer.write("@THIS"+eol);
					else writer.write("@THAT"+eol);
							
					writer.write("D=M"+eol);
					writer.write("@"+index_string+eol);
					writer.write("A=D+A"+eol);
					writer.write("D=M"+eol);
					writer.write("@SP"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);
					writer.write("@SP"+eol);
					writer.write("M=M+1"+eol);
				}				
				else if(segment.equals("static")) {
					writer.write("@"+fileName.substring(0,fileName.length()-2)+index_string+eol);
					writer.write("D=M"+eol);
					writer.write("@SP"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);
					writer.write("@SP"+eol);
					writer.write("M=M+1"+eol);
				}
				else if(segment.equals("constant")) {
					writer.write("@"+index_string+eol);
					writer.write("D=A"+eol);
					writer.write("@SP"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);	
					writer.write("@SP"+eol);
					writer.write("M=M+1"+eol);
				}
				else if(segment.equals("pointer") || segment.equals("temp")) {
					if(segment.equals("pointer")) writer.write("@R3"+eol);
					else writer.write("@R5"+eol);
					
					writer.write("D=A"+eol);
					writer.write("@"+index_string+eol);
					writer.write("A=D+A"+eol);
					writer.write("D=M"+eol);
					writer.write("@SP"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);
					writer.write("@SP"+eol);
					writer.write("M=M+1"+eol);
				}
			}
			else if(command.equals("pop")) {
				if(segment.equals("argument") || segment.equals("local") || segment.equals("this") || segment.equals("that")) {
					
					if(segment.equals("argument")) writer.write("@ARG"+eol);
					else if(segment.equals("local")) writer.write("@LCL"+eol);
					else if(segment.equals("this")) writer.write("@THIS"+eol);
					else writer.write("@THAT"+eol);
							
					writer.write("D=M"+eol);
					writer.write("@"+index_string+eol);
					writer.write("D=D+A"+eol);
					writer.write("@R13"+eol);
					writer.write("M=D"+eol);
					writer.write("@SP"+eol);
					writer.write("AM=M-1"+eol);
					writer.write("D=M"+eol);
					writer.write("@R13"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);
				}				
				else if(segment.equals("static")) {
					writer.write("@SP"+eol);
					writer.write("AM=M-1"+eol);
					writer.write("D=M"+eol);
					writer.write("@"+fileName.substring(0,fileName.length()-2)+index_string+eol);
					writer.write("M=D"+eol);
				}
				else if(segment.equals("pointer") || segment.equals("temp")) {
					if(segment.equals("pointer")) writer.write("@R3"+eol);
					else writer.write("@R5"+eol);
					
					writer.write("D=A"+eol);
					writer.write("@"+index_string+eol);
					writer.write("D=D+A"+eol);
					writer.write("@R13"+eol);
					writer.write("M=D"+eol);
					writer.write("@SP"+eol);
					writer.write("AM=M-1"+eol);
					writer.write("D=M"+eol);
					writer.write("@R13"+eol);
					writer.write("A=M"+eol);
					writer.write("M=D"+eol);
				}
			}	
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	public void Close() {
		try {
			writer.close();
		}
		catch (IOException ioe) {System.err.println("Error trying to close file.");}
	}
	
	public void writeInit() {
		try {
			//Set SP to 256
			writer.write("@256"+eol);
			writer.write("D=A"+eol);
			writer.write("@SP"+eol);
			writer.write("M=D"+eol);
			
			//Execute main function
			writeCall("Sys.init",0);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void writeLabel(String label) {
		try {
			writer.write("("+label+")"+eol);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void writeGoto(String label) {
		try {
			writer.write("@"+label+eol);
			writer.write("0;JMP"+eol);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void writeIf(String label) {
		try {
			writer.write("@SP"+eol);
			writer.write("AM=M-1"+eol);	
			writer.write("D=M"+eol);
			writer.write("@"+label+eol);
			writer.write("D;JNE"+eol);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	//Counter to make labels unique
	private static int counter = 0;
	public void writeCall(String functionName, int numArgs) {	
		try {
			//Push return address
			writer.write("@return-address-"+functionName+Integer.toString(counter)+eol);
			writer.write("D=A"+eol);
			writer.write("@SP"+eol);
			writer.write("A=M"+eol);
			writer.write("M=D"+eol);
			writer.write("@SP"+eol);
			writer.write("M=M+1"+eol);
			
			//Push LCL ARG
			writer.write("@R1"+eol);
			writer.write("D=M"+eol);
			writer.write("@SP"+eol);
			writer.write("A=M"+eol);
			writer.write("M=D"+eol);
			writer.write("@SP"+eol);
			writer.write("M=M+1"+eol);
			
			writer.write("@R2"+eol);
			writer.write("D=M"+eol);
			writer.write("@SP"+eol);
			writer.write("A=M"+eol);
			writer.write("M=D"+eol);
			writer.write("@SP"+eol);
			writer.write("M=M+1"+eol);
			
			//Push THIS, THAT
			for(int i = 0;i <= 1; i++) writePushPop("push","pointer",i);
			
			//Set new value for ARG
			writer.write("D=M"+eol);
			writer.write("@"+Integer.toString(numArgs+5)+eol);
			writer.write("D=D-A"+eol);
			writer.write("@ARG"+eol);
			writer.write("M=D"+eol);
			
			//Set new value for LCL
			writer.write("@SP"+eol);
			writer.write("D=M"+eol);
			writer.write("@LCL"+eol);
			writer.write("M=D"+eol);
			
			//Jump to function code and set return label
			writeGoto(functionName);
			writeLabel("return-address-"+functionName+Integer.toString(counter));
			
			counter++;
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void writeReturn() {
		try {
			//Frame = LCL
			writer.write("@LCL"+eol);
			writer.write("D=M"+eol);
			writer.write("@R14"+eol);
			writer.write("M=D"+eol);
			
			//Return address = *(Frame-5)
			writer.write("@R14"+eol);
			writer.write("D=M"+eol);
			writer.write("@5"+eol);
			writer.write("A=D-A"+eol);
			writer.write("D=M"+eol);
			writer.write("@R15"+eol);
			writer.write("M=D"+eol);
			
			//*ARG = pop()
			writer.write("@SP"+eol);
			writer.write("AM=M-1"+eol);
			writer.write("D=M"+eol);
			writer.write("@ARG"+eol);
			writer.write("A=M"+eol);
			writer.write("M=D"+eol);
			
			//SP = ARG+1
			writer.write("@ARG"+eol);
			writer.write("D=M"+eol);
			writer.write("@SP"+eol);
			writer.write("M=D+1"+eol);
			
			//Set THAT, THIS, ARG, LCL addresses to the values of the function that initially called the currently returning function
			writer.write("@R14"+eol);
			writer.write("A=M-1"+eol);
			writer.write("D=M"+eol);
			writer.write("@THAT"+eol);
			writer.write("M=D"+eol);
			
			writer.write("@R14"+eol);
			writer.write("D=M"+eol);
			writer.write("@2"+eol);
			writer.write("A=D-A"+eol);
			writer.write("D=M"+eol);
			writer.write("@THIS"+eol);
			writer.write("M=D"+eol);
			
			writer.write("@R14"+eol);
			writer.write("D=M"+eol);
			writer.write("@3"+eol);
			writer.write("A=D-A"+eol);
			writer.write("D=M"+eol);
			writer.write("@ARG"+eol);
			writer.write("M=D"+eol);
			
			writer.write("@R14"+eol);
			writer.write("D=M"+eol);
			writer.write("@4"+eol);
			writer.write("A=D-A"+eol);
			writer.write("D=M"+eol);
			writer.write("@LCL"+eol);
			writer.write("M=D"+eol);
			
			//Goto return address
			writer.write("@R15"+eol);
			writer.write("A=M"+eol);
			writer.write("0;JMP"+eol);
		}
		catch (IOException ioe) {System.err.println("Error trying to write to file.");}
	}
	
	public void writeFunction(String functionName, int numLocals) {
		
		writeLabel(functionName);
		for(int i = numLocals; i > 0;i--) {		
			writePushPop("push","constant",0);
		}
	}
}
