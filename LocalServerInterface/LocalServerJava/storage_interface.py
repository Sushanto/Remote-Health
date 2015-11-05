#!/usr/bin/python

"""
Format the output of security module
"""
import binascii, sys

outfilename = 'user_store.txt'

def put_user(userid, salt, key):
	"""
	Store the userid, salt and key in a file; salt and key are in bytearray format
	"""
	global outfilename
	newentry = userid + ':' + salt + ':' + key + '\n'
	
	with open(outfilename, 'a') as outfile:
		outfile.write(newentry)

def get_user(userid):
	"""
	Retrieve the salt and key for the given userid
	"""
	global outfilename
	retval = (None, None)
	with open(outfilename, 'r') as infile:
		line = infile.readline()
		while line != '':
			contents = line.split(':')
			if userid == contents[0]:
				retval =  (contents[1], contents[2].rstrip())
			line = infile.readline()
	return retval
	
	
if __name__ == '__main__':
	pass
