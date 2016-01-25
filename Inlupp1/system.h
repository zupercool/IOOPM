#include <stdbool.h>


struct goods
{
  char *name;
  char *desc;
  int price; 
  char *place;
  int count;
};
typedef struct goods goods_t;


struct action // används för att kunna ångra
{
  int type; // NOTHING = 0, ADD = 1, REMOVE = 2, EDIT = 3
  goods_t *merch;
  goods_t copy;
};
typedef struct action action_t;


goods_t good[100];

int amount;
action_t undo;


bool valid_place(char *place);

bool place_empty(char *place);

int remove_product(int index);

int add_good(goods_t god);

void regret();



