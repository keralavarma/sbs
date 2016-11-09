package com.southbank.sbs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
	
	public JSONUtils(){

	}
	
	public List<T1> getT1(String filePath) {
		
		List<T1> lstT1 = new ArrayList<T1>();
		
		try {
			File file = new File(filePath);
			JsonFactory f = new MappingJsonFactory();
	        JsonParser jp = f.createJsonParser(file);
	        
	        while (jp.nextToken() != JsonToken.NOT_AVAILABLE) {	        	
	        	T1 t1 = jp.readValueAs(T1.class);
	        	lstT1.add(t1);
	        }	        
			
		} catch (JsonMappingException e) {
//			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lstT1;
	}
	
	public List<T2> getT2(String filePath){

		List<T2> lstT2 = new ArrayList<T2>();

		try {
			File file = new File(filePath);
			JsonFactory f = new MappingJsonFactory();
			JsonParser jp = f.createJsonParser(file);

			while (jp.nextToken() != JsonToken.NOT_AVAILABLE) {	        	
				T2 t2 = jp.readValueAs(T2.class);
				lstT2.add(t2);
			}

		} catch (JsonMappingException e) {
//			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lstT2;
	}
	
	public void writeToJSON(List<JsonOutput> jsonOut, String outputPath){		
		
		try {

			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
				for (JsonOutput jsonOutput : jsonOut) {
					writer.write(jsonOutput.toString() + "\n");
				}
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
