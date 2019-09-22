'''
Created on 07-Mar-2018

@author: avdhut
'''
#python statements are indented but if you want multiline statements you have to enclose it is (), [] or {}
lstex = [111,  
         222,
         333]  #here the statement ends at the end of the [] bracket
#can also be used for compound statements
a,b,c =2,3,4  #this makes it a tuple
if (a==1 and
    b==2 and
    c==3):
        print('multiline compound statement')  #this line needs to be indented
a,b,c ='hel' #here the variables are not assigned the string but the char as it makes it a tuple and values assigned sequential
print('value of a is =>', a) #a is not hel! this is called unpacking
print('value of b is =>', b)       
#if you want all variables to be assigned the same value
a=b=c='hel'
print('value of a is =>', a) #a is now hel!
print('value of b is =>', b)
#another way of unpacking - if you want to unpack part of a string to one variable
((a,b),c )=  'he','llo'  #nested assignment
print('value a is assigned =>',a)
print('value b is assigned =>',b)
print('value c is assigned =>',c)

#this feature can be used to swap values without temp variables
nudge=1
wink=2
nudge,wink=wink,nudge
print('swapped values are =>', nudge, wink)
# concatenation makes new objects while augmented extension makes in place changes
L=[1,2,3]
M=L
L=L + [4,5,6]
print('new object created in concatenation, M is not changed =>', L, M)
L += [7,8,9]
print('new object place change by augmented assign, M is also changed =>', L, M)
#variables can only have letters, number and underscores
#variables with single underscore cannot be imported using from module syntax
#print is a method that internally calls the write method on a sys.stdout object
#by default this is the stdout. But you can redirect it to a file
logf = open('logex.txt', 'w')
#in 2.0, the below statement prints the file object
#print('example of a print redirected to a file', logf)
#the below statement prints the objec to the file
print >> logf, 'example of a print redirected to a file'
print 'example of a print redirected to stdout'
logf.close()
################################
#if statements
iex='world'
if iex=='hello':
    print('correct choice')
elif iex=='world':
    print('correct elif')
else:
    print('default choice')
#python does not have switch case statements
#hence multibranching is achieved using if-elif or using dict. dict 'get' method offers a default value which is similar to the default case statement
#'in' keyword can be used to scan lists
ll1 = [1,2,3,4]
#key = 3
key = 5
if key in ll1:
    print('using in word to scan list')
else:
    print('in word for scanning list - not found')
    
##################################
#Boolean statements
#In python, every non-null object is true and empty, null or None object is false
#Python uses words like 'or' and 'and' as logical operators
#logical operators return the object instead of True or False or 1 or 0. However they can be used to test in if or while statements
print('logical operators return the first or last object depending on the outcome', 2 or 3) # here short circuiting leads to return the first object
print('logical operators return the first or last object depending on the outcome', 2 and 3) # here the second object is returned
print('logical operator with empty objects ', [] or {})
print('logical operator with empty objects ', [] and {})
print('logical operator returns', 3 or {})
print('logical operator returns', 3 and {})
if ([] or 2):
    print('logical operator can be used in if statements')
#but if you use assignment statements with logical operators, it returns the object
bx = 2 or 3
print('value assigned with a logical operator', bx) #note that a boolean is not assigned but an object 
#ternary operator - left or right value is assigned while the logical operator is in the middle
#this feature can be used to return a non null object from a list of objects
#because of the above feature,in python you can test the null object without comparing to empty string 
if bx:  # equivalent to bx!=''
    print('no need of bx!=\'\'') 
ax = 'val' if bx else 'wrong'
print('value of ternery operator', ax)
#filter, any, all are in-built functions used to test or return non null values
lte = [1,0,'', 'hello']
ltn = list(filter(bool, lte))
print('non null values returned from filter', ltn)
print('values returned from all func', all(lte)) #checks if all are non-null
print('values returned from all func', any(lte)) #checks if any is null
#####################################
#while loop
wh=10
while wh:
    wh -= 1
    #if (wh==2): #uncomment the below two lines to see the else loop skipped
     #   break
else:
    print('else loop of while') #gets executed because there was no break
print('outside of loop') # gets executed only if break statement is executed
#else loop is useful for finding conditions and avoids the need for setting flags inside the loop
##################################### 
#for loop
for x in ['a','b','c']:  # x is the target
    print('value of each in for loop', x) 
