"use strict";

var url = 'test/TestProgram2Automata.json'

var testSimpleMonkeyData = {
    states: [
        {
            fields: [
                {
                    name: "monkey",
                    value: "tim"
                },
                {
                    name: "chimp",
                    value: "rodger"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "tom"
                },
                {
                    name: "chimp",
                    value: "rodger"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "tom"
                },
                {
                    name: "chimp",
                    value: "randy"
                }
            ]
        },
        {
            fields: [
                {
                    name: "monkey",
                    value: "timaline"
                },
                {
                    name: "chimp",
                    value: "rachael"
                }
            ]
        }
    ],
    links: [
        {
            methodName: "monkeyChange",
            source: 0,
            target: 1
        },
        {
            methodName: "chimpChange",
            source: 1,
            target: 2
        },
        {
            methodName: "bothChange",
            source: 2,
            target: 3
        }
    ]
}

// get automata data from server
// and use data to start viz
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

// for testing without a server
//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
//viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
