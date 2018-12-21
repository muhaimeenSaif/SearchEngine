package final_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import org.jsoup.*;
import java.util.regex.*;


public class MAIN_PROGRAM
{


	public static void main(String[] args) throws IOException
	{
		boolean bool;
		do {
				/* User's search input */
				int count = 0;
				CheckInput check = new CheckInput();
				String input = check.UserInput();				
				String[] inputWords = input.split("\\s"); //splitting inputWords in user input
				String[] resultWords = new String[inputWords.length];
				
				String[] result = new String[inputWords.length];
				
				//modifying user's input based on edit distance
				for(int i=0; i<inputWords.length; i++)
				{
					resultWords[i] = check.DidYouMean(inputWords[i]);
//					if(resultWords[i].equals(result[i]))
//					{
//						System.out.println("Write the word again");
//						break;
//					}
//					else 
					if(resultWords[i].equals(inputWords[i]))
					{
						result[i] = inputWords[i];
					}
					else
					{
						result[i] = resultWords[i];
					}
				}
				
				
	 	       	
				/* read web URLs and to parse them into text files*/
				long start = System.currentTimeMillis();
	 	       	FileReader file = new FileReader("urls_list/urls.txt");   
		 	    BufferedReader br = new BufferedReader(file);
		 	    
		 	    File text_file = new File("urls_list/text"); //path of a folder contains .txt files
	 			File[] text_files = text_file.listFiles(); //saving userInput of .txt files present in 'Text' folder
		 	    	 	    
		 	    int fileNumber = 0;
		 	    String text;
		 	    while ((text = br.readLine()) != null) //read .txt file line by line
			    {
		 	    	if (text_files.length < 100)
		 	    	{
		 	    		HTMLJsoup htmlJsoup = new HTMLJsoup(text, fileNumber);//pass HTML web links and web link number to HTMLJsoup parameter
		 	    	}
		 	    	fileNumber++;
			    }
			   
		 	   
		 	   System.out.println("\n -------------------  Search result  -------------------");
		 	  
		 	   /*  TST to generate word sequence   */
		 	    	boolean check_flag = false;
		 	    	
		 	    	long [] count_arr = new long[fileNumber]; //highest priority
		 	    	long [] count_arr2 = new long[fileNumber];//lowest priority 
		 	    	
		 	    	File file2 = new File("urls_list/text"); //path contains .txt files
		 			File[] listOfFiles = file2.listFiles(); //saving userInput of .txt files present in 'Text' folder

		 		    for (int fileWords = 0; fileWords < listOfFiles.length; fileWords++)//loop over 100 .txt files
		 		    {
		 		    	if (listOfFiles[fileWords].isFile())
		 			      {
		 		    			TST<Integer> st = new TST<Integer>(); 
		 		    			
		 		    			File txt = new File("urls_list/text/"+(fileWords)+".txt");
		 		    			Scanner scan = new Scanner(txt);
		 		    			String strings = "";
		 	        
		 		    			List<String> lst = new ArrayList<String>();
		 	        
		 		    			while(scan.hasNext())//scan inputWords in given file
		 		    			{
		 		    				strings = scan.next();
		 		    				
		 		    				Pattern pp = Pattern.compile("[a-zA-Z0-9]");
		 		    				Matcher mm = pp.matcher(strings);
		 		    				if(mm.find())
		 		    				{
		 		    					String trim_string = strings.replaceAll("[^a-zA-Z0-9]", "");//removing special characters or junks before making TST
		 		    					String lower = trim_string.toLowerCase();
		 		    					lst.add(lower);
		 		    				}
		 	        	
		 		    			}	
		 		    			scan.close();

		 		    			String[] arr = lst.toArray(new String[0]);//converting lst into array and adding all inputWords to arr[]
		 		    			//System.out.println(arg0); //checking if the array contains the exact words
		 		    			for (int i = 0; i < arr.length; i++)//loop over arr[]
		 		    			{
		 		    				st.put(arr[i], i); //creating TST
		 		    			}
		 		    			
		 		    			
		 		    			for(int j = 0; j < inputWords.length; j++) //loop for each inputWords in given user input
		 		    			{
		 		    				boolean flag = false;
		 		    				
		 		    				String trim_word = result[j].replaceAll("[^a-zA-Z0-9]", ""); //removing all special characters or junks from the inputWords in user input
		 		    				
		 		    				if(st.get(trim_word) != null) //if word is available in TST(searching using the concept of TST itself)
		 		    				{
		 		    					check_flag = true;
		 		    					
		 		    					/*Inverted index to get the number of occurences */
		 		    					for (int i = 0; i < arr.length; i++)//loop over arr[]
		 		    					{
		 		    						
		 		    						if(trim_word.equals(arr[i]))
		 		    						{
		 		    							if(!flag)
		 		    							{
		 		    								
		 		    								count_arr[fileWords] = count_arr[fileWords] + 1;//creating highest priority array where index is the web site number according to files in text folder
		 	        		
		 		    							}
		 		    							count++;
		 		    						}
		 	 	        	
		 		    					}
		 		    				}
		 		    				else
		 		    				{	
		 		    					flag = true;
		 		    					count_arr2[fileWords] = count_arr[fileWords];//creating lowest priority array where index is the web site number according to the files in text folder
		 		    					count_arr[fileWords] = 0;
		 		    				
		 		    				}
		 	        
		 	     
		 		    			}
		 	       	
		 			      }
		 		    	}
		 		  
		 		  if(check_flag) //if even one word of user input string matches with the word in TST
			 		  {
			 		   /*Ranking the webs by Quick Sort */
			 		   
			 		   Long[] sort_arr = new Long[fileNumber];
			 		   Long[] sort_arr2 = new Long[fileNumber];
			 		   for (int i = 0; i < count_arr.length; i++)
			 		   {
			 			 
			 				   sort_arr[i] = count_arr[i];
			 			 
			 		   }
			 		  for (int i = 0; i < count_arr2.length; i++)
			 		   {
			 			 
			 				   sort_arr2[i] = count_arr2[i];
			 			 
			 		   }

			 		   Sort.quicksort(sort_arr); //sort the highest priority 
			 		  Sort.quicksort(sort_arr2); //sort the lowest priority 
			 		  
			 		 
			 		 /*Show results of highest priority web links */ 
			 		 
			 		 List<Integer> lst = new ArrayList<>( );
			 		
			 		 for (int i = (sort_arr.length)-1; (i >= 0); i--)
					 {
			 			 
			 			 for(int j = 0; j < 10; j++)
			 			 {
			 				
			 				if (sort_arr[i] == count_arr[j] && count_arr[j] != 0) 
						    {
			 			 		if(!lst.contains(j)) 
			 			 		{
			 			 			lst.add(j);

			 			 			FileReader fr = new FileReader("urls_list/text/"+j+".txt");//accessing the file userInput one by one from Text folder 
			 			 			BufferedReader br2 = new BufferedReader(fr);
			 			 			
			 			 			Pattern regx = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");//Regular expression for URL available in file
					    			
			 			 			String line = br2.readLine();//read fist line of parsed .txt file only which contains the URL of web site
						  	      
					    			Matcher ma = regx.matcher(line);//match URL pattern
					    			if (ma.find())//if URL found
					    			{ 
					    				System.out.println("\n"+ma.group()); //URL results shown to the user
					    			}
			 			 		}	
						      }
					    }
					 }
			 		/*Showing results of lowest priority web links */ 
			 		List<Integer> lst2 = new ArrayList<>( );
			 		// same procedure likewise in high priority section
			 		for (int i = (sort_arr2.length)-1; (i >= 0); i--)
					 {
			 			 
			 			 for(int j = 0; j < count_arr2.length; j++)
			 			 {
			 			 	if (sort_arr2[i] == count_arr2[j] && count_arr2[j] != 0) 
						    {
			 			 		if(!lst2.contains(j)) 
			 			 		{
			 			 			lst2.add(j);

			 			 			FileReader fr = new FileReader("urls_list/text/"+j+".txt");//accessing the file userInput one by one from Text folder 
			 			 			BufferedReader br2 = new BufferedReader(fr);
			 			 			
			 			 			Pattern regx = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");//Regular expression for URL available in file
					    			
			 			 			String line = br2.readLine();//read fist line of parsed .txt file only which contains the URL of web site
						  	      
					    			Matcher ma = regx.matcher(line);//match URL pattern
					    			if (ma.find())//if URL found
					    			{ 
					    				System.out.println("\n"+ma.group()); 
					    			}
			 			 		}
						      }
					    }
					 }
			 		

			 		 long end = System.currentTimeMillis();
				 	 System.out.println("\nShowing results in: " + (end-start) + " milliseconds");
			 		  }
		 		  else
		 		  {
		 			 System.out.println("\n"+"Sorry. Your search did not match any documents!");
		 		  }
		 		  
		 		 /*to continue the search by getting the next input*/
		 		  
		 		  bool = check.Check();
		 		  
		}while(bool);
		System.out.println("\n\tThank you For using ACMS Search Engine\t");
		
		
		
	}

}
