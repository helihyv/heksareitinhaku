# Toteutusdokumentti – Heksareitinhaku

Ohjelma hakee annatulta  [Battle for Wesnoth -pelin](https://www.wesnoth.org/) kartalta lyhimmän reitin annetusta alkupisteestä annettuun kohteeseen. Ohjelma käyttää kolmea reitinhakualgoritmia: Djikstran algoritmia, A*-algoritmia ja Fringe Search-algoritmia. 
Eri maastoille annetut liikkumispistevaatimukset ovat ohjelman omia, eivät Battle for Wesnoth -pelin käyttämiä. 

Kaikkien algoritmien hakemat reitit näytetään kartalla. Koska Battle for Wesnoth -pelissä on maastojen esittämiseen yli 4000 kuvatiedostoa ja tietyssä heksassa olevan maaston ulkonäköön vaikuttavat myös viereisten heksojen maastot, ei tässä työssä ollut mielekästä yrittää esittää karttoja sellaisina kuin ne pelissä näkyvät. Sen sijaan maastot esitetään karkeasti maastotyypeittäin heksojen väreinä kartalla. Kaikkien algorimien hakemien reittien pituudet liikkumispisteinä ja reitin hakemiseen kulunut aika näytetän myös.

## Ohjelman yleisrakenne

Varsinainen ohjelma on jaettu kolmeen pakkaukseen: sovelluslogiikalle, tiedostonkäsittelylle ja käyttöliittymälle on omat pakkauksensa. Lisäksi on oma performance -pakkauksensa suoritusykytesteille.

Sovelluslogiikkkaan kuuluvat toteutetut tietorakenteet (linkitetty lista ja minimikeko), vertailtavat algoritmit toteuttavat luokat ja kaikkien vertailualgoritmien käyttämä karttatulkkiluokka. Karttatulkki kertoo reitinhakuolioille kartan koon ja kartan heksasta toiseen siirtymisen vaatimat liikkumispisteet. Lisäksi pakkauksessa on oma luokka matetmaattisille apufunktioille.  


## Saavutetut aika- ja tilavaativuudet

### Keko

Djikstran  ja A*-algoritmien tarvitsema prioriteettijono on toteutettu minimibinäärikekona. Kekoon tallennetaan NextHexEdge-oliota, jotka tallentavat heksan koordinaatit ja prioriteetin. Keossa on sen luomisen jälkeen tilaa 64 oliolle ja se kasvattaa aina täyttyesssään tilansa kaksinkertaiseksi. Keon tilavaativuus on O(n). Kekoon on toteutettu toiminnot sen tyhjyyden tarkastamiseen, alkion lisäämiseen kekoon sekä päällimmäisen alkion palauttamiseen poistaen se keosta. Tyhjyyden tarkastaminen on aikavaativuudeltaan O(1). Alkion lisääminen on aikavaativuudeltaan O(log n), koska kekoehdon saattaminen uudelleen voimaan keossa on aikavaativuudeltaan O(log n). Pienimmän alkion ottaminen keosta on myös O(log n), koska pienin alkio myös poistetaan keosta ja kekoehto on siksi saatettava uudelleen voimaan.     

### Kaksisuuntaisesti linkitetty lista

Fringe Search -algoritmia varten on toteutettu kaksisuuntaisesti linkitetty lista. Lista on toteutettu kahtena luokkana. CoordinateListItem tallentaa koordinaatit ja prioriteetin sekä linkit seuraavaan ja edelliseen listalla olijaan.  CoordinateListItem toimii samalla iteraattorina, siirtyminen listalla seuraavaan olioon on toteutettu sen getNext()-funktiolla. Tämän funktion aikavaativuus on O(1). Lisäksi luokkaan on toteutettu funktioita, joita on tarkoitus käyttää vain varsinaisen listaluokan kautta. Itse CoordinateList-luokka ylläpitää tietoa listan ensimmäisestä alkiosta ja siihen on toteutettu funktiot listan tyhjyyden tarkistamiselle, alkion lisäämiselle listan alkuun, alkion lisäämiselle toisen alkion jälkeen ja alkion poistamiselle listasta. Kaikki nämä funktiot ovat aikavaativuudeltaan O(1). Listan kaksisuuntaisuus mahdollistaa mahdollistaa alkion poistamisen aikavaativuudella O(1). (Alkion etsimistä listasta ei ole toteutettu, koska Fringe Search-algoritmin toteutus ylläpitää aputaulukkoa, josta kaikki listalle mahdollisesti laitettavat oliot löytyvät suoraan.) Listan tilavaativuus on O(n).  

### Djikstran algoritmi

Algorritmi käyttää useita aputaulukoita ja kekoa ja käsiteltävistä kaarista luodoaan oliot. Kaaria on solmua kohti enintään kuusi. Tilavaativuus on O(n) melko suurella kertoimella. Tutkittavia kaaria on kiinteästi kuusi solmua kohden (kartan rajojen tarkistaminen on jätetty karttatulkin vastuulle). Karttatulkki käy läpi lähtöheksan ja kohdeheksan maastokoodit joka kerta selvittäessään vaadittavaa liikkumispistemäärää. Maastokoodien pituus vaihtelee, mutta on ainakin useimmiten alle 10 merkkiä, joten maastokoodien käsittely voitaneen katsoa vakiokertoimeksi. Käsittelyä odottavat kaaret tallennetaan kekoon, jonka operaatioiden aikavaativuus on O(log(n)). Algoritmin aikavaativuus on siten O(n+log(n)) melko suurella kertoimella

### A*-algoritmi

A*-algoritmi on toiminnaltaan hyvin lähellä Djikstran algoritmia. Ainoa ero, joka voisi vaikuttaa aikavaativuuteen on heuristiikan laskeminen. Heuristiikkana käytetään pienintä mahdollista jäljelläolevaa etäisyyttä, joka on laskettu muuntamalla koordinaatit kuutiokoordinaateiksi ja laskemalla näiden kuutiokoordinaattien etäisyys. Tämä lasketaan ajassa O(1), joten A*-algoritmin aikavaativuus on sama kuin Djikstran algoritmilla, O(n+nlog(n)). A*-algoritmi käyttää samoja tietorakenteta kuin Djikstran algoritmi, joten sen tilavaativuus on sama kuin Djikstran algoritmillla eli O(n).

### Fringe Search -algoritmi

Fringe search -algortmi tutkii pintapuolisesti osan solmuista useamman kerran. Pahimmillaan se voisi teoriassa käydä jokaisen tutkimansa solmun kohdalla muut solmut lyhyesti läpi. Toisaalta se ei kuluta aikaa järjestyksen ylläpitoon. Kutakin solmua tutkittaessa käytetään aputaulukoita (O(1)) ja  saatetaan lisätä ja poistaa linkitetystä listasta (molemmat O(1)). Heuristiikka lasketaan samalla menetelmällä kuin A*-algoritmissa, joten heuristiikan laskemisen aikavaativuus on O(1). Fringe Search algoritmin toteutuksessa saavutettu aikavaativuus on O(n^2 ). 

Algoritmi tarvitsee useita aputaulukkoja, joiden kaikkien tilantarve saadaan suoraan kartan koosta ja linkitetyn listan, jolla kukin solmu voi olla samanaikaisesti vain kerran. Saavutettu tilavaativuus on O(n), tosin melko suurella kertoimella, sillä jokaista solmua kohden luodaan olio.

## Suorituskyky ja O-analyysivertailu totetutettujen reitinhakualgoritmien väilllä

## Puutteita ja parannusehdotuksia

## Lähteet

Björnsson, Yngvi, Enzrnberger, Markus, Holte, Robert C. & Schaeffer, Jonathan 2005: Fringe Search: Beating A\* at Pathfinding on Game Maps
http://www.cs.ualberta.ca/~games/pathfind/publications/cig2005.pdf

Laaksonen, Antti 2019: Tietorakenteet ja algoritmit

Patel, Amit 2020: Introduction to A\* https://www.redblobgames.com/pathfinding/a-star/introduction.html

Red Blob Games 2020: Hexagonal Grids https://www.redblobgames.com/grids/hexagons/

