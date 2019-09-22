'''
Created on 04-Apr-2018

@author: avdhut
'''
'''
1. function definition can be done in two ways - def and lambda:
def creates an object and assigns the function name
def can be defined in-line in an if statement
def is created the first time it is invoked
lamda also creates an object but returns it as a result. It is used in-line where def cannot be defined
2. two ways to send result back to caller - return and yield
yield returns the result but remembers where it left off. i.e used to maintain state in the func.
The state is suspended and resumed later to return a series of results later
3. scope - two ways besides default- global and non-local
by default all names are local to the func
global assign a name in the enclosing module
nonlocal is in v3.0 assigns a name in the scope of def - but can retain state - between successive calls
4. arguments
arguments are passed by reference
arguments are passed by position - unless you specify. i.e you can specify by -x=someval
arbitrary number of args can be passed by *pargs or **kargs
return values are not declared
################
def is a true executable statement. unlike other lang, def is not evaluated before. It is evaluated at runtime
this means it is the same as = statement. here a function object is created with a name.
The statements inside def are also evaluated later
Because of this you can have a def inside an if statement
if x:
    def func()
else:
    def func()
Thus you can have different implementation depending on the logic
'''
def times(x, y):
    return x*y
#call the function now
rv = times(2,3)
print('func called with number =>', rv)
rv = times('aa',3)
print('func called with string =>', rv)
#in python, you can achieve polymorphism as there is no strong typing. Args are matched and if they support
#the * expression,it will execute. if not, you get an error.
'''
########################
Scopes
If a variable is defined in a module, it is 'global', i.e only global to that module.i.e in a single file. There is no 'total global' in python
A variable defined or assigned in a def is local to that func
If a variable is assigned in the enclosing def, it is 'nonlocal' to nested func
Important to note is that in python, assignments make that variable local
But if you do in-place changes, they are not local. see examples below.
'''
var1='aaa' #is a global scope
def testglobal():
    #print('check to see if var var1 is global =>', var1) # var1 is now global and the func sees global value 
    var1='bbb' #because it is assigned it is now local  
    print('check to see if var var1 has become local after assignment =>', var1)
testglobal()
print('printing a global scope var after func call=>', var1) # still sees 'aaa'
var2=['aaa', 'bbb']
def testglobal2():
    var2.append('ccc')
    print('check to see if var var1 has become local after modification =>', var2)
testglobal2()
print('printing a global scope var after func call with modification =>', var2) # can see the in-place modification done by func
def testglobal3(var3):
    var3.append('ddd')
    print('check to see if var var1 has become local after modification =>', var2)
testglobal3(var2)
print('printing a global scope var after func call with modification =>', var2) #ref points to the same obj
def testglobal4(var3):
    var4=['xxx']
    var3=var4 #now var2 is local, outside variables will not see this reference
    var3.append('yyy') 
    print('check to see if var var2 has become local after modification =>', var2)
    print('check to see if var var3 is local after assignment =>', var3)
testglobal4(var2)
print('printing a global scope var after func call with modification =>', var2) #ref points to the same obj
'''
when you use an unqualified name in python, it searches upto 4 scopes - LEGB
local - see if it is assigned locally. Thus if you assign any variable it becomes local
Enclosing - looks for in enclosing def or lambda
Global - looks into the module
Built-in - looks for built in variables
Thus when you modify a variable in a func, it is in-place modification and affects the module also
But if you assign it and then modify, it is local to that func
However if you define a variable as 'global' inside a func it affects module also
Care should be taken that you do not name the variables with the builtin variable names
In python2, these variables are under __builtin__
In python3, you have to import __builtin__ 
'''
#global keyword - makes the variable global. i..e becomes part of the enclosing module
varx=88
yy=7
zz=10
def func():
    global varx #makes this variable global, i.e the variable from enclosing module is referred
    varx=99
    global vx
    vx= yy+zz #yy and zz are found by legb rule and are not local as they are not assigned. They remain global
    
