'''
Created on 26-Sep-2018

@author: avdhut
'''

'''
This module has examples of design patterns and advanced class features
'''
################################################
'''
Psuedoprivate Class attributes or Mangling
Python does not have data hiding capability. Normally, developers use single underscore, _X, to represent private variables. 
However, python supports mangling. If a variable name starts with a double underscore but does not end with a double underscore,
python adds the class name to it to make it unique for that class.
For eg. __X in a class called Person becomes _Person__X Variable. It adds the class name with one underscore before it
Why is it useful?
In python, all instance attributes end up in a single instance object at the bottom of the class tree.
In Java, C++, if two classes have the same variable name, they are two distinct variables, each with its own namespace
So if another class extends these two classes, there is no name clash
In python, if two classes have the same variable and another class extends these two classes, the variable ends
up in the scope of the 'bottom' class or the class where it is first found in the search hierarchy
This method is usually used in frameworks or libraries where you want to avoid name clashes if classes are extended
Similarly methods can also be named with double underscores
'''
#each class defines a variable X
class C1:
    def method1(self):
        self.X = 'hello1'
    def print1(self):
        print('class C1 print ==>', self.X)

class C2:
    def method2(self):
        self.X = 'hello2'
    def print2(self):
        print('class C2 print ==>', self.X)

#If the variable had a double underscore, mangling would take place and each class would have its own variable
class C4:
    def __init__(self):
        self.__X = 'hello4'
    def method4(self):
        #self.__X = 'hello4'
        pass
    def print4(self):
        print('class C4 print ==>', self.__X)

#this class extends C1, C2 and C4 and also overrides value of X
# X is now one variable in the bottom class, C3
#If the print2 and print 3 method is called, it prints the value of C3 which set it initially
class C3(C1, C2, C4):
    def method3(self):
        self.X = 'hello3'
    def print3(self):
        print('class C3 print ==>', self.X)

instc3 = C3()
instc3.method3()
instc3.print3() #both print the value of X set by C3. C1 and C2 name clashes and there is only one X!
instc3.print2()
instc3.print1()
#But if it calls C4, it does not find X and error is thrown.
#uncomment to see the error
#instc3.print4()
print(dir(instc3)) #you will see a mangled variable _C4__X which does not clash with X
print('the mangled inherited variable ==>', instc3._C4__X)
'''
Bound, Unbound and Function methods in a class
When a method in a class is called using a instance, python automatically passes the instance to the method, i.e 
the self argument. This is called Bound method.
If the method is called using only the class name as a reference, it is a Unbound method. In this case, you have to
explicitly pass the instance as the first argument
In v3.0, Classes can have simple functions. i.e you can have a method that does not have a 'self' as a first arg
You can them call it using the class name as reference
'''
class BoundEx:
    def __init__(self, base):
        self.base = base
    def boundm(self, x, y):
        print('example of a bound method =>', x*y)
    def unbound(self, x, y):
        print('example of a unbound method, self passed as first agr explicitly =>', x*y)
    #this is an example of a v3.0 simple function. Does not need self as the first arg
    #def simplefunc(a, b):
    #    print('example of a v3.0 simple func. no need of self ==>', a*b)
    def __call__(self, x, y):
        print('callable instance with call method ==>', x*y)

insb1 = BoundEx(5)
insb1.boundm(2, 3) #here you call the func with the instance ref. Self is passed automatically
BoundEx.unbound(insb1, 5, 6) # this is unbound call. Instance reference has to be passed explicitly
# in v3.0, it is possible to call below. No self reference has to be passed. It is called a simple func 
#BoundEx.simplefunc(4,3) 
#bound methods can also be referenced to a variable. Can be used in a for loop
metarry = [insb1.boundm,insb1.unbound]
for met in metarry:
    print(met(2,5))  #you can initialize these variables in init method of the class and call the method
