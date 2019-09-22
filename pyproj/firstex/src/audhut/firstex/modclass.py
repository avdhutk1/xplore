'''
Created on 26-Jul-2018

@author: avdhut
'''
from __builtin__ import AttributeError

class Esuper1:
    
    nameattr = 'super class 1'
        
    def setval(self, value): 
            self.data=value
            
    def display(self, cname):
        if not cname:
            print('name of the class is ==>', self.nameattr)
        else:              
            print('name of the class being passed  is ==>', cname)

class Esuper2:
    
    nameattr = 'super class 2'
    nameattr2 = 'second attr of second super class'
             
    def display(self):
        print('name of class is ==>', self.nameattr)      

#Sub class is created with superclasses written from left to right
#when it searches for an attribute, it searches from left to write
class Subclassex(Esuper1, Esuper2):
    nameattrsub = 'sub class attr'
    
    def display(self):
        print('Searches for the name of the first super class - name of class is ==>', self.nameattr)
    
    
class OperatorOverload:
    def __init__(self, value):
        self.data=value
        
    def __add__(self, other):
        return OperatorOverload(self.data + other)
    
    def __str__(self):
        return '[OperatorOverload: %s]' % self.data

    def display(self):
        print('name of class is ==>', self.data)
  

class Brec:
    pass

class Person:
    def __init__(self, name, job=None, pay=0):
        self.name = name
        self.job = job
        self.pay = pay
    
    def giveRaise(self, percent):
        self.pay = int(self.pay*(1+percent))
#this is same as __str__ it is another alternative        
    def __repr__(self):
        return '[Person %s %s]' % (self.name, self.pay)
        
class Manager(Person):
#the constructor is overloaded
#see how it calls the super class constructor with default value of job
    def __init__(self,name,pay):
        Person.__init__(self, name, 'manager', pay)    
#Here the method is overloaded
#Notice how a method in super class is called. It uses the class.method to call it 
    def giveRaise(self, percent, bonus=.10):
        Person.giveRaise(self, percent + bonus)
        
#this class demonstrates composition
class Department:
    def __init__(self,*args):
        self.members = list(args)
    def giveRaise(self,percent):
        for person in self.members:
            person.giveRaise(percent)
    def showAll(self):
        for person in self.members:
            print(person.__repr__()) 
    
#This class demonstrates how abstract class is implemented in python
class AbsSuper:
    def smethod(self):
        print('in AbsSuper')

#here delegate is like a abstract method -> it delegates to the instance
    def delegate(self):
        self.action()

class Provider(AbsSuper):
    def action(self):
        print('in Provider class called by delegate method of super class')
        
class Replacer(AbsSuper):
#overrides the smethod
    def smethod(self):
        print('in replacer')     

class Extender(AbsSuper):
#overrides and extends the smethod
    def smethod(self):
        print('in start of Extender')
        AbsSuper.smethod(self)
        print('ending extender method')
##########################################
#this class demonstrates iterable. It implements iter and next methods
#this class can be used in a for loop
class Squares:
    def __init__(self, startn, stopn):
        self.value = startn -1
        self.stopn = stopn
#the below is a single active scan. If we want a new object in each scan, we need to implement composition
#return a new wrapped object for every iter call
#another trick is to use generator func 
#if the iter method is a generator, i.e returns yield, there is no need for a next func
#the next func is automatically generated for every generator func. In addition, each call will generate a new generator func
#the generator func will not be a single iterable if called multiple times    
    def __iter__(self):
        return self
    #in v3.0, __next__method is used
    def __next__(self):
       #when the for loop ends raise an stopIteration exception
       if self.value == self.stopn:
           raise StopIteration
       self.value += 1
       return self.value**2
    #in v2.7, next method is used
    def next(self):
       #when the for loop ends raise an stopIteration exception
       if self.value == self.stopn:
           raise StopIteration
       self.value += 1
       return self.value**2
#############################################
#this class demonstrates indexing. If you want to implement a class to index like a list
#implement the __getitem__ method
#when the index includes a slice, __getitem__ is called with a slice object.
#The slice in turn class the getitem to get individual items
#if you want to change slicing, implement the __getslice__ and __setslice__ methods
#this class also has the __contains__ method that is called for checking membership
class Indexer:
    data = [5,6,7,8,9]
    def __getitem__(self,index):
        print('getitem:', index)
        return self.data[index]
    def __contains__(self, x):
        return x in self.data
#############################################
'''
This class demonstrates how the attribute reference is overridden
The __getattr__ method is called when a attribute of a class is referenced
The __setattr__ method is called when an attribute of a class is referenced for setting a value
The __delattr__ method is called when a del attribute is called
There is also a __getattribute__ method that intercepts all attribute fetches
'''
class AttrAccessor:
#This method can be overridden if you want to proxy the attr accessor. It can delegate the method to another class
#where the attribute can be manipulated. In this example, a fixed value of 40 is returned     
    def __getattr__(self, attrname):
        if attrname == 'age':
            return 40
        else:
            raise AttributeError(attrname)

class AttrAccessor2:
#This method is called when the attr is set
#do not call self.__attr__ and instead set it in the __dict__ internal variable
#alternatively, a method on super class can be called. superclass.__setattr__(attr, val)
    def __setattr__(self, attrname, val):
        if attrname == 'age':
            self.__dict__['age'] = val + 10 #do not call self.__setattr__ as it will cause recursive loop and memory error
        else:
            raise AttributeError(attrname + ' not allowed')  
###############################################
'''
This class demonstrates call expression
The __call__ method is similar to a function but called on an Instance
most commonly used after __init__, __str__ __repr__
Usually used as a callback method. Called as Callable instances
'''
class CallEx:
    def __call__(self,*pargs, **kargs):
        print('Called __call_ method ==>', pargs, kargs)
################################################
'''
This class demonstrates comparisons
The __lt__, __gt__ methods are overridden for comparing objects
'''
class CompEx:
    data = 'spam'
    def __lt__(self, other):
        print('called __lt__')
        return self.data < other #here you can have your own comparison criteria
    def __gt__(self, other):
        print('called __gt__')
        return self.data > other
##################################################
'''
This class is used to demonstrate inheritance or is-a relationship
'''
  
        
        
    
 
        