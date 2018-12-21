package final_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sequences {

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// two arbitrary strings
		String s1 = "said";
		In dictionay = new In("words.txt");
		char[] s2 = dictionay.readAll().toCharArray();
		
			File txt = new File("H:\\Master's\\Projects\\Algorithm Design\\algorithmDesign\\words.txt");//getting the .txt file with userInput according to the links number in urls.txt
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
			System.out.println(arr.length);
			for(int i =0;i<arr.length;i++) {
			
				 d = editDistance(s1, arr[i]);
				 if(d==0) {
					result = arr[i];
					count++;
					break;
				 }else if(d<2)
				 {
					resultList.add(arr[i]);
				 }
			}
			if(count==0)
			{
				String[] resultArr = resultList.toArray(new String[0]);
				for(int i=0; i<resultArr.length; i++)
				{
					System.out.println(resultArr[i]);
				}
			}
			else {
				System.out.println(result);
			}
			
			
			
			
		
//		//String s2 = "internet domain";
//		int d = editDistance(s1, s2);
//		double totaltime=0;
//		//System.out.println("Dist between '" + s1 + "' and '" + s2 + "' = " + d);
//		Random r = new Random();
//		int len = 100;// change the length
//		String str = new String();
//		//generate 1000 words
//		String[] lst= new String[1000]; 
//		for (int i=0; i <1000;i++)
//		{
//			
//		for( int j = 0; j < len; j++ )
//           str  += (char) ( 'a' + r.nextInt( 26 ) );
//		
//		lst[i] = str;
//		}
//		// calculate distance between each pair  in lst
//		
//		
//		// random strings
//       // Random r = new Random( );
//		String s1r = "";
//		String s2r = "";
//         //len = 4;
//
//		for ( int c =0; c<10000;c++){
//			s1r="";
//			s2r="";
//        for( int j = 0; j < len; j++ ){
//            s1r += (char) ( 'a' + r.nextInt(26));
//            s2r += (char) ( 'a' + r.nextInt(26));
//        }
//        double t1= System.currentTimeMillis();
//		int dr = editDistance(s1r, s2r);
//		double t2 = System.currentTimeMillis();
//		totaltime += t2-t1;  // totaltime = totaltime + (t2-t1);
////		System.out.println("Dist between '" + s1r + "' and '" + s2r + "' = " + dr);
//	}//1000 loop
//		//System.out.println(totaltime);
//		System.out.println("Average CPU time of avg dist for 1000 random pairs of strings " + totaltime/1000);

}//main
}//class