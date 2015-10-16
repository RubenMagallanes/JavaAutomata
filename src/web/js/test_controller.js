"use strict";

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

//viz.automata.init(JSON.stringify(testSimpleMonkeyData), $("div#automata"));
viz.petri.init(JSON.stringify(testSimpleMonkeyData), $("div#petrinet"));
