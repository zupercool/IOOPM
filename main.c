#include "interface.h"







int main_loop()
{
  int option = 0;
  option = ask_question_int("\n\nMenu\n1.Lägga till en vara \n2.Ta bort en vara \n3.Redigera en vara \n4.Ångra senaste ändringen \n5.Lista hela varukatalogen \n6.Avsluta \n\nVad vill du göra idag? _");
  
  switch(option)
    {
    case 1:
      add_product_interf();
      break;
      
    case 2: 
      remove_product_interf();
    break;

    case 3:
      edit_product_interf();
      break;
      
    case 4:
      regret_action_interf();
      break;
      
    case 5:
      list_products_interf();  
      break;
      
    case 6: 
      return 0;
      break;
      
    default: 

    break;
    }
  
  return 1;
}


int main()
{
  print_welcome();
  while(main_loop());
  return 0;
}

