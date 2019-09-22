'''
Created on 27-Jan-2018

@author: avdhut
'''
#Data type examples
#Numbers - Integers , floats are simple
#String examples
from asyncore import read
from audioop import reverse
strg = 'my first example' 
#strings are sequence of characters that can be accessed using index
print('the first char is ' + strg[0] + ' fourth char is ' + strg[3])
#find the length of string
print(len(strg))
#access a part of string. Here the first index is included while the last is not
print('part of string ' + strg[1:6])
#access of other end using negative index
print('negative index last one - used from other end ' + strg[-1])
print('negative index second last - used from other end ' + strg[-2])
print('negative index another way using length ' + strg[len(strg)-1])
#to access a part of a string
print('part of a string '+ strg[2:])
# to access from start to a point. Here you get 8chars - upto the 9th. same as strg[0:9]
print('part of a string from start '+ strg[:9])
#in reverse way - to get a part. here you get all except the last char
print('part of a string in reverse way ' + strg[:-1])
#entire string
print('entire string ' + strg[:])
#slicing is very useful when you want to know the args passed when executing a python program
#sys module of python stores args in a variable argv. So if you do sys.argv[1:], you get all agrs 
#and not worry about the file name in the command line

# strings are immutable
#strg[0] = 'z' #uncomment to get an error cannot modify a string
#convert a string to a list and you can change the chars now
lststr = list(strg)
print('list from a string')
print(lststr)
lststr[1]='x'
print('list from a string after modification ')
print(lststr)
print('with join  ' )
print(''.join(lststr));
#can also modify using a bytearray
ba = bytearray(strg)
#notice the 'b' which indicates byte
ba.extend(b' good job')
print('bytearray modified ' + ba.decode())
# other methods of string
print('finding the index of a string/char is ')
print(strg.find('ex'))
print('replacing a value in a string  ' )
print(strg.replace('ex','aa'))
#you can also chain
exstr = 'aaa, bbb,ccc '
print('stripping the whitespace last char and then splitting on a delimiter ')
print(exstr.rstrip().split(','))
#formatting
print('replacing with formating example is %s' % strg.replace('ex','ff'))
print('replacing {} with another formating example is {}'.format(strg, strg.replace('ex','ff')))
#string uses single or double quotes but one can be used inside another for escaping
#you can use three single or double quotes for multi-line. useful for encoding xml, json, etc
multiStr = '''
this is a multiline string
second line contains more details
'''
print('multiline example is ' + multiStr)
print( multiStr)
#to check if a character exist
print('check if char z exists in hello  =>', 'z' in 'hello')
#skipping and reversing a string is a useful function
#this can be achieved using three-limit slices
# the 2 limit defaults to one step skip
s = 'abcdefghijklmnop'
skp = s[1:10:2]
print('skipping chars by 2 is =>', skp)
skp = s[::3]  #defaults to 0
print('skipping chars by 3 is =>', skp)
#you can skip from reverse end. This can reverse the string
ss1 = "avdhut"
srev = ss1[::-1]
print('string reversed example is =>', srev)
ssrev = s[10:1:-2]  #when you use -ve skip. the first two are indexes from the start
print('skipping form other end =>', ssrev)
#converting a int from a file or user input to a str or vice versa is done as below
print('converting a int to a string =>', str(42))
print('converting a string to a int =>', int('42'))
#converting a char into ascii int and vice-versa using ord and chr function
print('converting a char into int =>', ord('c'))
print('converting a int into char =>', chr(115))
#formatting string
print(' this is the %d %s of a string formatting' % (1,'example'))
print('another {} of formatting, the {} way'.format('way', 2))
print('string end #######################################')
###############
#pattern matching is in module called re and not in string
import re
match = re.match('Hello[\t]*(.*)world', 'Hello      python world')
#this gives a group of matches - we have printed the first
print(match.group(1))
##########################################################
#List object type
#Lists have same sequence operations like string
#lists can hold different object types and can grow unbounded
exlst = [123, 'hello', 2.31]  #one is an integer,another string and a float
print('access list using index')
print(exlst[0])
print('access part of list')
print(exlst[1:])
print(exlst[:2])
print('access part of list from end')
print(exlst[:-1])
print('expanded list')
print(exlst + [444,'world',555])
#type specific operations
print('using append to add')
print(exlst.append('world')) # method used to grow a list and prevent out of range errors
print(exlst)
print('removing object using pop')
print(exlst.pop(1))  #pop is removing but using index
print(exlst)
print('inserting object')
print(exlst.insert(2, 'python'))
print(exlst)
#all the above are in-place changes. So if you do the below, exlst is assigned 'None' and is garbage collected
#exlst =exlst.append('aaa') #this is wrong
#you can also insert using slicing syntax
lt1 = [1,2,3]
lt1[1:2]=[8,9] #this replaces and inserts one. you can use to insert another list
print('insertion using slicing', lt1)
lt1[1:1]=[11,12] #this is just inserting
print('only inserting using slicing', lt1)
lt1[1:2]=[] # equivalent to deleting
print('deleting using slicing', lt1) 
lt1[:0]=[33,44] #equivalent to inserting a list at front while appends adds at the end
print('appending in front using slicing', lt1)
print('removing object')
print(exlst.remove('python')) # removing is done using values
print(exlst)
#this creates a copy of the list. another way is to call the copy method
ltcop = lt1[:] #if lt1 is changed, it does not affect ltcop
print('copy of a list is =>', ltcop)
#lists like other data types in python can be nested. You can have a dict inside a list
# and a list inside a dict
nestLst = [[1,2,3],
           [5,6,7],
           [9,10,11]]  #can be multiline if in a square bracket
