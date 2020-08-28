# Viikkoraportti 6

## Mitä tehty

Tällä viikolla (la-pe) olen käyttänyt projektiin vertaisarviointi mukaan lukien noin 13 tuntia.

Tällä viikolla olen korvannut tiedostonlukuluokasta ArrayList:in tavallisella taulukolla, jonka koko selvitetään lukemalla tiedosto kerran läpi. Samasta luokasta korvasin  String::split():n ja String::strip():n käytön funktiolla, joka käy rivin merkki kerrallaan läpi. 

Olen myös jatkanut suorituskykytestien toteuttamista ja korjannut siinä yhteydessä fringe search-algoritmista löytyneen virheen. Olen myös korjannut siltojen käsittelyssä olleet virheet.

Käyttöliittymään toteutin siltojen piirtämisen niin, että niiden suunta on näkyvissä.

## Miten edistytty

Javan valmiit tetorakenteet on nyt kaikki korvattu joko omilla toteutuksilla (keko, linkitetty lista) tai käyttämällä tavallista taulukkoa. Myös kaikki funktiot, joita ei saa lopullisessa työssä olla, on poistettu käytöstä (poislukien käyttöliittymä, yksikkötestit ja suorituskykytestit).

Suorituskykytestit tekevät nyt 1000 (samaa) satunnaista hakua kullekin algoritmille  käytettävää karttaa kohden ja raportoivat keskimääräisen hakuun kuluneen ajan ja keskihajonnan. Suorituskykytestit ajetaan vielä netbeansistä käsin Run file-toiminnolla.

Karttatulkki on nyt valmis. Samoin käyttöliittymä. 

## Mitä opin

Opin tällä viikolla lisää fringe search-algoritmista, heksan sisään piirtämisestä, suorituskykytestien tekemisestä ja keskihajonnan laskemisesta.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Suorituskykytesteissä ilmeni, että fringe search -algoritmi oli suurilla kartoilla hyvin hidas ja vaikutti siltä kuin se olisi joillain hauilla jäänyt ikuiseen luuppiin. Syyksi osoittautui > kun pseudokoodin mukaan olisi pitänyt olla >= . Solmuja nostettiin turhaan käsiteltäviksi, vaikka uusi reitti niihin oli ollut vain yhtä pitkä eikä lyhyempi kuin aiempi. 

## Mitä seuraavaksi

Seuraavalla viikolla kirjoitan raportit valmiiksi ja teen suorituskykytesteistä itsenäisesti ajettavan. Jos ehdin, teen suorituskykytestejä myös tietorakenteille.



