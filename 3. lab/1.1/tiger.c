#include <stdlib.h>
#include<malloc.h>

typedef char const* (*PTRFUN)();

typedef struct {
	PTRFUN* vtable;
	char const* name;
} Tiger;

char const* name(Tiger* tiger){
    return tiger->name;
}

char const* greet(void){
    return "Bok ja sam tigar!";
}

char const* menu(void){
    return "Takin";
}

PTRFUN vtable[] = {name, greet, menu};

void construct(Tiger* tiger, char const* name){
    tiger->vtable = vtable;
    tiger->name = name;
}

void* create(char const* name){
    Tiger* tiger = (Tiger*) malloc(sizeof(Tiger));
    construct(tiger, name);
    return tiger;
}

