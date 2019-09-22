'''
Created on 13-Jun-2018

@author: avdhut
'''


'''
Modules are the top level program unit, providing namespace to enclosed variables
Consist of three main statements:
1. import - allows a client to import a module as a whole
2. from - allows a client to import specific objects/names from a module
3.imp.reload - reloads the module without restarting python
'''
'''
How imports work
When an import statement is first encountered it is executed and performs these three steps
1. Finds a module file 
import statement does not have file extension. Python has a search path. 
2. Compile to byte code
Note that this happens the first time a module is loaded.Once it is loaded, python keeps track of it in a table called sys.module
In 2.x, it keeps the compiled file with extension, .pyc, in the same directory as the original py file
In 3.x, it keeps it in a sub-directory, __pycache__, and renames the file with the version number. The names are more
descriptive in 3.x. Instead of overwriting a file like in 2.x, it creates a different version for every compile.
In 2.x, the byte code has a version number encoded in it.Every import statement, python check if the module is loaded and checks 
if it is older version. If it is older version, i.e. if the source code has changed, then it reloads and recompiles the file
If not, this step is skipped 
3. Run the modules code to build the objects it defines
Executes statement from top to bottom
You can force it to re-import by imp.reload method
If the module has a top level statement like print - it is executed when the import statement is executed
'''
'''
Search path
Python search path is composed of concatenating the following paths in that order:
1. Home directory of the program
This is automatic. Python searches for the modules from the same directory as where the top-level module,
i.e the first program is being executed. Note that this modules will overwrite modules with same name in other directories.
2. PYTHONPATH directories
Setting the PYTHONPATH env variable.
3. Standard lib directories
Python automatically searches for the standard lib based on where you have installed python
4. .pth path file directories
This is an advanced feature. You can have a file with the extension '.pth', which contains the directory path in each line of where 
the files are present
It can be placed in the top level directory or in the /usr/local/lib/python2.7/site-packages or in /usr/local/lib/site-python
Python will add the directories listed in each line of that file and add it at the end of the search list - after pythonpath
5. lib/site-packages directory
Usually used by third party libs, this directory is added automatically to the search path. Usually this is also the place 
where the .pth file is placed
'''
'''
sys.path 
If you want to see the search path configured on your pc, looks for the sys.path variable of the sys built-in module
'''
import sys
print('search path is ===>', sys.path)  
'''
Import hooks
Python finds the module name using its search path and can load not just python but c.c++ modules linked
or java or c# in jython, etc when it encounters a import statement
You can make use of import hooks to redefine how to load files. For eg. Python uses import hooks to load files from archives
If the import module is a zip file, python uses import hooks to get the files in the archive
The doc of __import__ needs to be looked at for implementing this(in v3.3, it is importlib.__import__)
Python also supports optimized byte code files with exension.pyo - used by PyPy if the -O option is specified in the command line
'''
'''
Third-party extensions for Python typically use the distutils tools in the standard library to automatically install themselves,
so no path configuration is required to use their code.
Systems that use distutils generally come with a setup.py script, which is run to install them; 
this script imports and uses distutils modules to place such systems in a directory that is automatically part of the module search path 
(usually in the Lib\site-packages subdirectory of the Python install tree, wherever that resides on the target machine).
Third party tools like egg adds for dependency checking of installed python.
In future, there is a plan to replace distutils with distutils2
'''
'''
import statement
Two ways - using 'import' and 'from'
In import, the whole module is imported and you have to qualify the variables using the module name
In the example above, path was accessed using sys.path
Using 'from' copies all the variables into the importing modules namespace and you do not need to qualify it
Essentially, import is same as from - just one extra step of copying the variables into the namespace
Python loads the module only once it encounters the import statement for the first time
If you do import statement again, it just fetches the modules from its internal tables
So if any variable is initialized first and changed, importing it again will not change it back to the original value
'''
from sys import path
print('path using from ==>', path) #no need of sys.path
'''
Using 'from' copies the variable in the importing modules namespace.
So if the variable is changed, it does not change in other modules that import the same
But if the variable is mutable like a list, it changes everywhere
for eg.
from small import x,y (small has a variable x, and list y)
x=44 ==> changes only in this module , not in other
y[0] = 55 ==> this is mutable and changes everywhere
import small  
small.x = 33 ==> changes value in every module that imports
'from' create new variables with the same name as importing module. But the references are not copied
it is equivalent to 
x=small.x ==> assigning a variable x to x from small module
Generally,avoid the 'from xxx import *' statement as it can corrupt the namespace
similarly, even the from statement used in different modules can cause corruption and we need to be careful
import statement is recommended as if means that you have to qualify the variable with the module name.
It prevents name collision

There is a 'as' extension that can be  used with from statements to avoid name collision
If two modules define a function with same name, it can cause collision in the importing module 
using 
from xxx import func1 as xfunc1
from yyy import func1 as yfunc1
prevents name collision
'''
'''
Module files morph into a namespace
The first time a import statement is executed, it executes the top level statements in that file (not nested in func or classes)
The variables become part of that namespace. Any assignment then becomes a local - or global for that module
All the variables in that namespace can be accessed via the built in __dir__ or dir(M) method
Python also a builtin func called getattr(modulaNme, attrName) that takes the attribute name as a  string 
'''
#in the below example when the module testmodule is import, it executes the statements, print
# and sets the variables in its namespace

