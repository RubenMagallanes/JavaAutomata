"use strict";
// based on d3 force layout example:
// http://bl.ocks.org/mbostock/1153292
(function (self){

    var states, // nodes bound to program states
        links, // links bound to state transitions
        varsChosen = [], // variables to be shown NOT USED YET
        funcsChosen = [], // functions to be shown NOT USED YET
        svg, // the svg element to draw automata on
        currentState, // state hovering over
        text, //text for all links
        //showAllLinkText, //toggle the link text
        boundingDiv;
	
	

    // force layout
    var force = d3.layout.force()
        .charge(-8000)
        //.linkDistance(150)
        .gravity(1)
        .friction(0.5)
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
        currentState = data.states[0];

        // creates GUI elements like sliders to change layout properties
        makeGUI(boundingDiv, force);
		//size bounding div 
		
		/*top:220px;
	left: 0;
	position: absolute;
	width: 100%;*/
		
		//create svg & size
       	var width = boundingDiv.width();
		var height = screen.availHeight -300;
	
		
		//var rect = boundingDiv.getBoundingClientRect();
		//console.log(rect.top, rect.right, rect.bottom, rect.left);
		//conpute height frmo bounding div x
       // var height = boundingDiv.height();
		
        svg = d3.select("#" + boundingDiv.attr("id")).append("svg")
            .attr("width", width)
            .attr("height", height);

    	force.nodes(states);
        force.links(links);
        force.size([width, height]);

        var node = svg.selectAll(".state")
            .data(states)
             .enter().append("g")
            .attr("class", "state")
            .attr("id", function (d, i){
                return "state-" + i;
            })
            .on("mouseenter", selectState)
            .on("mouseout", deselectState)		
			
		/*   force.charge( TODO make this on click
            function(d2,i){
                if(d2!=d1)
                    return force.charge;//calculate your charge for other nodes
                else
                    return force.charge  - 500;//calculate your charge for the clicked node
            });

        force.start();*/
			//.attr("border",border);

	
		//border around svg mainly for testing
           	/*var borderPath = svg.append("rect")
       			.attr("x", 0)
       			.attr("y", 0)
       			.attr("height", height)
       			.attr("width", width)
       			.style("stroke", "grey")
       			.style("fill", "none")
       			.style("stroke-width", 5);*/
			
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
            .attr("stroke","red")
            .attr("fill","red")
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

			node.attr("cx", function(d) { return d.x = Math.max(15, Math.min(width - 15, d.x)); })
    			.attr("cy", function(d) { return d.y = Math.max(15, Math.min(height - 15, d.y)); });
			
            node.attr("transform", transform);
            // curved links

            link.select(".line").attr("d", linkArc);
            // straight lines
            // var path = link.select(".line");
            // path.attr("d", function (d){
            //     var dx = d.target.x - d.source.x,
            //         dy = d.target.y - d.source.y,
            //         dr = Math.sqrt(dx * dx + dy * dy);
            //      if ( dx ===0 && dy===0 ){
            //         var xRotation = 0;

            //         // Make drx and dry different to get an ellipse instead of a circle.
            //         var drx = 30;
            //         var dry = 20;

            //         return "M" + d.source.x + "," + d.source.y + "A" + drx + "," + dry + " " + xRotation + "," + 1 + "," + 0 + " " + (d.target.x + 1) + "," + (d.target.y + 1);
            //     }
            //     return "M" + d.source.x + "," + d.source.y + "L" + d.target.x + "," + d.target.y;
            // })
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
       text= svg.selectAll(".link")
             .append("text")
            .style("text-anchor", "middle")
             .attr("dy", -5)
             .append("textPath")
            .attr("xlink:href", function(d, i) { return "#link-" + d.source + "-" + d.target; })
            .attr("class", "label")
            .attr("startOffset", "50%")
            .text(function (d) { 
                if(showAllLinkText){
                    return d.methodName;                    
                }
                else{
                    if(currentState.id === d.source || currentState.id === d.target)
                        return d.methodName; 
                    else 
                        return "";
                }
                    

            });
    };

    self.hideFunctionNames = function (){
        svg.selectAll(".label").remove();
    };
	
	
    function selectState(d){
        //d3.select(this).select("circle").transition()
            //.attr("r", circleRad*2)
            //.ease("cubic-out")
            //.duration(200);
        currentState = d;
		
 
	
		
		
        console.log(currentState);
        d3.select("#state-info")
            .attr("visibility", "visible")
            .html(function() { return stateInfo(d); });

        //changes the text on hover
       text.text(function (d) { 
                console.log(d.source);
                if(showAllLinkText){
                    return d.methodName;                    
                }
                else{
                    if(currentState.id === d.source.id || currentState.id === d.target.id)
                        return d.methodName; 
                    else 
                        return "";
                }
            });
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
    // d3.select("button")
    //     .on("click", clicked);
    // function clicked() {
    //     showAllLinkText = !showAllLinkText;
    //     text.text(function (d) { 
    //         console.log(d.source);
    //         if(showAllLinkText){
    //             return d.methodName;                    
    //         }
    //         else{
    //             if(currentState.id === d.source.id || currentState.id === d.target.id)
    //                 return d.methodName; 
    //             else 
    //                 return "";
    //         }
    //     });
        
    // }

})(viz.automata = viz.automata || {});