func() # call the function to prove this
print('check global keyword has changed the variable =>', varx)
print('check global keyword inside a func makes it accessible outside =>', vx)
'''
If you import a module and change its variable with global keyword inside a func, it affects
all other module that import the module. Hence should be careful while using global keyword
'''
#Nested functions - follow the same LEGB principle for defining variable scope. 
#If the nested function does not find the assignment in its block, it looks for the enclosing func
nvar=77
def ofun():
    nvar=88  # here it becomes local due to assignment
    def ifun():  #this is legal as you can define a func within another. It just makes a object with the fucn name
        print('check the scope of nested func =>', nvar)
    return ifun
    #ifun()   # uncomment this and comment the return to get the same result 
rfun = ofun()
rfun()
#The above func is an example of closure. The returned func retains the variables in the enclosing func
#you can use this as a factory where different inputs can be specified and stored inside a func.
def efun(cvar):
    def  cfun(ivar):
        return cvar*ivar  #closure - uses the var in the enclosing func
    return cfun
clfun = efun(40) #closure func returned with the input of enclosing
rt = clfun(10)  # closure func retains the enclosing variable
print('value returned by closure =>', rt )
clfun =efun(50)
rt = clfun(20)
print('value returned by closure =>', rt )
# the above can also be returned using lambda keyword    
def outfunc(x):
    return lambda y:y**x    
h = outfunc(2)
z= h(3)
print('example of a lambda =>', z)
#in earlier version of python, the enclosing func did not retain the state of the enclosing func
#hence they use to use the 'default' var method
def efun2(cvar):
    def  cfun2(ivar=cvar):  #here it uses the default arg, arg=default value, to retain the state
        return cvar*ivar  #closure - uses the var in the enclosing func
    return cfun2
#above code is quite common also
#however there is one exception to take care of - when the default value setting is required in a lambda
#If a lambda is defined in a loop, it only remembers the last value passed in the loop
#However, if you define the default value, it will remember each value
#In the below example, a set of factory functions are created. But each remember only the last value
def makeActions():
    acts=[]
    for i in range(5):
        acts.append(lambda x:x**i) #only the last i, i.e i=4 is remembered
       # acts.append(lambda x, i=i:x**i) #uncomment this to see the right behaviour. Each value is now remembered
    return acts
acts = makeActions()
print('each lambda remembers only the first, all have same result =>', acts[0](2)) #all give 16 as the result
print('each lambda remembers only the first, all have same result =>', acts[1](2))
print('each lambda remembers only the first, all have same result =>', acts[2](2))
#nonlocal means that python searches for that variable only in enclosing func
#If it does not find it in enclosing func, it stops the search. It does not look in global or builtin scope
#global, nonlocal, classes all offer state retention. Another way which is better than nonlocal is function attributes
#function attributes make non-local redundant
def tester(start):
    def nested(lable):
        print(lable, nested.state)  # here it relies on the fact that nested is a attribute of enclosing func
        nested.state+=1
    nested.state = start
    return nested
nt = tester(0)
nt('hello')
nt('next hello') #multiple calls retain the state. thus making nonlocal redundant
######################
'''
arguments are passed by references
If mutable objects are changed in place , it affects the caller
'''
def tester2(a, b):
    a = 7 #new value assigned - this is local to the func. Hence does not affect the caller       
    b[0] = 'bbb' #in place change. affects the caller

xv = 10
xl = ['aaa']
tester2(xv, xl)
print('argument check for in place changes =>', xl)
print('argument check for local assign =>', xv)
#to avoid changes, you can make a local copy in the func
def tester3(a, b):
    a=8
    b=b[:]
    b[0]='yyy'
tester3(xv, xl)
tester3(xv,xl[:]) #alternative way of calling by sending a copy
#you can also make an immutable object using tuple.uncomment to see error
#tester3(xv, tuple(xl)) #any attempt to modify will result in an error
#default values are also in the enclosing scope, i.e global
#so need to be careful when using mutable types as default values
def tester4(x=[]):
    x.append(1) #changes object each time it is called
    print('default value mutable list ==>', x)

tester4()
tester4()#this will have the list that grows
##########
# a function can return multiple values but they are in fact sent as a tuple
#The return values when assigned are 'unpacked' into the variables - see the unpacking in statement assignment
def mult(x,y):
    x=6
    y=[1,2]
    return x,y
