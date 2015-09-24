package main.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Main;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JsonHandler extends AbstractHandler {

	@Override
	public void handle(String arg0, Request baseRequest, HttpServletRequest arg2,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println(arg0);
		if (arg0.contains("JSON")){
			response.setContentType("text/json; charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);

	        PrintWriter out = response.getWriter();

	        out.println(Main.getManager().getJson());

	        baseRequest.setHandled(true);
		}

	}

}
