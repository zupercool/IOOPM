#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include "data.h"
#include "quit.h"
#include <system.h>

void clear_stdin() // tömmer buffern (stdin)
{
  while (getchar() != '\n');
}

int ask_question_int(void){
	int num = 0;
	int temp = scanf("%d", &num);
  	while(temp == 0){
  		puts("Use numbers!");
  		clear_stdin();
  		temp = scanf("%d", &num);
  	}
  	return num;
}

/*
  int ask_question_int(void){
  char *x = calloc(3, sizeof(char));
  scanf("%s", x);
  for(int i = 0; i<(strlen(x)); i++)
    {
      if (isdigit(x[i]) == 0){
	printf("Not a valid number\n");
	free(x);
	ask_question_int();
	i++;
      }
      i++;
    }
  int i = atoi(x);
  free(x);
  return i;
}
*/

int amount_adder( int A1, int A2){
  A1 = A1+A2;
  return A1;
}


int price_is_right( int a, int b){
  if (a == b) return a;
  else{
    printf("They have diffrent prices but are the same thing\n");
    printf("Will use the current price\n");
    return a;
 }
  return a;
}

bool ask_yes_no(){
  char *confirm = calloc(1, sizeof(char));
  scanf("%s", confirm);
  if (*confirm == 'y' || *confirm == 'Y'){
    free(confirm);
    return true;
  }
  if (*confirm == 'n' || *confirm == 'N'){
    free(confirm);
    return false;
  }
  else{
    puts("Not a valid answer, try again");
    free(confirm);
    clear_stdin();
    ask_yes_no();
  }
  return true;
}
