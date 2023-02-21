#include "myfactory.h"
#include <string.h>
#include <windows.h>
#include <stdlib.h>
#include <stdio.h>

typedef void* (*CREATEFUNPTR)(char const*);
typedef void* (*CONSTRUCTFUNPTR)(void* memory, char const*);


void* myfactory(void* memory, char const* libname, char const* ctorarg){
    char str[80];
    strcpy(str, "./");
    strcat(str, libname);
    strcat(str, ".dll");

    HMODULE hDLL = LoadLibrary(str);
    if (!hDLL) {
        return NULL;
    }

    //memory == NULL ? printf("%s", "NULL JE\n") : printf("%s", "NIJE NULL\n");

    //if memory provided - construct
    if(memory != NULL){
        CONSTRUCTFUNPTR constructFun = (CONSTRUCTFUNPTR) GetProcAddress(hDLL, "construct");
        if (!constructFun) {
            FreeLibrary(hDLL);
            return NULL;
        }
        constructFun(memory, ctorarg);
        return memory;
    // if memory not provided - create
    } else{
        CREATEFUNPTR createFun = (CREATEFUNPTR) GetProcAddress(hDLL, "create");
        if (!createFun) {
            FreeLibrary(hDLL);
            return NULL;
        }
        return createFun(ctorarg);
    }
}

