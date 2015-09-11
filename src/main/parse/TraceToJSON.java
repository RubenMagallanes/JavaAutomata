package main.parse;

import org.json.JSONArray;
import org.json.JSONObject;

import main.tracer.tree.TraceEntryTree;
import main.tracer.tree.TraceEntryTreeNode;

/**
 * Class for converting the {@code TraceEntryTree} from a trace output of a Java program
 * into a JSON format.
 *
 * @author David Sheridan
 *
 */
public class TraceToJSON {

	public static final String METHOD_NAME = "methodName";
	public static final String STATE_BEFORE = "stateBefore";
	public static final String STATE_AFTER = "stateAfter";
	public static final String CHILDREN = "children";
	public static final int INDENT_FACTOR = 2;

	/**
	 * Converts the specified {@code TraceEntryTree} into a JSON {@code String}.
	 *
	 * @param tree
	 * 		- the tree to be converted
	 * @return
	 * 		- json representation of tree
	 */
	public static String generateJSON(TraceEntryTree tree){
		JSONObject trace = convertTraceEntryTreeNodeToJSON(tree.getRoot());
		return trace.toString(INDENT_FACTOR);
	}

	/**
	 * Converts the specified {@code TraceEntryTreeNode} into a {@code JSONObject}.
	 * Recursively calls this method on any children that the {@TraceEntryTreeNode} may
	 * have.
	 *
	 * @param node
	 * 		- the node to be converted
	 * @return
	 * 		- json representation of node
	 */
	private static JSONObject convertTraceEntryTreeNodeToJSON(TraceEntryTreeNode node){
		JSONObject json = new JSONObject();
		json.append(METHOD_NAME, node.getMethodName());
		json.append(STATE_BEFORE, node.getStateBefore().toJSON());
		json.append(STATE_AFTER, node.getStateAfter().toJSON());

		// get children
		JSONArray children = new JSONArray();
		for(TraceEntryTreeNode child : node.getChildren()){
			children.put(convertTraceEntryTreeNodeToJSON(child));
		}
		json.append(CHILDREN, children);

		return json;
	}
}
