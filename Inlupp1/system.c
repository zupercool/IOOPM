#include "system.h"
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>




int amount = 0;






bool valid_place(char *place) // används vid inmatning av lagerhylla
{
  bool valid = false;
  
  // testar ifall den första char är en bokstav
  valid = isalpha(place[0]); 

  //testar ifall de andra char är siffror
  int i = 1;
  while(place[i] != 0)
    {
    valid = valid && isdigit(place[i]);
    i++;
    }  

  //test if the length make sence 
  if(i < 2)
    {
    valid = false;
    }
  
  return valid;
}


bool place_empty(char *place)
{
  place = NULL;
  for(int i = 0; i < amount; i++)
    {     
      if( strcmp (place, good[i].place) == 0 )
  {
    return false;
  } 
    }
  return true;
}


int remove_product(int index)
{
  if(index >= 0 && index < amount)
    {
      //free mem
      undo.copy = good[index];
      undo.type = 2;

      while(index < amount)
  {
    good[index] = good[index+1];
    index++;
  }
      amount--;
    }


  return 0;
}


int add_good(struct goods god)
{
  undo.type = 1;
  undo.merch = &god;
  good[amount] = god;
  amount++;

  return 1;
}





void regret(){

  switch(undo.type){
    case 0: 
      //print ut no more regrets
      puts("INGET MER ATT ÅNGRA");
      break;

    case 1:
      remove_product(amount-1);
      break; 

    case 2:
      add_good(undo.copy);
      break; 

    case 3:
      *undo.merch = undo.copy;
      break;

    default:
      //error 
      break;
    }
  undo.type = 0;
}


