var invokeJavaMethod = function(className,methodName, parameter){
	var MyJavaClass = Java.type(className);
	return MyJavaClass[methodName](parameter);
}

var result = invokeJavaMethod("JavascriptFileWrapper","fun1","bob");
print(result);