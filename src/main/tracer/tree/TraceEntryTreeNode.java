package main.tracer.tree;

import java.util.ArrayList;
import java.util.List;

import main.tracer.state.State;

public class TraceEntryTreeNode {

	// fields
	private State before;
	private List<TraceEntryTreeNode> children;
	private State after;

	public TraceEntryTreeNode(State before){
		this.before = before;
		children = new ArrayList<TraceEntryTreeNode>();
	}

	public State getStateBefore(){
		return before;
	}

	public State getStateAfter(){
		return after;
	}

	public void setStateAfter(State after){
		this.after = after;
	}

	public List<TraceEntryTreeNode> getChildren(){
		return children;
	}

	public void addChild(TraceEntryTreeNode child){
		children.add(child);
	}
}
