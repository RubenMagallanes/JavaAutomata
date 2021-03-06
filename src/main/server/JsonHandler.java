package main.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.parse.Automata;
import main.parse.AutomataToVisualisation;
import main.parse.TraceToAutomataException;
import main.parse.TraceToAutomata;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JsonHandler extends AbstractHandler {

	private static final String CommandRequest = "TraceRequest";

	@Override
	public void handle(String arg0, Request baseRequest, HttpServletRequest arg2,
			HttpServletResponse response) throws IOException, ServletException {

		//Checks request type.
		if (arg0.contains(CommandRequest)){
			// gets request
			String request = arg0.substring(arg0.indexOf(CommandRequest)+CommandRequest.length()+1);

			//Loads file and prints.
			File file = new File("data/traces/"+request);
			response.setContentType("text/json; charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);

	        PrintWriter out = response.getWriter();

	        try {
				Automata automata = TraceToAutomata.generateAutomata(file);
				AutomataToVisualisation visulaization = new AutomataToVisualisation(automata);
				out.print(visulaization.parseAutomata());
			} catch (TraceToAutomataException e) {
				e.printStackTrace();
			}

	        baseRequest.setHandled(true);
		}

	}

}
