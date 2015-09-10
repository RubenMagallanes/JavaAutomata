package main.tracer.tree;

import java.util.List;

import main.tracer.TraceEntry;

public class TraceEntryTree {

	// fields
	private TraceEntryTreeNode root;

	protected TraceEntryTree(TraceEntryTreeNode root){
		this.root = root;
	}

	public static TraceEntryTree generateTraceEntryTree(List<TraceEntry> entries){
		TraceEntryTreeNode root = null;
		TraceEntryTreeNode current = null;

		for(TraceEntry entry : entries){

		}

		return new TraceEntryTree(root);
	}
}
