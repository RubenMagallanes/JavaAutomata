"use strict";

var url = 'test/TestProgram2Automata.json'

$.ajax({
  type: 'GET',
  url: url,
  contentType: 'application/json',
  success: function(data) {
	  console.log("success");
	  viz.automata.init(JSON.stringify(data));
	  },

//viz.automata.init(JSON.stringify(testData));
viz.petri.init(JSON.stringify(testSimpleMonkeyData));
	  error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
      }
});