a,b = mult(5,6)
print('value of a after unpacking =>', a)
print('value of b after unpacking =>', b)
#tuple unpacking arguments are disallowed in python 3.0. The argument has to be a single variable which is assigned the tuple
####################################
'''
Argument matching
There are different ways in which a caller can call a func and argument matching is done
By default, arguments are matched from left to right by position
Other ways are in which a caller can call :
2. func(name=value) => matched by name, no need for position
3. func(*iterable) => pass objects in iterables as individual positional args
4. func(**dict) => are passed a key value pairs of a dict in individual positional 
Function definition can also be done in different ways:
1. def func(name) => normal , left to right position matching
2. def func(name=value) => specifies the default value if one is not passed
3. def func(*name) => matches and  collects all remaining args in a tuple
4. def func(**name) => matches and collects all remaining args in a dict
In 3.0, we have two other way
def func(*other, name) => args to be passed by keyword only 
def func(*, name-value) => args to be passed by keyword only 

However, the above can be mixed - so that precedence order is :
In a function *call*, agrs must be passed in  => first it is positional left to right, next name=value pair
then *iterables and then any **dict
In a functions *header* (def func) , args should be =. first positional using name, then default values,
then *name and then **name 
Python assigns in the following way:
1. first it assigns the nonkeyword by position
2. assign keyword args by matching names
3. assign extra nonkeyword to *name tuple
4. assign extra keyword to **name dict
4. assign default values to unassigned args in header  
'''
# example of how position and keywords are matched
def perFunc(name, age, job):
    print('name passed is =>', name)
    print('age passed is =>', age)
    print('job passed is =>', job)

perFunc('bob', job='mason', age=34) # here position is changed but keywords are specified
#perFunc(job='mason', 'bob', age=34) #uncomment to see the error this is ambiguous
#example of default values
def perFunc2(name, age=25, job='service'):
    print('name passed is =>', name)
    print('age passed is =>', age)
    print('job passed is =>', job)
perFunc2('greg') # default values are assigned as args not specified
perFunc2('gerg', 40, 'driver') # default values are not assigned
#care should be taken for mutable default values
#mutable default values retain the value from previous call and can give wrong results and often a source of error
def perFunc3(name, job=[]): #the assignment to a mutable type, list should be inside a func
    print('job value now is =>', job)
perFunc3('capt', 'caretaker')
perFunc3('fred', 'nurse') 
# The chars * and ** can be both used in the caller and in func def to map arbitrary number of args
#in func def, * is mapped to a tuple
def functup(*prags):
    print('tuple formed from prags is =>', prags) #can also be accessed with position or looped using normal tuple features
    print('first item in tuple is ==>', prags[0])
functup(1,'Jeff','driver') #arbitrary args are mapped to tuple    
#while ** is used to map to a dict
def funcdic(**kargs):
    print('dict formed from arbitrary args is =>', kargs)
funcdic(name='phil', age=30, job='carpenter')
#you can also mix the above both but can be confusion.Not done but can be used to maintain BWC of a func
def funcmx(name, *cargs, **dargs ):
    print('mix values used are =>', name, cargs, dargs)
funcmx('ted', 23,27, color='red', size='big') # here first one is mapped by position, next two are tuple and the next are dict
#let us see how * and ** are used in func calls
#In func calls, * and ** means it is unpacks any number of args, while in header it collects any no of args
#in func calls, it is useful when you are building a arbitrary no of args dynamically and you do not know how many will be  there
def functc(a,b,c,d):
    print('unpacked in the func call and collected in this func =>', a)
    print('unpacked in the func call and collected in this func =>', b)
    print('unpacked in the func call and collected in this func =>', c)
    print('unpacked in the func call and collected in this func =>', d)
tlst = ('bob', 'rob')
tlst += ('fred','ted') # here the tuple is augmented dynamically with additional values
functc(*tlst) # here it is unpacked and passed to the func
tdic = {'a':'bob','b':2,'c':'mason'}
tdic['d'] ='smith'
functc(**tdic)
#you can also mix and match
functc('george', *('red','blue'), **{'d':'bus'})
#this results in an error as after unpacking there are more arguments to be matched than the function exposes
#functc('george', *('red','blue'), **{'d':'bus', 'e':'car'}) #uncomment to see the error 
#In 2.x, there is a generic apply func that can be used instead of calling a func
# the apply func accepts the func name as the first arg apply(functc, 'aa', 'bb', 'cc', 'dd') and then a list and dict of args can be given  *lst, **dict
#avoid using it as it is deprecated in 3.0

