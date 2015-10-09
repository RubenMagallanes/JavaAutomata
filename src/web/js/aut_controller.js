"use strict";

var temp = window.location.href;
temp = temp.replace("automata=", "TraceRequest/");
var algoritum = temp.substring(temp.indexOf('?')+1);
load(temp);

function load(url){
    console.log(url);
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
          viz.automata.init(json, $("div#automata"));
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        console.log("Status: " + textStatus);
        console.log("Error: " + errorThrown);
    }
    });
}
// for testing without a server
//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
