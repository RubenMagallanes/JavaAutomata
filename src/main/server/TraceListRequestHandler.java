package main.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TraceListRequestHandler extends AbstractHandler {

	//key for the request of list of traces
	private final String requestType  = "ListTraceFiles";

	//folder to look in for traces
	private final File folder = new File("data/traces");

	@Override
	public void handle(String arg0, Request arg1, HttpServletRequest arg2,
			HttpServletResponse arg3) throws IOException, ServletException {


		if(arg0.contains(requestType)){

	        arg3.setStatus(HttpServletResponse.SC_OK);
			arg3.setContentType("text/fileList; charset=utf-8");

	        PrintWriter out = arg3.getWriter();
	        printFilesToPrintWriter(folder, out);

	        arg1.setHandled(true);
		}
	}

	public void printFilesToPrintWriter(final File folder, PrintWriter out) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            continue;//ignore folder
	        }
	        else if(fileEntry.getName().length() < 5){
	        	continue;//not a valid JSON file
	        }
	        else {
	        	String fileName = fileEntry.getName();

	        	//check if the file type is json
	        	if(fileName.substring(fileName.length()-5, fileName.length()).equals(".json") || fileName.substring(fileName.length()-6, fileName.length()).equals(".trace")){
	        		out.println(fileName);
	        	}
	        }
	    }
	}

	public void testFileCheck(){
		File file = new File("Test_list.txt");

		try {
			PrintWriter out = new PrintWriter(file);

			printFilesToPrintWriter(folder, out);

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		TraceListRequestHandler th = new TraceListRequestHandler();
		th.testFileCheck();
	}
}