#method reference can also be introspected. It has attributes to refer to the main object and the func itself
metref = insb1.boundm
print('the method attr that refers to the main obj', metref.__self__)
print('the method attr that refers to the main obj and accesses the class variable', metref.__self__.base)
print('the method attr that refers to the func obj itself', metref.__func__)
#you can call the method as shown below
metref.__func__(insb1,2,3) #or simply metref(2,3) which has been used before
#another type of callable instance is the __call__ method which we saw earlier - refer to modclass for details
#below is an example of callable instance
insb1(6,7)
'''
Factory pattern
There are different ways in which a factory pattern can be implemented
You can have a single function that creates classes of different types or have a method for each specific class
One such illustration is given below
'''
def factory(aClass, *pargs, **kargs):
    return aClass(*pargs, **kargs)

class Animal:
    def __init__(self, name, color=None):
        self.name = name
        self.color = color
class Simply:
    def doIt(self, message):
        print(message)
        
animalObj = factory(Animal, 'ant', 'red')
simObj = factory(Simply)
animalObj2 = factory(Animal, name='rat')
print('calling factory obj animalobj1 ==>', animalObj.name, animalObj.color)
print('calling factory obj simply =>', simObj.doIt('hello'))
print('calling factory obj animalobj2 ==>', animalObj2.name, animalObj2.color)
'''
Advanced class topics
Extending built-in types by embedding or sub-classing
Built-in types like list, str, dict,tuples can be extended. The built-in type conversion functions like list, str, dict, tuple
are actually classes internally and any class can extend it
You can do this either by composition or by inheritance also. Below shows the inheritance of built-in types
'''
class Mylist(list):
    def __getitem__(self, *args, **kwargs):
        return list.__getitem__(self, *args, **kwargs) #here you can override the behaviour of the list. for eg. offset can start from 1 instead of 0
'''
New style classes
In v3.0, every class is inherited from the root class object 
In v2.0, you can get this behaviour by explicitly extending from the object class
This affects the way built-in overridden methods like __getattr__. In 2.0, this used to get the built in variables
from the instance. In v3.0, it looks for the method in the class and only returns the class variables, not instance variables
If x is an instance, x.someIbuiltvar used to call __Getattr__ of the instance. Now this is effectively type(x).__getatrr__
Thus all overloaded methods result in an error. But explicitly assigning that method to a instance will work
'''
class ExO: pass
x=ExO()
x.normal = lambda:99
x.__add__= lambda y: 99 + y
print('attr access in old style class', x.normal)
print('method call in old style class', x.__add__(5))
print('overloaded add method in old style class', x+1)

class ExN(object): pass
z=ExN()
z.normal = lambda:99
z.__add__= lambda y: 99 + y
print('attr access in new style class', z.normal)
print('method call in new style class', z.__add__(5)) # this works because it is explicitly assigned to an instance
#uncomment below to see error
#print('overloaded add method in new style class', z+1) # this does not work as overloaded methods are searched for in the 'Class'
'''
Type model changes
In v2.0, each instance was of a generic type called 'instance' and you had to actually use the __class__ attr to
find the actual type
In v3.0, each class is a type and each type is a class. Also the instance does not have a __class_attribute
This impacts the comparison also. 
In v2.0, you have to compare using the __class__ atrr of two instances while in v3.0, you can only use the type method
'''
class PO :pass
class QN(object): pass
print('type of v2.0 instance using type func', type(x)) #gives a generic 'instance'as a type
print('type of v2.0 instance using __class__ var', x.__class__) #this gives the actual class type 
p=PO()
print('checking types of two instances in v2.0 using type', type(x)==type(p)) #this will give true as both are generic 'instance' type - which is wrong
print('checking types of two instances in v2.0 using class', x.__class__== p.__class__) # this will give false as now it checks the actual type
#however if you check the type of a class it is not a 'type'
print('checking type of a Class in v2.0 ', type(ExO)) #this gives  a type class classobj 
#also the Class obj does not have a __class__attr
#print('checking class attr of a Class in v2.0 ', ExO.__class__)#uncomment to see error
      
