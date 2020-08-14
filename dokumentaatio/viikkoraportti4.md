# Viikkoraportti 4

## Mitä tehty

Karttatulkkiin on lisätty teiden ja siltojen erityiskäsittely ja useita korjauksia. Sille on myös tehty hieamn lisää testejä. 

Kartan lataamiseen on lisätty välilyöntien jättäminen pois maastokoodesita. 

Djikstran- ja A*-algoritmeissa käytössä ollut ArrayList on korvattu tavallisella taulukolla. 

Kaikkiin kolmeen reitinhakualgoritmiin on tehty korjauksia. Niihin om myös lisätty reitin pituuden liikkumispisteinä palauttaminen reitin mukana. Tätä varten on luotu reitlle oma luokkansa.

Reitinhaun testeille on tehty abstratkti kantaluokka, johon varsinaiset testit on sijoitettu. Algoritmikohtaisissa testilukissa itsessään on vain funktot reitinahkuolioiden luomiseen. Testejä on myös tehty hieman lisää. 

Käyttöliittymään on lisätty Fringe Search-algoritmin ajaminen, reittien näyttäminen karttaruudukolla, reitinhakuun kuluneen ajan ja reitin pituuden liikkumispisteinä näyttäminen ja mahdollisuus useampaan hakuun lataamatta kartaa uuudestaan.

Olen myös aloittanut käyttöohjeen, testausdokumentin ja toteutusdokumnetin kirjoittamisen.

## Miten edistytty

Käyttöliittymässä on nyt mukana kaikki kolme vertailtavaa algoritmia. Eri algoritmien hakemat reitit näytetään kartalla erivärisinä palloina heksoissa. Kunkin algoritmin reitinhakuun käyttämä aika näytetään myös. Samoin reitin pituus liikkumispisteinä. Kartan maastoja ei vielä näytetä.

 Reitinhakuihin on lisätty reitin pituuden liikkumispisteinä palauttainen. Reitinhakualgoritmien, etenkin Fringe Search-algoritmin, toteutuksiin on tehty korjauksia. Kaikki reitinhakualgoritmit löytävät nyt lyhimmän reitin.

Reitinhaun testausta on järkevöitetty käyttämällä abstarkia kantaluokkaa reitinhakutesteille, jolloin sama testi voidaan ajaa kaikille kolmelle reitinhakualgoritmille, Testejä on tehty hieman lisää,

Karttatulkissa on nyt sille suunnitellut ominaisuudet. Siltojen käsittelyssä voi olla vielä ongelmia. Testejä on hieman lisätty.

Omista tietorakenteista on toteutettuna edellisellä viikolla toteutettu linkitetty lista. Javan PriorityQueue:n korvaaminen itse tehdyllä keolla on vielä toteuttamatta. ArrayList ei ole enää käytössä minkään reitinhaun toteutuksessa, mutta tiedostonlukua hoitavasta aluokasta se pitää vielä korvata. Tässä luokassa ovat käytössä myös String.split() ja String.strip(), jotka korvataan myöhemmin omilla toteutuksilla.

Köyttöohje on osittain tehty, testausdokumentin ja toteutusdokumentin kirjoittaminen on aloitettu.

## Mitä opin

Tällä viikolla opin tekemään testeille abstraktin kantaluokan, jonka testejä voi käyttää useammalle saman rajapinnan toteuttavalle luokalle.

Opin myös lisää Battle for Wesnoth-karttatiedostoista. 

Fringe Search -algoritmiakin ymmärtää taas paremmin, kun on korjannut toteutuksen puutteita.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Battle for Wesnoth -karttatiedostojen dokumentaation vaillinaisuus on aiheuttanut päänvaivaa. Maastotyyppien esittely ei ollutkaan kattava (ainakin tiet puuttuivat). 

Tulkitsin välillä väärin aloitetaanko kartan sarakkeiden numerointi nollasta vai ykkösestä eli mitkä sarakkeet tulkitaan parillisiksi ja muuttelin turhaan koodia edestakaisin.  

Aiempi puutteellinen testaus kostautui tällä viikolla. Luokista löytyi useita vikoja ja tällä viikolla on mennyt paljon aikaa niiden etsimisessä ja korjaamisessa. 

Fringe Search -toteutukseni tuotti usein muita algoritmeja pidemmän reitin. Kerran niin oli käynyt A*-algoritmillekin. Ongelma poistui, kun vaihdoin karkeampaan, useimmiten alakanttiin menevään, arvioon pienimmästä mahdollisesta jäljellä olevasta matkasta. Eli ilmeiseti olin aiemmin laskenut pienimmän mahdollisen jäljellä olevan matkan liian suureksi. 

## Mitä seuraavaksi

Seuraavalla viikolla lisään käyttöliittymään jonkinlaisen maaston näyttämisen karttaruudukossa. Alan myös toteuttaa omana tietorakenteena minimikekona toteutettavaa prioriteettijonoa ja sen testejä. Jatkan myös suorituskykytestien toteuttamista ja dokumentaation kirjoittamista. Algoritmit toteuttaviin luokkiin ja karttatulkkiin teen lisää testejä ja  tarvittaessa korjauksia. 