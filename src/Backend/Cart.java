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

public class Cart {
	private Account user;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Integer> quantities = new ArrayList<Integer>(); 
	private double price = 0;
	
	public Cart(Account user) {
		this.user = user;
	}
	
	public void add(Item i, int q) {
		if(items.indexOf(i) == -1) {
		items.add(i);
		quantities.add(q);
		price += items.get(items.indexOf(i)).getPrice()*q;
		int price2 = (int) Math.round(price * 100);
		price = (double)price2/(double)100;
		} else {
			remove(i, -1*q);
		}
	}
	
	public void remove(Item i, int q) {
		int index = items.indexOf(i);
		price -= items.get(index).getPrice()*q;
		int price2 = (int) Math.round(price * 100);
		price = (double)price2/(double)100;
		if(q == quantities.get(index)) {
			items.remove(index);
			quantities.remove(index);
		} else {
			quantities.set(index, quantities.get(index)-q);
		}
	}
	
	public void clear() {
		items = new ArrayList<Item>();
		quantities = new ArrayList<Integer>();
		price = 0;
	}
	
	public void updateBalance() {
		user.updateBalance(price);
		for(int i = 0; i < items.size(); i++) {
			Account seller = items.get(i).getSeller();
			double sellerPrice = -items.get(i).getPrice()*quantities.get(i);
			seller.updateBalance(sellerPrice);
		}
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public int getItemQuantity(Item i) {
		return quantities.get(items.indexOf(i));
	}
	
	public double getPrice() {
		return price;
	}
	
	public void restock() {
		for(int i = 0; i < items.size(); i++) {
			items.get(i).setQuantity(items.get(i).getQuantity() + quantities.get(i));
		}
	}
}