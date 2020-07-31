# Viikkoraportti 2

## Mitä tehty

Tällä viikolla (la-pe) työhön on menny aikaa noin 12 h. Tänä aikana olen luonut projektin ja tehnyt sille jacoco- ja alustavat checkstyle-asetukset, työstänyt Djikstran algoritmin toteutusta, kartan tulkintaa, kartan lukemista tiedostosta ja ottanut käytttöliittymässä käyttöön jJavaFX:n. 

## Miten edistytty

Djikstran algoritmi on toteuttu käyttäen vielä javan PriorityQueue:a ja ArrayList:iä. (Jälkimmäinen on korvattavissa taulukolla.) Luokalle on tehty testit yksinkertaisimmille tilanteille. Algoritmin tarvitsema kaarilluokka on myös toteutettu ja sille on tehty testejä. 

Kaikille reitinhakualgoritmeille yhteinen kartan tulkinnnasta huolehtiva luokka on lähes valmis, teiden ja siltojen suuntien huomioiminen puuttuu vielä. 

Kartan tiedostosta lukeva luokka on toteutettu, mutta täysin testaamaton. Siellä on vielä käytetty ArrayList:iä ja String.split():iä. 

Käyttöliittymä on aivan alkutekijöissään, sovelluksen ikkuna aukeaa, sinä kaikki.

## Mitä opin

Tällä viikolla opin reitin talteenottamista Djikstran algoritmissa, checkstylen konfiguraatiota ja Battle for Wesnoth -pelin karttatiedostojen tulkintaa. Huomasin myös tarvitsevani jonkinlaista listaa tiedostosta luvun yhteydessä.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Djikstran algortimin toteutuksessa ei vielä ole mukana suunnittelemaani vuoropohjaisuuden huomioimista reitin valinnassa, sen mukaanotto on yhä harkinnassa.

Kartan näyttämisessä vaihtoehtoina ovat vielä pelin omien grafiikoiden (saatavilla GPL-lisenssillä) käyttäminen, tai yksinkertaistettu eriväristen kuusikulmioiden piirtäminen. 

## Mitä seuraavaksi

Seuraavalla viikolla toteutan A*-algoritmin ja sen testit, lisää testejä Djikstran algoritmille, aloitan Fringe Search-algoritmin toteuttamisen, täydennän karttatulkkia ja teen sille testit, teen tiedostonluvulle testit ja työstän käyttöliittymää eteenpäin.

