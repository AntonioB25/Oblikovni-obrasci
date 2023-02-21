#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int gt_int(const void* first, const void* second){
    const int* f = (int*) first;
    const int* s = (int*) second;

    //printf("%d : %d\n", *f, *s);
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


const void* mymax(
	const void *base, size_t nmemb, size_t size,
	int(*compar)(const void *, const void *)) {

	const void *max = base;
	char* first = (char*)base;

	for (size_t i = 1; i < nmemb; i++) {

		const void *current = (first + i*size);

		if (compar(max, current) == 0) {
			max = current;
		}
	}

	return max;
}



int main(void) {
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    char arr_char[]="Suncana strana ulice";
    const char* arr_str[] = {
       "Gle", "malu", "vocku", "poslije", "kise",
       "Puna", "je", "kapi", "pa", "ih", "njise"
    };

    int biggest_int = *(const int*)mymax(arr_int, sizeof(arr_int) / sizeof(int), sizeof(int), gt_int);
    char biggest_char = *(char*)mymax(arr_char, sizeof(arr_char) / sizeof(char), sizeof(char), gt_char);
    char* biggest_string = *(char**)mymax(arr_str, sizeof(arr_str) / sizeof(char*), sizeof(char*), gt_str);

    printf("Int: %d\n", biggest_int);
    printf("Char= %c\n", biggest_char);
	printf("String= %s\n", biggest_string );
}




