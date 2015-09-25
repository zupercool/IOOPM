#include <stdbool.h>

struct goods
{
  char *name;
  char *desc;
  int price; 
  char *place;
  int count;
  struct goods *next;
  struct goods *prev;
};
typedef struct goods goods_t;


struct action // används för att kunna ångra
{
  int type; // NOTHING = 0, ADD = 1, REMOVE = 2, EDIT = 3
  goods_t *merch;
  goods_t copy;
};
typedef struct action action_t;


goods_t *root_good;
goods_t *last_good;
action_t undo;

void test();

bool valid_place(char *place);

bool place_empty(char *place);

int remove_good(goods_t *d);


void regret();

goods_t * add_product(char *name,char *desc,int price,char *place,int count);

int remove_good(goods_t *d);

goods_t * next(goods_t *g,int i);
goods_t * prev(goods_t *g,int i);
