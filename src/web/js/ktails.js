"use strict";

var test = {"states":[
                      {
                    	    "id": 0,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "f"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "s"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 1,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "s"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 2,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 3,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "fff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 4,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "fff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "sss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 5,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "sss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 6,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ssss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 7,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "fff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ssss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 8,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "fffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ssss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 9,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "fffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "sssss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 10,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ffffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "sssss"
                    	      }
                    	    ]
                    	  },
                    	  {
                    	    "id": 11,
                    	    "fields": [
                    	      {
                    	        "name": "string1",
                    	        "value": "ffffff"
                    	      },
                    	      {
                    	        "name": "string2",
                    	        "value": "ssssss"
                    	      }
                    	    ]
                    	  }
                    	],"links":[
                    	  {
                    	    "methodName": "Strings.multipleIncrements",
                    	    "source": 0,
                    	    "target": 0,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 0,
                    	    "target": 1,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.multipleIncrements",
                    	    "source": 0,
                    	    "target": 6,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString2",
                    	    "source": 1,
                    	    "target": 2,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 2,
                    	    "target": 3,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString2",
                    	    "source": 3,
                    	    "target": 4,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 4,
                    	    "target": 5,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString2",
                    	    "source": 5,
                    	    "target": 6,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.subString1",
                    	    "source": 6,
                    	    "target": 7,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 6,
                    	    "target": 8,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.multipleIncrements",
                    	    "source": 6,
                    	    "target": 11,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 7,
                    	    "target": 6,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString2",
                    	    "source": 8,
                    	    "target": 9,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString1",
                    	    "source": 9,
                    	    "target": 10,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.addString2",
                    	    "source": 10,
                    	    "target": 11,
                    	    "count": 1
                    	  },
                    	  {
                    	    "methodName": "Strings.subString2",
                    	    "source": 11,
                    	    "target": 10,
                    	    "count": 1
                    	  }
                    	]};

// new states and links constructed by the algorithm
var kStates = [];
var kLinks = [];

function convertToKTailsData(data, k){
  // construct k states from data
  var id = 0;
  var stateMaps = []; // mapping of index id to states that id encompasses

  for(var i = 0; i < data.states.length - k + 1; i++){
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

  var json = "\"states\": " + JSON.stringify(kStates) + ", \"links\": " + JSON.stringify(kLinks);
  return JSON.stringify(json);
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

//console.log(test);
convertToKTailsData(test, 3);