'''
In 3.0, there is a feature where you need to use keyword only args
if a func is specified as func(a,*,b,c) => the * specifies that the func does not accept variable args
but all args followed by * have to be specified by keyword only
for eg. func(1, b=2,c=3) is correct but func(1,2,c=3) or func(1,2,3) is wrong and gives an error
keyword only args should be present before the ** argument
for eg. func(a,**b, c=3) is wrong. func(a,c=3,**b) is correct. Also func(a,*d, c=3, **b) is correct
keyword can follow the * args but the calling func should be careful as the * unpacks the args and assigns it to d above
** should be the last argument
'''
'''
Advanced function features
Function is a first-class object. The object name is the func name
You can also attach arbitrary attributes called function attributes
function attributes can be used to hold state for a func - here it acts like a static variable
You can also inspect a func
'''
def introfunc(a):
    b= 'hello'
    return a*b
#introspecting the func for inbuilt variables
print('function introspection ==>', dir(introfunc))
#add func attributes
introfunc.temp = 'static-variable'
print('seeing the variables in a func ==>', introfunc.__code__.co_varnames) #you can only see internal variables
#some variables do not have '__' in 2.x. This can be seen as below
print([x for x in dir(introfunc) if not x.startswith('__')]) #now you can see the function attr
#in 3.x, you can add annotations. Below the text after ':' is the annotation
#the varaibles are a,b,c. Any text after ':' is annotation
#func(a: 'describe a', b: 'decr b', c: 'descr c')
'''
lambda functions
The format of lambda func is <input variables>:<expression>
lambda expresssion are single line and not blocks
'''
xl = lambda x,y,z:x*y*z
print('calling lambda=>', xl(1,2,3))
#lambda with default
xl = lambda x,y=5,z=2:x*y*z
print('calling lambda with default values=>', xl(1))
#one use of lambda is to create 'jump tables' - these are dynamic list or dict based on values given by the clinet
jl = [lambda x:x*2,
      lambda y:y*3,
      lambda z:z*4]

for ifunc in jl:
    print('dynamic list produced ==>', ifunc(2))
#without lambda, you would have to define 3 def statements that could cause name clashes if spread across the code
#you can also define a dict that generates dynamic values
dl = {'a':lambda x:x*2, 'b':lambda y:y*2}
print('dynamic dict values with lambda=>', dl['a'](2))
#to prevent obfuscating lambda code , make use of single statements like ternary operator
#as lambda is a expression you can also use selection logic /conditional statements in the expression
cl = lambda x,y:x if x<y else y
print('selection logic in lambda ==>', cl(2,3)) 
'''
functional programming tools
Python has in-built functions to support functional programming. i.e map, filter, reduce
'''
counters = [1,2,3,4,5]
clst = map(lambda x:x+10, counters) #here a lambda is used but a func object defined elsewhere with a def statement can also be used
print('map func ex ==>', clst)
#in python 3, all in-built func return an iterable object, hence you need to wrap it into a list func
#list(map(lambda x:x+10, counters))
#If a func has multiple args, then you can pass multiple sequences. Map takes one value from each sequence
plt = map(pow, [2,3,4], [1,4,6]) #here a power func is used. it takes 2**1, 3**4, 4**6
print('map with multiple sequence==>', plt)
#filter - used to filter from a list. Takes a predicate func
flt= filter(lambda x:x>0, range(-5, 5))
print('example of a filter func ==>', flt)
#reduce function - it is part of the fucntools module and in v3.0, you have to import it. Not required to import in v2
rlt = reduce(lambda x,y:x+y, [1,2,3,4,5])
print('example of a reduce func==>', rlt)
#all the above func can also be done by using list comprehension
#There is a inbuilt module called operator which can be  used to specify the reduce func
import operator
rlt = reduce(operator.add, [1,2,3])
print('example of a reduce func using operator==>', rlt)
'''
List comprehension
Provides a better concise syntax compared to functional methods shown above
General syntax is :
[expression for target in iterable <if condition...>]
'if condition' is optional
You can chain a number of iterables with the syntax
[expression for target in iterable1 <if condition...>
            for target in iterable2 if condition..
            for target in iterable3 if condition..]
This is like a nested for loop
'''
tlst = [x**2 for x in [2,3,4,5]] # this is equivalent to a map func
print('list comprehension equivalent of map ==>', tlst)
flst = [x**2 for x in [2,3,4,5,6,7,8] if x>3] # this is equivalent to a filter func
print('list comprehension equivalent of filter ==>', flst)
#example of nested func 
#this is equivalent to 
'''
for x in range(5):
    ...
    for y in range(5)
'''
nlst = [(x,y) for x in range(5) if x%2==0 for y in range(5) if y%2==1] #pairs even and odd numbers
print('nested list comprehension==>', nlst) # for better readability you can indent the for statements 
#list comprehension can be powerful toll for handling matrixes
mt =[[1,2,3],
     [4,5,6],
     [7,8,9]]
