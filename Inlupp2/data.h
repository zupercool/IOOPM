/**\file data.h

 \ The warehouse main actions and structures. The list of items is constructed by a tree while the shelfs of a item is build up by linked list. 
 
*/

#ifndef DATA_H
#define DATA_H

#include <stdbool.h>
#include <stdio.h>

typedef struct Goods Goods;
struct Goods
{
  char *name;
  char *desc;
  int price; 
  int amount;
  struct shelff *shelf;
  struct Goods *left;
  struct Goods *right;
};

typedef struct shelff shelff;

typedef struct pall pall;

Goods *create_tree();

shelff *create_list();

pall *create_pall();

/**
 *\brief Frees the tree from the heap
 *\details ---
  */
void free_loop(Goods *m_t);

/**
 *\brief Frees the pall from the heap
 *\details ---
  */
void free_loop_pall(pall *m_p);
/**
 *\brief Adds an item\n
 *\details It takes the input of the user, place it in a node and places it in the 
 *main tree in the right order when compared to the other items in the list.\n
 *It will check if the input of the user is valid. Such as if the price is not negativ and that infact the user have used numbers. 
 * \param [main_tree] a tree structure
 *\retur Void funtion: It will just update the main tree.
  */
void add_action(Goods **main_tree);


/**
 *\brief Prints out list
 *\details The list_action will print out the item in the tree in the right order and also 
 *counted, for example:\n 
 * 1) Apple\n
 * 2) Banana\n
 * \param [main_tree] a tree structure
 *
 *\return Void funtion: It will just update the main tree.
  */
void list_action(Goods *main_tree, int select);

/**
 *\brief Takes away the an item from the tree
 *\details It should be able to also to remove just a shelf in an item. And if it takes away 
 *a whole item should the tree's nodes make sure that everything is still reschable,
 *except of the removed item.\n 
 *Uses list_ation();
 *\param [main_tree] tree stucture
 *\return Void function: It will just update the main tree.
 */
void  remove_action(Goods **m_t);

/**
 *\brief Edits a chosen item
 *\details Will create a new node, remove the old one, and place the new one in the tree. The user will be asked to change everthing.
 *\warning This doesn't work if you want to place serveral shelf in the new node when 
 *editing.
  */
void edit_action(Goods **m_t);

/**
 *\brief The user's basket\n\n
 *\details The function will place item that the user chose in the basket
 *\param [main_tree, main_pall]
 *\warning The function will from certain wrong inputs(like when you try to take to many item at the same time to the list) through you out of the function without any explaination.
 *\todo Make main_pall take from serveral shelfs from main_tree at the same time.
  */
void pal_action(Goods **m_t, pall **m_p);

#endif //LIST_H
