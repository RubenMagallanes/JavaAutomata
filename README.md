# JavaAutomata

Developed By Automateam:
Nicky van Hulst
David Sheridan 
Shane Brewer 
Will Hardwick-Smith
Chris Chin
Ruben Magallnanas

This program takes a Java program trace output as input and outputs a visualisation of the program as an automata. 

<b>Developer Guide</b>

<b>Tracer Package</b> 

<b>Running a trace</b>
To run a trace a new TraceLauncher object needs to be created and the path of the jar you want to trace needs to be passed in this is all you need to be able to call the run method which will trace the program. 

<b>Parameters</b>
Passing parameters to the program. You can pass in the parameters to the program to be traced by calling the set parameters method and passing in a string representing the parameters.

<b>Filtering The Trace</b>
filtering, to set the filter call the setfilter method with a filter to use if this is left null it will trace all the methods and fields of the traced program.

example 

    public static void main(String[] args){
        TraceLauncher tr = new TraceLauncher("JarPathName");
        tr.setCommandLineArguments("Arguments”);
        Trace t = tr.run();
    }

<b>TraceManager</b> 

Tracemanager can be used to further filter a trace it makes a copy of it upon receiving the trace and a filter can be applied to the copy by calling apply filter. 

<b>TraceFilterManager</b>

This can be used to create a filter by adding methods or fields to it using addMethodsToFilter and addFieldToFilter.  ONce all the methods and fields you want to add are added you can use getFilter to get the custom filter. 



<b>Trace File Format</b>
methodObject:

{<p>
      “methodName”: [\<method name\>],<p>
      “stateBefore”: [fieldObject, … ,fieldObject],<p>
      “stateAfter”: [fieldObject, … ,fieldObject],<p>
      “startState”: [\<true\>/\<false\>],<p>
      “children”: [methodObject, … ,methodObject]<p>
}<p>

fieldObject:

{<p>
      “name”: field name,<p>
      “value”: field value<p>
}<p>

The trace files are formatted in JSON and contains a single methodObject. This methodObject is the main method that is called from the Java program being run. For any methods called within a method, they get listed in the children field as listed above. This means that any methods called from main are listed in the main methodObject, and any methods called within that method are listed in that methodObject’s children field and so on.

The stateBefore and stateAfter fields represent the state of the program before and after that method has been executed. these fields are made up of a list of fieldObjects as listed above, with each field having a name and value (as they would within a Java program).

Note that the methodName and startState are considered arrays in the format is due to the JSON library used in the TraceToJSON class.

<b>From Program to Automata Visualistation (After the trace has been run):</b>

TraceToJSON: Converts the trace tree into a trace file
TraceToAutomata: Converts a trace file into an automata
AutomataToVisualistation: converts an automata into the JSON format used to visualise automata.

This process is hidden behind calls within the tool. So saving a trace saves the trace tree to a trace file. Then requesting a visualisation from the web page loads a trace file, converts it into an automata and then converts that automata into the JSON format for visualising.



<b>State Package</b>

Not much was done with the classes in the state package other than encapsulating them and documenting them with how we think they work. They are generated in a trace but it was not clear how they were created. From general observation we could see that they were generating correctly and didn’t want to mess with them too much. 

<b>Server Package - API included</b>
<b>API:</b>
Requesting a JSON Visualisation.

\<server url\>:\<port number\>\\TraceRequest\\\<requested file\>

\<requested file\> - should include .trace extension.

\<server url\> - localhost when run on you computer.

\<port number\> - is currently set to 8080

Requesting the list of trace files.

\<server url\>:\<port number\>\\ListTraceFiles

\<server url\> - localhost when run on you computer.

\<port number\> - is currently set to 8080

Returns a list of files to that are in the data/traces.

<b>Requesting a view</b> .

\<server url\>:\<port number\>\/\<option\>=\<file name\>?\<algorithm\> 

\<options\>  -  automata&petri_net \| automata \| petri_net

\<file name\> - file from the file list.

\<algorithm\> - normal \| ktails - can be extended on algorithm add in.

\<server url\> - localhost when run on you computer.

\<port number\> - is currently set to 8080

<b>Server Package</b>

Comments can be found in the main.server classes.

Server startup occurs in the main.ui.GUIFrame.java  comments can be found there with explination.


<b>JavaScript  </b>  

The web application uses bootstrap and d3 based on the example http://bl.ocks.org/mbostock/1153292



<b>Tasks still todo</b>

thread support
change default values for visualisations. 
make both visualisations scale better with browser size
