gcc main.c myfactory.c -o main.exe
gcc -shared tiger.c -o tiger.dll
gcc -shared parrot.c -o parrot.dll
main.exe parrot ChiChi tiger Tigger