print('type of v3.0 instance using type func', type(z)) # this gives the actual class type for new style class types
print('type of v3.0 instance using __class__ var', z.__class__) # this also gives the actual class type
q=QN()
print('checking types of two instances in v3.0 using type', type(z)==type(q)) # this gives false as class is a type 
print('checking types of two instances in v3.0 using class', z.__class__== q.__class__) # this gives false as class is a type 
#however if you check the type of a class in v3.0, it is a 'type'
print('checking type of a Class in v3.0 ', type(ExN)) #this gives  a 'type' class type 
#also the Class obj does have a __class__attr in v3.0
print('checking class attr of a Class in v3.0 ', ExN.__class__) # this also gives a value of 'type' 
#type of a instance is the class from which it is made while type of a class is a 'Type' class
#In python always avoid type checking - in case it is need better to use isIntance inbuilt method
###################################
'''
Search order in class hierarchy
In classic objects the search order is DFLR - depth first left to right. The search first reaches the top of the class
hierarchy and then goes left to right
In v3.0, every class extends object. Hence the search does not go to object - rather it follows a diamond pattern
It is called MRO - method resolution order. Here it first goes left and then goes top - following a diamond shape
'''
class A : attr=1
class B(A) : pass
class C(A) : attr=2
class D(B,C): pass
x = D() # this search will give attr=1 - the attr of A as it first goes to the top
print('values of D attr in classic old style is ',x.attr) 

class AN(object) : attr=1
class BN(AN) : pass
class CN(AN) : attr=2
class DN(BN,CN): pass
x = DN() # this search will give attr=2 - the attr of C as it first goes to the right from left before it goes  top
print('values of D attr in new style is ',x.attr) 
#similar search is also performed for method calls
#if you want to override this search corder, you have to set attr explicitly. for eg. D's attr can be set to 'C.attr'
#It is always  better to explicitly assign method class rather then rely on the search pattern
###########################
'''
slots
New style classes have a feature called slots where you can restrict the attrs that a class can have
using slots should be generally avoided as it complicates code and only used in v advanced use cases where you want to optimize memory space
If slots is defined, you do not get the __dict__ variable
'''
class Sl(object):
    __slots__ = ['name', 'age', 'job']
    def __init__(self):
    #this is not allowed when a slots is defined. As address is not in slots, you cannot assign a new attribute
    #uncomment to see error
        #self.address = 'something'
        pass

sle = Sl()
#assigning a new attribute to a class is also not allowed. Uncomment to see error
#sle.height = 35
#you get an error if you try to access a attribute that is not assigned
#uncomment to see below error
#print('attr name is ', sle.name)
sle.name ='fred'
print('attr name is ', sle.name)
#if you want dict , then you have to assign it to the slots variable. i.e add '__dict__' to the slots list
#this will enable you to add new attributes like a normal class
#################################
'''
Property method
Property is another feature/method in new style classes that is similar to getter and setter of classes
Instead of __getattr__ and __setattr__ overloaded methods, you can use the 'property' inbuilt class that takes
a getter, setter, delete and docs method for a particular attribute of a class
It still allows you to add the other attrs normally like in the classic case and not restrict like the slots
It is useful where you want to have specific getters and setters for some attrs but results in an extra call
'''
class PropEx(object):
    def getage(self):
        return 40
    def setage(self, val):
        print('setage method called with val', val)
        self._age=val
    age = property(getage, setage, None, None) #(get,set,del, doc)    

