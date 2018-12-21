package final_project;

import java.io.*;
import org.jsoup.*;

public class HTMLJsoup
{
	
	public HTMLJsoup(String url, int count) throws IOException
	{
		org.jsoup.nodes.Document doc = Jsoup.connect("https://www.w3.org/TR/wai-aria/").timeout(0).ignoreHttpErrors(true).userAgent("Mozilla").get();
		
		String text = doc.text();
		PrintWriter out = new PrintWriter("urls_list/text/"+count+".txt"); //also save web url in parsed text file
		out.println(url+" "+text);
		out.close();
	}
	

	public static void main(String[] args) throws IOException
	{
		
	}
}
