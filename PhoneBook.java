import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PhoneBook 
{
    private static HashMap<String, List<String>> phoneBook  = new HashMap<String, List<String>>();

    public PhoneBook() {
    }

    public static void addContact(String name, String phoneNumber) {
        if (phoneBook.containsKey(name)) {
            List<String> phoneNumbers = phoneBook.get(name);
            phoneNumbers.add(phoneNumber);
        } else {
            List<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(phoneNumber);
            phoneBook.put(name, phoneNumbers);
        }
    }

    public static void printphoneBook() {
    	if (phoneBook.entrySet().size() > 0)
    	{
	        List<Map.Entry<String, List<String>>> entries = new ArrayList<>(phoneBook.entrySet());
	        entries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));
	        
	        for (Map.Entry<String, List<String>> entry : entries) {
	            System.out.println("Name: " + entry.getKey() + ", Phone numbers: ");
	            for (String phoneNumber : entry.getValue()) {
	                System.out.println(phoneNumber + " ");
	            }
	            System.out.println();
	        }
    	}
    }
    
    public static void loadFromDB() throws IOException 
    {
        File file = new File("phone.txt");
        if (file.exists())
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("phone.txt")));
            String line;
            while ((line=reader.readLine())!=null) 
            {
            	String[] data = line.split(" ");
            	if (data.length > 1)
            	{
            		String name = data[0];
            		String[] phoneNumbers = line.substring(name.length()+1).split(" ");
            		for (String s : phoneNumbers)
            		{
            			addContact(name, s);
            		}
            	}        
            }
            reader.close();
        }
    }
    
    public static void saveToDB() throws IOException 
    {
    	if (phoneBook.entrySet().size() > 0)
    	{
	    	List<Map.Entry<String, List<String>>> entries = new ArrayList<>(phoneBook.entrySet());
	        entries.sort((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()));
	        
	        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("phone.txt")));
	        for (Map.Entry<String, List<String>> entry : entries) 
	        {
	            writer.write(entry.getKey());
	            
	            for (String phoneNumber : entry.getValue()) 
	            {
	            	writer.write(" " + phoneNumber);
	            }
	        }
	        writer.close();
    	}
    }
    
    public static void main(String[] args) throws IOException, InterruptedException 
    {    	
    	//Load phoneBook from DB
    	loadFromDB();    	
    	
    	// Print to display
    	printphoneBook();
    	
    	// Select commands for next actions
    	System.out.println("SELECT CMD (and press ENTER): 1 - ADD, 2 - SAVE, 3 - PRINT, 0 - EXIT");
    	
    	
    	Scanner scan = new Scanner (System.in);
    	String str = scan.nextLine();
    	System.out.println("You typed: " + str);
                
    	while(str != "0") 
    	{
			if (str.equals("1"))
    		{
    			System.out.println("Enter NAME:");	                
    			BufferedReader input = new BufferedReader(
    		              new InputStreamReader(System.in, "UTF-8"));
                String name = input.readLine();
                System.out.println("Enter PHONE:");

                String phone = input.readLine();
                addContact(name, phone); 
    		}
    		else if (str.equals("2"))
    		{
    			saveToDB();
    			System.out.println("SAVE TO DB");    			
    		}	
    		else if (str.equals("3"))
    		{    			
    			System.out.println("PRINT DB");
    			printphoneBook();
    		}	
			
			System.out.println("SELECT CMD (and press ENTER): 1 - ADD, 2 - SAVE, 3 - PRINT, 0 - EXIT");
            str = scan.nextLine();
		}	
    	
    	System.out.println("FINISH");    	
    }
}
