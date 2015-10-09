"use strict";

// get automata data from server
// and use data to start viz

var temp = window.location.href;
temp = temp.replace("petri_net=", "TraceRequest/")
var algoritum = temp.substring(temp.indexOf('?')+1);
load(temp);

function load(url){
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        success: function(data) {
            var json = data;
            if (algoritum.indexOf('ktails') >= 0){
                var states = algoritum.substring('ktails'.length+1);
                json = convertToKTailsData(json, parseInt(states));
            }
            else if (algoritum.indexOf('normal') >= 0){
                json = JSON.stringify(data);
            }
            viz.petri.init(json, $("div#petrinet"));
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("Status: " + textStatus);
            console.log("Error: " + errorThrown);
        }
    });
}

// for testing without a server
//viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