nt = [[2,2,2],
      [3,3,3],
      [4,4,4]]
#Getting the diagonal values
dg1 = [mt[i][i] for i in range(len(mt))]
print('diagonal value of matrix ==>', dg1)
dg2 = [mt[i][len(mt)-1-i] for i in range(len(mt))]
print('right diagonal of matrix ==>', dg2)
# Comprehension produces a new list but you can assign the variable back to the original
ipl = [col+10 for row in mt for col in row]
print('modification of list - flat list =>', ipl, mt)
ipl2 = [[col+10 for col in row] for row in mt] #use such nested statements to get complex processing
print('another way to modify list - to get nested list =>', ipl2, mt)
#another complex way to manipulate two lists
cpl = [[col1*col2 for (col1,col2) in zip(row1, row2)] for (row1, row2) in zip(mt,nt)]
print('complex processing using zip ==>', cpl)
#you can get a list of values from a tuple 
tupe = [('bob',35,'mason'), ('fred', 40,'engineer')]
lage = [ age for (name, age, job) in tupe]
print('getting a value from tuples==>', lage)
#or using map
lage2 = list(map((lambda (name, age, job):age), tupe))
print('getting a value from tuples using map==>', lage2)
'''
####Generator functions#####
Generators are coded as normal function using def but contain a yield statement
The generator 'yields' a value, i.e suspends the state of execution where the yield statement is present
It resumes from the next statement, once it is called again.
The func retains the last state 
They are compiled separately into a iterator function and maintains the code location and variables of the last suspended state
The iterator func is got from the 'iter' method and it has a 'next' method to loop through the next item.
In v2.x, the method is next, in v3.0, it is __next__
There is a inbuilt 'next' method that can be called on a iterable - which is equivalent to calling obj.next()
So when a Generator func is called it returns an object on which you call 'next' to get values
Generators can be replaced by list comprehension or map - but they can be useful in certain situations
List comprehension and map produce list while generators produce deffered lists - useful where memory requirement is high
If the list is big, you can return the contents of a list using generator 
'''

def gensquares(N):
    for i in range(N):
        yield i**2  # here the state is suspended and the value is returned.IN next call, it is resumed from the next statement

for x in gensquares(5):  #generators usually called in a loop so that it can be resumed
    print('generator values returned =>', x)
# if you see the object that is returned it is a iterable object
genobj = gensquares(4)
print('object returned by generator ==>', genobj)
#calling next method on the object
print('call next on generator object==>', genobj.next())
#another way to call next is using inbuilt next method
#see how the next value is retrieved from generator func - here it maintains the state and sends the next value
print('call inbuilt func next on generator object==>', next(genobj))
def gen():
    for i in range(10):
        x = yield i
        print('send example=>', x)
g = gen()
next(g)
#send v/s next - when send is sent to a generator, it returns the same value
#it can be used to send terminate code
g.send(77)
#if you call next , it will return 'none' for x. it does not assign the i value to x
ii=next(g)
print(ii)
next(g)
'''
Generator Expressions - like list comprehension but coded in round brackets
you call next on it to generate the next value
A Gen expresssion like a Gen func returns a Gen object on which you call next to get values
But if the Gen expression is used in a iteration context - for eg. wrapped in a list  - it will generate the values
This is because the iteration context automatically calls the 'next' method on the 'iter' object, i.e the Gen object
'''
# this returns a generator func instead of a list because of round brackets
genexp = (x**2 for x in range(5))
l1 = next(genexp)
l2 = next(genexp)
print('generator expression example ==>', l1, l2)
#Since gen expr is a iterable it can be used in a loop or any iteration context like calling list func, any, etc.
for num in (x**2 for x in range(5)):
    print('gene expr in a loop ==>', num)
