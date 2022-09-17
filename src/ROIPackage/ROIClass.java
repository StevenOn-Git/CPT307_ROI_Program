/*
 * ROI Program
 * CPT 307: Data Structures & Algorithms
 * Professor: Joshua Reichard
 * Week 5 - Final Project
 * 
 * Original Release - Steven On 20191016
 *  
 * References: 
 * https://stackoverflow.com/questions/14209085/how-to-define-a-relative-path-in-java
 * Regular Calculator by Jeremy J. Olson https://www.asciiart.eu/electronics/calculators
 * https://www.w3schools.com/java/java_user_input.asp
 * https://stackoverflow.com/questions/2379221/java-currency-number-format
 * https://www.avajava.com/tutorials/lessons/how-do-i-use-numberformat-to-format-a-percent.html
 * 
 * */

//////////IMPORTS//////////////
package ROIPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ROIClass {

	public static void main (String[] args) {
		//Welcome Users
		welcomeMsg();
		//Create file and use relative path to consume the CSV. 
		File equipmentfile = new File("GainsAndCostsForEquipment.csv");
		//Create a list for the equipment
		List<equipment> equipmentList = getEquipmentList(equipmentfile.toString());
		
		//Ask the User
		String brandNameSearch = userInput("Enter Equipment Brand Name: ").toUpperCase().replace(" ", "");
		NumberFormat currencyformatter = NumberFormat.getCurrencyInstance();
		NumberFormat percentformatter = NumberFormat.getPercentInstance();
		percentformatter.setMinimumFractionDigits(1);
		
		for(int i=0;i<equipmentList.size();i++) {
			//Selection Sort Algorithm 
			int indexBestRoi = i;
			for(int j=i+1;j<equipmentList.size();j++) {
				if(equipmentList.get(j).CalculatedROI > equipmentList.get(indexBestRoi).CalculatedROI) {
					indexBestRoi = j;
				}
			}
			//Swap numbers(i) and numbers (indexsmallest)
			equipment temp = equipmentList.get(i);
			equipmentList.set(i, equipmentList.get(indexBestRoi));
			equipmentList.set(indexBestRoi, temp);
			
			//search algorithm based on match condition
			if(equipmentList.get(i).Brand.toUpperCase().matches(brandNameSearch)) {
				equipment eq = equipmentList.get(i);
				System.out.println(Line(eq.Name.length() + 2));				
				System.out.println(" " + eq.Name + " ");
				System.out.println(" Gain: " + currencyformatter.format(eq.Gain) + " | Cost: " + currencyformatter.format(eq.Cost));
				System.out.println(" ROI: " + percentformatter.format(eq.CalculatedROI));
				System.out.println(Line(eq.Name.length() + 2));				
			}			
		}
		
		System.out.println();
		System.out.println("ROI Comparison");
		for(int i=0;i<equipmentList.size();i++) {
			if(i==0) {
				System.out.println("  BEST: ROI: " +  percentformatter.format(equipmentList.get(i).CalculatedROI) + " | GAIN: " + currencyformatter.format(equipmentList.get(i).Gain) 
				+ " | COST: " + currencyformatter.format(equipmentList.get(i).Cost) + " | " + equipmentList.get(i).Name);
			}
			else if(i == equipmentList.size()-1) {
				System.out.println(" LEAST: ROI: " +  percentformatter.format(equipmentList.get(i).CalculatedROI) + " | GAIN: " + currencyformatter.format(equipmentList.get(i).Gain) 
				+ " | COST: " + currencyformatter.format(equipmentList.get(i).Cost) + " | " + equipmentList.get(i).Name);		
			}
			else {
				System.out.println("      : ROI: " +  percentformatter.format(equipmentList.get(i).CalculatedROI) + " | GAIN: " + currencyformatter.format(equipmentList.get(i).Gain) 
				+ " | COST: " + currencyformatter.format(equipmentList.get(i).Cost) + " | " + equipmentList.get(i).Name);
			}
		}
		System.out.println();
		System.out.println("ROI Program Has Completed Processing!");
	}

	//Method to capture brand Name
	private static String userInput(String ask) {
		Scanner myObj = new Scanner(System.in);
		System.out.print("Enter Equipment Brand Name: ");
		return myObj.nextLine();
	}
	//Method to show welcom message
	private static void welcomeMsg() {
		System.out.println("		  _____________________");
		System.out.println("		 |  _________________  |");
		System.out.println("		 | | ROI Calculator  | |");
		System.out.println("		 | | Program         | |");		
		System.out.println("		 | |_________________| |");
		System.out.println("		 |  ___ ___ ___   ___  |");
		System.out.println("		 | | 7 | 8 | 9 | | + | |");
		System.out.println("		 | |___|___|___| |___| |");
		System.out.println("		 | | 4 | 5 | 6 | | - | |");
		System.out.println("		 | |___|___|___| |___| |");
		System.out.println("		 | | 1 | 2 | 3 | | x | |");
		System.out.println("		 | |___|___|___| |___| |");
		System.out.println("		 | | . | 0 | = | | / | |");
		System.out.println("		 | |___|___|___| |___| |");
		System.out.println("		 |_____________________|");		
	}
	//method to transform equipment data from CSV file to a Java equipment object list
	private static List<equipment> getEquipmentList(String filePath){
		//Create variable for a list of equipment
		List<equipment> equipmentList = new LinkedList<equipment>();
		
		//read CSV rows 
		try {
			//create array list of strings
			List<String> CSVReadlist = new LinkedList<String>();
			BufferedReader br = new BufferedReader(new FileReader(filePath));
		    
			//capture the line count to perform a for so we can ignore headings
			String line;
		    int lineCount = 0;
		    while ((line = br.readLine()) != null) {
		    	lineCount++;
		    	CSVReadlist.add(line);
		    }
		    //Display row count
		    System.out.println("Equipment Count:" + (lineCount -1));
		    
		    //for loop to only grab equipment data without header
		    for(int i=0; i<lineCount; i++) {
		    	if(i != 0) {
		    		//split the equipment line by commas
		    		String[] eqiupmentLineSplit = CSVReadlist.get(i).split(",");

		    		//Create equipment object and add to equipment list
		    		//Name = 0, Gain = 1, Cost =2
	    			equipment newEqiup = new equipment(eqiupmentLineSplit[0], 
	    					Double.parseDouble(eqiupmentLineSplit[1]), 
	    					Double.parseDouble(eqiupmentLineSplit[2]));
    			
	    			//Add equipment object to equipment list
	    			equipmentList.add(newEqiup);
		    	}
		    }
		    
		    //close buffer
		    br.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return equipmentList;
	}
	//Equipment object
	private static class equipment{
		private String Name;
		private String Brand;
		private double Gain;
		private double Cost;
		private double CalculatedROI;
		
		//Constructor
		private equipment(String name, double gain, double cost) {
			Name = name;
			Gain = gain;
			Cost = cost;
			CalculatedROI = (gain - cost)/cost; 
			Brand = name.split(" ")[0]; //get's the brand from name
			
		}
		
	}
	
	public static String Line(int length) {
		String line = "";
		for(int i=0; i<length;i++) {
			line = line + "_";
		}
		return line;
	}	
}
