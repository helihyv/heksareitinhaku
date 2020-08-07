# Viikkoraportti 3

## Mitä tehty

Tällä viikolla (la-pe) olen käyttänyt projekttin noin 21 tuntia. Tällä viikolla olen kehittänyt käyttöliittymää sekä tehnyt testejä kartan tiedostosta lukevalle uokalle ja korjannut luokkaa. Olen myös toteuttanut A*-algoritmin ja sille hieman testejä. kaksisuuntaisesti linkitetyn listan ja sille testejä sekä toteuttanut Fringe Search -algoritmin ja sille hieman testejä.  

## Miten edistytty

A*-algoritmi ja Fringe Search -algoritmi on toteutettu. Niille on testit aivan kaikkein yksinkertaisimpiin tapauksiin. 

Fringe Search -algoritmia varten on toteutettu kaksisuuntaisesti linkitetty lista, jossa on toteutettu algoritmin tarvitsemat toiminnot. Sille on tehty listatason testit (yksittäiselle listakomponetille ei ole tehty erillisiä testetjä).
 
Tiedoston lukemisesta huolehtuville luokalle on tehty testejä ja korjauksia.

Itseisarvon ja minimin laskeville apufunktioille on tehty oma SimpleMath-luokkansa.

Käyttöliittymään on lisätty mahdollisuus karttatiedoston avaamiseen. Käyttöliittymään on myös lisätty karttaruudukko, joka näyttää ruutujen reunat. Se tulee näkyviin kartan kokoisena, kun kartta on ladattu. Kartan sisältöä ei vielä näytetä. Ruudukosta voi valita haun alkupisteen ja kohteen. Kun kohde on valitu, ohjelma hakee reitin Djikstran - ja A*-algoritmeilla ja kertoo löysivätkö algoritmit reitin. Reittiä ei vielä näytetä käyttöliittymässä. (Tätä voi kokeilla vaikkapa tiedostonlukutesteissä käytetyllä minikartalla, joka löytyy tiedostosta heksareitinhaku/src/test/resources/verysmallmap .)

Testikattavuus:
![Testikattavuus](dokumentaatio/testikattavuusviikko3.png)


## Mitä opin

Fringe-algoritmia toteuttaessani ymmärsin sen toiminnasta paljon enemmän kuin vasta luettuani artikkelin. 

Käyttöliittymää tehdessä opin vierekkäisten kuusikulmioiden piirtämistä (sijaintien laskeminen).

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Vuoropohjaisuuden huomioonottaminen reittiä haettaessa on yhä toteuttamatta, toteutan sen jos ehdin.

Kartan osalta pelin omien grafiikoiden käyttäminen tuntuu tällä hetkellä mielekkäimmältä, Varavaihtoehtona on erityyppisten maastojen kuvaaminen pelkistetysto eri väreillä.

## Mitä seuraavaksi

Ensi viikolla teen  kaikille kolmelle reitinhakualgoritmille lisää testejä ja mahdollisesti tarvittavat korjaukset. Karttatullkiin lisään teiden ja siltojen suuntien käsittelyn ja teen sille lisää testejä. Käyttöliittymään lisään reitin ja kartan sisällön näyttämisen ja muokaan sen tekemään haun myös Fringe Search-algoritmilla. Aloitan myös kirjoittamaan toteutus- ja testausdokumentaatiota. Jos aikaa jää alan toteuttamaan vuoropohjaisuuden huomioonottamista reitinhakualgritmeissa ja oman prioriteettijonon toteuttamista kekona.
