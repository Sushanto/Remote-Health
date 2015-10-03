from pydrive.auth import GoogleAuth
from pydrive.drive import GoogleDrive

import os, sys

class RHDrive:
	"""
	A drive class for wrapping the necessary functions from pydrive
	"""
	def __init__(self):
		"""
		Initialize RHDrive
		"""
		self.gauth = GoogleAuth()	#initiate authenticator for OAuth2
		self.drive = None

	def get_auth_link(self):
		"""
		Returns the auth link required for authentication
		"""
		link = self.gauth.GetAuthUrl()
		return link

	def write_auth_link(self, local_filename):
		"""
		Write authentication link to local_filename for someone to read
		"""
		link = self.get_auth_link()
		with open(local_filename, 'w') as f:
			f.write(link)

	def authorize(self):
		"""
		Authenticate, try to get loaded credentials, if not possible then try CommandLineAuth
		"""
		self.gauth.LoadCredentialsFile("rhcred.txt")	#load saved credentials
		if self.gauth.credentials is None:
			self.gauth.CommandLineAuth()
		elif self.gauth.access_token_expired:
			self.gauth.Refresh()
		else:
			self.gauth.Authorize()
		self.gauth.SaveCredentialsFile("rhcred.txt")	#save credentials after authentication
		self.drive = GoogleDrive(self.gauth)

	def check_login_email(self):
		"""
		Check if we're logged into remotehealth.data@gmail.com (We don't want to log into any other account)
		This is a very hack-ish way to do this
		But the Drive REST API doesn't provide any better methods
		"""
		#check if there is a file called 'emailcheck.txt' containing the string 'this is remotehealth.data@gmail.com\n'
		filelist = self.get_files()
		emailcheck = None
		for file in filelist:
			if file['title'] == 'emailcheck.txt':
				toget = file
				break
		if toget is not None:
			#print toget.GetContentString()
			if toget.GetContentString() == 'this is remotehealth.data@gmail.com\n':
				return True
		return False

	def get_files(self):
		"""
		Get iterable list of files in root folder of GoogleDrive
		"""
		files = self.drive.ListFile({'q':'"root" in parents and trashed=false'}).GetList()
		return files

	def write_filelist(self, local_filename):
		"""
		write the names of the files in GoogleDrive to local_filename
		"""
		filelist = self.get_files()
		f = open(local_filename, 'w')
		for file in filelist:
			f.write(file['title'] + '\n')
		f.close()

	def update_file(self, server_filename, local_filename):
		"""
		Update the file called server_filename with the contents of local_filename
		"""
		filelist = self.get_files()
		toupdate = None
		for file in filelist:
			if file['title'] == server_filename:
				toupdate = file
				break
		if toupdate is not None:
			toupdate.SetContentFile(local_filename)
			toupdate.Upload()
		return toupdate

	def upload_file(self, filename):
		"""
		Upload a new file by name and content of filename, may cause naming conflict, should be unique
		"""
		newfile = self.drive.CreateFile()
		newfile.SetContentFile(filename)
		newfile.Upload()

	def retrieve_file(self, server_filename, local_filename):
		"""
		Get file called server_filename from GoogleDrive and save as local_filename
		"""
		filelist = self.get_files()
		toget = None
		for file in filelist:
			if file['title'] == server_filename:
				toget = file
				break
		if toget is not None:
			toget.GetContentFile(local_filename)
		return toget

def main():
	"""
	Script is designed to be called with a single command
	Commands supported are:	1. get (get a file from Drive)
							2. put (Update a file in Drive)
							3. link (Get authenticator link)
							4. list (Get list of files in top-level folder)
							5. new (Upload new file)
	See individual command calls for aguments
	"""
	rhd = RHDrive()	#initiate
	rhd.authorize()	#authorize
	#check if logged in to correct account
	if not rhd.check_login_email():
		sys.exit(1)

	command = sys.argv[1]
	if command not in ['get', 'put', 'link', 'list', 'new']:
		sys.exit(1)
	if command == 'get':
		#get <server_filename> <local_filename>
		s_filename = sys.argv[2]
		l_filename = sys.argv[3]
		f = rhd.retrieve_file(s_filename, l_filename)
		if not f:
			sys.exit(1)
	elif command == 'put':
		#put <server_filename> <local_filename>
		s_filename = sys.argv[2]
		l_filename = sys.argv[3]
		f = rhd.update_file(s_filename, l_filename)
		if not f:
			sys.exit(1)
	elif command == 'link':
		#link <filename>
		rhd.write_auth_link(sys.argv[2])
	elif command == 'list':
		#list <filename>
		rhd.write_filelist(sys.argv[2])
	else:
		filename = sys.argv[2]
		rhd.upload_file(filename)


if __name__=='__main__':
	main()
