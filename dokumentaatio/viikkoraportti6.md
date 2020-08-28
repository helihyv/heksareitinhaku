# Viikkoraportti 6

## Mitä tehty

Tällä viikolla olen korvannut tiedostonlukuluokasta ArrayList:in tavallisella taulukolla, jonka koko selvitetän lukemalla tiedosto kerran läpi. Samasta luokasta korvasin  String::split() :nja String::strip():n käytön funktiolla, joka käy rivin merkki kerrallan läpi. 

Olen myös jatkanut suorituskykytestien toteuttamista ja korjannut siltojen käsitteöyssä olleen virheen.

## Miten edistytty

Javan valmiit tetorakenteet on nyt kaikki korvattu joko omilla toteutuksilla (keko, linkitetty lista) tai käyttämällä tavallista taulukkoa. Myös kaikki funktiot, joita ei saa alopullisessa työssä olla, on poistettu käytöstä (poislukien käyttöliittymä, yksikkötestit ja suorituskykytestit).

Suorituskykytetsit tekevät nyt 1000 (samaa) satunnaista hakua kullekin algoritmille annettua kartaa kohden ja raportoivat keskimääräisen hakuun kuluneen ajan ja keskihajonnan. Suorituskykytestit ajetaan vielä netbeansistä käsin Run file-toiminnolla.

Karttatulkki on nyt valmis. 

Fringe search-algoritmi tarvitsee vielä korjausta.

## Mitä opin

Keskihajonnan laskemista.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Suorituskykytesteissä ilmeni, että fringe search -algoritmi jää joillakin kartoilla jumiin ikuiseen luuppiin. Se on myös varsin hidas isoimmilla kartoilla.

## Mitä seuraavaksi

Seuraavalla viikolla kirjoitan raportit valmiiksi ja teen suorituskykytesteistä itsenäisesti ajettavan. Lisäksi korjaan Fringe Search -algoritmin.