import testModule
#module namespaces are stored as dictionary objects
print('using __dict__ atttribute of a module ==>', testModule.__dict__) #gives a dict of variables and its value
print('using dir method of a module ==>', dir(testModule)) #gives a list of variables in the namespace of that module
print('printing a module imported by the module imported in this module ==> ', testModule.sys) #here sys is part of the imported modules namespace
print('example of getattr to find attr value ==>', getattr(testModule, 'name'))
'''
In python, the module is loaded only once when it is run or the import statement is executed
If you want to rerun the module, the reload func is provided - reload(ModuleName)
In v3.x, reload is part of the imp module, hence you have to use imp.reload 
reload reruns the files code and overwrites the namespace
It affects all client who have use import - it resets all the top level variables
reload affects a single module, not transitively to other import imported by the reloaded module
'''
'''
Package imports
You can also import for other directories or packages
For eg. if a module is on dir1/dir2/mymodule.py, you can import it using import dir1.dir2.mymodule
However, you need to ensure the following:
1.The root directory must be in the module search path - pythonpath. i.e it should be in sys.path.
The directory above dir1 should be in the search path
2.Each directory should have a __init__.py file
This file is used to initialize the module. It provides a hook where you can do module initialization 
It is run the first time a module name is imported. It is run only once an import is executed. 
A second import does not execute it. Unless you use reload(dir1.dir2).
You can use a __all__ list to define what to export when a directory is import using 'from dirc import *' statement.
'''
import audhut.secondex.pkgimpModule
#each directory becomes a module object whose namespace is assigned to variable is defined in __inti__file
print('variable adu in audhut directory in the init file ==>', audhut.adu) 
audhut.secondex.pkgimpModule.secfunc()
#To make it more convenient and avoid typing the full path name, you can use the from clause
from audhut.secondex import pkgimpModule
pkgimpModule.secfunc()
#alternatively use an alias using as clause
import audhut.secondex.pkgimpModule as secm
secm.secfunc()
'''
relative vs absolute import
In v3.0, you can do a relative import - import from the directory where the module is
from . import x => means that from the current directory import the module x
from .. import x ==> means from the sibling directory (one level up), import the module x
relative imports apply to only 'from' clause
What we saw in previous cases was absolute path , i.e searching according to sys.path variable setting
But the first module search path is the current working directory
'''
'''
Hiding variables - Private variables
Python does not have any access qualifiers. Hence hiding variables from a module is more of a convention
A variable with underscore is usually seen as private. for eg. _x, _yy
The 'From *' clause actually does not import such variables.However other clauses like import and also 'from' without * imports the variable
Another convention is to have a '__all__' list variable that contains the variables to export
'''

#from modaccess import * 
#print('public variables imported from modaccess=>', mpub)
#uncomment this line to see the error
#print('private variable imported from modaccess =>', __mpvt)
#print('check if variables not in __all__ are imported ==>', anpub)
'''
In built variables
__name__ is a built in variable that is given the value of the module name
However when a module is run as a top level script, __name__ is set to "__main__"
But if it is imported, __name__ is set to the name of the file in the importing module
__name__ is used to do unit tests - i.e. self test
If you want to test some func inside a module, you can use the __name__ variable to perform tests
These tests are only run when you run it as a top level script - because you call it __main__
But if it is imported, these tests are not called 
This variable can be used in various tricks
One way is to test as command line. The sys.argv contains the args passed in command line
You can check if the command line has args and then execute the self tests 
'''
import modaccess
print("name of imported module ==>", modaccess.__name__)
print("name of this file when executed as top level script ==> ", __name__) #here name is set to __main__

def stest(func, *args):
    res = args[0]
    for arg in args[1:]:
        if func(arg, res):
            res = arg
    return res

def leastnum(x,y):return x<y        
#to test the above func, we can add a self test using name
#This is not executed when imported in other modules
if __name__ == '__main__':
    print('doing self test using name')
    print(stest(leastnum, 7,4,8,2,5))
'''
Dynamically loading modules
If the module name is a string - probably given as an input then a simple import statement will not work
The import statement does not work on a variable
To do this use the __import__ built in func - __import__('someModuleNameinString') returns a module object
Another better alternative is to use the module 'importlib' - importlib.import_module('someModuleNameinString')
'''  