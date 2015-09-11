package main.tracer.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import main.tracer.MethodKey;
import main.tracer.TraceEntry;

/**
 * {@code TraceEntryTree} represents a tree of the method calls executed within
 * a Java program. A {@code TraceEntryTreeNode} in the {@code TraceEntryTree} represents
 * a method execution and contains information regarding the state of the program
 * before and after execution of that method. Children of each {@code TraceEntryTreeNode}
 * represents methods executed within that particular method.
 *
 * @author David Sheridan
 *
 */
public class TraceEntryTree {

	// fields
	private TraceEntryTreeNode root;
	private int size;

	/**
	 * Constructs a new instance of a {@code TraceEntryTree} with
	 * the specified {@code TraceEntryTreeNode} as the root.
	 *
	 * @param root
	 * 		- root of the tree
	 */
	protected TraceEntryTree(TraceEntryTreeNode root, int size){
		this.root = root;
	}

	/**
	 * Generates a {@code TraceEntryTree} from the specified list of {@code TraceEntries}.
	 *
	 * @param entries
	 * 		- list of entries
	 * @return
	 * 		- {@code TraceEntryTree}
	 */
	public static TraceEntryTree generateTraceEntryTree(List<TraceEntry> entries){
		TraceEntryTreeNode root = null;

		Stack<TraceEntryTreeNode> treeNodes = new Stack<TraceEntryTreeNode>();
		int size = 0;

		// iterate through entries and construct tree
		for(int i = 0; i < entries.size(); i++){
			// create root on first iteration of loop
			if(i == 0){
				MethodKey key = entries.get(i).getMethod();
				String methodName = key.className + "." + key.name;
				root = new TraceEntryTreeNode(methodName, entries.get(i).getState());
				treeNodes.push(root);
				size++;
			}

			// check if current entry is entering or exiting method
			if(!entries.get(i).isExit()){
				MethodKey key = entries.get(i).getMethod();
				String methodName = key.className + "." + key.name;
				TraceEntryTreeNode current = new TraceEntryTreeNode(methodName, entries.get(i).getState());
				treeNodes.peek().addChild(current);
				treeNodes.push(current);
				size++;
			}
			else{
				TraceEntryTreeNode previous = treeNodes.pop();
				previous.setStateAfter(entries.get(i).getState());
			}
		}

		return new TraceEntryTree(root, size);
	}

	/**
	 * Returns the root {@code TraceEntryTreeNode} for this {@code TraceEntryTree}.
	 *
	 * @return
	 * 		- root of tree
	 */
	public TraceEntryTreeNode getRoot(){
		return root;
	}

	/**
	 * Returns a {@code String} representation of this {@code TraceEntryTree}.
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TraceEntryTree(Size = " + size + "){\n");

		Queue<TraceEntryTreeNode> nodeStack = new LinkedList<TraceEntryTreeNode>();
		nodeStack.add(root);

		String indent = "  ";
		int depth = 1;

		while(!nodeStack.isEmpty()){
			TraceEntryTreeNode current = nodeStack.poll();
			for(int i = 0; i < depth; i++){
				builder.append(indent);
			}
			builder.append(current.getMethodName() + "\n");
			if(!current.isLeaf()){
				depth++;
				for(TraceEntryTreeNode child : current.getChildren()){
					nodeStack.add(child);
				}
			}
		}

		builder.append("}");
		return builder.toString();
	}
}
