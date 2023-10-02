package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Review {
	Account reviewer;
	private double rating;
	private String description;
	private String displayDescription;
	
	public Review(Account user, double rating, String description){
		reviewer = user;
		this.rating = rating;
		this.description = "";
		this.description = convertToOneLine(description);
		String[] why = description.split("\\\\n ");
		displayDescription = "";
		for(int i = 0; i < why.length; i++) {
			if(i == why.length - 1) {
				displayDescription += why[i];
			} else {
				displayDescription += why[i].substring(0, why[i].length() - 1) + "\n";
			}
		}
		//convertStringFrom();
		//convertStringTo();
	}
	
	public String getDisplay() {
		return displayDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getRating() {
		return rating;
	}
	
	public Account getReviewer() {
		return reviewer;
	}
	
	public String toString() {
		return reviewer.getEmail() + " says " + description;
	}
	
	/*public void convertStringFrom() {
		description = description.replaceAll("\\n", "\\n");
		System.out.println(description);
		displayDescription = description.replaceAll("\n", "SHUT");
		System.out.println(displayDescription);
	}*/
	
	/*public void convertStringTo(){
		System.out.println(description);
		String desc[] = description.split("\\r?\\n");
		displayDescription = "";
		for(int i = 0; i < desc.length; i++) {
			System.out.println(desc);
			displayDescription += desc[i];
			if(i < desc.length - 1) {
				displayDescription += "%n";
			}
		}
		System.out.println(displayDescription);
	}*/
	
	public static String convertToOneLine(String str) {
		File f = new File(System.getProperty("user.dir") + "/data/itemFolder/temp.txt");
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(PrintWriter writer = new PrintWriter(f);){
			writer.println(str);
		} catch(Exception e) {
			e.printStackTrace();
		}
		String toReturn = "";
		ArrayList<String> keep = Account.getFileText(f);
		for(int i = 0; i < keep.size(); i++) {
			System.out.println(keep.get(i));
			toReturn += keep.get(i);
			if(i < keep.size() - 1) {
				System.out.println("why");
				toReturn += " \\\\n ";
			}
		}
		System.out.println(toReturn);
		f.delete();
		return toReturn;
	}
}