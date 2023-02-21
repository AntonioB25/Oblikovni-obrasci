#include <stdlib.h>
#include<malloc.h>
#include <stdio.h>
typedef char const* (*PTRFUN)();

typedef struct {
	PTRFUN* vtable;
	char const* name;
} Parrot;

char const* name(Parrot* parrot){
    return parrot->name;
}

char const* greet(void){
    return "Bok ja sam papiga!";
}

char const* menu(void){
    return "Sjemenke suncokreta";
}

PTRFUN vtable[] = {name, greet, menu};

void construct(Parrot* parrot, char const* name){
    parrot->vtable = vtable;
    parrot->name = name;
}

void* create(char const* name){
    Parrot* parrot = (Parrot*) malloc(sizeof(Parrot));
    construct(parrot, name);
    return parrot;
}
