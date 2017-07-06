package dev.monoentity.worldofcolors_v3.maps.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility {


	public static String loadFileAsString(String path){
		StringBuilder build = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				build.append(line + "\n");
			
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return build.toString();
	}
	
	public static int parseInt(String n){
		try{
			return Integer.parseInt(n);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
