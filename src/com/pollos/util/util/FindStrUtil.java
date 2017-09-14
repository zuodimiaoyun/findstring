package com.pollos.util.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindStrUtil {
	private static final String FIND_NOTHING = "find nothing!";
	
	public static String findStr(String path, String str) throws IOException{
		return findStr(path, str, false, false);
	}
	
	public static void findStr(String path, String str, File output){
		findStr(path, str, output, false, false);
	}
	
	public static String findStr(String path, String str, boolean showLine, boolean onlyFileName) throws IOException{
		return findStr(new File(path), Pattern.compile(str), showLine, onlyFileName);
	}
	
	public static String findStr(File searchFile, Pattern pattern, boolean showLine, boolean onlyFileName) throws IOException{
		if(searchFile != null && searchFile.exists()){
			if(searchFile.isDirectory()){
				File[] fileArray = searchFile.listFiles();
				StringBuilder sb = new StringBuilder();
				for (File file : fileArray) {
					sb.append(findStr(file, pattern, showLine, onlyFileName));
				}
				return sb.toString();
			}else{
				return findStrInFile(searchFile, pattern, showLine, onlyFileName);
			}
		}
		return FIND_NOTHING;
	}
	
	private static String findStrInFile(File searchFile, Pattern pattern, boolean showLine, boolean onlyFileName) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(searchFile))){
			StringBuilder sb = new StringBuilder();
			String line = null;
			int lineNum = 0;
			while((line = reader.readLine()) != null){
				lineNum ++ ;
				line = line.replaceAll("\0", "");
				Matcher m = pattern.matcher(line);
				if(m.find()){
					sb.append(searchFile.getAbsolutePath());
					if(showLine) { sb.append("[").append(lineNum).append("]"); }
					if(!onlyFileName){
						sb.append(":").append(line);
					}
					sb.append("\n");
				}
			}
			return sb.toString();
		}
	}

	public static void findStr(String path, String str, File output, boolean showLine, boolean onlyFileName){
	}
}
