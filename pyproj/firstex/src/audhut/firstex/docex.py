'''
Created on 03-Apr-2018

@author: avdhut

This is where module documentation goes
explain here what the module does.
All the documentation including the one below for class and methods are included in the internal __doc__ method
use hash # only for small comments related to the statements, expression
'''
from pydoc import doc

def square(x):
    '''
    all function doc goes here
    '''
    return x*x #small comments go here

class Employee:
    '''
    class doc goes here
    '''
    def somefunc(self):
        "doc can be also in a literal string"
        pass


'''
all the documentation is stored in an internal method called __doc__
to access the doc strings, import the module, docex
docex.__doc__ gives the module doc
docex.square.__doc__ gives the function doc
docex.Employee.__doc__ gives the class doc
for doc of methods in class you follow the same convention - module.class.method.__doc__
Python provides  a inbuilt 'help' method that prints the man page of the module or type
you can also use it like the print example given above 
'''
import sys
help(sys)
#you can also use help(typename) to get info on some types
help(dict)

'''
Python provides a utility called pydoc that generates docs
you can generate the python doc by
In python 3.3 and above - 
python -m pydoc -b 
this starts a http server at a port that is given in the response of the above command
you can also specify the port by -p option
py -3 -m pydoc -b is another command
-b is  the browser mode support n 3.3 and above
For 3.2 and below - there is a gui support. For that you use the -g option
python -m pydoc -g
python -3.2 -m pydoc -b 
and specify the module name
'''
emp = Employee()
#prints class doc
print(emp.__doc__)
#prints the doc of a function inside a class
print(emp.somefunc.__doc__)













