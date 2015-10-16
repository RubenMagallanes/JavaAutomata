"use strict";

// get automata data from server
// and use data to start viz
var temp = window.location.href;
temp = temp.replace("automata&petri_net=", "TraceRequest/");
var algoritum = temp.substring(temp.indexOf('?')+1);
load(temp);


//var noderefPetri = viz.petri.getNode;


function load(url){
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        success: function(data) {
            var json = data;
            if (algoritum.indexOf('ktails') >= 0){
                var states = algoritum.substring('ktails'.length);
                json = convertToKTailsData(json, parseInt(states));
            }
            else if (algoritum.indexOf('normal') >= 0){
                json = JSON.stringify(data);
            }
           // console.log(json);
			//set flag then load, or load then call method
            viz.automata.init(json, $("div#automata"));
			viz.automata.addMousel();
			
            viz.petri.init(json, $("div#petrinet"));
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
        }
    });
}

//define onhover for both
//viz.automata && viz.petri
//.on("mouseenter",
//viz.automata.node.on("mouseclick");

//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
//viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
