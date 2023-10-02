package Backend;

import java.awt.Image;
import java.awt.image.BufferedImage;
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

import javax.imageio.ImageIO;

public class Item {
	
	private String title;
	private String description;
	public String displayDescription;
	private double price;
	private int quantity;
	private BufferedImage image;
	private File imgFile;
	private Account seller;
	private double avgReview;
	private ArrayList<Review> reviews;
	private double value;
	private static final String filePath = System.getProperty("user.dir") + "/data/itemFolder/";
    private static final String filePathDatabase = "items.txt";
    private static final String itemData = "itemList/";
    private int itemNum;
    private boolean enabled;
	
	public Item(String title, String description, double price, int quantity, Account seller) {
		this.title = title;
		this.description = Review.convertToOneLine(description);
		this.price = price;
		this.quantity = quantity;
		this.seller = seller;
		avgReview = -1;
		reviews = new ArrayList<Review>();
		enabled = true;
		String[] why = description.split("\\\\n ");
		displayDescription = "";
		for(int i = 0; i < why.length; i++) {
			if(i == why.length - 1) {
				displayDescription += why[i];
			} else {
				displayDescription += why[i].substring(0, why[i].length() - 1) + "\n";
			}
		}
	}
	
	public Item(Account seller, int itemNum) {
		this.seller = seller;
		this.itemNum = itemNum;
		//System.out.println("æ"+itemNum);
	}
	
	public double getValue() {
		return value;
	}
	
	public int getItemNum() {
		return itemNum;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDisplay() {
		return displayDescription;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public File getImage() {
		return imgFile;
	}
	
	public Account getSeller() {
		return seller;
	}
	
	public double getAverageReview() {
		return avgReview;
	}
	
	public int getNumReviews() {
		return reviews.size();
	}
	
	public ArrayList<Review> getReviews(){
		return reviews;
	}
	
    public static boolean doesDatabaseExist() {
    	try{
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + filePathDatabase));
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    public static void newItemDatabase() {
    	File database = new File(filePath + filePathDatabase);
    	try {
    		Path path = Paths.get(filePath);
    		Files.createDirectory(path);
			database.createNewFile();
		} catch (IOException e) {
		}
    }
	
    public void createNewFile() {
    	itemNum = seller.newItem(this);
    	File database = new File(filePath + filePathDatabase);
    	ArrayList<String> keep = new ArrayList<String>();
    	try{
			BufferedReader loadFile = new BufferedReader(new FileReader(filePath + filePathDatabase));
			String currentString = loadFile.readLine();
			while(currentString != null) {
				keep.add(currentString);
				currentString = loadFile.readLine();
			}
		}catch(Exception e) {
		}
    	try(PrintWriter writer = new PrintWriter(database);){
    		for(int i = 0; i < keep.size(); i++) {
    			writer.println(keep.get(i));
    		}
			writer.println(seller.getEmail() + " " + String.valueOf(itemNum));
			writer.println(seller.getUsername());
			writer.println(title);
			writer.println(description);
		} catch (FileNotFoundException e) {
		}
    	File myFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt");
    	try {
    		if(itemNum == 1) {
	    		String p = filePath + itemData;
	    		p = p.substring(0,p.length()-1);
	    		Path path = Paths.get(p);
	    		Files.createDirectory(path);
    		}
		} catch (IOException e1) {
		}
    	try {
    		if(itemNum == 1) {
	    		String p2 = filePath + itemData + seller.getEmail();
	    		Path path2 = Paths.get(p2);
	    		Files.createDirectory(path2);
    		}
			String p3 = filePath + itemData + seller.getEmail() + "/" + itemNum;
			Path path3 = Paths.get(p3);
			Files.createDirectory(path3);
			myFile.createNewFile();
    	}catch(IOException e1) {
    	}
    	try (PrintWriter myFileWriter = new PrintWriter(myFile);){
			myFileWriter.println(title);
			myFileWriter.println(description);
			myFileWriter.println(price);
			myFileWriter.println(quantity);
			myFileWriter.println(avgReview);
			myFileWriter.println(value);
			//has the item been deleted? true if it still exists, false otherwise
			myFileWriter.println(true);
			//reviews
			myFileWriter.println("reviews: ");
		} catch (FileNotFoundException e) {
		}
    }
    
	public double calculateValue() {
		//Is this a good equation?
		return Math.pow(avgReview/3.75, 1 + 0.1*(reviews.size()))/price;
	}
	
	//Sets the image variable and writes the image to file. This should only be called AFTER a text file has been created
	public void setImage(File imgFile) {
		this.imgFile = imgFile;
		File file = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".png");
		try {
			image = ImageIO.read(imgFile);
			
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
		}
	}
	
	public void resetImage(File imgFile) {
		if(imgFile != this.imgFile) {
			File file = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".png");
			file.delete();
			setImage(imgFile);
		}
	}
	
