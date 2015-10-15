 package main.parse;

import java.util.Set;

/**
 * Class representing an automata showing the state changes of a Java program.
 * Consists of a set of states describing a state of the program and a set of
 * links describing method calls that link states together.
 *
 * @author David Sheridan
 *
 */
public class Automata {

	// fields
	private Set<AutomataState> states;
	private Set<AutomataLink> links;

	/**
	 * Constructs a new instance of an {@code Automata} with the specified
	 * {@code AutomataStates} and {@code AutamataLinks}.
	 *
	 * @param states
	 * 		- states of the program
	 * @param links
	 * 		- method calls between states
	 */
	public Automata(Set<AutomataState> states, Set<AutomataLink> links){
		this.states = states;
		this.links = links;
	}

	/**
	 * Returns a set of {@code AutomataStates} associated with this {@code Automata}.
	 *
	 * @return
	 * 		- set of states
	 */
	public Set<AutomataState> getStates(){
		return states;
	}

	/**
	 * Returns a set of {@code AutomataLinks} associated with this {@code Automata}.
	 *
	 * @return
	 * 		- set of links
	 */
	public Set<AutomataLink> getLinks(){
		return links;
	}

	/**
	 * Returns a {@code String} representation of this {@code Automata}.
	 */
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
