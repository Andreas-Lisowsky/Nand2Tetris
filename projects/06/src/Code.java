public class Code {
	public static void main(String args[]) {
		
	}
	
	public static String dest(String dest_symbol) {
		String bit1,bit2,bit3;
		if(dest_symbol.contains("A")) {bit1 = "1";}
		else {bit1 = "0";}
		if(dest_symbol.contains("D")) {bit2 = "1";}
		else {bit2 = "0";}
		if(dest_symbol.contains("M")) {bit3 = "1";}
		else {bit3 = "0";}	
		
		return  bit1+bit2+bit3;
	}
	
	public static String comp(String comp_symbol) {
		if(comp_symbol.equals("0")) return("0101010");
		else if(comp_symbol.equals("1")) return("0111111");
		else if(comp_symbol.equals("-1")) return("0111010");
		else if(comp_symbol.equals("D")) return("0001100");
		else if(comp_symbol.equals("A")) return("0110000");
		else if(comp_symbol.equals("!D")) return("0001101");
		else if(comp_symbol.equals("!A")) return("0110001");
		else if(comp_symbol.equals("-D")) return("0001111");
		else if(comp_symbol.equals("-A")) return("0110011");
		else if(comp_symbol.equals("D+1")) return("0011111");
		else if(comp_symbol.equals("A+1")) return("0110111");
		else if(comp_symbol.equals("D-1")) return("0001110");
		else if(comp_symbol.equals("A-1")) return("0110010");
		else if(comp_symbol.equals("D+A")) return("0000010");
		else if(comp_symbol.equals("D-A")) return("0010011");
		else if(comp_symbol.equals("A-D")) return("0000111");
		else if(comp_symbol.equals("D&A")) return("0000000");
		else if(comp_symbol.equals("D|A")) return("0010101");
		else if(comp_symbol.equals("M")) return("1110000");
		else if(comp_symbol.equals("!M")) return("1110001");
		else if(comp_symbol.equals("-M")) return("1110011");
		else if(comp_symbol.equals("M+1")) return("1110111");
		else if(comp_symbol.equals("M-1")) return("1110010");
		else if(comp_symbol.equals("D+M")) return("1000010");
		else if(comp_symbol.equals("D-M")) return("1010011");
		else if(comp_symbol.equals("M-D")) return("1000111");
		else if(comp_symbol.equals("D&M")) return("1000000");
		else if(comp_symbol.equals("D|M")) return("1010101");
		else return null;
	}
	
	public static String jump(String jump_symbol) {
		if(jump_symbol.equals("JGT")) return "001";
		else if(jump_symbol.equals("JEQ")) return "010";
		else if(jump_symbol.equals("JGE")) return "011";
		else if(jump_symbol.equals("JLT")) return "100";
		else if(jump_symbol.equals("JNE")) return "101";
		else if(jump_symbol.equals("JLE")) return "110";
		else if(jump_symbol.equals("JMP")) return "111";
		else return "000";
	}
}
