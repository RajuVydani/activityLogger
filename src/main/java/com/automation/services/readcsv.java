package com.automation.services;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readcsv {

    public static void main(String[] args) {

        String csvFile = "D:\\FB-Co_Pheonix_Latest1 - Copy.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
           	 
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
          
       System.out.println(data[0]+",'"+data[1]+"',"+data[2]+",'"+data[3]+"','"+data[4]+"');");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}