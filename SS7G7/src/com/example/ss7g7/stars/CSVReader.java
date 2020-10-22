package com.example.ss7g7.stars;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

	public List [] readFile () {
		
		String csvFile = "../SS7G7/lib/logincred.txt";
        String line = new String();
        String cvsSplitBy = ",";
        String [] temp = {};
        List<String> username = new ArrayList<String>();
        List<String> pass = new ArrayList<String>();
        List<String> userType = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
            while ((line = br.readLine()) != null) {
            	
                temp = line.split(cvsSplitBy);
                username.add(temp[0]);
                pass.add(temp[1]);
                userType.add(temp[2]);
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new List[] {username, pass, userType};
	}
	
}
