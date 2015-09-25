package main.tracer.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.tracer.MethodKey;
import main.tracer.Trace;
import main.tracer.TraceEntry;
import main.tracer.TraceFilterSelector;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

public class TraceTests {

	private int size = 100;

	@Test
	public void constructorTest() {
		Trace trace = getTrace();
		trace.setLines(getLines());
	}

	/**
	 * Ensures that a empty filter leaves nothing.
	 */
	@Test
	public void filterTestOne(){
		Trace trace = getTrace();
		trace.setLines(getLines());
		assertTrue(trace.getLines().size() == size);
		trace.applyFilter(getSelector().getFilter());
		assertTrue(trace.getLines().size() == 0);
	}

	/**
	 * Ensures that a empty filter leaves nothing.
	 */
	@Test
	public void filterTestTwo(){
		Trace trace = getTrace();
		trace.setLines(getLines());
		assertTrue(trace.getLines().size() == size);

		TraceFilterSelector selector = getSelector();
		List<String> temp = new ArrayList<>();
		//to string is the checking for filters.
		temp.add(getMethodKey().toString());
		selector.addMethodsToFilter(temp);


		trace.applyFilter(selector.getFilter());
		System.out.println(trace.getLines().size());
		assertTrue(trace.getLines().size() == size/2);
	}

	/**
	 * Gets a new trace
	 * @return - new trace.
	 */
	private Trace getTrace(){
		return new Trace();
	}

	/**
	 * Get a list containing 100 entry.
	 * @return
	 */
	private List<TraceEntry> getLines(){
		List<TraceEntry> lines = new ArrayList<TraceEntry>();
		int to = size;
		int from = 0;
		for (; from < to; from++){
			if (from%2 == 0){
				lines.add(getTraceEntry(getMethodKey()));
			}
			else {
				lines.add(getTraceEntry(getMethodKey(1)));
			}
		}
		return lines;
	}

	/**
	 * For making a trace entry.
	 * @param methodKey - a method key
	 * @return - a new trace entry with a set method key.
	 */
	private TraceEntry getTraceEntry(MethodKey methodKey){
		TraceEntry tr = new TraceEntry();
		tr.setMethod(methodKey);
		//tr.setIsExit(false);
		return tr;
	}

	/**
	 * No params
	 * @return methodKey
	 */
	private MethodKey getMethodKey(){
		try {
			return new MethodKey(this.getClass().getDeclaredMethod("getMethodKey"));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * One param
	 * @param one
	 * @return
	 */
	private MethodKey getMethodKey(int one){
		try {
			return new MethodKey(this.getClass().
					getDeclaredMethod("getMethodKey", int.class));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * gets a new trace filter selector
	 * @return
	 */
	private TraceFilterSelector getSelector(){
		return new TraceFilterSelector();
	}

}
