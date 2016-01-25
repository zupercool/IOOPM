#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include <data.h>
#include "quit.h"
#include "system.h"

struct shelff
{
  char *shelf;
  int  amount;
  struct shelff * next;
};

struct pall
{
  char *name;
  int price;
  int amount;
  struct shelff *shelf;
  struct pall *next;
};

struct count{
  char *name;
  struct count *next;
};
typedef struct count count;

Goods *create_tree(){
  return calloc(1, sizeof(struct Goods));
}
shelff *create_list(){
  return calloc(1, sizeof(struct shelff));
}

pall *create_pall(){
  return calloc(1, sizeof(struct pall));
}

count *create_listB(){
  return calloc(1, sizeof(struct count));
}











//remove left most in comparison to the node main_tree is currently standing on(used by remove)

void left_remove(Goods *node){
  if (node->left == NULL){
    free(node);
    return;
  }
  else
    left_remove(node->left);
    return;
}


//iterativ lösning
void left_remove(Goods *node){
  Goods *temp = node;
  while(temp->left != NULL){
    temp = node->left;
  }
  free(temp);
};





















// Find the left most Node (used by remove) 

void left_most(Goods *node, Goods *replace){
  if (node->left == NULL){
    node->left = replace->left;
    node->right = replace->right;
    replace = node;
    return;
    }
  else left_most(node->left, replace);
  return;
}

//free shelf
void free_loop_shelf(shelff *s){
  if( s == NULL) return;
  else if(s->shelf == NULL){
    free(s);
    return;
  }
  else if(s->next == NULL){
    free(s->shelf);
    free(s);
  }
  else{
    shelff *i = s;
    s = s->next;
      free(i->shelf);
      free(i);
      free_loop_shelf(s);
      return;
  }
  return;
}


void free_loop(Goods *m_t){
  if(m_t->name == NULL){
    free(m_t);
    return;
  }
  else if((m_t->left == NULL) && (m_t->right == NULL)){
    free(m_t->name);
    free(m_t->desc);
    free_loop_shelf(m_t->shelf);
    free(m_t);
    return;
  }
  else if((m_t->left == NULL) && (m_t->right != NULL)){
    Goods *i = m_t;
    m_t = m_t->right;
    free(i->name);
    free(i->desc);
    free_loop_shelf(i->shelf);
    free(i->right);
    free(i);
    free_loop(m_t);
  }
  else if((m_t->left != NULL) && (m_t->right == NULL)){
    Goods *i = m_t;
    m_t = m_t->left;
    free(i->name);
    free(i->desc);
    free_loop_shelf(i->shelf);
    free(i->left);
    free(i);
    free_loop(m_t);
  }
  else if ((m_t->right != NULL) && (m_t->left != NULL)){ 
    Goods *i = m_t;
    left_most(m_t->right, m_t);
    left_remove(m_t->right);
    free(i->name);
    free(i->desc);
    free_loop_shelf(i->shelf);
    free(i);
    free_loop(m_t);
  }
}  


//free count
void free_loop_count(count *c){
  if(c->name == NULL){
    free(c);
    return;
  }
  else if(c->next == NULL){
    free(c);
     }
  else{
  count *i = c;
  c = c->next;
  free(i);
  free_loop_count(c);
  return;
  } 
  return;
}


void free_loop_pall(pall *m_p){
  if(m_p->name == NULL){
    free(m_p);
    return;
  }
  else if(m_p->next == NULL){
    free(m_p->name);
    free_loop_shelf(m_p->shelf);
    free(m_p);
     }
  else{
  pall *i = m_p;
  m_p = m_p->next;
  free(m_p->name);
  free_loop_shelf(m_p->shelf);
  free(m_p->next);
  free(i);
  free_loop_pall(m_p);
  return;
  }
 return;
}


// Looks were to place the new created shelf in the main_tree's shelf list(for add)
shelff * shelf_compare(shelff *m_t, shelff *new){
  if (strcmp(m_t->shelf, new->shelf) == 0){
    shelff *i = m_t;
    new->amount = m_t->amount + new->amount;
    new->next = m_t->next;
    m_t = new;
    free(i->shelf);
    free(i);
    return m_t;
  }
  else if (m_t->next == NULL){
    m_t->next = new;
    return m_t;
  }
  shelf_compare(m_t->next, new);
  return new;
}


