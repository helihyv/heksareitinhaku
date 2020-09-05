# Testausdokumentti – Heksareitinhaku

## Yksikkö- ja integraatiotestit

Ohjelmaan on tehty automaattisia JUnit-testejä sovelluslogiikalle ja tiedostonkäsittelylle. Käyttöliittymälle ja suorituskykytesteille ei ole automaattisia testejä. Testin voi ajaa antamalla hakemsitossa heksareitinhaku komennon ./gradlew test . Testetissö käytetyt karttatiedostot löytyvät hakemistosta heksareitinkhaky/src/test/resources

Testikattavuuden raportoinnissa on käytetty jacocoa. Raportin voi generoida esim. antamalla komennon ./gradlew check hakemistossa heksareitinhaku. Raportin pääsivu muodostuu hakemistoon heksareitinhaku/build/reports/jacoco/test/html/index.html

Reitinhakualoritmien testeillä on yhteinen abstrakti kantaluokka, johon varsinaiset testit on sijoitettu. Kantaluokasta periytetyt reitinhakuluokkien testiluokat sisältävät funktiot, jotka palauttavat kyseisestä luokan olion. Reitinhakuluokkien yksikkötestejä varten on toteutettu hyvin yksinkertainen mock-versio karttatulkkiluokasta. Se palauttaa aina kartan sisällä saman, silel parametrina annetun, liikkumispistemäärän, Lisäksi luokille on integraatiotestejä, joissa niitä testataan yhdessä karttatulkkiluokan ja karttatiedostonlukuluokan kanssa. Näissä testeissä syötteenä käyettään testejä varten karttaeditorilla tehtyjä  (ja kartan yleistiedot poistamalla sovellukseen sopiviksi muokattuja) karttatiedostoja.

Kekoa on testattu yhdessä sen alkioina käytettävän luokan NextHexEdge kanssa. NextEdgeHedge-luokalle on lisäksi omat testit compareTo-funktiolle.

Linkitetyä listaa on testattu yhdessä se alkioina käytettävän CoordinatListItem:in kanssa. CoordinateListItem-luokalle ei ole tämän lisäksi omia testejä sen yksinkertaisuuden vuoksi.

Karttatulkkia on yksikkötestattu käyttäen syötteenä pieniä testiluokan sisään toteutettuja karttoja.

Kartan tiedostosta lukevaa luokkaa on yksikkötestattu köyttäen syötteenä tedtejä varten käsin tehtyä karttatiedostoa.

Testikattavuus paketeittain:

![testikattavuus paketeittain](/dokumentaatio/testikattavuus_paketeittain.png)

Logic-paketin testikattavuus luokittain: 

![logic-paketin testikattavuus luokittain](/dokumentaatio/testikattavuus_logic.png)


## Suoritusykytestit

Reitinhakualgoritmeja varten on toteuttu suorituskykytestejä. Suorituskykytestin jar-pakettti on mukana github-releasessa. Testissä käytettävät karttatiedostot annetaan komentoriviparametreina.  Testissä tehdään samat satunnaiset reitinhaut kaikilla algoritmeilla. Hakuja tehdään 1000 karttaa kohden. Kaikilla kolmella algoritmilla mitataan hakuihin keskimäärin kuluva aika ja sen keskihajonta. Sorituskykytestin koodi löytyy pakkauksesta performance. Testien jar-paketin voi muodostaa komennolla ./gradlew performanceTestJar . (Sitä ei modosteta komennon ./gradlew build yhteydessä)).

Suorituskykytestit ajettiin fuksilaitteella vuosimallia 2019 (Thinkpad-kannettava). Testeissä käytettiin kuutta Battle of Wesnoth- pelin kampanjoista otettua karttaa. Suorituskykytesteissä käytetyt kartat löytyvät hakemiststa heksareitnhaku/data , suuret ja keskisuuret kartat omista alihakemistoistaan. Suorituskykytestien tulokset vaihtelivat ajokertojen välillä. Silloin kun kaikkien algoritmien tulokset olivat lähellä toisiaan, niiden nopeusjärjestys vaihteli eri ajokerroilla. Reitinhakuun kuluneen ajan hajonta yhden testikerran sisöllä oli suurta, toisinaan keskihajonta jopa ylitti keskiarvon. Tämä kertoo ennenkaikkea haettujen reittien erilaisuudesta, onhan niiden pituus satunnainen. Osalla kartoista oli myös runsaasti kulkukelvotonta aluetta, jonka keskelle osunut reitin alkupite tuottaa hyvin nopean haun. 

Huolimatta suurimmasta O-arvostaan fringe search-algoritmi oli kuudesta testatusta kartasta viidellä selvästi nopein useimmila ajokerroilla. Yhdellä kartoista (Ray of hope) kaikki kolme algoritmia olivat käytännöllisesti katsoen yhtä nopeita. Tämä kartta koostui enmmäkseen kapeahkoista käytävistä. A* oli joillain kartoilla nopeampi kuin Djikstran algoritmi, joillain ne olivat hyvin tasaväkiset. Reitinhakuun keskimäärin kuluvaa aikaa määritteli kuitenkin enemmän kartan kulkukelpoisen alueen koko kuin käytetty algoritmi. Seuraavassa kuvaajassa on esitetty yhden ajokerran tulokset eri kartoille.  

![suorituskykyvertailu kartoittain](/dokumentaatio/suorituskykyvertailu.png)




