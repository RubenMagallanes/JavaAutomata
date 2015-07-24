"use strict";
(function (self){

    var states,
        links;

    var force = d3.layout.force()
        .charge(-300)
        .size([500, 500])
        .linkDistance(80)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10;

    self.init = function (data, svg){
        states = data.states;
        links = data.links;

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

        var link = svg.selectAll(".link")
            .data(links)
          .enter().append("line")
            .attr("class", "link")
            .style("stroke", function (d){
                //return colour(d.source)
                return "black"
            });

            force.on("tick", function (){
                node.attr("transform", function (d){
                    return "translate(" + d.x + ", " + d.y + ")";
                });
                link.attr({
                    x1: function (d){ return d.source.x; },
                    y1: function (d){ return d.source.y; },
                    x2: function (d){ return d.target.x; },
                    y2: function (d){ return d.target.y; }
                });
            })

            console.log(data);

            node.call(force.drag);
            force.start();
    }

    self.update = function (data){
        states = data.states;
        links = data.links;
    }
})(automata.viz = automata.viz || {})
