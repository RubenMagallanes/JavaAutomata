"use strict";

// get automata data from server
// and use data to start viz

var temp = window.location.href;
temp = temp.replace("petri_net=", "TraceRequest/")
load(temp);

function load(url){
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        success: function(data) {
            viz.petri.init(JSON.stringify(data), $("div#petrinet"));
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("Status: " + textStatus);
            console.log("Error: " + errorThrown);
        }
    });
}

// for testing without a server
//viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
