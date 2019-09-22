'''
Created on 26-Jul-2018

@author: avdhut
'''
from audhut.firstex.modclass import Esuper1, Subclassex, OperatorOverload, Brec,\
    Manager, Person, Department, Esuper2, Provider, AbsSuper, Extender, Replacer,\
    Squares, Indexer, AttrAccessor, AttrAccessor2, CallEx, CompEx


'''
Classes are executable statements and are executed when they are encountered the first time
Each Class creates a namespace using which its attributes and methods can be accessed
Functions in classes are called methods. Methods have an implicit first argument which is the object being called
By convention, Classes start with a uppercase letter while a module has a lower case
''' 

#import modclass

#you can directly access the attributes of a class
print('printing the class level attribute ==>', Esuper1.nameattr)

#However you cannot execute a method in a class.  You need an instance
#uncomment to see the error. 
#esuper1.setval(esuper1,50)

#creating an instance of a class
inst1 = Esuper1()
#calling a method of an instance. Here self is implicit. Internally it is calling Esuper1.display(inst1, msg), where first arg is instance
inst1.display('inst1') #prints the instance name
inst1.display('') #prints the super class name

#Prints the name set in the super class as it was not overwritten
print('instance variable that is inherited ==>', inst1.nameattr)
#Now set the name attr for the class and print  it
inst1.nameattr ='instance 1'
print('instance variable that is overridden ==>', inst1.nameattr)

#When a attr of a Subclass is searched, it is from left to right of the declaration
subclssinst1 = Subclassex()
#nameattr is not defined in subclass and is in both the super class but the first super class defined in left is picked up
#each invocation of object.arr or object.method is a new invocation and triggers a new search
print('finds the name of the class from the first super class ==>', subclssinst1.nameattr,subclssinst1.display())
#nameattr2 is only defined in second super class
print('finds the attr from second super class ==>', subclssinst1.nameattr2)
#nameattrsub is only defined in sub class and found from there
print('finds the attr of the sub class ==>', subclssinst1.nameattrsub)
'''
Operator overloading
Classes can have special methods with double underscore,__x__, that represents operator overloading
If you want to overload any operator like 'add' , printing object, you can define such a method
Usually only print(__str__) and a special initialization method __init__ are commonly used
But default methods like add are not inherited from the python object model and results in error
only methods like print are inherited from the Python object model
'''
#this calls the __init__method
#you can explicitly call init also if you want to reinitialize the object
opeo = OperatorOverload('adu')
opeo.display()
#this calls the overloaded __Add__ method
opeo2 = opeo + 'kh'
opeo2.display()
#this calls the overloaded __Str__method
print(opeo2)

'''
under the hood of the class
Classes are just namespaces and attributes can be assigned on the fly.
OOP in python is just looking up attributes in linked object namespace
Each class has a link to its base classes via attribute __base__
Each instance has a link to its class via the attribute __class__
Each class and instance has it own index of attrs in __dict__
Attributes are actually dictionary keys inside Python. Hence you can fetch them in two ways
X.attrname or X.__dict__['attrname']
'''
#assigning a attribute to a class on fly
Brec.name= 'blank super class'
Brec.data = 'some data in blank class'

instbrec1 = Brec()
print('inherited attributed by instance ==>', instbrec1.name)

#you can give a new value to the instance
instbrec1.name = 'name of blank instance1'
#you can also assign a attribute to the instance
instbrec1.somenewvar = 'a new attribute of only the instance'
print('only the attribute of the instance ==>', instbrec1.somenewvar)

#If dict is printed for the instance, it does not show the inherited attributes like data
print('dict of instance =>', instbrec1.__dict__)
#gives error as the data attr is inherited. Uncomment to see error
#print('trying to find inherited attr in instance ==>', instbrec1.__dict__['data'])
#but as name attr is overriden it can be seen  
print('trying to find inherited and overriden attr in instance ==>', instbrec1.__dict__['name'])

#each instance has a link to the class via the class attr
print('instance link to a class ==>', instbrec1.__class__)
print('classes have link to base class ===>', subclssinst1.__class__.__bases__)
'''
Subclassing and adding custom behaviour
The below demonstrates how a super class is called from the sub class method
'''

projman = Manager('Fred', 100)
projman.giveRaise(20) #this calls the overriden method - see it in the modclass
print(projman)#this calls the __repr__ method
#Instead of specifying the job everytime you can customize the constructor also
projman = Manager('Smith', 100)
print(projman)

#demonstrates composition class
sue = Manager('Sue', 200)
bob = Person('Bob', 'account', 100)
dept = Department(sue,bob)
dept.giveRaise(10)
dept.showAll()
'''
Introspection of instances
You can introspect the instance to get the type and name of the class
The __class__ has a __name__ attr which can be used to find the name of the class
The __dict__ gives the attrs in dict form. You can get the value of a attr also using the getattr method
__dict__ only gives attrs defined in the class not inherited ones.For inherited attrs, use the dir method
'''
print('name of sue type  ==>', sue.__class__)
print('name of sue instance type  ==>', sue.__class__.__name__) #prints the actual type which is sub class
print('name of bob instance type  ==>', bob.__class__.__name__) #prints the actual type which is Person
for key in sue.__dict__:
    print('key is ==>', key, sue.__dict__[key])
    print('value is using getattr method ==>', getattr(sue, key))
#see previous example above where dict did not print inherited attrs. 
# Here constructor of sue called the super class with 'self', hence inherited attrs where set in sue and could be printed 
#this displays only the data attr of the sub class and not inherited attrs
#Inherited attrs are found by the search tree and not copied on instances.They are attached only to the class    
for key in opeo.__dict__:
    print('key is - of sub class without constructor overloading ==>', opeo.__dict__[key])
