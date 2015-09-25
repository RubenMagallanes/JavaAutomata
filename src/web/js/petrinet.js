"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var groups = [], // field groups (contains places)
        transitions = [], // boxes which represent methods
        nodes = [], // all transitions and places (for force layout)
        arcs = [], // arcs bound to transitions between places and methods
        states = [], // all states, regardless of group
        svg; // the svg element to draw viz on

    // force layout
    var force = d3.layout.force()
        .charge(-2000)
        .size([1200, 800])
        //.linkDistance(100)
        .gravity(0.1);

    var colour = d3.scale.category20(),
        circleRad = 10,
        transWidth = 10,
        transHeight = 10;

    self.getLinks = function() { return arcs; }

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
            places = places.concat(group.places);
        });

        console.log(places);

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
            .attr("x", -transWidth/2)
            .attr("y", -transHeight/2)
            .attr("width", transWidth)
            .attr("height", transHeight)
            .style("fill", function (d, i){
                return colour(d.group);
            });

        transition.append("text")
            .text(function (d){
                return d.name;
            })
            //.style("alignment-baseline", "auto")
            .attr("x", 10);

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

        // arcs between transitions and places
        var arc = svg.selectAll(".arc")
            .data(arcs)
            // draw first
             .enter().insert("g", ":first-child")
             .attr("class", "arc");

        arc.append("path")
            .attr("class", "line")
            .attr("id", function (d, i) { return "arc-" + d.source + "-" + d.target; })
            .attr("marker-end", "url(#end)")
            .on("end");

        console.log("nodes", nodes);
        console.log("arcs", arcs);
        force.nodes(nodes);
        force.links(arcs);

        force.on("tick", function (){
            place.attr("transform", transform)
            transition.attr("transform", transform)
            arc.select(".line").attr("d", arcArc);
        });

        // updates a curved link
        function arcArc(d) {

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

        place.call(force.drag);
        transition.call(force.drag);
        force.start();
    }

    // update the layout data
    //self.updateData = function (data) {
        //convertToPetriData(data);

        //force.nodes(node);
        //force.links(arcs);

        //// should start?
        //force.start();
    //};

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

        //console.log("groups", groups);

        // set up transitions
        data.links.forEach(function (method){
            var before = data.states[method.source];
            var after = data.states[method.target];
            var places = placesAffected(before, after);
            transitions.push({
                name: method.methodName,
                fromPlaces: places.before,
                toPlaces: places.after
            });
        });

        // put all places and transitions into shared nodes collection (for
        // force layout)
        groups.forEach(function (group){
            nodes = nodes.concat(group.places);
        });
        nodes = nodes.concat(transitions);

        // make arcs (between transitions and places)
        nodes.filter(function(node, nodeIndex){
            return isTransition(node);
        }).forEach(function (trans){
            var transIndex = nodes.indexOf(trans);
            // create links between transition and from places
            trans.fromPlaces.forEach(function (fromPlace){
                arcs.push({
                    source: getPlaceIndex(fromPlace, nodes),
                    target: transIndex, // transition
                });
            });
            // create links between transition and to places
            trans.toPlaces.forEach(function (toPlace){
                arcs.push({
                    source: transIndex, // transition
                    target: getPlaceIndex(toPlace, nodes)
                });
            });
        });
    }

    function placesAffected(stateBefore, stateAfter){
        var fieldsChanged = getFieldsChanged(stateBefore, stateAfter);
        var places = {
            before: [],
            after: []
        };
        // get groups which are affected by these state changes
        var relevantGroups = groups.filter(function(group){
            // if any relevant fields are changed
            return group.fieldNames.some(function (fieldName){
                return $.inArray(fieldName, fieldsChanged) !== -1;
            });
        });
        relevantGroups.forEach(function (group){
            // before
            group.places.filter(function(place){
                // only places that have the same values as stateAfter
                return place.fields.every(function(field, i){
                    return field.value === getFieldWithName(field.name, stateBefore.fields).value;
                });
            }).forEach( function (place){
                places.before.push({
                    groupIndex: groups.indexOf(group),
                    placeIndex: group.places.indexOf(place)
                });
            });
            // after
            group.places.filter(function(place){
                // only places that have the same values as stateAfter
                return place.fields.every(function(field, i){
                    return field.value === getFieldWithName(field.name, stateAfter.fields).value;
                });
            }).forEach( function (place, placeIndex){
                places.after.push({
                    groupIndex: groups.indexOf(group),
                    placeIndex: group.places.indexOf(place)
                });
            });
        });
        return places;
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

    function getFieldsChanged(stateBefore, stateAfter){
        var fields = [];
        for (var i = 0; i < stateBefore.fields.length; i++){
            if (stateBefore.fields[i].value !== stateAfter.fields[i].value){
                fields.push(stateBefore.fields[i].name);
            }
        }
        return fields;
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

    function getFieldWithName(name, fields){
        var found = null;
        fields.forEach(function (field){
            if (field.name == name){
                found = field;
            }
        });
        return found;
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

    // gross
    function isTransition(node){
        return node.fromPlaces !== undefined && node.toPlaces !== undefined;
    }

    // gross
    function getPlaceIndex(placeInfo, array){
        var place = groups[placeInfo.groupIndex].places[placeInfo.placeIndex];
        var index = array.indexOf(place);
        return index;
    }

})(viz.petri = viz.petri || {})
