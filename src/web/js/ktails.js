"use strict";

// new states and links constructed by the algorithm
var kStates = [];
var kLinks = [];

function convertToKTailsData(data, k){
  // construct k states from data
  var id = 0;
  var stateMaps = []; // mapping of index id to states that id encompasses

  var length = data.states.length - k + 1;
  if(length <= 0){
     length = 1;
  }

  console.log(length);

  for(var i = 0; i < length; i++){
	var kState = {id:0, fields:[]};
	kState.id = id;

	// get the next k states
	var stateMap = []
	for(var j = i; j < i + k; j++){
		stateMap.push(j);
	}

	stateMaps[id] = stateMap;
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
		  kLinks[index] = kLink;
		  index++;
	  }
  }

  var json = "{\"states\": " + JSON.stringify(kStates) + ", \"links\": " + JSON.stringify(kLinks)+"}";
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
