#include<iostream>
#include<vector>
#include<set>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int gt_int(const void* first, const void* second){
    const int* f = (int*) first;
    const int* s = (int*) second;

    return *f > *s ? 1 : 0;
}

int gt_char(const void* first, const void* second){
    const char* f = (char*) first;
    const char* s = (char*) second;

    return (*f > *s) ? 1 : 0;
}

int gt_str(const void* first, const void* second){
    const char** f = (const char**) first;
    const char** s = (const char**) second;

    return (strcmp(*f, *s) > 0) ? 1 : 0 ;
}

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred) {

    Iterator max = first;

    while (first != last ){
       if( pred(&(*max),&(*first)) == 0){
            max = first;
       }
       first ++;
    }
    return max;
}


 int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0, 10, 11 };
 char arr_char[]="Suncana strana ulice";
 const char* arr_str[] = {
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"
 };

int main(){
  const int* maxint = mymax( &arr_int[0],
    &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
  std::cout << "Int: " << *maxint <<"\n";

  const char* maxchar = mymax( &arr_char[0],
  &arr_char[sizeof(arr_char)/sizeof(*arr_char)], gt_char);
  std::cout << "Char: " <<*maxchar <<"\n";

  const char** maxstr = mymax( &arr_str[0],
    &arr_str[sizeof(arr_str)/sizeof(*arr_str)], gt_str);
  std::cout << "String: " <<*maxstr <<"\n";


  std::vector<int> int_vec = {1,2,3,4,8,5,6};
  maxint = mymax(&int_vec[0], &int_vec.back(), gt_int);
  std::cout << "Vector int: " <<*maxint << "\n";

   std::set<int> int_set = {1, 3, 5, 7, 4, 6, 95, 2, 0, 76};
   int set_max = *mymax(int_set.begin(), int_set.end(), gt_int);
   std::cout << "Set int: " <<set_max << "\n";

}
