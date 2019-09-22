'''
Created on 25-Jan-2018

@author: avdhut

demonstrates import and from statement
when import is used, the module is loaded and executed and you can use the variables/attributes
defined in that module
when from is used, you can only use the variables specified.
uncomment the from statement and the print(z) statement to check
dir function gives the inbuilt variables in a module
import enables namespace and hence it does not overwrite variables with same name
but from overwrites the variables.
uncomment the first section of the file to check the overwrite. Hence import is preferred to from   
'''

#import helloworld

#p=helloworld.x
#q=helloworld.y
#r=helloworld.z
#print('the imported variables are p=' + p + ' q=' + q + ' r='+ r)
#print(dir())
#print(dir(helloworld))

from helloworld import x,y
from audhut.firstex import helloworld
p=x
q=y
print('the imported variables are p=' + p + ' q=' + q)
#r=z #attempting to assign this gives error as it is not imported
x='from second file'
print(x) #x is overwritten because we used from
print(helloworld.x)

