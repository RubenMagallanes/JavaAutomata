package main.tracer;

import java.io.File;
import java.io.IOException;

import main.load.JarData;
import main.load.JarLoader;
import main.tracer.ExecutionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.jar.Attributes.Name;

public class TestSimpleProgram {

	private boolean DEBUG = true;
	private String jarPath;
	private List<ExecutionData> executions = new ArrayList<ExecutionData>(Arrays.asList(new ExecutionData()));


	public TestSimpleProgram(String JarPathName){
		this.jarPath = JarPathName;
	}

	public void run(){
		if(jarPath == null)return;


		//the file of the jar
		File file = new File(jarPath);


		//loads in the jar file
		JarData jd = JarLoader.loadJarFile(file);

		if(DEBUG)System.out.println("Jar null " + jd == null);

		//the path of the jar file
		jarPath = jd.getFile().getAbsolutePath();



		final ExecutionData[] executionsArray = executions
				.toArray(new ExecutionData[executions.size()]);


		//need to get a filter

		//grabs the jar file main class
		//grabs the main class of the jar file
		final String mainClass = jd.getManifest().getMainAttributes().getValue(Name.MAIN_CLASS);

		//grabs the classes from the jar file
		final Set<String> loadedClasses = new HashSet<String>();
		for (Class<?> cl : jd.getClasses()){
			loadedClasses.add(cl.getName());
		}


		//the tracefill to be made
		final File savedTraceFile = null;

		TestThread thread = new TestThread(loadedClasses, executionsArray, mainClass);

		thread.setName("MainWindow tracer thread");
		thread.setDaemon(true);
		thread.start();
		System.out.println("Done");
	}

/**
 * Called after a set of traces is either traced, or loaded from a file.
 *
 * @param traces
 *            The traces.
 * @param algorithm
 *            The algorithm to use.
 */
private void processTraces(Trace[] traces) throws IOException, InterruptedException {
	TraceFilter filter = getSelectionFilter();

	System.out.println(traces.length);
	for (int i = 0; i < traces.length; i++) {
		for(int j = 0; j < traces[i].getLines().size(); j++){
			//traces[i].applyFilter(filter);
			//System.out.println("Trace :" + traces[i].toString());
			if(!traces[i].getLines().isEmpty()){

				for(TraceEntry s : traces[i].getLines()){
					System.out.println(s);
				}

			}
		}
	}
}

private TraceFilter getSelectionFilter() {
	return new TraceFilter() {
		private Set<MethodKey> selectedMethods = new HashSet<MethodKey>();
		private Set<FieldKey> selectedFields = new HashSet<FieldKey>();
		private Set<ParameterKey> selectedParameters = new HashSet<ParameterKey>();



		@Override
		public boolean isMethodTraced(MethodKey m) {
			System.out.println(m.toString());
			return selectedMethods.contains(m);
		}

		@Override
		public boolean isFieldTraced(FieldKey f) {
			if(f.className.startsWith("__g"))
				return true;
			return selectedFields.contains(f);
		}

		@Override
		public boolean isParameterTraced(ParameterKey p) {
			return selectedParameters.contains(p);
		}
	};
}


	public static void main(String[] args){
		//if(args[0] == null)return;


		String jar = "data" + File.separatorChar + "tests" + File.separatorChar + "TestProgram.jar";

		TestSimpleProgram t = new TestSimpleProgram(jar);
		t.run();

	}

	private class TestThread extends Thread{

		// fields
		private Set<String> loadedClasses;
		private ExecutionData[] executionsArray;
		private String mainClass;

		public TestThread(Set<String> loadedClasses, ExecutionData[] executionsArray, String mainClass){
			this.loadedClasses = loadedClasses;
			this.executionsArray = executionsArray;
			this.mainClass = mainClass;
		}

		@Override
		public void start(){
			run();
		}

		@Override
		public void run() {
			System.out.println("Running");

			// The filters to use when tracing
			TraceFilter initialFilter;

			initialFilter = new TraceFilter() {
				@Override
				public boolean isMethodTraced(MethodKey m) {

					return loadedClasses.contains(m.className);
				}

				@Override
				public boolean isFieldTraced(FieldKey f) {

					return loadedClasses.contains(f.className);
				}

				@Override
				public boolean isParameterTraced(ParameterKey p) {

					return true;
				}
			};


			Trace[] traces = new Trace[executionsArray.length];


			System.out.println(traces.length);

			for (int k = 0; k < executions.size(); k++) {
				System.out.println("Executions");
				ExecutionData ed = executions.get(k);
				FutureTraceConsumer future = new FutureTraceConsumer();
				try {
					Tracer.launchAndTraceAsync("-cp \"" + jarPath + "\"",
							mainClass + " " + ed.commandLineArguments,
							initialFilter, future);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					traces[k] = future.get();
					Trace test = traces[k];
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}

			try {
				processTraces(traces);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