// Find certain node in the tree
Goods * search_tree(Goods *root, char *nameI){
  int e = 0;
  while(e == 0){
  if (root == NULL){
    root = NULL;
    e = 1; 
  }
  else if (root->name == NULL){
    printf("Doesn't excist\n");
    root = NULL;
    e = 1;
  }
  else if ((strcmp (root->name, nameI) > 0) && (root->left != NULL)){
    root = root->left;
  }
  else if ((strcmp (root->name, nameI) < 0) && (root->right != NULL)){
    root = root->right;
  }
  else if (strcmp(root->name, nameI) == 0){
    e = 1;
  }
  else if ((root->left == NULL) || (root->right == NULL)){
    root = NULL;
    e = 1;
  }
  }
  return root;
}


// places thing in the tree in the right order ( aka left-right)
void InTree(Goods *new, Goods **main_tree){
  
  if (((*main_tree)->name == NULL) || ((*main_tree)->name == NULL)){
    Goods *i = *main_tree;
    *main_tree = new;
    free(i);
    return;
  }
  else if (strcmp((*main_tree)->name, new->name) == 0){
    new->amount = (((*main_tree)->amount) + (new->amount));
    new->price = price_is_right((*main_tree)->price, new->price);
    new->shelf = shelf_compare((*main_tree)->shelf, new->shelf);
    Goods *i = *main_tree;
    new->left = (*main_tree)->left;
    new->right = (*main_tree)->right;
    new->shelf->next = (*main_tree)->shelf->next;
    *main_tree = new;
    free(i->name);
    free(i->desc);
    free(i);
    return;
  }
  else if (strcmp((*main_tree)->name, new->name) > 0){
    if ((*main_tree)->left == NULL){
      (*main_tree)->left = new;
      return;
    }
    else InTree(new, &((*main_tree)->left));
  }
  else if  (strcmp((*main_tree)->name, new->name) < 0){
    if ((*main_tree)->right == NULL){
      (*main_tree)->right = new;
      return;
    }
    else InTree(new, &((*main_tree)->right));
  }
  else return;
  return;
}

 
//place names of the items in the tree in a pre-order to create an alfabetic order in list
void place_l(Goods *root, count **list){
  if ((*list)->name == NULL){
    (*list)->name = root->name;
    return;
      }
  else if((*list)->next == NULL){
    count *new = create_listB();
    new->name = root->name;
    (*list)->next = new;
    return;
  }
  else place_l(root, &((*list)->next));
  return;
}


//Sends in pre-order nodes of root to place_l function
void print_action(Goods *root, count **list){
  if((root == NULL)|| (root->name == NULL))return;
  else{
    print_action(root->left, list);
    place_l(root, list);
    print_action(root->right, list);
  }
}


//Prints out the names in list with numbers
void print_list(count *list){
  int item = 1;
  if (list->next == NULL){
  printf("%d ) %s\n", item, list->name);
  return;
  }
  else{
    while((list->next != NULL) && (list->next->name != NULL)){
    printf("%d ) %s\n", item, list->name);
    item++;
    list =list->next;
  }
  printf("%d ) %s\n", item, list->name);
  }
  return;
}


//Print out what you have in the shelf( used by add)
void print_shelf(shelff *p){
  printf("Shelf(s):");
  while (p != NULL){
    printf( "%s,", p->shelf);
    p = p->next;
  }
  printf("\n");
  return;
}


//shows detail about chosen item
void ask_detail(Goods *root){
  char *detail = calloc(10, sizeof(char));
  printf("Do you wish to look at something?\n");
  if(ask_yes_no()){
    printf("What?\n");
    scanf("%s", detail);
    Goods *temp = search_tree(root, detail);
    if(temp != NULL){
    puts("\nItem:\n");  
    printf ("Name: %s\n", temp->name);
    printf("Description: %s\n", temp->desc);
    printf("Price: %dkr\n", temp->price);
    printf("Amount: %d\n", temp->amount);
    print_shelf(temp->shelf);
    free(detail);
    return;
    }
    else{
      printf("\nThat does not excist in the list\n");
      free(detail);
      return;
    }
  }
  else{
    free(detail);
    return;
  }
  return;
}

// The main function for printing and listning
void list_action(Goods *root, int select){
  if(root->name == NULL){
    printf("The list is empty\n");
    return;
  }
  else{
  count *list = create_listB();
  print_action(root, &list);
  print_list(list);
  if (select == 1){
      ask_detail(root);
      select = 0;
      };
  free_loop_count(list);
  }
  return;
}


