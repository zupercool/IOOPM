CFLAGS=gcc -std=c11 -Wall -ggdb -o  -I. -D_GNU SOURCE
CODEFILES=$(shell ls *.c *.h)

all:
	gcc -std=c11 -Wall -ggdb -o main2 main2.c quit.c data.c system.c -I.

clean: 

	rm -f   data
	rm -f 	lol2
	rm -f 	quit
	rm -f 	system

doc: $(CODEFILES)
	doxygen config-file