print('nested list is accessed')
print(nestLst[1])
print(nestLst[1][2])
print(len(nestLst))

#List have operations known as list comprehension expression
iterlst = [[1,2,3],[4,5,6],[7,8,9]]
col2 = [row[1] for row in iterlst]  #comprehension exp are coded in square brackets and consist of a looping construct with a shared variable
# the list should contain an iterable object. In this case, it was a nested list. So you got the second column from the list
#if the list is simple,for eg. list of integers, it gives an error that it is not iterable 
#comprehension can be used with sequence objects or iterable objects like lists, sets, strings, tuples, also files
print('comprehension expr example')
print(col2)  #original list is unchanged
#comprehension expr can be used to build new list
newlst = [row[0]+10 for row in iterlst]
print('comprehension expr example of building new lists')
print(newlst)
#you can have different ways of creating using built-in functions like range,map, filter, sum
#example of a list created using the range function
raglst = list(range(4))
print('example of list using range')
print(raglst)
raglst =list(range(-6,7,2)) #builds a range from -6 to 6 with hops of 2
print('example of list using range and hops')
print(raglst)
raglst = [[x**2, x**3] for x in range(4)]
print('example of list using range in comprehension expr')
print(raglst)
maplst = list(map(sum, iterlst))
print('example of a list creation with map')
print(maplst)
#sorting list is by default in ascending order.
#but this can be changed using special keyword arguments
lt2=['abc', 'ABD', 'aBe']
lt2.sort()
print('sorted list is =>', lt2)
lt2.sort(reverse=True)
print('sorted using reverse is =>', lt2)
lt2.sort(key=str.lower)
print('sorting on lower case is=>', lt2)
#In sort and append, the list is modified in place. So you do not get a new list that is sorted.
#instead these functions return a value that is 'None'
# to get a new list you need to call the inbuilt sorted function
ltsort = sorted(lt2, reverse=True)
print('sorting using sorted function to get new list', ltsort)
#Other functions in List##
ltf = [1,2,3]
#extend iterates through each item while append adds to the end. But append can add only one item 
#while extend can add many items
ltf.extend([5,6,7])
print('extend function on list =>', ltf)
#extend is similar to slicing also. can be also done by
ltf[len(ltf):] = [11,12,13]
ltf.reverse()
print('reverse function on list =>', ltf)
print('pop function on list =>', ltf.pop())
#reversed inbuilt function can also be used
print('reverse using inbuilt function', list(reversed(ltf)))
print('index of the list =>', ltf.index(3))
ltf.remove(3) #removing a value
print('removing a value =>', ltf)
#removing using index can be done by the del function
del ltf[0]
print('deleting using index and del function', ltf)
print('list end #######################################')
###################
#you can create 'Generators' when you use curved brackets in comprehension
gen = (sum(row) for row in iterlst)
print('example of a generator')
print(next(gen))

