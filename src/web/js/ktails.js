"use strict";

// new states and links constructed by the algorithm
var kStates = [];
var kLinks = [];

/**
 * Converts the specified data into a k-tails representation of
 * an algorithm. This involves grouping individual states into groups
 * of k states.
 *
 * @param data
 * 		- automata data to convert
 * @param k
 * 		- the number of states to group together
 *
 * @returns {String}
 */
function convertToKTailsData(data, k){
  // construct k states from data
  var id = 0;
  var stateMaps = []; // mapping of index id to states that id encompasses
  console.log("number of states: " + data.states.length);
  // ensure that length is at least 1
  var length = data.states.length - k + 1;
  if(length <= 0){
     length = 1;
  }

  for(var i = 0; i < length; i++){
	var kState = {id: 0, startState: false, fields: []};
	kState.id = id;

	// get the next k states
	var stateMap = [];
	for(var j = i; j < i + k; j++){
		// check if current state is a start state
		if(data.states[j].startState){
			kState.startState = true;
		}

		stateMap.push(j);
	}


	// initialise fields array
	var fields = [];
	for(var q = 0; q < data.states[i].fields.length; q++){
		var field = {name: "", value: ""};
		field.name = data.states[i].fields[q].name;
		fields.push(field);
	}

	// populate fields array
	for(var p = 0; p < data.states[i].fields.length; p++){
		for(var q = 0; q < k; q++){
			fields[p].value += data.states[i + q].fields[p].value;
			if(q < k - 1){
				fields[p].value += "-";
			}
		}
	}

	stateMaps[id] = stateMap;
	kState.fields = fields;
	id++;
	kStates.push(kState);
  }

  // construct links between new states
  var index = 0;
  for(var i = 0; i < data.links.length; i++){
	  var kLink = {methodName: "test", source: 0, target: 0};

	  for(var j = 0; j < stateMaps.length; j++){
		  var states = stateMaps[j];
		  for(var k = 0; k < states.length; k++){
			  if(data.links[i].source === states[k]){
				  kLink.source = kStates[j].id;
			  }

			  if(data.links[i].target === states[k]){
				  kLink.target = kStates[j].id;
			  }
		  }
	  }

	  // check if the link is a duplicate
	  if(!checkDuplicateLink(kLinks, kLink)){
		  kLink.methodName = kLink.source + " to " + kLink.target;
		  kLinks[index] = kLink;
		  index++;
	  }
  }

  var json = "{\"states\": " + JSON.stringify(kStates) + ", \"links\": " + JSON.stringify(kLinks)+"}";
  console.log(json);
  return json;
}

/**
 * Determines whether the specified state has already appeared in the
 * specified list of states.
 *
 * @param kStates
 * 		- list of states
 * @param kState
 * 		- current state
 * @returns
 * 		- true if duplicate, otherwise false
 */
function checkDuplicateKState(kStates, kState){
	for(var i = 0; i < kStates.length; i++){
		if(kStates[i].fields.name === kState.fields.name &&
				kStates[i].fields.value === kState.fields.value){
			return true;
		}
	}
	return false;
};

/**
 * Determines whether the specified link has already appeared in the
 * specified list of links.
 *
 * @param kLinks
 * 		- list of links
 * @param kLink
 * 		- link
 * @returns
 * 		- true if duplicate, otherwise false
 */
function checkDuplicateLink(kLinks, kLink){
	for(var i = 0; i < kLinks.length; i++){
		if(kLinks[i].source === kLink.source && kLinks[i].target === kLink.target){
			return true;
		}
	}
	return false;
};
