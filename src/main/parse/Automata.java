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

	public Automata(Set<AutomataState> states, Set<AutomataLink> links){
		this.states = states;
		this.links = links;
	}

	public Set<AutomataState> getStates(){
		return states;
	}

	public Set<AutomataLink> getLinks(){
		return links;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();

		builder.append("States:\n");
		for(AutomataState state : states){
			builder.append("ID = " + state.getId() + ", ");
			builder.append("Fields = [");
			for(AutomataField field : state.getFields()){
				builder.append("{" + field.getName() + " = " + field.getValue() + "}");
			}
			builder.append("]\n");
		}

		builder.append("\nLinks:\n");
		for(AutomataLink link : links){
			builder.append("Method = " + link.getName() + ", Source = " + link.getSource() + ", Target = " + link.getTarget() + "\n");
		}

		return builder.toString();
	}
}
