'''
Created on 29-Jun-2018

@author: avdhut
'''
print('loading test module')
import sys


name = 'xxx'
age=42
_pvt = 'it is private'
_anotherpvt = 'another private'

#uncomment to check the __all__ variable
__all__=[name, _pvt] #usually variables with _ should not be included


def somefunc():
    pass

class someclass:
    pass

print('completed loading of testmodule')

