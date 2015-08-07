"use strict";

// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var states, // nodes bound to program states
        links, // links bound to state transitions
        varsChosen = [], // variables to be shown
        funcsChosen = [], // functions to be shown
        svg; // the svg element to draw viz on

    // force layout
    var force = d3.layout.force()
        .charge(-1000)
        .size([1200, 800])
        .linkDistance(200)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10;

    self.getLinks = function() { return links; }

    // initialise the layout with data
    self.init = function (data, _svg){
        states = data.states;
        links = data.links;
        svg = _svg;

        //setChosenNames();

    	force.nodes(states);
        force.links(links);

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
             .enter().insert("g", ":first-child");

        link.attr("class", "link")
             .append("path")
            .attr("class", "line")
            .attr("id", function (d, i) { return "link-" + d.source + "-" + d.target; })
            .attr("marker-end", "url(#end)")
            .on("end");

        //setTimeout(self.showMethodNames, 10);
        self.showMethodNames();

        force.on("tick", function (){
            node.attr("transform", transform)
            link.select(".line").attr("d", linkArc);
        });

        //force.linkDistance(100);

        // updates a curved link
        function linkArc(d) {
            var dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y,
                dr = Math.sqrt(dx * dx + dy * dy);
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
    self.updateData = function (data) {
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
             .attr("dy", 4)
             .append("textPath")
            .attr("xlink:href", function(d, i) { console.log(d); return "#link-" + d.source + "-" + d.target; })
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

})(automata.viz = automata.viz || {})
