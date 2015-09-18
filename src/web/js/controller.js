"use strict";
$.ajax({
  type: 'GET',
  url: 'test/TestProgram2Automata.json',
  contentType: 'application/json',
  success: function(data) { 
	  console.log("success");
	  viz.automata.init(JSON.stringify(data));
	  },  

	  error: function(XMLHttpRequest, textStatus, errorThrown) { 
          console.log("Status: " + textStatus); 
          console.log("Error: " + errorThrown); 
      }  
});