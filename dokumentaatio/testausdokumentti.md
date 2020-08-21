# Testausdokumentti – Heksareitinhaku

## Yksikkötstit

Ohjelmaan on tehty automaattisia JUnit-testejä sovelluslogiikalle ja tiedostonkäsittelylle. Käyttöliittymällle ei ole automaattisia testejä.

Testikattavuuden raportoinnissa on käytetty jacocoa. Raportin voi generoida esim. antamalla komennon ./gradlew check hakemistossa reitinhaku. Raportin pääsivu muodostuu hakemistoon heksareitinhaku/build/reports/jacoco/test/html/index.html

Reitinhakualoritmien testetillö on yhteinen abstrakti kantaluokka, jhon varsinaiset testit n sijoitettu. Kantaluokasta periytetyt reitinhakuluokkien testiluokat sisältävät funktiot, jotka plalauttavat kyseisestä luokan olion. Reitinhakuluokkien yksikkötestejä vartren on toteutettu hyvin yksinkertainen mock-versio karttatulkkiluokasta. Lisäksi luokille on integraatiotestejä, joissa niitä testaan yhdessä karttatulkkiluokan kanssa.

## Suoritusykytestit

Reitinhakualgoritmeja varten on alettu toteuttamaan suorituskykytestejä. Keskeneräinen alustavasuorituskykytesti löytyy pakkauuksesta performance. Testissä tehdään samat satunnasiet reitihaut (100 per kartta, kartat puuttuvat vielä) kaikilla kolmella algoritmilla ja mitataan niihin yhteensä kuluva aika. Nämä testit eivät ole vielä ajettavissa.