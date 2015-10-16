"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var groups = [], // field groups (contains places)
        transitions = [], // boxes which represent methods
        nodes = [], // all transitions and places (for force layout)
        arcs = [], // arcs bound to transitions between places and methods
        states = [], // all states, regardless of group
        svg,// the svg element to draw viz on
        boundingDiv; // the div that encapsulates svg

    // force layout
    var force = d3.layout.force()
        .charge(-2000)
        .gravity(0.1)
        .friction(0.8); // default

    var colour = d3.scale.category20(),
        circleRad = 10,
        transWidth = 10,
        transHeight = 10;

    self.getLinks = function() { return arcs; }

    self.setSvg = function(_svg){
        svg = _svg;
    }

    // initialise the layout with data and force layout properties
    self.init = function (dataStr, _boundingDiv){

        var data = JSON.parse(dataStr);
        convertToPetriData(data);

        boundingDiv = _boundingDiv;

        // creates GUI elements like sliders to change layout properties
        makeGUI(boundingDiv, force);

        var width = boundingDiv.width();
        //var height = boundingDiv.height();
		var height = screen.availHeight -300;

        svg = d3.select("#" + boundingDiv.attr("id")).append("svg")
            .attr("width", width)
            .attr("height", height);

        force.size([width, height]);

        var places = [];
        groups.forEach(function(group){
            places = places.concat(group.places);
        });

        var place = svg.selectAll(".place")
            .data(places)
             .enter().append("g")
            .attr("class", "place")
            .attr("id", function (d){
                return "place-" + d.group + "," + d.state;
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
            .style("alignment-baseline", "middle")
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

        force.nodes(nodes);
        force.links(arcs);

        force.on("tick", function (){
			place.attr("cx", function(d) { return d.x = Math.max(15, Math.min(width - 15, d.x)); })
    			.attr("cy", function(d) { return d.y = Math.max(15, Math.min(height - 15, d.y)); });

			transition.attr("cx", function(d) { return d.x = Math.max(15, Math.min(width - 15, d.x)); })
    			.attr("cy", function(d) { return d.y = Math.max(15, Math.min(height - 15, d.y)); });

            place.attr("transform", transform)
            transition.attr("transform", transform)
            arc.select(".line").attr("d", arcArc);


        });

        // updates a curved arc (arcs the arc)
        function arcArc(d) {
            var dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y,
                dr = Math.sqrt(dx * dx + dy * dy);
                return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
        }

        function transform(d) {
            return "translate(" + d.x + "," + d.y + ")";
        }

        place.call(force.drag);
        transition.call(force.drag);
        force.start();
    }

    // ---------------------------
    // Important function: most other smaller functions are children of this
    // converts (states, transitions) to (groups(places), transitions)
    function convertToPetriData(data){
        var fields = getAllFields(data.states);

        // initialise groups, one per field
        fields.forEach(function (field, i){
            groups.push({
                fieldNames: [field.name],
                places: [],
                colour: colour(i)
            });
        });

        // copying states to global variable
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

        // set up transitions
        data.links.forEach(function (method){
            var before = data.states[method.source];
            var after = data.states[method.target];
            var placesTrans;
            if (before === after){
                var statePlaces = getStateInfoFromPlaces(method.source, getPlaces(groups));
                //console.log("statePlaces", statePlaces);
                placesTrans = { before: statePlaces, after: statePlaces };
            }
            else {
                placesTrans = placesAffected(before, after);
            }

            //console.log("placesTrans", placesTrans);

            transitions.push({
                name: method.methodName,
                fromPlaces: placesTrans.before,
                toPlaces: placesTrans.after
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

        //console.log("groups", groups);
        //console.log("transitions", transitions);
        //console.log("nodes", nodes);
        //console.log("arcs", arcs);
    }

    // returns places changed between stateBefore and stateAfter
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

    // returns whether the state changes any fields in group
    function stateAffectsGroup(group, states, stateI){
        if (stateI === 0) return true;

        var state = states[stateI];
        var last = states[stateI-1];

        var affects = false;

        state.fields.forEach(function (field, i){
            if (fieldInGroup(field, group)){
                // if last state has the field
                if ($.inArray(field.name, last.fields.map(function (field){ return field.name; })) !== -1){
                    // if the field value has changed
                    if (field.value !== last.fields[i].value){
                        affects = true;
                    }
                }
            }
        });
        return affects;
    }

    // returns fields that are changed by state
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

    // returns all fields changed between stateBefore and stateAfter
    function getFieldsChanged(stateBefore, stateAfter){
        var fields = [];
        for (var i = 0; i < stateBefore.fields.length; i++){
            if (stateBefore.fields[i].value !== stateAfter.fields[i].value){
                fields.push(stateBefore.fields[i].name);
            }
        }
        return fields;
    }

    // returns the group that has field
    function getGroupWithField(field){
        var groupo;
        groups.forEach(function (group){
            if (fieldInGroup(field, group)){
                groupo = group;
            }
        });
        return groupo;
    }

    // returns a field with name
    function getFieldWithName(name, fields){
        var found = null;
        fields.forEach(function (field){
            if (field.name == name){
                found = field;
            }
        });
        return found;
    }

    // causes place to be selected
    function selectPlace(d){
        d3.select("#state-info")
            .attr("visibility", "visible")
            .html(function() { return stateInfo(d); });
    }

    // if field is part of group
    function fieldInGroup(field, group){
        // look at this method. Wtf right?
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

    // gross. like below method, but returns different representation of place
    function getStateInfoFromPlaces(stateI, places){
        return places.filter(function (place){
            return place.state === stateI;
        }).map(function (place){
            return {
                groupIndex: place.group,
                placeIndex: groups[place.group].places.indexOf(place)
            }
        });
    }

    // gets places that originated from states[stateI]
    function getStatePlaces(stateI, places){
        return places.filter(function (place){
            return place.state === stateI;
        });
    }

    // gets all places
    function getPlaces(groups){
        var allPlaces = [];
        groups.forEach(function (group){
            allPlaces = allPlaces.concat(group.places);
        });
        return allPlaces;
    }

    // gets all fields that exist in the trace
    function getAllFields(states){
        var fields = [];
        console.log("states", states);
        states.forEach(function(state){
            state.fields.forEach(function (field){
                if ($.inArray(field.name, fields.map(function(field){ return field.name; })) === -1){
                    fields.push(field);
                }
            });
        });
        console.log("fields", fields);
        return fields;
    }

    // unfinished
    self.addMouseEnterListener = function(listener){
        d3.selectAll(".place").on("mouseenter.extra", function(){
            console.log("hello?");
            var id = d3.select(this).attr("id");
            var state = +id.substring(id.indexOf(",")+1);
            listener(state);
        });
    }

    // unfinished
    self.addMouseOutListener = function(listener){
        d3.selectAll(".place").on("mouseout.extra", function(){
            var id = d3.select(this).attr("id");
            var state = +id.substring(id.indexOf(",")+1);
            listener(state);
        });
    }

    // selects all nodes that origined from states[stateI]
    self.selectNodes = function(stateI){
        var allPlaces = getPlaces(groups);
        var places = getStatePlaces(stateI, allPlaces);

        places.forEach(function(place){
            var placeI = allPlaces.indexOf(place);
            svg.select("#place-circle-" + placeI)
                .transition()
                .duration(200)
                .ease("cubic-out")
                .attr("r", circleRad * 2);
        });
    };

    // selects all nodes that origined from states[stateI]
    self.deselectNodes = function(stateI){
        var places = getStatePlaces(stateI, getPlaces(groups));
        var allPlaces = getPlaces(groups);

        places.forEach(function(place){
            var placeI = allPlaces.indexOf(place);
            svg.select("#place-circle-" + placeI)
                .transition()
                .ease("cubic-out")
                .duration(200)
                .attr("r", circleRad);
        });
    }

})(viz.petri = viz.petri || {});
