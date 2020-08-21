# Toteutusdokumentti – Heksareitinhaku

Ohjelma hakee annatulta  [Battle for Wesnoth -pelin](https://www.wesnoth.org/) kartalta lyhimmän reitin annetusta alkupistestä annettuun kohteeseen.Ohjelma käyttää kolmea reitinhakualgoritmia: Djikstarn algoritmia, A*-algoritmia ja Fringe Search-algoritmia. A* ja Fringe srearch -algoritmit käyttävät heuristiikkanan pienintä mahdollista jäljelläoolevaa etäisyyttä, joka on laskettu muuntamalla koordinaatit kuutiokkoordinaateiksi ja laskemalla näiden kuutiokoordinaattien etäisyys.
Eri maastoille annetut liikkumispisteevaatimukset ovat ohjelman omia, eivät Battle for Wesnoth -pelin käyttämiä. 

Kaikkien algoritmien hakemat reitit näytetään kartalla. Koska Batlle for Wesnoth -pelissä on maasttojen esittämiseen yli 4000 kuvatiedostoa ja tietyssä heksassa olevam maaston ulkonäköön vaikuttavat myös viereisten heksojen maastot, ei tässä työssä ollut mielekästä yrittää esittää karttoja sellaisina kuin ne pelissä näkyvät. Sen sijaan maastot esitetään karkeasti maastotyypeittäin heksojen väreinä kartalla. Kaikkien algorimien hakemien reittien pituudet liikkumispisteinä ja reitin hakemiseen kulunut aika näytetän myös,

## Ohjelman yleisrakenne

Varsinainen ohjelma on jaettu kolmeen pakkaukseen: sovelluslogiigalle, tiedostonkäsittelylle ja käyttöliittymälle on omat pakkauksensa. Lsiäksi on oma performance -pakkauksensa suoritusykytesteille.

Sovelluslogiikkkaan kuuluvat toteutetut tietorakenteet (linkitetty lista ja minimikeko), vertailtavat algoritmit toteuttavat luokat ja kaikkien vertailualgoritmien käyttämä karttatulkkiluokka. Karttatulkki kertoo reitinhakuoilioille kartan koon ja kartan heksasta toiseen siirtymisen vaatimat liikkumispisteet. Lisäksi pakkauksessa on oma luokka matetmaattisille apufunktioille.  


## Saavutetut aika- ja tilavaativuudet

### Keko

Djikstran  ja A*-algoritmien tarvitsema prioriteettijono on toteutettu minimibinäärikekona. Kekoon tallennetaan NextHexEdge-oliota, jotka tallentavat heksan koordinaatit ja prioriteetin. Keossa on sen luomisen jälkeen tilaa 64 oliolle ja se kasvatta aina täyttyesssään tilansa kaksinkertaiseksi. Keon tilavaativuus on O(n). Kekoon on toteutettu toiminnot sen tyhjyyden tarkastamiseen, alkion lisäämiseen kekoon sekä päällimmäisen alkion palauttamsieen poistaen se keosta. Tyhjyyden tarkastaminen on aikavaativuudeltaan O(1). Alkion lisääminen on aikavaativuudeltaan O(log n), koska keoehdon saattaminen uudelleen voimaan keossa on aikavaativuudeltaan O(log n). Pienimmän alkion ottaminen keosta on myös O(log n), koska pienin alkio myös poistetaan keosta ja kekoehto on siksi saatettava uudelleen voimaanm.     

### Kaksisuuntaisesti linkitetty lista

Fringe Search -algoritmia varten on toteutettu kaksisuuntaisesti linkitetty lista. Lista on toteutettu kahtena luokkana. CoordinateListItem tallentaa koordinaatit ja prioriteetin sekä linkit seuraavaan ja edelliseen listalla oiljaan.  CoordinateListItem toimii samalla iteraattorina, siirtyminen listalla seuraavaan olioon on toteutettu sen getNext()-funktiolla. Tämän funktion akavaativuus on O(1). Lsiäksi siihen on toteutettu funktioita, joita on tarkoitus käyttää vain varsinaisen listaluokan kautta. Itse CoordinateList-luokka ylläpitää tietoa listan ensimmäisestä alkiosta ja siihen on toteutettu funktiot listan tyhjyyden tarkistamisellle, alkon lisäämiselle listan alkuun, alkion lisäämiselle toisen alkion jälkeen ja alkion poistamiselle listasta. Kaikki nämä funktiot ovat aikavaativuudeltaan O(1). Lsitan kaksisuuntaisuus mahdollistaa pmahdollistaa l (Alkion etsimistä listasta ei ole toteutettu, koska Fringe Search-algoritmin toteutus ylläpitää aputaulukkoa, josta kaikki listalle mahdollisesti laitettavat oliot löytyvät suoraan.) Listan tilavaativuus on O(n).  

### Djikstran algoritmi

Algorritmi käyttä useta aputaulukoita ja käsiteltävistä kaarista luodoaan oliot. Kaaria on solmua kohti enintään kuusi. Tilavaativuus on O(n) melko suurella kertoimella. 

Djikstran algoritmin osalta tavoitteena oleva aikavaativuus on tirakirjan mukainen Djikstran aikavaativuus O(n+mlog(n), jossa n on solmujen (heksojen) määrä ja m kaarien eli heksojen välisten yhteyksien määrä. Kuusikulmioista koostuvalla kartalla ei voi olla enempää kuin 6n yhteyttä yhdestä kuusikulmiosta muihin, joten aikavaativuus voidaan kirjata myös muodossa O(n+nlog(n)). Djikstran algoritmi tarvitsee itse kartan (taulukko, keon, ja taulukot etäisyyksille, tiedolle siitä onko solmua jo käsitelty ja tiedolle siitä mistä solmuun tultiin. Taulukoiden koko on n, keossa ei voi olla ainakaan enempäää kuin jokaisen heksan yhteys kaikkiin kuuteen viereiseen heksaan elin nm = 6n kaarta kerralla. Tilavaativuustavoite on tämän peusteella O(n).

### A*-algoritmi

A*-algoritmi on toiminnaltaan hyvin lähellä Djikstran algoritmia, minkä vuoksi sen aikavaatimuustavoite on sama kuin Djikstran algoritmilla, O(n+nlog(n)). A*-algoritmi käyttää samoja tietorakenteta kuin Djikstran algoritmi, joten sen tilavaativuustavoitekin on sama kuin Djikstran algoritmillla eli O(n).

### Fringe Search -algoritmi

Fringe search -algortmi tutkii osan solmuista useamman kerran, joten se voi pahimmillaan tutkia > n solmua. Toisaalta se ei kuluta aikaa järjestyksen ylläpitoon. Kutakin solmua tutkittaessa saatetaan lisätä ja poistaa linkitetystä listasta (molemmat O(1) ja etsiä linkitetystä listasta O(n). Aikavaativuustavoite on O(n^2 ). Tilaa tarvitaan itse kartalle, käsittelyä samalla tai seuraavalla kierroksella odottavien solmujen listalle, "välimuistille", aputaulukolle, joka kertoo onko solmu listalla ja etäisyys taulukolle. Kaikissa näissä tilantarve on suhteessa heksojen tai niiden välisten yhteyksien määrään (joka on max 6 n), joten tilavaativuustavoite on O(n).

## Suorituskyky ja O-analyysivertailu totetutettujen reitinhakualgoritmien väilllä

## Puutteita ja parannusehdotuksia

## Lähteet

Björnsson, Yngvi, Enzrnberger, Markus, Holte, Robert C. & Schaeffer, Jonathan 2005: Fringe Search: Beating A\* at Pathfinding on Game Maps
http://www.cs.ualberta.ca/~games/pathfind/publications/cig2005.pdf

Laaksonen, Antti 2019: Tietorakenteet ja algoritmit

Patel, Amit 2020: Introduction to A\* https://www.redblobgames.com/pathfinding/a-star/introduction.html

Red Blob Games 2020: Hexagonal Grids https://www.redblobgames.com/grids/hexagons/

