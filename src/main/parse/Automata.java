 package main.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Automata {

	// fields
	private Set<AutomataState> states;
	private Set<AutomataLink> links;

	protected Automata(Set<AutomataState> states, Set<AutomataLink> links){
		this.states = states;
		this.links = links;
	}

	public Set<AutomataState> getStates(){
		return states;
	}

	public Set<AutomataLink> getLinks(){
		return links;
	}
}