//check if the shelf given is valid (used in add)
char * validshelf(){
    char *shelfI = calloc(3, sizeof(char));
    scanf("%s", shelfI);
    int len = strlen(shelfI);
    if(len != 3){
      printf("Not a valid shelf, use one letter and a two digits in combination.\n");
      free(shelfI);
      validshelf();
    }
    else if(((((shelfI[0] == 'a') || (shelfI[0] == 'A')) || ((shelfI[0] == 'b') || (shelfI[0] == 'B'))) && ((shelfI[1] == '1') || (shelfI[1] == '2'))) && ((shelfI[2] == '1') || (shelfI[2] == '2'))){
      printf("Good to go\n");
    }
    else {
      printf("Not a valid shelf, use one letter and a two digits in combination.\n");
      free(shelfI);
      validshelf();
    }
  return shelfI;
}


// Main function for adding in ware
void add_action(Goods **main_tree){
  char *userinput = calloc(150, sizeof(char));
  char *descr = calloc(150, sizeof(char));        
  Goods *node = create_tree();
  printf("Follow the instructions;\n");
  printf("What's the item's name?\n");
  scanf("%s", userinput);
  node->name = userinput;

  printf("Enter a description of the item.\n");
  scanf("%s", descr);
  node->desc = descr;
 
  printf("What's the price of the item?.\n");
  node->price = ask_question_int();

  printf("How many %s do you want to store?\n", node->name);
  node->amount = ask_question_int();

  printf("Which shelf do you want it to be put on?\n");
  node->shelf = create_list();
  node->shelf->shelf = validshelf();
  node->shelf->amount = node->amount;

  InTree(node, main_tree);
  
  printf( "The name is; %s\n", node->name);
  printf( "The description is; %s \n", node->desc);
  printf( "The price is; %d \n", node->price);
  print_shelf(node->shelf);
  printf( "There's %d %s currently in the warehouse\n", node->amount, node->name); 
  return;
}

// Removes chosen shelf (used by remove)
void shelf_remove(Goods *m_t, char *remove){
  int e = 0;
  while(e == 0){
    if (strcmp(m_t->shelf->shelf,remove)== 0){
	if (m_t->shelf->next == NULL){
	  free(m_t->shelf->shelf);
	  m_t->shelf->amount = 0;
	  e =1;
	  return;
	}
	else{
	  shelff *list = m_t->shelf;
	  m_t->shelf = m_t->shelf->next;

	  free(list);
	  e =1;
	  return;
      }
    }
    else {
      m_t->shelf = m_t->shelf->next;
      e = 0;
    }
  }
  return;
}

// ask for shelf to remove from chosen item (used by remove)
void ask_to_remove(Goods *main_tree){ 
    char *remove = calloc(3, sizeof(char));
    printf("Which shelf do you want to remove? (X for break)\n");
    print_shelf(main_tree->shelf);
    scanf("%s", remove);
    shelf_remove(main_tree, remove);
    free(remove);
    return;
}

  
//Takes away item from tree and make sure the tree is still connected
void remove_Node(Goods *main_tree, char * n, Goods **m_t){
  if (main_tree == NULL) return;
  if ((strcmp(main_tree->name, n)) == 0) { 
    if (main_tree->right != NULL && main_tree->left != NULL){ 
      if ((main_tree->shelf == NULL) || (main_tree->shelf->next == NULL)){
	Goods *temp = main_tree;
        left_most(main_tree->right, main_tree);
	left_remove(main_tree->right);
	free(temp);
	return;
      }
      else{
	ask_to_remove(main_tree);
	return;
      }
    }  
    else if (main_tree->right == NULL && main_tree->left != NULL) {
      if(main_tree->shelf->next == NULL){
	Goods *temp = main_tree;
	main_tree = main_tree->left;
	free(temp->name);
	free(temp->desc);
	free(temp->shelf->shelf);
	free(temp->shelf);
	free(temp);
	return;
      }
    else{
      ask_to_remove(main_tree);
      }
    }
    else if ((main_tree->right != NULL) && (main_tree->left == NULL)){ 
      if(main_tree->shelf->next == NULL){
	Goods *temp = main_tree;
	main_tree = main_tree->left;
	free(temp->name);
	free(temp->desc);
	free(temp->shelf->shelf);
	free(temp->shelf);
	free(temp);
	return;
      }
    else{
      ask_to_remove(main_tree);
      }
    }
    else if ((main_tree->right == NULL) && (main_tree->left == NULL)){
      if(strcmp(main_tree->name, (*m_t)->name) == 0){
	if (main_tree->shelf->next == NULL){
	  printf("remove all\n");
	  free(main_tree->name);
	  free(main_tree->desc);
	  main_tree->name = NULL;
	  free(main_tree->shelf->shelf);
	  free(main_tree->shelf);
	  main_tree->price = 0;
	  main_tree->amount = 0;
	  return;
	}
	else {
	  ask_to_remove(main_tree);
	  return;
	}
      }
      else{
     	if (main_tree->shelf->next == NULL){
	  printf("Remove All\n");
	  free(main_tree->name);
	  main_tree->name = NULL;
	  free(main_tree->shelf->shelf);
	  free(main_tree->shelf);
	  free(main_tree->desc);
	  free(main_tree);
	  main_tree = NULL;
	  return;
	}
	else {
	  ask_to_remove(main_tree);
	  return;
	}

      }
    }
  }
  else if (strcmp(main_tree->name, n) < 0) remove_Node((main_tree->right), n, m_t);
  else if (strcmp(main_tree->name, n) > 0 ) remove_Node((main_tree->left), n, m_t);
  return;
}


