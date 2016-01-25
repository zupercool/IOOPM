#include "interface.h"
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
  puts("\n\nVälkommen till lagerhantering 1.0\n=================================\n\n");
}


char ask_question_char(char *q, char *alt) //fråga
{
  printf("%s [%s]\n", q, alt);
 
  char alf = 0;
  alf = getchar();

  while (strchr(alt, alf) == NULL)
    {
      printf("Felaktig input '%c', försök igen om du vågar! [%s]\n", alf, alt);
      clear_stdin();
      alf = getchar();
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


char *ask_question_string(char *q, int ln)
{
  printf("%s",q);
  
  char *buffer;
  buffer = malloc(ln); 
  
  fgets (buffer, ln, stdin);
 
  if ((strlen(buffer)>0) && (buffer[strlen (buffer) - 1] == '\n'))
    buffer[strlen (buffer) - 1] = '\0';
  
  return buffer;
}


void print_product(goods_t g)
{ 
  printf("Namn: %s\n", g.name);
  printf("Beskrivning: %s\n", g.desc);
  printf("Pris: %d kr\n", g.price);
  printf("Lagerhylla: %s\n",g.place);
  printf("Antal: %d st\n", g.count);
}


void list_page(int page)
{
  int first_i = page*20;
 
  for(int i = 0; (i+first_i)<amount && i < 20; i++)
    {
      //visa 20 första
      printf("%d. %s\n",(i+1),good[i+first_i].name);
    }
}


int list_products()
{
  int page = 0; 
  char *a;
  char q[70];

  while(1){ 
      list_page(0);
      //check if there is next and prev page 
      int left_products_on_page = amount - (page*20);

      if(page == 0){
        //no prev
        if(page*20 > amount){
            //q = "\n Välj en vara [1-20] eller [N]ästa sida,[A]vbryt";
            sprintf(q,"\n Välj en vara [1-%d] eller [N]ästa sida,[A]vbryt",left_products_on_page);
          }else{
            sprintf(q,"\n Välj en vara [1-%d] eller [A]vbryt",left_products_on_page);
              //q = "\n Välj en vara [1-20],[A]vbryt";
            }
        }else{
        if(page*20 > amount){
            sprintf(q,"\n Välj en vara [1-%d] eller [N]ästa sida,[F]öregående,[A]vbryt",left_products_on_page);
          }else{
            sprintf(q,"\n Välj en vara [1-%d] eller [F]öregående,[A]vbryt",left_products_on_page);
          }
        }
      
      a = ask_question_string(q,100);


      if(isdigit(a[0])){
            //first char is digit 
            int digit = atoi(a);
            free(a);
            //TODO show product with index
            return digit+(page*20)-1;

      }else if(a[0] == 'N'){
          page++;
          }else if(a[0] == 'F'){
            page--;  
          }else if(a[0] == 'A'){
            free(a);
            return -1; 
          }else{
            puts("Felaktig inmatning");
          }
      }
  free(a);
  return -1;
}








char *ask_for_place(char *q)
{
  char *place = ask_question_string(q,100);
  
  while (valid_place(place) == false)
    {
      puts("Det är inte en lagerhylla…");
      free(place);
      place = ask_question_string(q,100);
    }
  
  while (place_empty(place) == false)
    {
      puts("Lagerhyllan är redan använd. Ange en annan");
      free(place);
      place = ask_question_string(q,100);
    }
  
  place[0] = toupper(place[0]);
  
  return place;
}


int edit_product(int index)
{
  //show product
  print_product(good[index]);
  
  struct goods tmp = good[index];


  int q = ask_question_char("\nVad vill du redigera?\n[N]amn\n[B]eskrivning\n[P]ris\n[L]agerhylla\nAn[t]al\n\n[a]vbryt","NBPLta");
  switch (q)
    {
    case 'N': 
      printf("Nuvarande namn:%s\n", good[index].name);
      char *new_name = ask_question_string("Nytt namn:",100);
      free(good[index].name);
      good[index].name = new_name;
      break;
      
    case 'B':
      printf("Nuvarande beskrivning:%s\n", good[index].desc);
      char *new_desc = ask_question_string("Ny beskrivning:",100);
      free(good[index].desc);
      good[index].desc = new_desc;
      break;
      
    case 'P':
      printf("Nuvarande pris:%d\n", good[index].price);
      int new_price = ask_question_int("Nytt pris:");
      good[index].price = new_price;
      break;
      
    case 'L':
      printf("Nuvarande lagerhylla:%s\n", good[index].place);
      char *new_place = ask_for_place("Ny lagerhylla:");
      free(good[index].place);
      good[index].place = new_place;
      break;
      
    case 't':
      printf("Nuvarande antal:%d\n", good[index].count);
      int new_count = ask_question_int("Nytt antal:");
      good[index].count = new_count;
      break;
      
    case 'a':
      
      break;
      
    default:
      edit_product(index);
      break;   
    }
  
  undo.copy = tmp;
  undo.merch = &good[index];
  undo.type = 3;
  return 0;
}



void add_product_interf()
{
  puts("\n...Lägga till produkt...");
  char *name  =   ask_question_string("Namn: ",100);
  char *desc  =   ask_question_string("Beskrivning: ",100);
  char *place =   ask_for_place("Lagerhylla: ");

  int price = ask_question_int("Pris:");
  int count = ask_question_int("Antal:");
     
  puts("\n\nDin produkt:");
  printf("Namn: %s \n Beksrivning:%s \n Lagerhylla:%s \n Pris:%d \n Antal:%d \n", name, desc, place, price, count);
  char q = ask_question_char("Vill du lägga till varan? [J]a,[N]ej,[R]edigera","JNR");

  struct goods god=
    {
      .name  = name,
      .desc  = desc,
      .place = place,
      .price = price,
      .count = count
    };
  
  switch (q)
    {
    case 'J': 
      add_good(god);
      break;

    case 'N':
      free(name);
      free(desc);
      free(place);
      break;

    case 'R':
      add_good(god);
      edit_product(amount-1);
      break;
    }   

  // free(name);
  // free(desc);
  // free(place);
  
}


void remove_product_interf()
{
  puts("Ta bort en vara");
  puts("Välj en vara att ta bort:");
  int i = list_products();
  
  if(i > 0 && i < amount){
      print_product(good[i]);
      char q = ask_question_char("Ta bort varan? [J]a,[N]ej","JN");
      if(q == 'J'){
        remove_product(i);
      }
    }
  
}

void list_products_interf(){
	 int index = list_products();
      if(index > 0 && index < amount){
        //if index = -1 then user aborted 
        print_product(good[index]);
      }
}
void regret_action_interf(){
	char q = ask_question_char("Är du säker på att du vill ångra senaste ändringen? ([J]a,[N]ej)","JA");
      if(q == 'J')
  {
    regret();
  }
}

void edit_product_interf()
{
  puts("Redigera en vara");
  
  list_products();
  int s = ask_question_int("Välj vara:");
  edit_product(s-1);
}

