package main.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ViewHandler extends AbstractHandler {

	// Command options
	private static final String OPTION_ONE = "automata&petri_net";
	private static final String OPTION_TWO = "automata";
	private static final String OPTION_THREE = "petri_net";



	@Override
	public void handle(String arg0, Request baseRequest, HttpServletRequest arg2,
			HttpServletResponse response) throws IOException, ServletException {
		// Checks for matching options
		if (arg0.contains(OPTION_ONE) || arg0.contains(OPTION_TWO) || arg0.contains(OPTION_THREE)){

			// Removes the request type.
			String request = arg0.substring(1);

			// sets sending content type.
			response.setContentType("text/html; charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);

	        // Gets file type based on request
	        String fileName = "";
	        if (request.startsWith(OPTION_ONE)){
	        	fileName = "both.html";
	        }
	        else if (request.startsWith(OPTION_TWO)){
	        	fileName = "automata.html";
	        }
	        else if (request.startsWith(OPTION_THREE)){
	        	fileName = "petrinet.html";
	        }
	        else return;

	        // Retrieves file.
			File file = new File("src/web/"+fileName);
	        PrintWriter out = response.getWriter();
	        BufferedReader reader = new BufferedReader(new FileReader(file));

	        // Sends it.
	        while (reader.ready()){
	        	out.println(reader.readLine());
	        }
	        reader.close();

	        // Handles the request.
	        baseRequest.setHandled(true);
		}
	}

}
