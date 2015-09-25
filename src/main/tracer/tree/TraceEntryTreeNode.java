package main.tracer.tree;

import java.util.ArrayList;
import java.util.List;

import main.tracer.state.State;

/**
 * {@code TraceEntryTreeNode} represents a method call within a Java program. It holds
 * a record of the state before the method is executed as well as the state after in
 * is executed. It also contains a list of {@code TraceEntryTreeNode}s which refers to
 * method calls that are executed within this method.
 *
 * @author David Sheridan
 *
 */
public class TraceEntryTreeNode {

	// fields

	private String methodName;
	private State before;
	private State after;
	private List<TraceEntryTreeNode> children;

	/**
	 * Constructs a new instance of a {@Ccode TraceEntryTreeNode} with the containing
	 * the specified {@code State}. This {@code State} refers to the state of a program
	 * before a method has been executed.
	 *
	 * @param before
	 * 		- state of program before method executed
	 */
	public TraceEntryTreeNode(String methodName, State before){
		this.methodName = methodName;
		this.before = before;
		children = new ArrayList<TraceEntryTreeNode>();
	}

	/**
	 * Returns the name of the method this {@TraceEntryTreeNode} is representing.
	 *
	 * @return
	 * 		- name of method
	 */
	public String getMethodName(){
		return methodName;
	}

	/**
	 * Returns the state of the program before the method has been executed.
	 *
	 * @return
	 * 		- state before execution
	 */
	public State getStateBefore(){
		return before;
	}

	/**
	 * Returns the state of the program after the method has been executed.
	 *
	 * @return
	 * 		- state after execution
	 */
	public State getStateAfter(){
		return after;
	}

	/**
	 * Sets the state after execution to the specified {@code State}.
	 * @param after
	 */
	public void setStateAfter(State after){
		this.after = after;
	}

	/**
	 * Returns a list of the {@code TraceEntryTreeNode}s which represents the method
	 * calls executed within this instance of the {@code TraceEntryTreeNode}.
	 *
	 * @return
	 * 		list of {@code TraceEntryTreeNode}
	 */
	public List<TraceEntryTreeNode> getChildren(){
		return children;
	}

	/**
	 * Adds the specified {@code TraceEntryTreeNode} to the list of method calls executed
	 * within this instance of the {@code TraceEntryTreeNode}.
	 *
	 * @param child
	 * 		- nested method call
	 */
	public void addChild(TraceEntryTreeNode child){
		children.add(child);
	}

	/**
	 * Returns true if this {@code TraceEntryTreeNode} is a leaf, meaning that there
	 * are no method calls executed within the method this {@code TraceEntryTreeNode} is
	 * representing.
	 *
	 * @return
	 * 		- true if has no children, otherwise false
	 */
	public boolean isLeaf(){
		return children.isEmpty();
	}

	/**
	 * Returns a {@code String} representation of this {@code TraceEntryTreeNode}.
	 */
	public String toString(){
		return methodName;
	}
}
