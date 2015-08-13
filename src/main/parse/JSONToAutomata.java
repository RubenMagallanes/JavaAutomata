package main.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class JSONToAutomata {

	private static final Pattern ENTER = Pattern.compile("\"enterMethod\":");
	private static final Pattern EXIT = Pattern.compile("\"exitMethod\":");
	private static final Pattern METHOD = Pattern.compile("\"methodName\":");
	private static final Pattern STATE = Pattern.compile("\"state\":");

	private static final Pattern METHOD_NAME = Pattern.compile("\"[a-zA-Z]+.[a-zA-Z()\\[\\]]+\\)\"[,]*");

	private static final Pattern OPEN_BRACE = Pattern.compile("\\{");
	private static final Pattern CLOSE_BRACE = Pattern.compile("\\}[,]*");
	private static final Pattern OPEN_BRACKET = Pattern.compile("\\[");
	private static final Pattern CLOSE_BRACKET = Pattern.compile("\\]");

	public static Automata generateAutomata(File file){
		Scanner scan = null;
		try{
			scan = new Scanner(file);
		}catch(IOException e){System.err.println(e);}

		gobbleStart(scan);

		while(scan.hasNext(OPEN_BRACE)){
			gobble(scan, OPEN_BRACE);
			if(scan.hasNext(ENTER)){
				gobble(scan, ENTER);
				gobble(scan, OPEN_BRACE);
				String name = parseMethodName(scan);
				System.out.println(name);
				parseState(scan);
			}
			else if(scan.hasNext(EXIT)){
				gobble(scan, EXIT);
				gobble(scan, OPEN_BRACE);
				String name = parseMethodName(scan);
				parseState(scan);
			}
			else{
				System.out.println("error");
			}
		}

		scan.close();
		return null;
	}

	private static String parseMethodName(Scanner scan){
		gobble(scan, METHOD);
		String name = scan.nextLine();
		return name.substring(2, name.length() - 2);
	}

	private static void parseState(Scanner scan){
		gobble(scan, STATE);
		gobble(scan, OPEN_BRACE);
		scan.nextLine();
		List<AutomataField> fields = new ArrayList<AutomataField>();
		while(!scan.hasNext(CLOSE_BRACE)){
			String line = scan.nextLine().trim( );
			String[] data = line.split(":", 2);
			String name = data[0].substring(1, data[0].length() - 1);
			int end = (data[1].charAt(data[1].length() - 1) == ',') ? 2 : 1;
			String value = data[1].substring(1, end);
			fields.add(new AutomataField(null, name, value));
		}
		gobble(scan, CLOSE_BRACE);
	}

	private static void gobbleStart(Scanner scan){
		gobble(scan, OPEN_BRACKET);
		gobble(scan, OPEN_BRACE);
		gobble(scan, ENTER);
		gobble(scan, OPEN_BRACE);
		gobble(scan, METHOD);
		gobble(scan, METHOD_NAME);
		gobble(scan, CLOSE_BRACE);
		gobble(scan, CLOSE_BRACE);
	}

	private static String gobble(Scanner scan, Pattern expected){
		if(!scan.hasNext(expected)){
			System.out.println("ERROR");
		}
		return scan.next();
	}

	public static void main(String[] args){
		File file = new File("data" + File.separatorChar + "traces" + File.separatorChar + "test.json");
		generateAutomata(file);
	}
}