lstgen = list(x**2 for x in range(5)) # here list is a iteration context and automatically calls next on the gen func
print('gen expr in a list iteration context==>', lstgen)
#Gen expression can also use filter clause like normal list comprehension
lstf = ''.join(x for x in 'aaa bbb c'.split() if len(x)>1)
print('gen expr example of filter==>', lstf)
'''
Gen func and Gen expression have one important property compared to other iterable objects
They support only one active iteration,i.e it is like a singleton object that supports only one pass of iteration
Even if you create multiple 'iter' objects from the Gen func/Gen exp, they all point to the same and after iterating it exhauts
Lists on the other hand can create multiple iter objects
This means that you need to be careful when using generators. They are usually used for one-shot iteration
If different places in code access the same gen object, they might get unexpected results
'''
linee= 'aa bbb c'
def gensub(line):
    for x in line.split():
        yield x.upper()
#get the gen object
geni = gensub(linee)
#call iter twice to generator two iter objects
it1 = iter(geni)
it2= iter(geni)
#now calling next on the different objects will not result in independent iteration, the second gets the value from where the first left 
f = next(it1)
s = next(it2)
# checking if the object returned by iter is same as original gen object - which is true
#this it acts like a singleton. It returns itself
tst = it1 is geni
print('example proving single active iteration of gen func==>', f, s , tst)
#lists on other hand iterate independently as the iter object are different
lis1 = [1,2,3,4]
#generate the iter objects twice by calling the iter func
#here the iter func on a list returns a new object each time it is called
lit1 = iter(lis1)
lit2 = iter(lis1)
#here you will see both producing the same value as they are different iter objects
fl = next(lit1)
sl= next(lit2)
print('example of list to prove multiple iteration objs ==> ', fl, sl)
'''
Generator example in python libraries
the os module uses it to walk the file directory paths
By using yield, the program does not have to wait for the entire file path to be returned
'''
import os
for (root, sub, files) in os.walk('.'):
    for names in files:
        print(root, sub, names)
# or just get the generator and use iter
go =  os.walk('.')
git = iter(go)
'''
User -defined iterables in classes
You can define a class to be an iterable with a __iter__method that returns self or object with __next__ method 

class someiterable:
    def __iter__(): #return self or another object
    def  __next__(): # used if self is returned
'''
'''
Comprehension Syntax summary
We have seen comprehension can be used for creating :
1. List - by using the [] brackets
2. Generators - by using the () brackets
3. Sets - by using the {} brackets
4. Dictionaries - by using the {} brackets but different syntax than sets. See below example
'''
#list creation 
lc= [x*x for x in range(5)]
print('list creation using comprehension==>', lc)
#Generator - used to create list of items but first creates a generator function
gfc = (x*x for x in range(5))
print('generator creation using comprehension==>', gfc)
#set creation
sc = {x*x for x in range(10)}
print('set creation using comprehension==>', sc)
#dict creation
dc = {x:x*x for x in range(10)}
print('dict creation using comprehension==>', dc)
# set and dict can also be created using generator functions
sg = set(x**x for x in range(10)) #set is an iterable and a gen object is created in the comprehension
print('set creation using generator comprehension==>', sg)
dg  = dict((x,x**x) for x in range(10)) #dict is an iterable
print('dict creation using comprehension==>', dg)
'''
In python 2, you need to be careful about the scope of the loop variable
The loop variable is not local and sometimes can unintentionally overwrite the enclosing scope of function
In python 3, the loop variables are local scope assigned
Other variable in the comprehension statement follow the LEGB rule
'''
x=2
lex = [x*2 for x in range(5)]
#here you will see that value of x is overwritten by the loop value
print(x)
'''
Performance of for loops, list comprehension, map and generators
Usually, list comprehension are found to have better performance
But depends on certain situations. Map with in-built func performs better than using a custom func/lambda
Similarly, Generators are better where large lists are required as they save memory
List comprehension with filters might not perform so well
For loops are easy to read
'''




    
    