#include <interface.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>



void clear_stdin() // tömmer buffern (stdin)
{
  while (getchar() != '\n');
}


void print_welcome() // skriver ut text, görs endast en gång
{
  puts("\n\nVälkommen till lagerhantering 1.1\n=================================\n\n");
  test();
}


char ask_question_char(char *q, char *alt)
{
  printf("%s [%s]\n", q, alt);
 
  char alf = 0;
  int error = scanf(" %c", &alf);
  
  while ((strchr(alt, alf) == NULL && alt != NULL )|| error == 0)
    {
      printf("Felaktig input '%c', försök igen om du vågar! [%s]\n", alf, alt);
      clear_stdin();
      error = scanf(" %c", &alf);
    } 

  clear_stdin();
  return alf;
}


int ask_question_int(char *q) 
{
  printf("%s", q);

  int num = 0;
  int tmp = scanf("%d", &num);
  while(tmp == 0)
    {
      puts("Använd siffror!");
      clear_stdin();
      tmp = scanf("%d", &num);
    }
  clear_stdin();
  return num;
}


char *ask_question_string(char *q,int ln)
{
  printf("%s",q);
  
  char *buffer;
  buffer = malloc(ln); 
  
  fgets (buffer, ln-1, stdin);
  int lngth = strlen(buffer);
  if ((lngth>0) && (buffer[lngth - 1] == '\n'))
    buffer[lngth - 1] = '\0';
  
  clear_stdin();
  return buffer;
}


void print_product(goods_t *g)
{ 
  if(g == NULL){
    return;
  }
  printf("Namn: %s\n", g->name);
  printf("Beskrivning: %s\n", g->desc);
  printf("Pris: %d kr\n", g->price);
  printf("Lagerhylla: %s\n",g->place);
  printf("Antal: %d st\n", g->count);
}


goods_t * list_page(goods_t *node) //returns  products on page
{

  int i = 0;
  while(1){
  
    printf("\n%d. %s",i+1,node->name);
    i++;

    if(node->next == NULL || i > 19){
      return node;
    }
    
    node = node->next;
  }
  return node;

}

goods_t * list_products(){

  char *a;
  char q[70];
  int page = 0;

  goods_t *last_node_on_page = root_good;
  goods_t *first_node_on_page = root_good;

  if(root_good == NULL){
    return NULL;
  }

  while(1){ 
      
      first_node_on_page = last_node_on_page;
      last_node_on_page = list_page(last_node_on_page);


      //check if there is next and prev page 
      int left_products_on_page = 20;

     
      sprintf(q,"\n Välj en vara [1-%d] eller [N]ästa sida,[F]öregående,[A]vbryt",left_products_on_page);

        
      
      a = ask_question_string(q,100);


      if(isdigit(a[0])){
            //first char is digit 
            int digit = atoi(a);
            free(a);
            //TODO show product with index
            return next(first_node_on_page,digit-1);


          }else if(a[0] == 'N'){
            page++;
          
          }else if(a[0] == 'F'){
            page--;
            last_node_on_page = prev(first_node_on_page,20);


          }else if(a[0] == 'A'){
            return NULL; 
          }else{
            puts("Felaktig inmatning");
          }


      }
  
  return NULL;
}








char *ask_for_place(char *q)
{
  char *place = ask_question_string(q,100);
  
  while (valid_place(place) == false)
    {
      puts("Det är inte en lagerhylla…");
      place = ask_question_string(q,100);
    }
  
  while (place_empty(place) == false)
    {
      puts("Lagerhyllan är redan använd. Ange en annan");
      place = ask_question_string(q,100);
    }
  
  place[0] = toupper(place[0]);
  
  return place;
}


int edit_product(goods_t *good)
{
  //show product

  
  goods_t tmp = *good;


  int q = ask_question_char("\nVad vill du redigera?\n[N]amn\n[B]eskrivning\n[P]ris\n[L]agerhylla\nAn[t]al\n\n[a]vbryt","NBPLta");
  switch (q)
    {
    case 'N': 
      printf("Nuvarande namn:%s\n", good->name);
      char *new_name = ask_question_string("Nytt namn:",100);
      good->name = new_name;
      break;
      
    case 'B':
      printf("Nuvarande beskrivning:%s\n", good->desc);
      char *new_desc = ask_question_string("Ny beskrivning:",100);
      good->desc = new_desc;
      break;
      
    case 'P':
      printf("Nuvarande pris:%d\n", good->price);
      int new_price = ask_question_int("Nytt pris:");
      good->price = new_price;
      break;
      
    case 'L':
      printf("Nuvarande lagerhylla:%s\n", good->place);
      char *new_place = ask_for_place("Ny lagerhylla:");
      good->place = new_place;
      break;
      
    case 't':
      printf("Nuvarande antal:%d\n", good->count);
      int new_count = ask_question_int("Nytt antal:");
      good->count = new_count;
      break;
      
    case 'a':
      
      break;
      
    default:
      
      break;   
    }
  
  undo.copy = tmp;
  undo.merch = good;
  undo.type = 3;
  return 0;
}



void add_product_interf()
{
  puts("\n...Lägga till produkt...");
  char *name  =   ask_question_string("Namn: ",100);
  char *desc  =   ask_question_string("Beskrivning:",100);
  char *place =   ask_for_place("Lagerhylla:");

  int price = ask_question_int("Pris:");
  int count = ask_question_int("Antal:");
     
  puts("\n\nDin produkt:");
  printf("Namn: %s \n Beksrivning:%s \n Lagerhylla:%s \n Pris:%d \n Antal:%d \n", name, desc, place, price, count);
  char q = ask_question_char("Vill du lägga till varan? [J]a,[N]ej,[R]edigera","JNR");

  
  
  switch (q){
    case 'J': 
      undo.merch = add_product(name,desc,price,place,count);
      undo.type = 1;
      break;

    case 'N':
      free(name);
      free(desc);
      free(place);
      break;

    case 'R':
      edit_product(add_product(name,desc,price,place,count));   
      break;
    }   

  
}


void remove_product_interf()
{
  puts("Ta bort en vara");
  puts("Välj en vara att ta bort:");
  goods_t *l = list_products();
  
      print_product(l);
      char q = ask_question_char("Ta bort varan? [J]a,[N]ej","JN");
      if(q == 'J'){
        undo.copy = *l;
        undo.type = 2;
        remove_good(l);
      }
    
  
}

void list_products_interf(){

	 
    print_product(list_products());
}
void regret_action_interf(){

  if(undo.type == 0){
    puts("Inget att ångra");
    return;
  }

    puts("Ångra händelsen:");
  switch(undo.type){
    case 1:
    printf("Lägg till:%s\n",undo.merch->name);
    break;
    case 2:
    printf("Radera:%s\n",undo.copy.name);
    break;
    case 3: 
    printf("Redigera:%s\n",undo.copy.name);
    break;
  }

	char q = ask_question_char("Är du säker på att du vill ångra? ([J]a,[N]ej)","JN");
    if(q == 'J'){
      regret();
    }
}

void edit_product_interf()
{
  puts("Redigera en vara");
  edit_product(list_products());
}

