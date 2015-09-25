#include "system.h"
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <stdlib.h>







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




bool place_empty(char *place){

  goods_t *node = root_good;

  if(node == NULL){
    return true;
  }
  while(1){
    
    if( strcmp (place, node->place) == 0 ){
      return false;
    }

    if(node->next == NULL){
      return true;
    }
    node = node->next;
  }
}









int remove_good(goods_t *d){

  if(d->prev == NULL){
    //remove first element 
    root_good = root_good->next;
    return 1; 
  }
  if(d->next == NULL){
    //remove las element

  }
  d->prev->next = d->next;

  free(d);



  return 0;
}

  


goods_t *add_product(char *name,char *desc,int price,char *place,int count){

  if(root_good == NULL){

    root_good = calloc(1,sizeof(struct goods));

    root_good->name = name;
    root_good->desc = desc;
    root_good->place = place;
    root_good->price = price;
    root_good->count = count;
    root_good->next = NULL;
    root_good->prev = NULL;
    last_good = root_good;
    
  }else{
    last_good->next = calloc(1,sizeof(struct goods));
    last_good->next->name = name;
    last_good->next->desc = desc;
    last_good->next->place = place;
    last_good->next->price = price;
    last_good->next->count = count;
    last_good->next->next = NULL;

    last_good->next->prev = last_good;
    last_good = last_good->next;
  }

  return last_good;
}


goods_t *add_good(goods_t d){
  return add_product(d.name,d.desc,d.price,d.place,d.count);
}



goods_t *next(goods_t *g,int i){

  while(i--){
    if(g->next == NULL){
      return NULL;
    }
    g = g->next;
  }
  return g; 
}

goods_t *prev(goods_t *g,int i){

  while(i--){
    if(g->prev == NULL){
      return NULL;
    }
    g = g->prev;
  }
  return g; 
}


void list(){
  goods_t *node = root_good;

  while(1){
 
    printf("\n%s",node->name);

    if(node->next == NULL){
      return;
    }
    node = node->next;
  }
}

void test(){

  
  add_product("ett","Strömberg",44,"A1",5);
  add_product("två","Strömberg",44,"A2",5);
  add_product("tre","Strömberg",44,"A3",5);
 
  
}




void regret(){

  switch(undo.type){
  case 0: 
    //print ut no more regrets
    puts("INGET MER ATT ÅNGRA");
    break;

  case 1:
    remove_good(undo.merch);
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
  free(undo.merch);
}


