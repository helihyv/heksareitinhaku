# Viikkoraportti 5

## Mitä tehty

Tällä viikolla (la-pe) käytin projektiin, vertaisarviointi mukaanluettuna, n. 16   tuntia.                                                                                                                                                           Tällä viikolla olen toteuttanut oman keon ja sille testit. Lisäksi olen vaihtant käyttöön paremman heuristiikanlaskennan ja lisännyt reitinhakuluokille uusia testejä. Käyttöliittymän olen lisännyt karkean maastojen näyttämisen ja vierityspalkit kartan ympärille. Olen myös täydentänyt dokumentaatiota.

## Miten edistytty

Oma mimikeko (binäärikekona) on toteutettu ja otettu käyttöön kaikissa reitinhakuluokissa. Keolle on myös tehty testit. 

Javan luokkia ja funktioita, jotka eivät saa jäädä lopulliseen versioon on enää käytetty tiedoston lukevassa ja karttataulukoksi tulkitsevassa luokassa.

Heuristiikkana käytettävä pienin mahdollinen jäljellä oleva matka lasketaan nyt muuntamalla koordinaatit "kuutiokoordinaateiksi" ja laskemalla Manhattan-etäisyys 
kuutiokoordinaateista. (Sivun [https://www.redblobgames.com/grids/hexagons/](https://www.redblobgames.com/grids/hexagons/) ohjeita mukaillen.) Tämä korjasi vian jossa A*-algoritmi joissain tilanteissa harhautui lyhyimmältä reitiltä yhtä pidemmälle reitille. Tämä heuristiikka on otettu käyttöön sekä A*- että Fringe Search -algoritmeissa.

Käyttöliittymään on lisätty hyvin karkea kartan maastojen näyttäminen. Maastotyypit näytetään väreinä, esim. kaikki erilaiset vesimaastotyypit näytetään samansiniisina. Monet liikkumispisteisiin vaikuttamattomat asiat kuten koristeet jätetään kokonaan huomioimatta. Siltojen suunnan näyttämistä ei ole vilä toteutettu. 

Käyttöliittymä näyttää suuret kartat nyt vierityspalkeilla varustettuna.

Käyttöliittymä ja suorituskykytestit on jätetty pois testikattavuusraportista.

## Mitä opin

Opin tällä viikolla heksakartan offset-koordinaattien muuntamisen kuutiokoordinaateiksi ja etäisyyden laskemisen kuutiokoordinaateilla. Lisäksi keon toiminta tuli kerrattua.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

Suorituskykytestien toteuttamisessa mietin vielä kannattaisiko ne liittää varsinaiseen ohjelmaan vai tehdä niistä oma konsolista ajettava ohjelmansa.

Mietin myös onko tiedoston koon selvittäminen  File::length() -funktiolla sallittua (jolloin voisi varata suoraan varmasti riittävän kokoisen taulukon maastokoodeille eikä tarvitsisi toteuttaa taulukkolistaa tms.).

Koska Battle of Wesnoth -pelissä maastot vaikuttavat kartan ulkonäköön myös viereisten heksojen kohdalla ja erilaisia kuvatiedostoja maastojen piirämiseen löytyi wesnothin reposta yli 4000, ei karttaa voinut tämän projektin puitteissa kuitenkaan saada näyttämään läheskään samalta kuin itse pelissä. Päädyin samantien pelkistämään maastojen näyttämisen karkeiksi värikoodeiksi. 

Siltojen käsittelyssä on yhä virheitä, karttatulkkia pitää vielä korjata.

## Mitä seuraavksi

Seuraavalla viikolla korvaan tiedostonlukuluokasta String::split() ja String::strip() -funktiot omalla toteutuksella ja samassa luokassa käytetyn ArrayList:in joko tavallisella taulukolla ja tai omalla toteutuksella. Lisäksi korjaan siltojen käsittelyn karttatulkissa ja kartan näyttämisessä. Jatkan myös suorituskykytestien toteuttamista ja täydennän dokumentaatiota.
