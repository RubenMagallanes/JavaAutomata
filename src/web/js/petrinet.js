"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var groups = [], // nodes bound to field states
        transitions = [], // boxes which represent methods
        arcs = [], // links bound to transitions between places and methods
        states = [], // all states, regardless of group
        svg; // the svg element to draw viz on

    // force layout
    var force = d3.layout.force()
        .charge(-2000)
        .size([1200, 800])
        .linkDistance(100)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10,
        transWidth = 10,
        transHeight = 10;

    self.getLinks = function() { return links; }

    self.setSvg = function(_svg){
        svg = _svg;
    }
    // initialise the layout with data
    self.init = function (dataStr){
        var data = JSON.parse(dataStr);
        convertToPetriData(data);

        svg = d3.select("svg")
            .attr("width", 1200)
            .attr("height", 800);

        var places = [];
        groups.forEach(function(group){
            places.concat(group.places);
        });

        var place = svg.selectAll(".place")
            .data(places)
             .enter().append("g")
            .attr("class", "place")
            .attr("id", function (d, i){
                return "place-" + i;
            });

        place.append("circle")
            .attr("class", "place-circle")
            .attr("id", function (d, i){
                return "place-circle-" + i;
            })
            .attr("r", circleRad)
            .style("fill", function (d, i){
                return colour(d.group);
            });

        var transition = svg.selectAll(".transition")
            .data(transitions)
             .enter().append("g")
            .attr("class", "transition")
            .attr("id", function (d,i){
                return "transition-" + i;
            });

        transition.append("rect")
            .attr("id", function (d, i){
                return "trans-rect-" + i;
            })
            .attr("width", transWidth)
            .attr("height", transHeight)
            .style("fill", function (d, i){
                return colour(d.group);
            });

        // build the arrowhead for lines.
        //svg.append("defs")
             //.append("marker")
            //.attr("id", "end")
            //.attr("viewBox", "0 -5 10 10")
            //.attr("refX", circleRad + 10)
            //.attr("refY", 0)
            //.attr("markerWidth", 6)
            //.attr("markerHeight", 6)
            //.attr("orient", "auto")
             //.append("path")
            //.attr("d", "M0,-5L10,0L0,5");

        //var arc = svg.selectAll(".arc")
            //.data(arcs)
            //// draw first
             //.enter().insert("g", ":first-child");

        //arc.attr("class", "arc")
             //.append("path")
            //.attr("class", "line")
            //.attr("id", function (d, i) { return "arc-" + d.source + "-" + d.target; })
            //.attr("marker-end", "url(#end)")
            //.on("end");

        //force.on("tick", function (){
            //node.attr("transform", transform)
            //link.select(".line").attr("d", linkArc);
        //});

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

        //node.call(force.drag);
        //force.start();
    }

    // update the layout data
    self.updateData = function (data) {
        convertToPetriData(data);

        force.nodes(node);
        force.links(links);

        // should start?
        force.start();
    };

    function convertToPetriData(data){
        var fields = data.states[0].fields;

        // initialise groups, one per field
        fields.forEach(function (field, i){
            groups.push({
                fieldNames: [field.name],
                places: [],
                colour: colour(i)
            });
        });

        // copying states to local variable
        states = data.states;

        // give each a place a link back to the state
        states.forEach(function (place,i){
            place.id = i;
        });

        // make and add places to groups
        groups.forEach(function (group, groupI){
            states.forEach(function (state, stateI){
                if (stateAffectsGroup(group, states, stateI)){
                    group.places.push({
                        group: groupI, // backlink to group
                        state: stateI, // backlink to state
                        fields: getFieldChanges(group, states, stateI)
                    });
                }
            });
        });

        console.log("groups", groups);

        // set up transitions
        data.links.forEach(function (method){
            var before = data.states[method.source];
            var after = data.states[method.target];

            var placesInvolved = [];

            transitions.push({
                name: method.methodName,
                fromPlaces: findPlaces(before),
                toPlaces: findPlaces(after)
            });
        });

        console.log("transitions", transitions);
    }

    function stateAffectsGroup(group, states, stateI){
        if (stateI === 0) return true;

        var state = states[stateI];
        var last = states[stateI-1];

        var affects = false;

        state.fields.forEach(function (field, i){
            if (fieldInGroup(field, group)){
                if (field.value !== last.fields[i].value){
                    affects = true;
                }
            }
        });
        return affects;
    }

    function getFieldChanges(group, states, stateI){
        if (stateI === 0){
            var state = states[stateI];
            var changes = [];
            state.fields.forEach(function (field){
                if (fieldInGroup(field, group)){
                    changes.push(field);
                };
            });
            return changes;
        }
        var state = states[stateI];
        var last = states[stateI-1];

        var changes = [];

        state.fields.forEach(function (field, fieldI){
            if (fieldInGroup(field, group)){
                if (field.value !== last.fields[fieldI].value){
                    changes.push(field);
                }
            }
        });
        return changes;
    }

    function findPlaces(state){
        var stateIndex = states.indexOf(state);
        var places = [];
        groups.forEach(function (group, groupIndex){
            group.places.forEach(function (place, placeIndex){
                if (place.state === stateIndex){
                    places.push({
                        groupIndex: groupIndex,
                        placeIndex: placeIndex
                    });
                }
            });
        });
        return places;
    }

    function getGroupWithField(field){
        var groupo;
        groups.forEach(function (group){
            if (fieldInGroup(field, group)){
                groupo = group;
            }
        });
        return groupo;
    }

    function selectPlace(d){
        d3.select("#state-info")
            .attr("visibility", "visible")
            .html(function() { return stateInfo(d); });
    }

    function fieldInGroup(field, group){
        // look at this if condition. Wtf right?
        return $.inArray(field.name, group.fieldNames) !== -1;
    }

})(viz.petri = viz.petri || {})
