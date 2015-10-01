package main.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Main;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JsonHandler extends AbstractHandler {

	private static final String CommandRequest = "TraceRequest";

	@Override
	public void handle(String arg0, Request baseRequest, HttpServletRequest arg2,
			HttpServletResponse response) throws IOException, ServletException {

		if (arg0.contains(CommandRequest)){
			String request = arg0.substring(arg0.indexOf(CommandRequest)+CommandRequest.length()+1);
			System.out.println(request);
			File file = new File("data/traces/"+request);
			response.setContentType("text/json; charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);

	        PrintWriter out = response.getWriter();
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        while (reader.ready()){
	        	out.println(reader.readLine());
	        }
	        reader.close();
	        baseRequest.setHandled(true);
		}

	}

}
