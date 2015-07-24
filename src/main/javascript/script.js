var fun1 = function(name) {
    print('Hi there from Javascript, ' + name);    
    return "greetings from javascript";
};

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};




var invokeJavaMethod = function(className, parameter){
	var MyJavaClass = Java.type('main.javascript.' + className);
	MyJavaClass.fun1('John Doe');
}


// var result = MyJavaClass.fun1('John Doe');
// print(result);

// MyJavaClass.fun3({
//     foo: 'bar',
//     bar: 'foo'
// });
// window.open ("index.html");

// function Person(firstName, lastName) {

//     this.firstName = firstName;
//     this.lastName = lastName;
//     this.getFullName = function() {
//         return this.firstName + " " + this.lastName;
//     }
// }

// var person1 = new Person("Peter", "Parker");
// MyJavaClass.fun4(person1);