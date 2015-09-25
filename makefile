all:
	 gcc -std=c11 -Wall -ggdb -o main interface.c system.c main.c -I.

clean:
	rm -f main
	rm -f *.o
	rm -rf *.dSYM