#for loop for a tuple
tu = [(1,2),(3,4),(5,6)] #should be a list of tuple
for (a,b) in tu:
    print('for loop for a list of tuple', (a,b))
#above can be used for iterating items in dicts which are also tuples
dt = {'a':1, 'b':2, 'c':3}
for (k,v) in dt.items():
    print('key value in a dict using for loop', (k,v))
#same using a variable
for both in tu: #both is a variable
    a,b=both
    print('for loop for a list of tuple', (a,b))
# for counter loops we can use range function
#range function generates a series of numbers and you specify the steps also
#in python 3 you need to wrap this in list -> list(range(1,5))
print('range with lower and upper bound', range(5,15))
print('range with increment steps', range(5,60,5))
#to use in for loops
for i in range(10):
    print('use of range in loops', i)
#sometimes you can use range with length
wd = 'hello'
for i in range(len(wd)):
    print('using length for range', wd[i])
#or you can simply use
for item in wd:
    print('item in a string', item)
#range can be used for special cases like skipping, slicing,, etc inside the loop 
#sometimes you want to change a list but for loops might be misleading
ltm = [1,2,3,4,5]
for i in ltm:
    i += 2
    print('this only changes the items', i)
print('this does not change the list', ltm)
#to change the list we need range
for i in range(len(ltm)):
    ltm[i] += 2
print('this changes the list and items', ltm)
################
#zip func
#one can traverse two lists in parallel using zip
ltm2=[11,12,13,14,15,16] 
for (a,b) in zip(ltm,ltm2):
    print('using zip to traverse two lists', a,b,a+b) #if one list is longer, the contents are ignored. here 16 is ignored
#zip makes a list of tuple of two or more sequence objects
zz=zip(ltm,ltm2)
print('zip example', zz)
lll=list(zip(ltm,ltm2))  # still a list of tuples
print('making a single list using zip', lll)
#zip is used to construct dicts if you have list of keys and values
kk= ['a','b','c','d','e','f']
dzp = dict(zip(kk,ltm2))
print('dictionary created using zip ===>', dzp)
#################
#enumerate func
#sometimes you need the object/item and also the counter or offset
#using range you have to define a offset variable and keep track by incrementing
#enumerate gives you a free counter. It uses a generator func internally that calls 'next'
soff = 'hello'
for (i,ch) in enumerate(soff):
    print('use of enumerate giving offset and the item =>', i, ch)

eex = enumerate(soff)
print('printing an enumerate obj=>',eex)
print('calling next on enumeraate obj', eex.next())
####################
#iterable
#objects like file, lists, tuples, dict are iterables. Internally they implement __iter__ function
#that returns a iterator. The iterator has a next func that calls __next__ internally
#the next function raises a stopIteration exception indicating the end of producing results
#the for and while loops use internally these calls
#for objects like lists,dicts you have to call the inbuilt iter func to get a iterator
#iter(mylist)
#####################
#list comprehension
#list comprehension looks like a backward for loop
#it consist of an expression, a for statement with a variable and the iterable. All this is inside a sqaure bracket
#it always creates a new object
#list comprehension is faster than a for loop. But they can be written as a loop
lt=[1,2,3,4,5]
lt=[x+10 for x in lt]
print('list comprehension example is =>', lt)
# it can be used for files also.
#in the example below, the file is opened and new lines char at the end are stripped and all other chars converted to upper case 
#lines = [lines.rstrip().upper() for lines in open('somefile.txt')]
#extended list comprehension
#extended list comprehension provides ability to add more clauses like filter, etc
#filter clause - you can add conditions for a loop. For eg. checking if a file starts with a char 'p'
ltft=[x+10 for x in lt if lt[1]==12]
print('filter clause in list comprehension =>', ltft)
#nested loop - another extended form
ltn=[x+y for x in 'abc' for y in 'pqr'] #multiplies each column and row - like in a matrix
print('nested loop in list comprehension =>', ltn)
#many built-in functions like map,zip,enumerate,reduce,sorted process iterables 
# list comprehension can also be used in dict and set which use the curly bracket
dt={x:x*2 for x in range(1,10)}
###########################
#In python3, all built-in functions return iterable object. eg. map
#Hence you have to wrap all calls in list, etc if you want the data structure.






