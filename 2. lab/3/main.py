def mymax(iterable, key=lambda x: x):
    # incijaliziraj maksimalni element i maksimalni kljuc
    max_x = max_key = None

    # obidi sve elemente
    for x in iterable:
        current_key = key(x)
        # ako je key(x) najveci -> azuriraj max_x i max_key
        if not max_key or current_key > max_key:
            max_key = current_key
            max_x = x

    # vrati rezultat
    return max_x


maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
maxchar = mymax("Suncana strana ulice")

g = lambda x: len(x)
maxstring = mymax([
    "Gle", "malu", "vocku", "poslije", "kise",
    "Puna", "je", "kapi", "pa", "ih", "njise"], g)

print(maxint)
print(maxchar)
print(maxstring)

D = {'burek': 8, 'calzone': 45}
maxprice = mymax(D, D.get)  # ovdje kao key šaljemo funkciju koja će dohvaćati vrijednost za pojedini ključ
# Kada ne bi slali D.get, onda bi se koristila funkcija identiteta, tj. defaultna funkcija?
print(maxprice)

people = [("Antonio", "Knežević"), ("Ivan", "Horvat"), ("Tomislav", "Bukovac")]
#f = lambda x: x[1]
f = lambda x: x[1::-1]
maxperson = mymax(people, f)
print(maxperson)
