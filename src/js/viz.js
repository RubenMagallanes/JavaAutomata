"use strict"
(function (self){

    var states,
        links;

    var force = d3.force()
        .charge(-50)
        .size(500, 500);

    self.init(data, svg){
        states = data.states;
        links = data.links;

    	force.nodes(states);
        force.links(links);

        var colour = d3.scale.catagory20();

        var node = svg.selectAll(".state")
            .
    }

    self.update(data){

    }
})(automata.viz = automata.viz || {})