#####################
#comprehension is also used to create dictionaries
mapex = {i:sum(iterlst[i]) for i in range(3)}
print('example of a dict creation using comprehension')
print(mapex)
###################################################
#dictionary object type
#dictionary like lists can contain multiple types, grow unbounded and are mutable. But they are not ordered
#one way to create it is initializing at start
dicex={'dish' :'noodles', 'quantity':4, 'type':'flat'}
print('example of fetching a dict value ' + dicex['dish'])
dicex['quantity'] += 1
print('example of manipulating a dict value ')
print(dicex['quantity'])
#another way to create - dict are usually created using a empty curly brackets
dicex={}
dicex['dish'] ='curry'
dicex['quantity'] = 5
dicex['type'] ='spicy'
print('example of creating a dict using empty brackets')
print(dicex)
#you can also create dict by using the dict type constructor. Notice the '=' used and no quotes for keys
dicex = dict(dish='rice', quantity=6, type='steamed')
print('another example of creating a dict using constructor of dict type')
print(dicex)
#another way of creating using zip. useful when reading from a file at runtime 
dicex = dict(zip(['dish', 'quantity', 'type'],['pasta',2,'red']))
print('another example of creating a dict using zip')
print(dicex)
#creating dict using comprehension
cdict = {x:x*2 for x in range(10)}
print('dict created using comprehension =>', cdict)
# nested dicts and different types in dict
#below we have a dict that has a nested dict and also different types like list and int
nstdic = {'name': {'first':'bob', 'last':'smith'},
          'jobs':['manager','supervisor'],
          'age':25}
#to access the values
namedic = nstdic['name']
print('accessing name nested dic')
print(namedic)
# to access a value of the nested dic
#note that in a dic order is not maintained. values accesses can be any order
print('accessing value of a nested dic')
print(nstdic['name']['first'])
print('accessing list value of a dic')
print(nstdic['jobs'])
print('accessing a particular list value of a dic')
print(nstdic['jobs'][1])
print('adding values to a list inside a dic')
nstdic['jobs'].append('developer')
print(nstdic)
#accessing a non-existent key is error
#print(dicex['name']) #uncomment to see the error
#for such cases you can check the existence of a key and use it
tst = 'name' in dicex
print('check if the key is there ')
print(tst)
#another way is using if
if not 'name' in dicex:
    print('name key does not exist')
#other ways to check keys
#if the key does not exist,it gives back the specified default value which is 0 here
kval = dicex.get('name', 0)
print('no key but default value is specified')
print(kval)
kval = dicex['name'] if 'name' in dicex else 999
print('using the if else while accessing the key')
print(kval)
kvalb= dicex.has_key('dish')
print('checking with has key method')
print(kvalb)
#sorting of keys in dict
# there are multiple ways to achieve this but a new method called sorted can be used
#one way is to get the keys as list and sort it
srtdic = {'a':'first', 'b':'second','c':'third'}
kys = list(srtdic.keys())
print('unsorted keys are', kys)
kys.sort()
print('sorted keys using list sorting', kys)
#iterating through sorted list
for k in kys:
    print(k, 'sorted using list ==>', srtdic[k])
# the new way using sorted on dic directly - you do not need to call keys
for k in sorted(srtdic):
    print(k, 'sorted using sorted method on dict ==>', srtdic[k])
