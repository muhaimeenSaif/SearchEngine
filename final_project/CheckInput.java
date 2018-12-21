package final_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckInput {
	Scanner scanner = new Scanner(System.in);
	public String UserInput() {
		System.out.println("\tWelcome to ACMS Search Engine\t");
		System.out.println("\nType anything to search (press enter when done): \n");
		String userInput = scanner.nextLine();
		return userInput.toLowerCase();
	}
	public boolean Check()
	{
		System.out.println("\n");
		System.out.println("Do you want to search again? Type 'Yes' or 'No': ");
		String s = scanner.nextLine();
		String str = s.toLowerCase();
		while(!str.equals("yes") && !str.equals("no"))
		{
			System.out.println("\n");
	 		System.out.println("Wrong input!!! Type only 'Yes' or 'No': ");
			s = scanner.nextLine();
	 		str = s.toLowerCase();
		}
		if(str.equals("yes"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String CheckDictionary(String str) throws FileNotFoundException
	{
		In dictionay = new In("words.txt");
		char[] s2 = dictionay.readAll().toCharArray();
		
			File txt = new File("H:\\Master's\\Projects\\Final Project\\final_project\\words.txt");//getting the .txt file with userInput according to the links number in urls.txt
			Scanner scan = new Scanner(txt);
			String strings = "";
			List<String> lst = new ArrayList<String>();
	        
			while(scan.hasNext())
			{
				strings = scan.next();
				
				Pattern pp = Pattern.compile("[a-zA-Z0-9]");
				Matcher mm = pp.matcher(strings);
				if(mm.find())
				{
					String trim_string = strings.replaceAll("[^a-zA-Z0-9]", "");//removing special characters or junks before making TST
					String lower = trim_string.toLowerCase();
					lst.add(lower);//append inputWords in lst
				}
	
			}
			scan.close();
			int d=0;
			int count=0;
			String result = "";
			List<String> resultList = new ArrayList<String>();
			
			String[] arr = lst.toArray(new String[0]);
			for(int i =0;i<arr.length;i++) {
			
				 d = Sequences.editDistance(str, arr[i]);
				 if(d==0) {
					result = arr[i];
					count++;
					break;
				 }
				 else if(d<2)
				 {
					resultList.add(arr[i]);
				 }
			}
			if(count==0)
			{
				String[] resultArr = resultList.toArray(new String[0]);
					return resultArr[0];
			}
			else {
				return "Found";
			}
	}
	
	public String DidYouMean(String str) throws FileNotFoundException
	{
		String result = CheckDictionary(str);
		if(result.equals("Found"))
		{
			return str;
		}
		else
		{
			System.out.println("\n");
			System.out.println("Did you mean " + result + "? Type 'Yes' or 'No': ");
			String s = scanner.nextLine();
			String string = s.toLowerCase();
			while(!string.equals("yes") && !string.equals("no"))
			{
				System.out.println("\n");
		 		System.out.println("Wrong input!!! Type only 'Yes' or 'No': ");
				s = scanner.nextLine();
				string = s.toLowerCase();
			}
			if(string.equals("yes"))
			{
				return result;
			}
			else
			{
				return str;
			}
		}
	}
}
