package main.ui;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUIFrame extends Application{


	private static Stage primaryStage;
	final static int width = 1000;
	final static int diffrence = 100;
	final static int consoleSize = 100;
	final static int height = 500;
	private static Server server;

	@Override
	public void start(Stage primaryStage) throws Exception {
		GUIFrame.primaryStage = primaryStage;
		primaryStage.setTitle("Automata");
		MenuPane menu = new MenuPane();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		primaryStage.setScene(new Scene(menu, GUIFrame.width, GUIFrame.height));
		primaryStage.show();

		server = new Server(8080);
		try {

			// Create the ResourceHandler. It is the object that will actually handle the request for a given file. It is
	        // a Jetty Handler object so it is suitable for chaining with other handlers as you will see in other examples.
	        ResourceHandler resource_handler = new ResourceHandler();
	        // Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
	        // In this example it is the current directory but it can be configured to anything that the jvm has access to.
	        resource_handler.setDirectoriesListed(true);
	        //resource_handler.setWelcomeFiles(new String[]{ "index.html" });
	        resource_handler.setResourceBase("src/web/");

	        // Add the ResourceHandler to the server.
	        GzipHandler gzip = new GzipHandler();
	        server.setHandler(gzip);
	        HandlerList handlers = new HandlerList();
	        handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
	        gzip.setHandler(handlers);


			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void main(String[] args){
		launch(args);
	}

}