#if you want to display all the inherited attrs, use dir in-built method
#in v3.x, it prints a lot of inbuilt variables
print('printing inherited attrs using dir method ==>', dir(subclssinst1))
print('subclass dict', subclssinst1.__dict__) 
print('printing dir of Class ==>', dir(Subclassex))
#################################################
'''
Abstract classes
In abstract classes, the abstract class provides a template method that is implemented by the subclass
In Python, this pattern is implemented by using a 'delegate' like pattern. i.e the super class implements a method
that calls the sub class via the 'self' argument of the subclass
'''
#Instantiating a implementation class but calling abstract method on super class
prod = Provider()
prod.delegate() 

#Creating classes in a loop using tuple and calling overridden and extended methods
for klass in (AbsSuper, Extender, Replacer):
    print('class name created in tuple is ==>', klass.__name__)
    #the loop creates a 'class' instance first 
    print('class in loop==>', klass)
    #to create a instance of a class, you have to call the class constructor
    print('create class instance in loop==>', klass())
#see the syntax of klass - the loop creates a 'class' object and the instance is created below
    klass().smethod()
################################################
'''
Implementing user defined Iterables
One can define iterables by implementing the __iter__ and __next__ overloaded methods
When the caller calls iter and next - the above two overloaded methods are called to get the values
The iterable can also be used in a for loop
When the loop reaches the end, a StopIteration exception is raised
In v2.7, the next method is to be defined
More often, using generator function is good for such use cases. Iterable Classes are used for more complex iterations and
take advantage of the explicit attributes and methods
'''
#the class Squares is a iterable and can be used in a loop. see the class on how it implements iter and next methods

for x in Squares(1,5):
    print('iterable square class ==>', x) 

sq = Squares(1,4)
sqitr = iter(sq)
#the next method calls __next__ in v3.0
#xval = next(sqitr)
#in v2.7, next method is called
print('the next value is ==>', next(sqitr))
#in the above case, Square returns the same iterator and hence is a single iterator object supporting single active scan
#to support multiple iterators for each call, use composition and return a new object each time the __iter__ method is called
###################################################
'''
Implementing indexing and membership
If you want a class to implement indexing like a list, it should implement the __getitem__ method
When a slice is used, the __getitem__ method is called with a slice object
To check membership, the __contains__ method is overridden. It is called whenever a membership check, i.e. in , is used
'''
idx = Indexer()
print('indexer called with index ==>', idx[1]) #this calls the __getitem__ method
print('indexer called with a slice ==>', idx[1:4]) #this calls the __getitem__ method with a slice object. see the print
print('indexer called with a slice method ==>', idx[slice(1,4)])#the above is equivalent to the slice method
print('check membership of a data ==>', 3 in idx)
print('check membership of a data ==>', 5 in idx)
###################################################
'''
Atttribute reference
When an attribute of a class is referenced, the __getattr__ method is called internally.
You can override this method to proxy the attr value or delegate it to another class
similarly, the __setattr_ method is called when an attr value is set. You have to be careful with proxing set values
If you again set the value by referencing (self.data=xxx), it will cause a recursive loop. Hence, the value should
be set in a __dict__. Alternatively call the __setattr__ of a super class
Similarly, __delattr__ is called when del x.attr is used
'''
atx = AttrAccessor()
print('accessing attr calls __getattr__ ==>', atx.age) # this will return 40 as the method is overridden in the class
#uncomment to see the error
#print('accessing attr calls __getattr__ and result in an error ==>', atx.std)

atx2 = AttrAccessor2()
atx2.age = 60
print('checking the setattr method call  =>', atx2.age)
#######################################################
'''
String displays
We have seen previously that to print a class, you can override the __str__ or __repr__ methods
Usually, when a print or str is called, it first calls __str__ method.
If it is not available, __repr__ method is called
__str__ is called for user friendly displays
__repr__ is usually used for as-code display, i.e displays the class name with what input to give, etc.eg. Person('bob')
Other display options like interactive call __repr__ func
Thus __repr__ func acts as a display for all kinds of callers
Both __str__ and __repr__ must return string
'''
#########################################################  
'''
Call expressions
The method __call__ is called when your instance is called.
It is used to retain more state once a instance is created. It is similar to a function call
Such methods are used as callbacks for event listeners
The class can retains state with __init__ and then when the class is called (i.e __call__ method), the class
can use the retained state to change it.
For eg. register this class (with some state) as a callback listener in another class. When you want to fire a event
call this class , CallEx(), and the  logic is defined in the __call__ method 
Also called as Callable instances
'''
calle = CallEx()
calle(1,2,3, x=4, y=5)
###########################################################
'''
Comparisons - relational operators
When you want to compare two objects, you can override the methods that are used for comparisons
The relational operators, <,>,=,!=,==,<=,>=, all have equivalent methods. __lt__, __gt__, etc 
There is also another method called, __cmp__, which is also called during comparison. But this method is withdrawn in v3.0
'''
compx = CompEx()
print('comparing objects 1==>', compx > 'ham')
print('comparing objects 2 ==>', compx < 'ham')
###########################################################
'''
Boolean operators
In python, every object is true or false
To check this, the internal method __bool__ is called. By default it returns true
The other fallback method is __len__. Here if the length is greater then 0, it returns true
In v2.x, there is a __nonzero__ method instead of __bool__
BUt these methods are rarely overridden unless you want to return false for an existing object!
'''
#############################################################
'''
Object destructor method
There is a __del__method called during garbage collection. But it is not advised to use this for closing resources like file
'''
###############################################################



















