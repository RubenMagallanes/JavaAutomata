"use strict";


/**
 * Global veriable.
 * These should be made into a function so that the application
 * will not have clashes but not problems yet.
 */
var temp = window.location.href;
temp = temp.replace("automata=", "TraceRequest/");
var algorithm = temp.substring(temp.indexOf('?')+1);
load(temp);


/**
 * Used for loading in the automata checks if the data
 * is ment to be in ktails or normal.
 *
 * TODO: Move the ktails to the server side it makes more sence.
 */
function load(url){
    console.log(url);
    $.ajax({
      type: 'GET',
      url: url,
      contentType: 'application/json',
      success: function(data) {
            var json = data;
            // Currently to add more algorityms you will need to
            // Modify here e.g.
            // if (algoritum.indexOf('<somthing new>') >= 0) {
            //    var states = algoritum.substring('<somthing new>'.length);
            //    console.log(states);
            //    json = convertTo<somthing new>Data(json;
            //}
            //
            // Where somthing new is the implementation you have chosen.
            // Should move this to server.
            if (algorithm.indexOf('ktails') >= 0){
                var states = algorithm.substring('ktails'.length);
                json = convertToKTailsData(json, parseInt(states));
            }
            else if (algorithm.indexOf('normal') >= 0){
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
