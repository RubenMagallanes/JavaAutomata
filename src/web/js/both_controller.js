"use strict";

// get automata data from server
// and use data to start viz
var temp = window.location.href;
temp = temp.replace("automata&petri_net=", "TraceRequest/")
load(temp);
console.log(temp);

function load(url){
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        success: function(data) {
            viz.automata.init(JSON.stringify(data), $("div#automata");
            viz.petri.init(JSON.stringify(data), $("div#petrinet");
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
        }
    });
}

// testing without a server
//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
//viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
