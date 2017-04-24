import sys
for line in sys.stdin: 
	line = line.strip()
	gather = line[:22]
	gather2 = line[22:]
	delete = gather.replace("We have a new message ", "")
	#outfile.write(delete)
	print gather2

	


# with open('data.txt', 'r') as infile, open('data.txt', 'w') as outfile:
#     data_new = infile.read().replace("We have a new message ", "")
#     outfile.write(data_new)