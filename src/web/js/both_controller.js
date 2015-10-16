"use strict";

// get automata data from server
// and use data to start viz
var temp = window.location.href;
temp = temp.replace("automata&petri_net=", "TraceRequest/");
var algorithm = temp.substring(temp.indexOf('?')+1);
load(temp);


//var noderefPetri = viz.petri.getNode;


function load(url){
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        success: function(data) {
            var json = data;
            if (algorithm.indexOf('ktails') >= 0){
                var states = algorithm.substring('ktails'.length+1);
                json = convertToKTailsData(json, parseInt(states));
            }
            else if (algorithm.indexOf('normal') >= 0){
                json = JSON.stringify(data);
            }
           // console.log(json);
			//set flag then load, or load then call method
            viz.automata.init(json, $("div#automata"));

            // mouse listeners
			viz.automata.addMouseEnterListener(automataMouseEnter);
			viz.automata.addMouseOutListener(automataMouseOut);
			viz.petri.addMouseEnterListener(petriMouseEnter);
			viz.petri.addMouseOutListener(petriMouseOut);

            viz.petri.init(json, $("div#petrinet"));
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
        }
    });

    function automataMouseEnter(node){
        var stateI = +d3.select(node).attr("id").slice(-1);
        viz.petri.selectNodes(stateI);
    }

    function automataMouseOut(node){
        var stateI = +d3.select(node).attr("id").slice(-1);
        viz.petri.deselectNodes(stateI);
    }

    // unfinished
    function petriMouseEnter(stateI){
        var stateI = +d3.select(node).attr("id").slice(-1);
        console.log(stateI);
        viz.automata.selectNode(stateI);
    }

    // unfinished
    function petriMouseOut(stateI){
        var stateI = +d3.select(node).attr("id").slice(-1);
        viz.automata.deselectNode(stateI);
    }
}