	public boolean loadItem() {
    	try{
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt"));
    		this.title = loadFile.readLine();
    		this.description = loadFile.readLine();
    		String[] why = description.split("\\\\n ");
    		displayDescription = "";
    		for(int i = 0; i < why.length; i++) {
    			if(i == why.length - 1) {
    				displayDescription += why[i];
    			} else {
    				displayDescription += why[i].substring(0, why[i].length() - 1) + "\n";
    			}
    		}
    		this.price = Double.valueOf(loadFile.readLine());
    		this.quantity = Integer.valueOf(loadFile.readLine());
    		this.avgReview = Math.round(Double.valueOf(loadFile.readLine())*100);
    		avgReview /= 100;
    		value = Double.valueOf(loadFile.readLine());
    		//System.out.println("nyello" + value);
    		this.enabled = Boolean.valueOf(loadFile.readLine());
    		this.imgFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".png");
    		this.image = ImageIO.read(imgFile);
    		reviews = new ArrayList<Review>();
    		loadFile.readLine();
    		String userStr = loadFile.readLine();
    		while(userStr != null) {
    			Account reviewer = new Account(userStr);
    			double rating = Double.valueOf(loadFile.readLine());
    			Review r = new Review(reviewer, rating, loadFile.readLine());
    			userStr = loadFile.readLine();
    			reviews.add(r);
    			//System.out.println(r);
    		}
    		return true;
    	} catch(Exception e) {
    	}
    	return false;
    }
	
	public String toString() {
		return title;
	}
	
	public void setQuantity(int q) {
		quantity = q;
		//System.out.println("e" + itemNum);
		File myFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt");
    	ArrayList<String> keep = Account.getFileText(myFile);
    	keep.set(3, Integer.toString(q));
    	Account.writeToFile(myFile, keep);
	}
	
	public static ArrayList<Item> search(String[] requests) {
		for(String s : requests) {
			//System.out.println(s);
		}
		File myFile = new File(filePath + filePathDatabase);
    	ArrayList<String> keep = Account.getFileText(myFile);
    	ArrayList<Item> toReturn = new ArrayList<Item>();
    	ArrayList<ArrayList<Item>> why = new ArrayList<ArrayList<Item>>();
    	for(int i = 0; i < requests.length; i++) {
    		for(int j = 0; j < keep.size(); j++) {
    			if(j%4==0) {j++;}
    			for(int k = 0; k < keep.get(j).length()-requests[i].length()+1; k++) {
    				if(keep.get(j).substring(k, k+requests[i].length()).equalsIgnoreCase(requests[i])){
    					//System.out.println("found u");
    					int toKeep = j/4;
    					toKeep*=4;
    					String temp = keep.get(toKeep);
    					String[] temps = temp.split(" ");
    					Account tempUser = new Account(temps[0]);
    					tempUser.loadAccount();
    					Item newItem = new Item(tempUser, Integer.valueOf(temps[1]));
    					newItem.loadItem();
    					if(newItem.getEnabled()) {
    						toReturn.add(newItem);
    					}
    					k = keep.get(j).length()-requests[i].length();
    					//System.out.println(requests[i] + "haha" + newItem);
    					//work on this
    					int skip = 4-(j%4);
    					j+=skip;
    					break;
    				}
    			}
    		}
    		toReturn = condenseArrayList(toReturn);
    		why.add(toReturn);
    		toReturn = new ArrayList<Item>();
    	}
    	toReturn = condenseMassiveArrayList(why);
    	toReturn = sortValue(toReturn);
    	return toReturn;
	}
	
	public boolean equals(Item i) {
		if(seller.getEmail().equals(i.getSeller().getEmail()) && title.equals(i.getTitle())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<Item> condenseArrayList(ArrayList<Item> items) {
		ArrayList<Item> toReturn = new ArrayList<Item>();
		boolean included = false;
		for(Item i : items) {
			for(Item j : toReturn) {
				if(j.equals(i)) {
					included = true;
					break;
				}
			}
			if(!included) {
				toReturn.add(i);
			}
			included = false;
		}
		return toReturn;
	}
	
	/*public static ArrayList<Item> condenseBadArrayList(ArrayList<ArrayList<Item>> itemss) { 
		ArrayList<Item> items = new ArrayList<Item>();
		int max = -1;
		int maxIndex = -1;
		for(int i = 0; i < itemss.size(); i++) {
			if(itemss.get(i).size() > max) {
				max = itemss.get(i).size();
				maxIndex = i;
			}
		}
		boolean found = false;
		boolean found2 = true;
		for(int j = 0; j < itemss.get(maxIndex).size(); j++) {
			for(int i = 0; i < itemss.size(); i++) {
				for(int k = 0; k < itemss.get(i).size(); k++) {
					if(itemss.get(maxIndex).get(j).equals(itemss.get(i).get(k))) {
						found = true;
						break;
					}
				}
				if(found == false) {
					found2 = false;
					break;
				}
			}
			if(found2) {
				items.add(itemss.get(maxIndex).get(j));
			}
		}
		return condenseArrayList(items);
	}*/
	
	public static ArrayList<Item> condenseMassiveArrayList(ArrayList<ArrayList<Item>> items) { 
		ArrayList<Item> toReturn = new ArrayList<Item>();
		for(Item i : items.get(0)) {
			toReturn.add(i);
			//System.out.println(i);
		}
		//System.out.println(items.size() + "juk");
		toReturn = condenseArrayList(toReturn);
		ArrayList<Item> toReturn2 = new ArrayList<Item>();
		for(Item l : toReturn) {
			toReturn2.add(l);
		}
		boolean included = false;
		for(int i = 0; i < items.size(); i++) {
			for(Item j : toReturn) {
				for(Item k : items.get(i)) {
					if(k.equals(j)) {
						//System.out.println("very sus");
						included = true;
						break;
					}
				}
				if(!included) {
					//System.out.println("aesus");
					toReturn2.remove(j);
				}
				included = false;
			}
		}
		return toReturn2;
	}
	
	public void addReview(Account user, double rate, String txt) {
		//System.out.println("do question");
		Review r = new Review(user, rate, txt);
		File myFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt");
    	ArrayList<String> keep = Account.getFileText(myFile);
    	keep.add(user.getEmail());
    	keep.add(Double.toString(rate));
    	System.out.println(r.getDescription() + "ello");
    	keep.add(r.getDescription());
    	//System.out.println("do question");
    	reviews.add(r);
    	//System.out.println("do question");
    	calculateAverageReview();
    	//System.out.println("do question");
    	keep.set(4, Double.toString(avgReview));
    	value = calculateValue();
    	keep.set(5, Double.toString(value));
    	Account.writeToFile(myFile, keep);
	}
	
	public void deleteItem() {
		File myFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt");
		ArrayList<String> keep = Account.getFileText(myFile);
		keep.set(6, "false");
		Account.writeToFile(myFile, keep);
	}
	
	public void updateFile(ArrayList<String> data) {
		File myFile = new File(filePath + itemData + seller.getEmail() + "/" + itemNum + "/" + itemNum + ".txt");
    	ArrayList<String> keep = Account.getFileText(myFile);
    	//System.out.println("AeE");
    	//System.out.println(itemNum);
    	keep.set(0, data.get(0));
    	keep.set(1, data.get(1));
    	keep.set(2, data.get(2));
    	Account.writeToFile(myFile, keep);
    	File myFile2 = new File(filePath + filePathDatabase);
    	ArrayList<String> keep2 = Account.getFileText(myFile2);
    	for(int i = 0; i < keep2.size(); i++) {
    		if(keep2.get(i).equals(seller.getEmail() + " " + itemNum)) {
    			keep2.set(i+2, data.get(0));
    			keep2.set(i+3, data.get(1));
    			break;
    		}
    	}
    	Account.writeToFile(myFile2, keep2);
    	seller.setTitle(title, data.get(0));
	}
	
	public boolean getEnabled() {
		return enabled;
	}
    
	public void calculateAverageReview() {
		double avg = 0;
		for(Review r : reviews) {
			avg+= r.getRating();
		}
		avg/=reviews.size();
		avg = Math.round(avg * 100);
		avg /= 100;
		avgReview = avg;
	}
	
	public static ArrayList<Item> sortPriceLow(ArrayList<Item> items){
		for(int i = 0; i < items.size(); i++) {
			double low = items.get(i).getPrice();
			int lowIndex = i;
			for(int j = i+1; j < items.size(); j++) {
				if(items.get(i).getPrice() > items.get(j).getPrice()) {
					low = items.get(j).getPrice();
					lowIndex = j;
				}
			}
			Item temp = items.get(i);
			items.set(i, items.get(lowIndex));
			items.set(lowIndex, temp);
		}
		return items;
	}
	
	public static ArrayList<Item> sortPriceHigh(ArrayList<Item> items){
		ArrayList<Item> toReturn = new ArrayList<Item>();
		items = sortPriceLow(items);
		for(int i = items.size()-1; i >= 0; i--) {
			toReturn.add(items.get(i));
		}
		return toReturn;
	}
	
	public static ArrayList<Item> sortValue(ArrayList<Item> items){
		for(int i = 0; i < items.size(); i++) {
			double low = items.get(i).getValue();
			int lowIndex = i;
			for(int j = i+1; j < items.size(); j++) {
				if(items.get(i).getValue() < items.get(j).getValue()) {
					low = items.get(j).getValue();
					lowIndex = j;
				}
			}
			Item temp = items.get(i);
			items.set(i, items.get(lowIndex));
			items.set(lowIndex, temp);
		}
		return items;
	}
	
	public static ArrayList<Item> sortReview(ArrayList<Item> items){
		for(int i = 0; i < items.size(); i++) {
			double low = items.get(i).getAverageReview();
			int lowIndex = i;
			for(int j = i+1; j < items.size(); j++) {
				if(items.get(i).getAverageReview() < items.get(j).getAverageReview()) {
					low = items.get(j).getAverageReview();
					lowIndex = j;
				}
			}
			Item temp = items.get(i);
			items.set(i, items.get(lowIndex));
			items.set(lowIndex, temp);
		}
		return items;
	}
	
	public static ArrayList<Item> filterPrice(ArrayList<Item> items, int low, int high){
		items = sortPriceLow(items);
		ArrayList<Item> toReturn = new ArrayList<Item>();
		for(Item i : items) {
			if(i.getPrice() >= low && i.getPrice() <= high) {
				toReturn.add(i);
			}
		}
		return toReturn;
	}
	
	public static File getDefault() {
		return new File(System.getProperty("user.dir") + "/[No Image].png");
	}
	
	public static ArrayList<Item> historySearch(Account user, String[] terms){
		ArrayList<ArrayList<Item>> temp = new ArrayList<ArrayList<Item>>();
		temp.add(user.getPurchaseHistory());
		temp.add(search(terms));
		return condenseMassiveArrayList(temp);
	}
	
	public static ArrayList<Item> sortQuantity(ArrayList<Item> items){
		for(int i = 0; i < items.size(); i++) {
			double low = items.get(i).getQuantity();
			int lowIndex = i;
			for(int j = i+1; j < items.size(); j++) {
				if(items.get(i).getQuantity() > items.get(j).getQuantity()) {
					low = items.get(j).getQuantity();
					lowIndex = j;
				}
			}
			Item temp = items.get(i);
			items.set(i, items.get(lowIndex));
			items.set(lowIndex, temp);
		}
		return items;
	}
}