#keys(), values(), items() are methods to access the keys,values and key-value tuples
#these methods return view objects in 3.0 and are not lists. you have to wrap them in a list method if you want a list
dval = srtdic.values()
print('values in dict are =>', dval)
dtup = srtdic.items()
print('items in dict are - same as tuples of key-value =>', dtup)
srtdic['d']=  'fourth'
print('check the contents of dict =>', srtdic)
#deleting a key entry in a dict
del srtdic['d']
print('deleting by key in a dict using del =>', srtdic)
#you can also delete an entry by pop method
srtdic['d']=  'fourth'
srtdic.pop('d')
print('deleting key in a dict using pop', srtdic)
#you can merger two dict using update method
srtdic.update(nstdic)
print('merging two dict using update', srtdic)
#iterating through keys - you need not use the keys() method. Dict support iterating even without the keys() method
itdict = {'a':'one', 'b':'two', 'c':'three'}
for k in itdict:
    print('iterating each key in dict without keys() method => ' + k +'\t' + itdict[k] + '\n' )
#clearing a dict of all keys
cdict.clear()
print('clearing a dict =>', cdict)
#you can find keys based on values also
#this returns a list because there can be many keys for a value
keyv = [key for (key, value) in srtdic.items() if value=='second']
print('getting a key from a value using items =>', keyv)
keyv = [key for key in srtdic.keys() if srtdic[key]=='second']
print('another way of getting a key from a value using keys =>', keyv)
#you can only use immutable structures as keys - thus objects, tuples can be used as keys but not lists and sets
dictt = {}
dictt[('a', 'b', 'c')] = "alpha"
print('a dict with tuples as a key =>', dictt)
print('dict end #######################################')
########################################
#Tuple data type
#Tuples are sequences like list but immutable, they cannot be changed once initialized
#hence they cannot grow or shrink. They are ordered collection
#Tuples are initialized using round brackets
tup= (1,2,3,4,5,6)
print('length of tuple is ', len(tup))
#accessing a tuple - using indexes like lists
print('accessing a tuple value', tup[1])
#accessing a part of tuple - supports same operations of slicing like lists and strings as it is ordered collection
print('accessing a part of tuple ', tup[:-1])
print('accessing a part of tuple ', tup[0:2])
print('accessing tuple via index => ', tup.index(3))
print('to find the occurrences of particular value using count =>', tup.count(4))
#get an error if you try to change it. It is immutable 
#tup[0] = 9 #uncomment to see an error
print('tuple can be modified ==>', tup + (7,8,9))
print('check if original tup is modified =>', tup)
#as you cannot modify a tuple, it creates a new one on any operation on it
tup2 = (10,) + tup[1:] # a trailing comma is added to let python know it is a tuple. Otherwise, it will consider it as an integer
print('new tuple created by operating on another one =>', tup2)
#use concatenation to create a new tuple
tupm1 = (1,2) + (1,3)
print('new tuple created using concatenation =>', tupm1)
tupm2 = (1,2)*4
print('new tuple with repetition =>', tupm2)
#but you can change the contents of a tuple but not to the top level tuple itself
tupmd = (1, [2,3], 4)
#cannot do this
#tupmd[1]='hello'
tupmd[1][0]='hello'
print('tuple with the contents modified =>', tupmd)
tup3 = 'hello', 3.0, [444, 555, 666]  #you can leave out the round brackets but not a good way for readability
print('mixed tuple can be accessed =>', tup3[2][2])
tups1 = ('bb', 'aa', 'cc')
tups2 = sorted(tups1) #tuple does not sorted func like list - you have to convert it into a list
print('sorting a tuple using inbuilt sorted func =>', tups2)
print('original tuple is not changed - still unsorted =>', tups1)
lsttup = ['xx','yy','zz']
print('converting a list to a tuple =>', tuple(lsttup))
print('converting a tuple to a list =>', list(tups1))
#tuples can be accessed via position and not via name like dict
# but you have a feature called 'namedtuple' that can enable this
#for named tuple, you have to first import the function
#then creates a class and this class can be populated with a tuple
#namedtuple are a tuple/class/dict hybrid
from collections import namedtuple
tupclss =  namedtuple('tupclss', ['name','age','job']) #create a class after import
tuprec  = tupclss(name='bob', age=45, job='manager') #create a record of that class
print('printing a named tuple =>', tuprec)
print('accessing a named tuple using index 1 =>', tuprec[1]) 
print('accessing a named tuple using attributes =>', tuprec.name)
#you cannot access like dicts, you have to first convert to a dict
#print('accessing a named tuple using attributes =>', tuprec['name'])
tupdic = tuprec._asdict()
print('tuple converted to a dict =>', tupdic)
print('accessing a tuple converted to a dict =>', tupdic['job'])
#namedtuples also support unpacking values
for x in tuprec:
    print('accessing each vale of tuple =>', x)