//Main function for remove
void remove_action(Goods **main_tree){
  char input[150];
  printf("You have chosen to delete a item, which to you want to remove?\n");
  list_action(*main_tree, 0);
  scanf("%s", input);
  remove_Node(*main_tree, input, main_tree);
  return; 
}


//Write the changes you want to make ( you have to change everything ftm)
Goods * edit_this(Goods *edit){
  printf("Change to what you want it to be, else write the same thing as before\n");
  char *userinput = calloc(150, sizeof(char));
  char *descr = calloc(150, sizeof(char));       
  Goods *list = create_tree();
  printf("Name %s __\n", edit->name);
  scanf("%s", userinput);
  list->name = userinput;
  
  printf(" The last decription was :: %s ___ \n", edit->desc);
  scanf("%s", descr);
  list->desc = descr;
  
  printf("Price %d __\n", edit->price);
  list->price = ask_question_int();

  printf("Amount %d __\n", edit->amount);
  list->amount = ask_question_int();
  
  printf("It's in");
  print_shelf(edit->shelf);
  list->shelf = create_list();
  list->shelf->shelf = validshelf();
  list->shelf->amount = list->amount;
  return list;
}

//Main function for edit
void edit_action(Goods **main_tree){
  char *in = calloc(100, sizeof(char));
  printf("what to you want to edit\n");
  list_action(*main_tree, 1);
  scanf("%s", in);
  Goods *new = search_tree(*main_tree, in);
  if(new != NULL){
    Goods *newer = edit_this(new);
    shelff *cheat = new->shelf;
    new->shelf = new->shelf->next;
    free_loop_shelf(new->shelf);
    new->shelf = cheat;
    remove_Node(*main_tree, in, main_tree);
    if((new->left != NULL) || (new->right != NULL)){
      InTree(newer, main_tree);
      free(in);
      return;
    }
    else{
      Goods *temp = *main_tree;
      *main_tree = newer;
      free(temp);
      free(in);
      return;
    }
  }
  else{
    free(in);
    printf("Invalid\n");
    edit_action(main_tree);
  }
  return;
}

// bigger and biggest toghether find the shelf in list with the most item in it
int biggest(int a, int b){
  if (a >= b) return a;
  if (a < b) return b;
  return a;
}
int bigger(int e, shelff *m_s){
  e = biggest(e, m_s->amount);
  while(m_s->next != NULL){
     e = biggest(m_s->next->amount, m_s->amount);
     m_s = m_s->next;
  }
  return e;
}

//find the shelf that have the amount e
shelff *find_shelf(int e, shelff *m_s){
  if ( m_s->amount == e ){
    return m_s;
    }
  else if (m_s == NULL){
    return NULL;
  }
  else{
    m_s = m_s->next;
    find_shelf( e , m_s);
  }
  return m_s;
}


