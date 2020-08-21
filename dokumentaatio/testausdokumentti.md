# Testausdokumentti – Heksareitinhaku

## Yksikkötestit

Ohjelmaan on tehty automaattisia JUnit-testejä sovelluslogiikalle ja tiedostonkäsittelylle. Käyttöliittymälle ja suorituskykytesteille ei ole automaattisia testejä. Testin voi ajaa antamalla hakemsitossa heksareitinhaku komennon ./gradlew test .

Testikattavuuden raportoinnissa on käytetty jacocoa. Raportin voi generoida esim. antamalla komennon ./gradlew check hakemistossa heksareitinhaku. Raportin pääsivu muodostuu hakemistoon heksareitinhaku/build/reports/jacoco/test/html/index.html

Reitinhakualoritmien testeillä on yhteinen abstrakti kantaluokka, johon varsinaiset testit on sijoitettu. Kantaluokasta periytetyt reitinhakuluokkien testiluokat sisältävät funktiot, jotka palauttavat kyseisestä luokan olion. Reitinhakuluokkien yksikkötestejä vartren on toteutettu hyvin yksinkertainen mock-versio karttatulkkiluokasta. Lisäksi luokille on integraatiotestejä, joissa niitä testaan yhdessä karttatulkkiluokan ja karttatiedostonlukuluokan kanssa. Näissä testeissä syötteenä käyettään testejä varten tehtyjä karttatiedostoja.

Kekoa on testattu yhdessä sen alkioina käytettävän luokan NextHexEdge kanssa. NextEdgeHedge-luokalle on lisäksi omat testit compareTo-funktiolle.

Linkitetyä listaa on testattu yhdessä se alkioina käytettävän CoordinatListItem:in kanssa. CoordinateListItem-luokalle ei ole tämän lisäksi omia testejä sen yksinkertaisuuden vuoksi.

Karttatulkkia on testattu käyttäen syötteenä pieniä testiluokan sisään toteutettuja karttoja.

## Suoritusykytestit

Reitinhakualgoritmeja varten on alettu toteuttamaan suorituskykytestejä. Keskeneräinen alustava suorituskykytesti löytyy pakkauksesta performance. Testissä tehdään samat satunnaiset reitinhaut (100 per kartta, kartat puuttuvat vielä) kaikilla kolmella algoritmilla ja mitataan niihin yhteensä kuluva aika. Nämä testit eivät ole vielä ajettavissa.