print('tuple end #######################################')
###################################################
#File data type
#You open a file with write , read or writeread mode. Read mode is default
#file always writes and reads in strings. Hence you have to convert any object into string before reading. Here you can use inbuilt func like int, float, etc 
fl = open('datatp.txt', 'w')
rslt = fl.write('first example of file\n')
fl.write('second line in file\n')
print('printing result of a file write =>', rslt)
fl.close()
fl = open('datatp.txt') #default mode is read
fltxt = fl.read()  #read - reads all lines in to one string
print('all file contents are =>',fltxt) # read prints all the lines
fl.close()
#example of reading lines
fl = open('datatp.txt')
fltxtl = fl.readline()
print('first line in file is =>', fltxtl) #file ends when an empty string is read
#file iterator is the best option to read files
print('printing using iterator')
for line in open('datatp.txt'): #do not use open('xxx').readlines() as it loads the whole file in memory. without readlines it uses iterator 
    print(line)
#to write to a file, you have to convert the objects into strings    
x, y, z = 3,4,5
sr='hello'
dicw ={'a':1, 'b':2}
lstw = [7,8,9]
flw = open('datafilecvt.txt', 'w')
flw.write(sr + '\n')
flw.write('%s,%s,%s\n' % (x,y,z)) #convert the integers to string
flw.write(str(lstw) + ' $ ' + str(dicw) + '\n') # convert the list and dic to a string
flw.close()
flw = open('datafilecvt.txt', 'r')
chars = flw.read() #read raw strings
flw.close()
print('reading the objects converted into strings=>', chars)
#if you do not want to convert into string but store native objects - use pickle. pickle performs serialization
import pickle
flw = open('datafilecvt.txt', 'w')
pickle.dump(dicw, flw) #dump the dic into the file
flw.close()
flw = open('datafilecvt.txt', 'r')
dicrd = pickle.load(flw)
print('dic object read using pickle =>', dicrd)
#you can also store the objects as json
import json
jstr = json.dumps(dicw)
print('json string of dic =>', jstr)
jstr2 = json.loads(jstr)
print('json string read of dic =>', jstr2)
# to write and read json from a file
json.dump(dicw, fp=open('datafilecvt.txt', 'w'), indent=4)
print('json stored in file =>', open('datafilecvt.txt').read())
readj = json.load(open('datafilecvt.txt'))
print('json data read from file =>', readj) 
#you can use context manager to close the file automatically
print('reading file using context manager - with clause =>')
with open('datatp.txt') as myfile:
    for line in myfile: #internally this uses iterator
        print(line)
#you can also use try finally clause
print('example of try-finally clause =>')
myfile = open('datatp.txt')
try:
    for line in myfile:
        print(line)
finally:
    myfile.close()
#writing and reading binary files
import struct  #struct is needed to create binary data 
packed = struct.pack('>i4sh', 7, b'spam',8) #create some binary data
bfl = open('databi.bin', 'wb') #b used to indicate binary
rl = bfl.write(packed)
bfl.close()
print('result of writing a file=>', rl)
data = open('databi.bin', 'rb').read()
print('binary data read =>', data)
print('binary data unpacked=>', struct.unpack('i4sh', data))
print('file end #######################################')
#############################
#sets
#sets are mutable and unordered collection but they can contain only immutable objects
#set can be created using curly brackets or the set method
st = set([1,2,1,3,5,3,4,7,4])
print('set is =>', st)
#sets operations like or, and, intersect, union can be performed
set1={'a', 'b', 'c'}
set2={'a', 'd', 'e'}
print('intersection of sets =>', set1&set2)
print('union of sets =>', set1 | set2)
print('difference of sets =>', set1-set2)
print('superset of sets =>', set2>set1)
#sets are also used to remove duplicates
print('removing duplicates =>', list(set([1,1,2,2,3,4])))
#can be used for order neutral comparison without sorting
print('order neutral comparison=>', set('hello')==set('olhel'))
#to validate membership
print('membership test=>', 'o' in set('hello'))
#set has methods to perform the same function as above
print('intersection of sets using method =>', set1.intersection(set2))
print('adding items using method =>', set1.add('spam'))
print('update is union or in-place merge =>', set1.update(set2))
print('removing from a set =>', set1.remove('c'))
print('checking if set is immutable after adding and removing =>', set1)
print('set is iterable =>')
for i in set1:
    print('items in set1 are =>', i)
