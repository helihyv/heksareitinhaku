# Testausdokumentti – Heksareitinhaku

## Yksikkötstit

Ohjelmaan on tehty automaattisia JUnit-testejä sovelluslogiikalle ja tiedostonkäsittelylle. Käyttöliittymällle ei ole automaattisia testejä.

Testikattavuuden raportoinnissa on käytetty jacocoa. Raportin voi generoida esim. antamalla komennon ./gradlew check hakemistossa reitinhaku. Raportin pääsivu muodostuu hakemistoon heksareitinhaku/build/reports/jacoco/test/html/index.html

## Suoritusykytestit

Reitinhakualgoritmeja varten on alettu toteuttamaan suorituskykytestejä. Keskeneräinen alustavasuorituskykytesti löytyy pakkauuksesta performance. Testissä tehdään samat satunnaset reitihaut (100 per kartta, kartat puuttuvat vielä) kaikilla kolmella algoritmilla ja mitataan niihin yhteensä kuluva aika. 