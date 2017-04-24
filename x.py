import sys
for line in sys.stdin: 
	line = line.strip()
	new = line.replace(',', '');
	gather2 = new[16:27]
	#gather3 = gather.replace("We have a new message ", "")
	
	#outfile.write(delete)
	print gather2
