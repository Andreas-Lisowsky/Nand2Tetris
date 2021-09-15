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
	
	//For each eq,gt and lt command we need labels, which we give unique names by adding an incremental integer as a string
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
}
