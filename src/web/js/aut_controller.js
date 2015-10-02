"use strict";

// get automata data from server
// and use data to start viz

var temp = window.location.href;
temp = temp.replace("automata=", "TraceRequest/")
load(temp);

function load(url){
    console.log(url);
    $.ajax({
      type: 'GET',
      url: url,
      contentType: 'application/json',
      success: function(data) {
          viz.automata.init(JSON.stringify(data), $("div#automata"));
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        console.log("Status: " + textStatus);
        console.log("Error: " + errorThrown);
    }
    });
}
// for testing without a server
//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
