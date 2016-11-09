package com.southbank.sbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class App 
{	
    public static void main( String[] args )
    {   
    	long start = new Date().getTime();
        System.out.println( "Hello World!" );
        
		try {
			String t1JsonFile = new java.io.File( args[0] ).getCanonicalPath();
			String t2JsonFile = new java.io.File( args[1] ).getCanonicalPath();
			String outputJsonFile = new java.io.File( args[2] ).getCanonicalPath();
			System.out.println("Found:"+t1JsonFile);
			System.out.println("Found:"+t2JsonFile);
			System.out.println("Found:"+outputJsonFile);
	        
	        new App().executeCode(t1JsonFile,t2JsonFile,outputJsonFile);        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("Total Execution Time (Sum of all above) - " + (new Date().getTime() - start));
    }
    
    
    public void executeCode(String t1FilePath, String t2FilePath, String outputFilePath){
    	
    	long startJ = new Date().getTime();
    	JSONUtils utils = new JSONUtils();
    	
    	//Load T1
    	List<T1> listT1 = utils.getT1(t1FilePath);        

		//Load T2
		List<T2> listT2 = utils.getT2(t2FilePath);
		
		TreeMap<Double, Double> xMapT1 = new TreeMap<Double, Double>();
		TreeMap<Double, Double> xMapT2 = new TreeMap<Double, Double>();
		System.out.println("Execution Time for loading JSON data - " + (new Date().getTime() - startJ));
		
		long startZ = new Date().getTime();
		
		Multimap<Double, Double> t2ZMap = ArrayListMultimap.create();
		
		for (T2 t2 : listT2) {
			t2ZMap.put(t2.getZ(), t2.getY());			
		}

		for (T1 t1 : listT1) {
			if(t1.getZ() > 0){
				if(t2ZMap.containsKey(t1.getZ())){
					Collection<Double> t2Z = t2ZMap.get(t1.getZ());
					for (Double double1 : t2Z) {
						if(xMapT1.containsKey(t1.getX())){
							double initialTY1Val = ((Double)xMapT1.get(t1.getX())).doubleValue();
							double initialTY2Val = ((Double)xMapT2.get(t1.getX())).doubleValue();
							xMapT1.put(t1.getX(), new Double(initialTY1Val + t1.getY()));
							xMapT2.put(t1.getX(), new Double(initialTY2Val + double1));
						} else {
							xMapT1.put(t1.getX(), new Double(t1.getY()));
							xMapT2.put(t1.getX(), new Double(double1));
						}
					}
				}
			}
		}
				
		// Create-and-load a List of entries
		List<Map.Entry<Double, Double>> entries = new ArrayList<Map.Entry<Double, Double>>(xMapT2.entrySet());
		// Sort the list using a custom Comparator that compares the z of t2
		Collections.sort(entries, new Comparator<Map.Entry<Double, Double>>() {
			public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}});

		// Load the entries into a Map that preserves insert order
		Map<Double, Double> sortedMap = new LinkedHashMap<Double, Double>();
		for (Map.Entry<Double, Double> entry : entries){
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		List<JsonOutput> listJsonOut = new ArrayList<JsonOutput>();
				
		Set<Entry<Double,Double>> set = xMapT1.entrySet();		
		Iterator<Entry<Double, Double>> i = set.iterator();
		while(i.hasNext()){
			Map.Entry<Double, Double> me = (Map.Entry<Double, Double>)i.next();
			double outX = me.getKey();
			double outT1Y = me.getValue();
			double outT2Y = xMapT2.get(outX);
			
			JsonOutput jsonOut = new JsonOutput(outX, outT1Y, outT2Y);
			listJsonOut.add(jsonOut);
		}
		
		Collections.reverse(listJsonOut);
		System.out.println("Execution Time for data processing - " + (new Date().getTime() - startZ));
		
		long startP = new Date().getTime();
		utils.writeToJSON(listJsonOut, outputFilePath);
		System.out.println("Execution Time for writing to JSON file - " + (new Date().getTime() - startP));
    }
    
   
    
    
}
