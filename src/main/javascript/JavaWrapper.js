/**
invokeJavaMethod invokes the method from the class name, method name and parameter
@param className name of the class including the package eg main.javascript.JavascriptFileWrapper
@param methodName name of the java method to invoke
@param parameter parameter for the java method to invoke
*/
var invokeJavaMethod = function(className,methodName, parameter){
	var MyJavaClass = Java.type(className);
	return MyJavaClass[methodName](parameter);
}

var test = function(){
	var result = invokeJavaMethod("main.tests.JavaWrapperTests","fun1","bob");
	return result;
}

var test2 = function(){
	var result = invokeJavaMethod("main.tests.JavaWrapperTests","fun3",
	{
    foo: 'bar',
    bar: 'foo'
	});
	return result;
}
