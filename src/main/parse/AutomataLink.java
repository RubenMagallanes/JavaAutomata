package main.parse;

public class AutomataLink {

	// fields
	private int source;
	private int target;

	public AutomataLink(int source, int target){
		this.source = source;
		this.target = target;
	}

	public int getSource(){
		return source;
	}

	public int getTarget(){
		return target;
	}
}
