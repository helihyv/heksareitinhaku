# Testausdokumentti – Heksareitinhaku

## Yksikkötestit

Ohjelmaan on tehty automaattisia JUnit-testejä sovelluslogiikalle ja tiedostonkäsittelylle. Käyttöliittymälle ja suorituskykytesteille ei ole automaattisia testejä. Testin voi ajaa antamalla hakemsitossa heksareitinhaku komennon ./gradlew test .

Testikattavuuden raportoinnissa on käytetty jacocoa. Raportin voi generoida esim. antamalla komennon ./gradlew check hakemistossa heksareitinhaku. Raportin pääsivu muodostuu hakemistoon heksareitinhaku/build/reports/jacoco/test/html/index.html

Reitinhakualoritmien testeillä on yhteinen abstrakti kantaluokka, johon varsinaiset testit on sijoitettu. Kantaluokasta periytetyt reitinhakuluokkien testiluokat sisältävät funktiot, jotka palauttavat kyseisestä luokan olion. Reitinhakuluokkien yksikkötestejä vartren on toteutettu hyvin yksinkertainen mock-versio karttatulkkiluokasta. Lisäksi luokille on integraatiotestejä, joissa niitä testaan yhdessä karttatulkkiluokan ja karttatiedostonlukuluokan kanssa. Näissä testeissä syötteenä käyettään testejä varten tehtyjä karttatiedostoja.

Kekoa on testattu yhdessä sen alkioina käytettävän luokan NextHexEdge kanssa. NextEdgeHedge-luokalle on lisäksi omat testit compareTo-funktiolle.

Linkitetyä listaa on testattu yhdessä se alkioina käytettävän CoordinatListItem:in kanssa. CoordinateListItem-luokalle ei ole tämän lisäksi omia testejä sen yksinkertaisuuden vuoksi.

Karttatulkkia on testattu käyttäen syötteenä pieniä testiluokan sisään toteutettuja karttoja.

Testikattavuus paketeittain:

![testikattavuus paketeittain](/dokumentaatio/testikattavuus_paketeittain.png)

Logic-paketin testikattavuus luokittain: 

![logic-paketin testikattavuus luokittain](/dokumentaatio/testikattavuus_logic.png)


## Suoritusykytestit

Reitinhakualgoritmeja varten on toteuttu suorituskykytestejä. Vielä hiean keskeneräinen uorituskykytesti löytyy pakkauksesta performance. Testissä tehdään samat satunnaiset reitinhaut (1000 per kartta, kartat toistaiseksi kiinteästi määritetty.) Kaikilla kolmella algoritmilla mitataan hakuihin keskimäärin kuluva aika ja sen keskihajonta. Nämä testit ovat toistaiseksi ajettavissa vasta netbeansin Run file -toiminnolla.

Suorituskykytestit ajettiin fuksilaitteella vuosimallia 2019 (Thinkpad-kannettava). 

Huolimatta suurimmasta O-arvostaan fringe search-algoritmi oli kuudesta testatusta kartasta viidellä selvästi nopein. Yhdellä kartoista kaikki kolme algoritmia olivat käytännöllisesti katsoen yhtä nopeita. Tämä kartta koostui enmmäkseen kapeahkoista käytävistä. A* oli joillain kartoilla nopeampi kuin Djikstran algoritmi, joillain ne olivat hyvin tasaväkiset. Reitinhakuun kuluvaa aikaa määritteli kuitenkin enemmän kartan kulkukelpoisen alueen koko kuin käytetty algoritmi. Hajonnat olivat ymmärettävästi suuria suhteessa keskimääräiseen käytettyyn aikaan.


