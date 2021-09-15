import java.io.*;

public class JackCompiler {
	
	static String file_separator = System.getProperty("file_separator");

 	public static void main(String[] args) {
 		
 		//Convert path format from windows to java
 		String file_name = args[0].replace("\\", file_separator);
 		File file = new File(file_name);
 				
 		//Single file translation
 		if(!file.isDirectory()) {
 			VMWriter vm_writer = new VMWriter(file_name.substring(0, file_name.length()-4) + "vm");
 					
 		}
 				
 		//Multiple files translation
 		else {
 			File file_list[] = file.listFiles();
 			VMWriter vm_writer = new VMWriter(file.getAbsolutePath() + file_separator + file.getName() + ".vm");	
 					
 			for(int i = 0; i < file_list.length; i++) {
 						
 				//Only read jack files
 				String f = file_list[i].getName();
 				String f_ending = f.substring(f.length()-5,f.length());
 				if(f_ending.equals(".jack")) {
 					
 				}
 			}
 		}	
 	}
}
