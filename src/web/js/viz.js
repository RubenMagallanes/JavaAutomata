"use strict";

// http://bl.ocks.org/mbostock/1153292
(function (self){

    var states, // nodes bound to program states
        links, // links bound to state transitions
        varsChosen = [], // variables to be shown
        funcsChosen = [], // functions to be shown
        svg; // the svg element to draw viz on

    // force layout
    var force = d3.layout.force()
        .charge(-300)
        .size([500, 500])
        .linkDistance(80)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10;

    self.getLinks = function() { return links; }

    // initialise the layout with data
    self.init = function (data, _svg){
        states = data.states;
        links = data.links;
        svg = _svg;

        setChosenNames();

    	force.nodes(states);
        force.links(links);

        var node = svg.selectAll(".state")
            .data(states)
             .enter().append("g")
            .attr("class", "state")
            .attr("id", function (d, i){
                return "state-" + i;
            });

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
            .attr("id", function (d) { return "link-line-" + d.name; })
            .attr("marker-end", "url(#end)");

        force.on("tick", function (){
            node.attr("transform", transform)
            link.select(".line").attr("d", linkArc);
            //console.log(link.selectAll("path"));
        });

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
            funcsChosen.push(d.name);
        });

        // adds all variable names (that appear in states) to varsChosen
        for (var name in states[0]){
            varsChosen.push(name);
        }
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

    self.showFunctionNames = function (){
        //svg.select("defs")
            //.selectAll(".func-name")
            //.data(funcsChosen)
          //.enter().append("marker")
            //.attr("id", function (d){ return d; })
            //.attr("class", "func-name")
            //.attr("marker-width", 20)
            //.attr("marker-height", 20)
            //.attr("viewBox", "0 -10 20 20")
            //.attr("orient", "auto")
          //.append("text")
            //.text(function (d){ return d + "()"; });

        //svg.selectAll(".link")
            //.attr("marker-mid", function(d){
                ////return "url(#" + d.name + ")";
                //return "url(#end)";
            //});

        svg.selectAll(".link")
             .append("text")
            .style("text-anchor", "middle")
             .attr("dy", 10)
             .append("textPath")
            .attr("xlink:href", function(d) { return "#" + "link-line-" + d.name; })
            .attr("startOffset", "50%")
            .text(function (d) { return d.name; });
    };

    self.hideFunctionNames = function (){
        svg.selectAll(".func-name").remove();
        svg.selectAll(".link").attr("marker-mid", "");
    };

})(automata.viz = automata.viz || {})