#sets can contain only immutable objects. Hence you cannot add list or dict to sets
#but tupel can be added. set is like a dict with null values and keys are objects that are hashable or immutable
#s1 = st.add([1,3,4])   #uncomment to see the unhashable error
#s1 = st.add({'a':1})    #uncomment to see the unhashable error
s1 = st.add((1,3,8))
print("adding immutable tuple to set =>", s1)
#comprehension is also used to create set and dictionaries
setex = {sum(row) for row in iterlst}  # set is created using curly brackets
print('example of a set creation using comprehension')
print(setex)  

#####################################
#floats and Decimals
print('float example')
x=float((3/2)+(8/3))
print(x)
import decimal
print('decimal example', decimal.Decimal('3.14'))
print('decimal addition', decimal.Decimal('3.14')+1)
########################################
#conversion to hex, octal and binary
x=bin(3)
print('binary 3 is =>', x)
x=hex(10)
print('hex 10 is =>', x)
x=oct(12)
print('oct 12 is =>', x)
x=0b100
print('convert binary to int =>', int(x))
########################################
#Boolean objects
#internally python has a type called bool that is numeric in nature 1 and 0 but represents True and False when printed
#There are two boolean types - True and False
#There is a special object type called None used to initialize objects
print('boolean types are =>', 2>1)
print('boolean types are =>', 1>2)
#here none is used to set the size of a list. None is an object that has memory - it does not mean 'undefined' 
lt=[None]*10
print('initialize list with none =>', lt)
#########################################
#how to check types
#There is 'Type' object that can be obtained from the 'type' method
#In 3.0 it is called the 'class' object. type method returns class
#it is not advisable to use this in python as it means that the code is looking for a specific type
#this breaks the inherent flexibility of dynamic data types in python
print('type of the variable lt is => ', type(lt))
#you can also compare and check the type by using following methods
print('comparing two type ==>', type(lt)==type(maplst))
print('checking for a type using the type name =>', type(lt)==list)
print('checking by using the isInstance method =>', isinstance(lt,list))
###########################################
#User defined class
#each method has an implicit self variable
class Worker:
    def __init__(self,name,pay):  #initialize method  - note double underscores in init
        self.name = name
        self.pay = pay
    def lastname(self):
        return self.name.split()[-1]
################
bob = Worker('Bob smith', 10000)
print('calling method lastname of bob =>', bob.lastname())
##########################################################
#to check equivalence - there are two ways
l1=[1,('a', 3)]
l2=[1,('a', 3)]
print('check eqivalence using == sign - compares the content =>', l1==l2) #does a deep nested comparison
print('check eqivalence using is sign - compares the pointers =>', l1 is l2)
#but short strings can give a different results as python caches small strings
ss1='hello'
ss2='hello'
print('check eqivalence using == sign - compares the content =>', ss1==ss2) 
print('check eqivalence using is sign - compares the pointers =>', ss1 is ss2) # this is also true as python caches small strings
#every object is either True or false. i.e If it is empty, it is false otherwise true
#you can also check the empty values liek this
if x:   #this is the same as if x!='' 
    print('check for null value => ' + 'x is null')
#cyclic data structures. Python prints a [..] if the data is cyclic
L=[1,2,3]
L.append(L)
print('cyclic data structure =>', L)






