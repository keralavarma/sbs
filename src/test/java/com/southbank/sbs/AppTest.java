package com.southbank.sbs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	private String t1FilePath = "t1.json";
	private String t2FilePath = "t2.json";
	private String outputFilePath = "output.json";
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	System.out.println(">>>>> Testing...");
    	App app = new App();
    	
    	//Get the T1 nd m data files
    	System.out.println(">>>>> STEP 1: STARTED");
    	System.out.println(">>>>> STEP 1: Get T1 and T2 data files and check DISTINCT X...");
    	long countOfXData = getCountOfT1XFromData();
    	System.out.println(">>>>> STEP 1: Number of DISTINCT X from data files - " + countOfXData);
    	System.out.println(">>>>> STEP 1: COMPLETED...");
    	
    	//Generate Output file
    	System.out.println(">>>>> STEP 2: Generating JSON Result Output File");
    	app.executeCode(t1FilePath, t2FilePath, outputFilePath);
    	System.out.println(">>>>> STEP 2: Output file generated. Now checking number of rows...");
    	//Get number of lines in file
    	long countOfOutputFile = getNumberOfLines(outputFilePath);
    	System.out.println(">>>>> STEP 2: Number of rows from output file - " + countOfOutputFile);
    	System.out.println(">>>>> STEP 2: COMPLETED...");
    	Assert.assertEquals(countOfXData, countOfOutputFile);
    }
    
    private long getNumberOfLines(String filePath){
    	
    	long countOfOutputFile = 0;
    	try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
			
			String currentLine = br.readLine();
			
			while(currentLine != null){
				if(!currentLine.trim().equalsIgnoreCase("")){
					countOfOutputFile++;
				}
				currentLine = br.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return countOfOutputFile;
    }
    
    private HashSet<Double> getT1ZSet(List<T1> listT1){
    	HashSet<Double> zSetT1 = new HashSet<Double>();

    	for (T1 t1 : listT1) {
    		if(t1.getZ() > 0){
    			if(!zSetT1.contains(t1.getZ())){
    				zSetT1.add(t1.getZ());
    			}
    		}
    	}
    	return zSetT1;
    }
    
    private long getCountOfT1XFromData(){
    	
    	JSONUtils utils = new JSONUtils();
    	List<T1> listT1 = utils.getT1(t1FilePath);
    	List<T2> listT2 = utils.getT2(t2FilePath);
    	
    	//Get T1 z in a set to check with T2 z
		HashSet<Double> zSetT1 = getT1ZSet(listT1);
		
		TreeMap<Double, Double> xMapT1 = new TreeMap<Double, Double>();
		
		long startP = new Date().getTime();
		for (T1 t1 : listT1) {
			
			if(t1.getZ() > 0){
				double t1YValue = t1.getY();
				for (T2 t2 : listT2){
					if(zSetT1.contains(t2.getZ()) && t1.getZ() == t2.getZ()){
						if(xMapT1.containsKey(t1.getX())){
							double initialTY1Val = ((Double)xMapT1.get(t1.getX())).doubleValue();
							xMapT1.put(t1.getX(), new Double(initialTY1Val + t1YValue));
						} else {
							xMapT1.put(t1.getX(), new Double(t1.getY()));
						}						
					}
				}
			}			
		}
		System.out.println(">>>>> STEP 1: Execution Time - " + (new Date().getTime() - startP));
		
		return xMapT1.size();

    }

}
