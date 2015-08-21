"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var groups = [], // nodes bound to field states
        methods = [], // boxes which represent methods
        links = [], // links bound to transitions between places and methods
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

        svg = d3.select("svg")
            .attr("width", 1200)
            .attr("height", 800);

        var places = [];
        groups.forEach(function(group){
            places.concat(group.places);
        });

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

        // make and add places to groups
        data.states.forEach(function (state, stateI){
            var places = [];
            groups.forEach(function (group){
                places.push([]);
            });

            state.fields.forEach(function (field, fieldI){
                var groupI = groups.indexOf(getGroup(field.name));

                // if field is different from last state, put it into a place
                if (fieldI === 0 || data.states[stateI][fieldI] !== field.value){
                    places[groupI].push({
                        name: field.name,
                        value: field.value
                    });
                }
            });

            groups.forEach(function (group, i){
                var place = places[i];
                if (place.length > 0){
                    group.places.push(place);
                }
            });
        });

        console.log(groups);
    }

    function getGroup(fieldName){
        var groupo;
        groups.forEach(function (group){
            if ($.inArray(fieldName, group.fieldNames)){
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

})(viz.petri = viz.petri || {})
