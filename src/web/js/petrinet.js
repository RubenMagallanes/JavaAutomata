"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var places, // nodes bound to field states
        methods, // boxes which represent methods
        links, // links bound to transitions between places and methods
        svg; // the svg element to draw viz on

    // force layout
    var force = d3.layout.force()
        .charge(-2000)
        .size([1200, 800])
        .linkDistance(100)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10;

    self.getLinks = function() { return links; }

    self.setSvg = function(_svg){
        svg = _svg;
    }
    // initialise the layout with data
    self.init = function (dataStr){
        var data = JSON.parse(dataStr);
        convertToPetriData(data);

        var node = svg.selectAll(".place")
            .data(places)
             .enter().append("g")
            .attr("class", "place")
            .attr("id", function (d, i){
                return "place-" + i;
            });

        node.append("circle")
            .attr("class", "place-circle")
            .attr("id", function (d, i){
                return "place-circle-" + i;
            })
            .attr("r", circleRad)
            .style("fill", function (d, i){
                return colour(i);
            })

        // build the arrowhead for lines.
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

        force.on("tick", function (){
            node.attr("transform", transform)
            link.select(".line").attr("d", linkArc);
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

    function convertToPetriData(data){
        places = [];
        var fields = [];

        data.links.forEach(function (link) {

        });
    }

    // update the layout data
    self.updateData = function (data) {
        convertToPetriData(data);

        force.nodes(places);
        force.links(links);

        // should start?
        force.start();
    };

    function selectPlace(d){
        d3.select("#state-info")
            .attr("visibility", "visible")
            .html(function() { return stateInfo(d); });
    }

})(viz.petri = viz.petri || {})
