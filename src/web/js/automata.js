"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var states, // nodes bound to program states
        links, // links bound to state transitions
        varsChosen = [], // variables to be shown NOT USED YET
        funcsChosen = [], // functions to be shown NOT USED YET
        svg, // the svg element to draw automata on
        boundingDiv;

    // force layout
    var force = d3.layout.force()
        .charge(-8000)
        //.linkDistance(150)
        .gravity(0.1)
        .friction(0.8)
        .linkStrength(0.5);

    var colour = d3.scale.category20(),
        circleRad = 10;




    // for testing
    self.getLinks = function() { return links; }

    // initialise the layout with data
    // boundingdiv is a jquery selection
    self.init = function (dataStr, _boundingDiv){
        var data = JSON.parse(dataStr);

        states = data.states;
        links = data.links;
        boundingDiv = _boundingDiv;

        // creates GUI elements like sliders to change layout properties
        makeGUI(boundingDiv, force);

        var width = boundingDiv.width();
        var height = boundingDiv.height();
        svg = d3.select("#" + boundingDiv.attr("id")).append("svg")
            .attr("width", width)
            .attr("height", height);

    	force.nodes(states);
        force.links(links);
        force.size([width, height]);

        // //title
        // var title = svg.append("text")
        //    .attr("class", "title")
        //    .attr("dy", ".100em")
        //    .text("Automata")
        //    .attr("x",10)
        //    .attr("y",10);

        // var tracesText = svg.append("text")
        //    .attr("class", "title")
        //    .attr("dy", ".100em")
        //    .attr("x",1200)
        //    .attr("y",10)
        //    .attr("width",300)
        //    .attr("height",400)
        //    .html(getTraceList());

        var node = svg.selectAll(".state")
            .data(states)
             .enter().append("g")
            .attr("class", "state")
            .attr("id", function (d, i){
                return "state-" + i;
            })
            .on("mouseenter", selectState)
            .on("mouseout", deselectState);

        node.append("circle")
            .attr("class", "state-circle")
            .attr("id", function (d, i){
                return "state-circle-" + i;
            })
            .attr("r", circleRad)
            .style("fill", function (d, i){
                return colour(i);
            })

        // build the arrow.
        svg.append("defs")
             .append("marker")
            .attr("id", "end")
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", circleRad + 10)
            .attr("refY", 0)
            .attr("markerWidth", 6)
            .attr("markerHeight", 6)
            .attr("orient", "auto")
             .append("path")
            .attr("d", "M0,-5L10,0L0,5");

        var link = svg.selectAll(".link")
            .data(links)
             .enter().insert("g", ":first-child")
             .attr("class", "link");

        link.append("path")
            .attr("class", "line")
            .attr("id", function (d, i) { return "link-" + d.source + "-" + d.target; })
            .attr("marker-end", "url(#end)")
            .on("end");

        self.showMethodNames();

        force.on("tick", function (){
            node.attr("transform", transform)
            // curved links
            link.select(".line").attr("d", linkArc);
            // straight lines
            //var path = link.select(".line");
            //path.attr("d", function (d){
                //return "M" + d.source.x + "," + d.source.y + "L" + d.target.x + "," + d.target.y;
            //})
        });

        // updates a curved link
        function linkArc(d) {
            var dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y,
                dr = Math.sqrt(dx * dx + dy * dy);
                 if ( dx ===0 && dy===0 ){
                    var xRotation = 0;

                    // Make drx and dry different to get an ellipse instead of a circle.
                    var drx = 30;
                    var dry = 20;

                    return "M" + d.source.x + "," + d.source.y + "A" + drx + "," + dry + " " + xRotation + "," + 1 + "," + 0 + " " + (d.target.x + 1) + "," + (d.target.y + 1);
                }
                //return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + (d.source.x + (dx/2)) + "," + (d.source.y + (20))
                //+ "M" + (d.source.x + (dx/2)) + "," + (d.source.y + (20)) + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
                return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
        }

        function transform(d) {
            return "translate(" + d.x + "," + d.y + ")";
        }


        node.call(force.drag);
        force.start();
    }

    // adds all func and var names to funcsChosen and varsChosen respectively
    function setChosenNames(){
        links.forEach(function (d){
            funcsChosen.push(d.methodName);
        });

        // adds all variable names (that appear in states) to varsChosen
        states[0].fields.forEach(function (d){
            varsChosen.push(methodName);
        });
    }

    // update the layout data
    self.updateData = function (dataStr) {
        var data = JSON.parse(dataStr);
        states = data.states;
        links = data.links;

        force.nodes(states);
        force.links(links);

        // should start?
        force.start();
    };

    self.showMethodNames = function (){
        svg.selectAll(".link")
             .append("text")
            .style("text-anchor", "middle")
             .attr("dy", -5)
             .append("textPath")
            .attr("xlink:href", function(d, i) { return "#link-" + d.source + "-" + d.target; })
            .attr("class", "label")
            .attr("startOffset", "50%")
            .text(function (d) { return d.methodName; });
    };

    self.hideFunctionNames = function (){
        svg.selectAll(".label").remove();
    };

    function selectState(d){
        //d3.select(this).select("circle").transition()
            //.attr("r", circleRad*2)
            //.ease("cubic-out")
            //.duration(200);

        d3.select("#state-info")
            .attr("visibility", "visible")
            .html(function() { return stateInfo(d); });
    }

    // function getTraceList(){
    //     //call handler
    //     //list
    //     var list =["testApplication","BattleShips","TestApplication2"];

    //     var output = "Trace List<br>";
    //     for(var i=0; i<list.length; i++){
    //         output += list[i];
    //         output += "<br>";
    //     }
    //     console.log(output);
    //     return output;
    // }

    // return state info as a string
    function stateInfo(state){
        var str = "";
        state.fields.forEach(function (field) {
            str += field.name + ": " + field.value;
            str += "<br>"
        });
        return str;
    }

    function deselectState(d){
        //d3.select(this).select("circle").transition()
            //.attr("r", circleRad)
            //.ease("cubic-out")
            //.duration(200);

        d3.select("state-info").attr("visibiliy", "hidden");
    }

})(viz.automata = viz.automata || {});
