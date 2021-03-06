#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include "quit.h"
#include "data.h"


int main_loop(Goods **m_t, pall **m_p){
  printf("\n\n========Warehouse1337 v1.0========\n");
  printf("Choose what action to perform;\n");
  printf("(A)dd an item.\n");
  printf("(R)emove an item.\n");
  printf("(L)ist all the items currently in the warehouse.\n");
  printf("(E)dit an item in the warehouse.\n");
  printf("(P)ut something in basket\n");
  printf("E(x)it the program.\n");
  char action;
  scanf("%s", &action);
  
  if (action == 'a' || action == 'A'){
    add_action(m_t);
    return main_loop(m_t, m_p);    
  }
  if (action == 'r' || action == 'R'){
    puts("You've chosen to Remove an item. To proceed, please;");
    remove_action(m_t);
    return main_loop(m_t, m_p);    
  }
  if (action == 'l' || action == 'L'){
    list_action(*m_t, 1);
    return main_loop(m_t, m_p);
    }
  if (action == 'e' || action == 'E'){
      edit_action(m_t);
      return main_loop(m_t, m_p);
  }
  if (action == 'p' || action == 'P'){
    pal_action(m_t, m_p);
    return main_loop(m_t, m_p);
  }
  if ((action == 'x' || action == 'X') && (quit_action() == false)){
    printf("Thank you for using the warehouse1337 v1.2 software.\n");
    free_loop(*m_t);
    free_loop_pall(*m_p);
    return 0;
  }
  else {
    puts("\nThat's not a valid option, please try again.");
    return main_loop(m_t, m_p);
  }
  return 0;
}


// MAIN FUNCTION FOR WAREHOUSE
int main(){ 
  Goods *m_t = create_tree(); 
  pall *m_p = create_pall();
  main_loop(&m_t, &m_p);
  return 0;
}

//-----------CURRENT PROBLEMS---------
//free memory. alot of free memory
//go though valgrind without problems
//left_remove more proper so that simple things can be removed( works in bigger functions
//remove, if statser ( om man inte väljer valid shelf)
// när man går igenom ask_question_int och gör fel kommer man att seg.fault för man friar två gånger
