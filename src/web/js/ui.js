var host = window.location.href;
var temp;

var selection = d3.select("#files")
	.append("table")
	.attr("class", "responstable");

var header = selection
	.append("tr");

$.ajax({
  type: 'GET',
  url: (host + "ListTraceFiles"),
  contentType: 'text/fileList',
  success: function(data) {
	  console.log("success");
	  	temp = data.split("\n");
	  	var strings = data.split("\n");
	  	for (var i = strings.length - 2; i >= 0; i--) {
	  		var row = selection
				.append("tr");
	  		//strings[i];
	  		row.append("th")
				.text(strings[i]);

			row.append("th")
				.text("");

			row.append("th")
				.text("");

			row.append("th")
				.text("");
	  	};
	  },

	  error: function(XMLHttpRequest, textStatus, errorThrown) {
          console.log("Status: " + textStatus);
          console.log("Error: " + errorThrown);
      }
});

header.append("th")
	.text("File Name");

header.append("th")
	.text("Automata");

header.append("th")
	.text("Petri Net");

header.append("th")
	.text("Automata & Petri Net");
