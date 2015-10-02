"use strict";
var host = window.location.href;
var trace_file_list;

var selection = d3.select("#files")
	.append("table")
	.attr("class", "responstable");

var header = selection
	.append("tr");
//Gets the list of files form the server and 
//makes a table containing them.
$.ajax({
  type: 'GET',
  url: (host + "ListTraceFiles"),
  contentType: 'text/fileList',
  success: function(data) {
	  console.log("success");
	  	trace_file_list = data.split("\n");
	  	var strings = data.split("\n");
	  	for (var i = strings.length - 2; i >= 0; i--) {
	  		var row = selection
				.append("tr");
	  		//strings[i];
	  		row.append("th")
				.text(strings[i]);

			row.append("th")
				.append("input")
				.attr("value", strings[i])
				.attr("type","radio")
				.attr("class", "file_radios")
				.attr("name","file_selection")
				.attr("id",strings[i]);
				//.text(" ");
	  	};
	  },

	  error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
      }
});

//Sets up the onclick function for the buttons 
d3.select("#automata")
	.on("click", function(d,i){
		for (var i = trace_file_list.length - 2; i >= 0; i--) {
			if (document.getElementById(trace_file_list[i]).checked) {
				var url = window.location.href;
  				window.open(url+"automata="+document.getElementById(trace_file_list[i]).value);
			}
		}
	});

d3.select("#petri_net")
	.on("click", function(d,i){
		for (var i = trace_file_list.length - 2; i >= 0; i--) {
			if (document.getElementById(trace_file_list[i]).checked) {
				var url = window.location.href;
  				window.open(url+"petri_net="+document.getElementById(trace_file_list[i]).value);
			}
		}
	});

d3.select("#both")
	.on("click", function(d,i){
		for (var i = trace_file_list.length - 2; i >= 0; i--) {
			if (document.getElementById(trace_file_list[i]).checked) {
				var url = window.location.href;
  				window.open(url+"automata&petri_net="+document.getElementById(trace_file_list[i]).value);
			}
		}
	});

//Sets up the header for the table.
header.append("th")
	.attr("width", "75%")
	.text("File Name");

header.append("th")
	.attr("width", "25%")
	.text("Selection");

