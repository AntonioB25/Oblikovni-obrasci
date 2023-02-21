#include "myfactory.h"
#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct{
    PTRFUN* vtable;
} Animal;

void animalPrintGreeting(Animal* animal){
    printf("%s pozdravlja: %s\n", (animal->vtable[0])(animal), (animal->vtable[1])(animal));
}

void animalPrintMenu(Animal* animal){
    printf("%s voli: %s\n", (animal->vtable[0])(animal), (animal->vtable[2])(animal));
}
struct AnimalWithName {
    PTRFUN* vtable_;
    const char* name_;
};

int main(int argc, char *argv[]){

    for (int i=0; i<argc/2; ++i){
        Animal* p=(Animal*)myfactory(NULL, argv[1+2*i], argv[1+2*i+1]);
        if (!p){
            printf("Creation of plug-in object %s failed.\n", argv[1+2*i]);
            continue;
        }
        animalPrintGreeting(p);
        animalPrintMenu(p);
        free(p);
    }


    struct AnimalWithName a1;
    if (myfactory(&a1, "parrot", "Stanley") == NULL){
        printf("Creation of plug-in object on stack failed.\n");
        exit(1);
    }
    animalPrintGreeting((Animal *) &a1);
    animalPrintMenu((Animal *) &a1);

}
