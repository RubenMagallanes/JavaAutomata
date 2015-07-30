/**
 * contains functions and/or variables that will be used when testing interface 
 * between java and javascript.
 */
//================================
var returnZero = function(){
	return 0;
}
//================================
var helloString = "hello world";

var changeHelloString = function(newstr){
	helloString = newstr;
}
var returnHelloString = function(){
	return helloString;	
}
//================================
var zero = 0;

var getRandom = function(i){
	return  Math.floor(Math.random(i));
}
function pausecomp(millis)
{
 var date = new Date();
 var curDate = null;
 do { curDate = new Date(); }
 while(curDate-date < millis);
}
var slowFunction = function(){
	//do something that takes a while
	//var array1 = new Array(1000000);
	//var array2 = new Array(1000000);
	
	//for (var i = 0; i< 1000000; i++){
	//	array1[i] = getRandom(i);
	//}	
	//for (var i = 0; i< 1000000; i++){
	//array2[i] = array1[i];
	//}	
	pausecomp(10000);
	zero = 1;
}
var returnOurVariable = function(){
	return zero;
}
//============================================
var numb = 1;