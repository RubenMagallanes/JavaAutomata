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

public class TraceLauncher {

	private String jarPath;
	private List<ExecutionData> executions = new ArrayList<ExecutionData>(Arrays.asList(new ExecutionData()));


	public TraceLauncher(String JarPathName){
		this.jarPath = JarPathName;
	}

	public Trace[] run(){
		if(jarPath == null)return null;


		//the file of the jar
		File file = new File(jarPath);


		//loads in the jar file
		JarData jd = JarLoader.loadJarFile(file);


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


		TestThread thread = new TestThread(loadedClasses, executionsArray, mainClass);

		thread.setName("MainWindow tracer thread");
		thread.setDaemon(true);
		thread.start();

		return thread.getTraces();

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

	//System.out.println(traces.length);
	for (int i = 0; i < traces.length; i++) {
		for(int j = 0; j < traces[i].getLines().size(); j++){
			//traces[i].applyFilter(filter);
			//System.out.println("Trace :" + traces[i].toString());
			if(!traces[i].getLines().isEmpty()){

				for(TraceEntry s : traces[i].getLines()){
					//System.out.println(s);
				}

			}
		}
	}
}

private TraceFilter getSelectionFilter() {
	/*return new TraceFilter() {
		private Set<MethodKey> selectedMethods = new HashSet<MethodKey>();
		private Set<FieldKey> selectedFields = new HashSet<FieldKey>();
		private Set<ParameterKey> selectedParameters = new HashSet<ParameterKey>();



		@Override
		public boolean isMethodTraced(MethodKey m) {
			return selectedMethods.contains(m);
		}

		@Override
		public boolean isFieldTraced(FieldKey f) {
			if(f.className.startsWith("__g"))
				return true;
			return selectedFields.contains(f);
		}

		@Override
		public boolean isParameterTraced(ParoldCharameterKey p) {
			return selectedParameters.contains(p);
		}
	};*/
	return null;
}


	public static void main(String[] args){

		String jar = "data" + File.separatorChar + "tests" + File.separatorChar + "TestProgram2.jar";


//		TraceFilter TemmpFilter = new TraceFilter(){
//
//			@Override
//			public boolean isFieldTraced(FieldKey f) {
//				if(f.name.equals("number1"))return true;
//				return false;
//			}
//
//			@Override
//			public boolean isMethodTraced(MethodKey m) {
//				System.out.println(m.name);
//				if(m.name.equals("setUpNumbers")){
//					System.out.println("True");
//					return true;
//				}
//				System.out.println("False");
//				return false;
//			}
//
//			@Override
//			public boolean isParameterTraced(ParameterKey p) {
//				return true;
//			}
//
//		};


		TraceLauncher t = new TraceLauncher(jar);
		Trace[] tr  = t.run();
	//	tr[0].applyFilter(TemmpFilter);
		//System.out.println(tr[0]);
		tr[0].constructJSONFile("test");
	}

	private class TestThread extends Thread{

		// fields
		private Set<String> loadedClasses;
		private ExecutionData[] executionsArray;
		private String mainClass;


		private Trace[] traces;

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


			traces = new Trace[executionsArray.length];



			for (int k = 0; k < executions.size(); k++) {
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

		public Trace[] getTraces(){return this.traces;}
	}
}
