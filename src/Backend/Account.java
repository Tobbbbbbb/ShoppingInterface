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

public class Account {
	private String email;
    private String username;
    private String password;
    private int security;
    private String answer;
    private ArrayList<Item> myItems;
    private ArrayList<Item> purchaseHistory;
    private Cart myCart = new Cart(this);
    private static final String filePath = System.getProperty("user.dir") + "/data/";
    private static final String userFolder = "userFolder/";
    private static final String filePathDatabase = "users.txt";
    private static final String userData = "userList/";
    
    public Account(String email, String username, String password, int security, String answer){
        this.email = email;
    	this.username = username;
        this.password = password;
        this.security = security;
        this.answer = answer;
    }
    
    public Account(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public ArrayList<Item> getMyItems() {
    	return myItems;
    }
    
    public boolean loadAccount() {
    	try{
    		//System.out.println("Yes");
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + userFolder + userData + email + ".txt"));
    		loadFile.readLine();
    		this.username = loadFile.readLine();
    		this.password = loadFile.readLine();
    		this.security = loadFile.readLine().charAt(0) - 48;
    		this.answer = loadFile.readLine();
    		//logged-in
    		loadFile.readLine();
    		//balance
    		loadFile.readLine();
    		//history
    		String history = loadFile.readLine();
    		String[] ae = history.split(", ");
    		purchaseHistory = new ArrayList<Item>();
    		//System.out.println(ae.length);
    		if(ae.length > 1) {
	    		for(int i = 0; i < ae.length; i++) {
	    			String[] temp = ae[i].split(" ");
	    			//System.out.println(ae[0]);
	    			//System.out.println(ae[1]);
	    			Item j = new Item(new Account(temp[0]), Integer.valueOf(temp[1]));
	    			j.loadItem();
	    			if(j.getEnabled())
	    				purchaseHistory.add(j);
	    		}
	    		purchaseHistory = Item.condenseArrayList(purchaseHistory);
    		}
    		
    		//numItems selling
    		int numItems = Integer.valueOf(loadFile.readLine());
    		//System.out.println(numItems + "bruv");
    		myItems = new ArrayList<Item>();
    		Item j;
    		for(int i = 1; i <= numItems; i++) {
    			//System.out.println("oof" + i);
    			j = new Item(this, i);
    			j.loadItem();
    			if(j.getEnabled())
    				myItems.add(j);
    			//System.out.println(j.getItemNum());
    		}
    		//System.out.println("Hello");
    		return true;
    	} catch(Exception e) {
    		return false;
    	}
    }
    
    public int logIn(String pass) {
    	if(!loadAccount()) {
    		return 1;
    	}
    	if(password.equals(pass)) {
    		File myFile = new File(filePath + userFolder + userData + email + ".txt");
    		ArrayList<String> keep = getFileText(myFile);
    		keep.set(5, "true");
    		writeToFile(myFile, keep);
    		return 0;
    	} else {
    		//Password doesn't match
    		return 2;
    	}
    }
    
    public void logOut() {
		File myFile = new File(filePath + userFolder + userData + email + ".txt");
		ArrayList<String> keep = getFileText(myFile);
		keep.set(5, "false");
		writeToFile(myFile, keep);
    }
    
    public void setPassword(String password) {
    	this.password = password;
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
    	ArrayList<String> keep = getFileText(myFile);
    	keep.set(2, password);
    	/*try {
    		Path path = Paths.get(filePath + userFolder + "/userFolder");
    		Files.createDirectory(path);
			myFile.createNewFile();
		} catch (IOException e1) {
		}*/
    	writeToFile(myFile, keep);
    }
    
    public String getUsername() {
    	return username;
    }
    
    public int getSecurity() {
    	return security;
    }
    
    public static int checkUsers(String email, String username) {
    	try{
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + userFolder + filePathDatabase));
    		String accountEmail = loadFile.readLine();
    		String accountUsername = loadFile.readLine();
    		while (accountEmail != null) {
	    		if(accountEmail.equals(email)) {
	    			return 1;
	    		}
	    		if(accountUsername.equals(username)) {
	    			return 2;
	    		}
    			accountEmail = loadFile.readLine();
	    		accountUsername = loadFile.readLine();
    		}
    	} catch( Exception e ) {
    	}
    	return 0;
    }
    
    public static boolean doesDatabaseExist() {
    	try{
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + userFolder + filePathDatabase));
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    public static void newUserDatabase() {
    	File database = new File(filePath + userFolder + filePathDatabase);
    	try {
    		Path path = Paths.get(filePath);
    		Files.createDirectory(path);
    		Path path2 = Paths.get(filePath + userFolder);
    		Files.createDirectory(path2);
			database.createNewFile();
		} catch (IOException e) {
		}
    }
    
    public void createNewFile() {
    	File database = new File(filePath + userFolder + filePathDatabase);
    	ArrayList<String> keep = new ArrayList<String>();
    	try{
			BufferedReader loadFile = new BufferedReader(new FileReader(filePath + userFolder + filePathDatabase));
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
			writer.println(email);
			writer.println(username);
		} catch (FileNotFoundException e) {
		}
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
    	try {
    		String p = filePath + userFolder + userData;
    		p = p.substring(0,p.length()-1);
    		//System.out.println(p);
    		Path path = Paths.get(p);
    		Files.createDirectory(path);
			myFile.createNewFile();
		} catch (IOException e1) {
		}
    	try (PrintWriter myFileWriter = new PrintWriter(myFile);){
			myFileWriter.println(email);
			myFileWriter.println(username);
			myFileWriter.println(password);
			myFileWriter.println(security);
			myFileWriter.println(answer);
			//logged-in status
			myFileWriter.println(false);
			//balance
			myFileWriter.println(0);
			//purchase history
			myFileWriter.println("");
			//items for sale as file names
			myFileWriter.println(0);
			//item actual names
			myFileWriter.println("");
		} catch (FileNotFoundException e) {
		}
    }
    
    public static boolean emailExists(String email) {
    	try{
    		BufferedReader loadFile = new BufferedReader(new FileReader(filePath + userFolder + filePathDatabase));
    		String accountEmail = loadFile.readLine();
    		String accountUsername = loadFile.readLine();
    		while (accountEmail != null) {
	    		if(accountEmail.equals(email)) {
	    			return true;
	    		}
    			accountEmail = loadFile.readLine();
	    		accountUsername = loadFile.readLine();
    		}
    	} catch( Exception e ) {
    	}
    	return false;
    }
    
    public boolean securityMatches(String ans) {
    	if(answer.equals(ans)) {
    		return true;
    	}
    	return false;
    }
    
    public static ArrayList<String> getFileText(File myFile){
    	ArrayList<String> keep = new ArrayList<String>();
    	try{
			BufferedReader loadFile = new BufferedReader(new FileReader(myFile));
			String currentString = loadFile.readLine();
			while(currentString != null) {
				keep.add(currentString);
				currentString = loadFile.readLine();
			}
			return keep;
		}catch(Exception e) {
			return null;
		}
    }
    
    public static void writeToFile(File myFile, ArrayList<String> keep) {
    	try (PrintWriter myFileWriter = new PrintWriter(myFile);){
    		for(int i = 0; i < keep.size(); i++) {
    			myFileWriter.println(keep.get(i));
    		}
		} catch (FileNotFoundException e) {
		}
    }
    
    //adds item to user's file
    public int newItem(Item i) {
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
		ArrayList<String> keep = getFileText(myFile);
		String[] items;
		
		String num = keep.get(8);
		int newNum = Integer.valueOf(num) + 1;
		String finalNum = String.valueOf(newNum);
		String[] names;
		try {
			names = keep.get(9).split(", ");
		} catch(Exception e) {
			names = new String[0];
		}
		ArrayList<String> namesNew = new ArrayList<>();
		for(int j = 0; j < names.length; j++) {
			namesNew.add(names[j]);
		}
		namesNew.add(i.getTitle());
		String namesFinal = "";
		if(namesNew.size() == 1) {
			namesFinal = namesNew.get(0);
		} else {
			for(int j = 0; j < namesNew.size(); j++) {
				namesFinal += namesNew.get(j);
				if(j < namesNew.size()-1) {
					namesFinal += ", ";
				}
			}
		}
		keep.set(8, finalNum);
		keep.set(9, namesFinal);
		writeToFile(myFile, keep);
		
		return newNum;
    }
    
    //checks items for item name duplicates. False if there are, true if there aren't
    public boolean checkItems(String title) {
    	try{
    		File myFile = new File(filePath + userFolder + userData + email + ".txt");
    		ArrayList<String> keep = getFileText(myFile);
    		String[] names;
    		try {
    			names = keep.get(9).split(", ");
    		} catch(Exception e) {
    			names = new String[0];
    		}
    		for(int i = 0; i < names.length; i++) {
    			//System.out.println(names[i]);
    			if(names[i].equals(title)) {
    				return false;
    			}
    		}
    	} catch( Exception e ) {
    	}
    	return true;
    }
    
    public void updateBalance(double price) {
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
    	ArrayList<String> keep = getFileText(myFile);
    	double updated = Double.valueOf(keep.get(6))-price;
    	keep.set(6, Double.toString(updated));
    	writeToFile(myFile, keep);
    }
    
    public Cart getCart() {
    	return myCart;
    }
    
    public void addToCart(Item i, int q) {
    	myCart.add(i, q);
    }
    
    public void removeFromCart(Item i, int q) {
    	myCart.remove(i, q);
    }
    
    public void clearCart() {
    	myCart.clear();
    }
    
    public void restock() {
    	myCart.restock();
    	myCart.clear();
    }
    
    public ArrayList<Item> getCartItems() {
    	return myCart.getItems();
    }
    
    public int getItemQuantity(Item i) {
    	return myCart.getItemQuantity(i);
    }
    
    public double getCartTotal() {
    	return myCart.getPrice();
    }
    
    public void checkOut() {
    	updatePurchaseHistory(myCart.getItems());
    	myCart.updateBalance();
    	myCart.clear();
    }
    
    public void updatePurchaseHistory(ArrayList<Item> items) {
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
    	ArrayList<String> keep = getFileText(myFile);
    	String updated = keep.get(7);
    	for(int i = 0; i < items.size(); i++) {
    		if(updated.equals("")) {
    			updated += items.get(i).getSeller().getEmail() + " " + items.get(i).getItemNum();
    		}
    		updated += ", " + items.get(i).getSeller().getEmail() + " " + items.get(i).getItemNum();
    	}
    	keep.set(7, updated);
    	writeToFile(myFile, keep);
    }
    
    public ArrayList<Item> getPurchaseHistory(){
    	return purchaseHistory;
    }
    
    public boolean equals(Account u) {
    	if(u.getEmail().equals(email)) {
    		return true;
    	}
    	return false;
    }
    
    public void deleteItem(String title) {
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
		ArrayList<String> keep = getFileText(myFile);
		
		String num = keep.get(8);
		int newNum = Integer.valueOf(num) - 1;
		String finalNum = String.valueOf(newNum);
		
		String[] names = keep.get(9).split(", ");
		List<String> namesList = Arrays.asList(names);
		for(int i = 0; i < namesList.size(); i++) {
			if(namesList.get(i) == title) {
				namesList.remove(i);
			}
		}
		String finalNames = "";
		for(int i = 0; i < namesList.size(); i++) {
			finalNames += namesList.get(i);
			if(i < namesList.size() - 1) {
				finalNames += ", ";
			}
		}
		keep.set(8, finalNum);
		keep.set(9, finalNames);
		writeToFile(myFile, keep);
    }
    
    public void setTitle(String oldTitle, String newTitle) {
    	File myFile = new File(filePath + userFolder + userData + email + ".txt");
		ArrayList<String> keep = getFileText(myFile);
		String[] names = keep.get(9).split(", ");
		List<String> namesList = Arrays.asList(names);
		for(int i = 0; i < namesList.size(); i++) {
			if(namesList.get(i) == oldTitle) {
				namesList.set(i, newTitle);
			}
		}
		String finalNames = "";
		for(int i = 0; i < namesList.size(); i++) {
			finalNames += namesList.get(i);
			if(i < namesList.size() - 1) {
				finalNames += ", ";
			}
		}
		keep.set(9, finalNames);
		writeToFile(myFile, keep);
    }
}