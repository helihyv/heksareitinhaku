# Viikkoraportti 3

## Mitä tehty

Tällä viikolla olen kehittänyt käyttöliittymää sekä tehnyt testejä kartan tiedostosta lukevalle uokalle ja korjannut luokkaa. Olen myös toteuttanut A*-algoritmin ja sille hieman testejä sekä aloittanut Fringe Search -algoritmin ja linkitetyn listan toteuttamisen.  

## Miten edistytty

A*-algoritmi on toteutettu. Sille on testit aivan yksinkertaisimpiin tapauksiin. Fringe Search-alogoritmita on tehty vielä toimimaton versio. Se ei vielä löydä reittejä. Sille on testit aivan yksinkertaisimpiin tapauksiin, tosin vain yksi testi menee läpi. Fringen algoritmia varten on toteutettu kaksisuuntaisesti linkitetty lista, jossa on toteutettu algoritmin tarvitsemat toiminnot. Sille ei ole vielä omia testejä.

Tiedoston lukemisesta huolehtuvalle luokalle on tehty testejä.

Käyttöliittymään on lisätty mahdollisuus karttatiedoston avaamiseen. Käyttöliittymään on listty karttaruudukko, joka näyttää ruutujen reunat. Kartan sisältöä ei vielä näytetä. Ruudukosta voi valita haun alkupisteen ja kohteen. Kun kohde on valitu, ohjelma hakee reitin Djikstran - ja A*-algoritmeilla ja kertoo löysivätkö algoritmit reitin. Reittiä ei vielä näytetä käyttöliittymässä.

## Mitä opin

Fringe-algoritmia toteuttaessani ymmärsin sen toiminnasta paljon enemmän kuin vasta luettuani artikkelin. Käyttöliittymää tehdessä opin vierekkäisten kuusikulmioiden piirtämistä (sijaintien laskeminen).

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Vuoropohjaisuuden huomioonottaminen reittiö haettaessa on yhä toteuttamatta, toteutan sen jos ehdin.

Kartan osalta pelin omien grafiikoiden käyttäminen tunutt tällä hetkellä mielekkäimmältä, Varavaihtoehtona on ertyyppisten maastojen kuvaaminen väreillä.

Fringen algoritmin toteuttaessa pseudokoodin tulkitseminen osoittautui haastavaksi.

## Mitä seuraavaksi

Ensi viikolla teen Fringen algoritmin valmiiksi, linkitetyn listan omaat  testit ja kaikille kolmelle algoritmille lisää testejä. Karttatulkiin lisään teiden ja siltojen suuntien käsittelyn ja teen sille le lisää testejä. Käyttöliittymään lisään reitin ja kartan sisällön näyttämisen. 