// When placing in pall, you chose from several shelfs with the same thing
void shelf_taker(Goods *m_l, int d, pall **p){
   int e = 0; 
   while( d != 0 ){
     e = bigger(e, m_l->shelf);
     shelff *temp = find_shelf(e, m_l->shelf);
     if (d <= e){
       if ((*p)->shelf == NULL) {
	 (*p)->shelf = temp;
	 (*p)->shelf->amount = d;
	 d = 0;
	 return;
       }
       if (strcmp((*p)->shelf->shelf, temp->shelf) == 0){
	 if (((*p)->shelf->amount + d) <= temp->amount){
	   (*p)->shelf->amount = ((*p)->shelf->amount ) + d;
	   d = 0;
	   return;
	 }
	 else{
	   (*p)->shelf->amount = ((*p)->shelf->amount ) + d;
	   d = 0;
	   return;
	 }
       }
       else{
	 shelf_taker(m_l, d ,&(*p)->next);
       }
     }
   if (d > e){
     if ((*p)->shelf == NULL) {
       (*p)->shelf = temp;
       (*p)->shelf->amount = e;
       (*p)->amount = temp->amount;
       temp = temp->next;
       d = d-e;
     }
     else if (strcmp((*p)->shelf->shelf, m_l->shelf->shelf) == 0){
       int a = e - (*p)->shelf->amount;
       (*p)->shelf->amount = e; 
       (*p)->amount = (*p)->amount + a;
       temp = temp->next;
       d = d-e;
     }
     else{ 
       (*p)->shelf  = (*p)->shelf->next;
     }
   }
   }
   return;
}


//Put item d in pall p
void place_in_list(Goods *m_l, int d, pall **p){
  if (*p == NULL){
    pall *new = create_pall();
    new->name = m_l->name;
    new->price = m_l->price;
    *p = new;
    shelf_taker(m_l, d, p);
    (*p)->amount = d;
    return;
  }
  if ((*p)->name == NULL){
      (*p)->name = m_l->name;
      (*p)->price = m_l->price;
      shelf_taker(m_l, d, p); 
      (*p)->amount = d;
      return;
  }
  if (strcmp((*p)->name, m_l->name) == 0){
    if (((*p)->amount + d) > m_l->amount){
      printf("There doesn't excist that many\n");
      return;
    }
    else{
      shelf_taker(m_l, d, p);
      (*p)->amount = (*p)->amount + d;
      return;
    }
  }
  else{
    place_in_list( m_l, d, &((*p)->next));
  }
  return;
}


//Ask how many you want to store in pall p
bool Ask_add(Goods **m_t, pall **p){
  printf("Current in ware: \n");
  list_action(*m_t, 0);
  printf("What do you want to add?\n");
  char *i = calloc(100, sizeof(char));
  scanf("%s", i);
  Goods *m_l = search_tree(*m_t, i);
  free(i);
  if (m_l == NULL){
    return false;
  }
  else{
    printf("\nName : %s\n", m_l->name);
    printf( "Amount : %d\n", m_l->amount);
    printf("How many do you want to store? (0 -- %d)\n", m_l->amount);
    int d;
    scanf("%d", &d);
    if (((d > 0) && (d < (m_l->amount))) &&(d <= (m_l->amount))){
      place_in_list(m_l, d, p);
      d = 0;
      return true;
    }
    //else if{ }


      else{
      d = 0;
      return false;
    }
  }
  return false;
}


//Shows basket
void print_basket(pall *p){
  if (p == NULL){
  while (p != NULL){
    printf( "%s ( %d )\n", p->name, p->shelf->amount);
    p = p->next;
  }
  }
}


//Shows basket but with shelf as well as name and amount
void print_basket_shelf(pall *p){
  while (p != NULL){
    printf( "%s (%d) in %s\n", p->name, p->shelf->amount, p->shelf->shelf);
    p = p->next;
  }
}

// what the price of everything in the pall is
int total_price(pall *p){
  int u;
  u = (p->amount) * (p->price);
  return u;
}

//prints total cost
void total(pall *p){
  int i;
  i = total_price(p);
  while(p->next != NULL){
    i = i + (total_price(p->next));
    p = p->next;
  }
  printf("Total: %d.00:-\n", i);
  return;
}

//main function of the pall and users basket
void pal_action(Goods **m_t, pall **m_p){
  printf("Welcome to your basket\n");
  if ((*m_p) == NULL)
    {
      printf("The list is empty...\n\n\n\n\n\n");
      printf("Do you wish to add something?(Y/N)\n");
      if (ask_yes_no())
	{
	  Ask_add(m_t, m_p);
	  pal_action(m_t, m_p);
	  return;
	}
      else
	{
	  return;
	}
      return;
    }
  else
    {
    print_basket(*m_p);
    printf("\n\n\n\n\n\nDo you wish to add something?(Y/N)\n");
    if (ask_yes_no())
      {
	if (Ask_add(m_t, m_p))
	  {
	    pal_action(m_t, m_p);
	    return;
	  }
	else if (Ask_add(m_t, m_p) == false)
	  {
	    printf("Invalid");
	    return;
	  }
      }
    else
      {
	total(*m_p);
	print_basket_shelf(*m_p);
	return;
      }
    return;
    }
  return;
}
