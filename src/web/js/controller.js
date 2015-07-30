"use strict";

var testData = {
    states: [
        {
            x: 5,
            y: 10,
            z: 3
        },
        {
            x: 3,
            y: 12,
            z: 6
        },
        {
            x: 2,
            y: 15,
            z: 7
        }
    ],
    links: [
        {
            name: "toString",
            source: 0,
            target: 1
        },
        {
            name: "equals",
            source: 1,
            target: 2
        }
    ],
}

var svg = d3.select("svg")
    .attr("width", 500)
    .attr("height", 500)
    .attr("transform", "translate(0,0)");

automata.viz.init(testData, svg);
