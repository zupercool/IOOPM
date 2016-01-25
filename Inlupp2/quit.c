#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include "quit.h"

char confirm;

//The function that decides if to exit
bool quit_action(void){
    printf("Do you want to quit? [Y/N]\n");
    scanf("%s", &confirm);
    if (confirm == 'y' || confirm == 'Y') return false;
    if (confirm == 'n' || confirm == 'N') return true;
    else printf("Not a valid answer, try again\n\n"); //invalid inpu
       return quit_action();
}