pex = PropEx()
print('accessing age attr will call getage', pex.age)
print('setting age attr will call setage')
pex.age=52
print('normal acess - does not call getage ', pex._age)
pex.name='bob' #normal setting does not call setter method
print('acessing normal attrs', pex.name)
#############################################
'''
Static and Class methods
In v2.0, you can only call methods with instances. Even an 'Unbound' method call requires the instance to be passed as first arg
In v3.0, you can call a simple func - but this can be called only with the Class. You cannot call this method with instance
Refer above for bound, unbound, function section.
For eg. a = SomeClass(), you cannot call a.simpleFunc() in v3.0. You can only call - SomeClass.simpleFunc()
To support this, python has a concept of staticmethod and classmethod
You designate a method as staticmethod or classmethod and it works in v2.0 also. 
You can now call a method without an instance
Thus there are 3 types of methods in v2.0,
Instance method - calls with the instance specified as first arg. i.e self
Static method - no extra object is passed. designated with in-built staticmethod call or annotation
Class method - calls with Class specified as first arg and designated with in-built classmethod method
In v3.0, you also have the simple method that can be called via the Class 
'''
#this class has a class level variable to count the number of instances
class Counter:
    numInstances = 0
    
    def __init__(self):
        Counter.numInstances += 1 # this is important rather than using self
    
    def imtd(self, sval):
        print('instance method called using a instance', sval)
    
    def printNumInst():
        print('example of static method - number of instances is ', Counter.numInstances)
    
    #class method has the class name as the first arg and is passed automatically 
    def clsMeth(cls, x):
        print('example of a call method  - value called is ==>', cls, x)
    
    printNumInst = staticmethod(printNumInst) #here you designate the method as static
    clsMeth = classmethod(clsMeth) #here you designate this method as class method
    
cnt = Counter()
#calling instance method
cnt.imtd(5)      
#calling static method - can be called by both using a instance object or a Class
#In v3.0, calling a simple method using a instance is not allowed unless you designate it as staticmethod
cnt.printNumInst() 
Counter.printNumInst()
#calling class method - can be called by both using a instance object or a Class
#class object is passed automatically - can be used where Class object is used
cnt.clsMeth(7)
cnt.clsMeth(88)
#just creating more instances
i2 = Counter()
i3 = Counter()
cnt.printNumInst() 
Counter.printNumInst()
#you can now also inherit the static method and also override it if required
#this is better than importing a function from a module in v2.0 
class SubCounter(Counter): pass
sc1 = SubCounter()
SubCounter.printNumInst()
SubCounter.clsMeth(22) #note that the lowest class name is passed
'''
With Class methods and inheritance, you need to be careful
In Class methods the lowest class is passed as arg. So if you use the Class variable to update local variable
it might update the intended one
Static class methods are better where the method updates local shared variables
Class methods are better for managing state information that varies in each class
For eg. If you want per-class instance numOfInstances  
'''
class SubsusbCounter(SubCounter): pass
ssc = SubsusbCounter()
ssc.clsMeth(33)#note that the lowest class name is passed
#If you do cls.variable = lastvalue=1 in the class method, it might not update the shared variable
'''
Use of super()
avoid the use of super as it can cause unintended problems especially when using inheritence in new style classes
The search order of MRO causes problems in case a class extends more than one class

Gotchas in Class
1. Changing class attributes can have unintended consequences
changing class attrs changes the attr in all instances that were created. Hence can cause a problem
Better to change the attrs of the instance
2. Avoid mutable types in class variables
similar to point 1, if the class attr is a mutable like list, dict, any change in that by any instance cause change in all instances
3. Multiple inheritance - as discussed before the search order is from left to right in v2.0
hence need to be careful in what variable is being searched
4. Nested classes - nested classes in modules can cause problems in scope of variables. They are good for closures where you want to retain
state of enclosing variables of the main module method. But preferably keep classes in module level scope
5. Explicitly call super class constructors - python does not call automatically the init methods of super classes
It only calls the constructor of the lowest call. If you want to call super class constructors you need to call it explicitly
'''
class Gotcha1:
    mutattr = []

gotc1 = Gotcha1()
gotc2 = Gotcha1()

Gotcha1.somevar = 44
print('class variable change affects all instances ', gotc1.somevar, gotc2.somevar )
gotc1.mutattr.append('hello') #assigning is better. assigning , i.e gotc1.mutattr = [1,2,3] changes only gotc1 instance
print('change in mutable class attr by one instance affects others also ', gotc2.mutattr)
  

    
    
    
     



        
        
        
          