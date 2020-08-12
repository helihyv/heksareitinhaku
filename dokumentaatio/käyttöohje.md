# Käyttöohje – Heksareitinhaku

Ohjelma hakee annatulta  [Battle for Wesnoth -pelin](https://www.wesnoth.org/) kartalta reitin annetusta alkupistestä annettuun kohteeseen. Ohjelma käyttää kolmea reitinhakualgoritmia: Djikstarn algoritmia, A*-algoritmia ja Fringe Search-algoritmia. Kaikkien algoritmien hakemat reitit näytetään kartalla. Kartan sisältööä (maastoja) ei vielä näytetä, tämä on tarkoitus toteuttaa jollain tavalla myöhemmin,


## Ohjelman toiminta

Ohjelmaa käytettäessä on ensin ladattava kartta tiedostosta (Valitse karttatiedosto -nappi).Tämän jälkeen valitaan hiirellä esiin tulleesta ruudukosta heksa, josta haku aloitetaan ja sen jälkeen heksa, johon pyritään. Haku käynnistyy automaattisesti kun määränpää on valittu. Uusi haku -nappi tyhjentää aiemman reitin ja päästät valitsemaan uudet alku-ja määränpääheksat. 

Haetut reitit eivät vastaa liikkumista itse pelissä, vaan ohjelma käyttää omia maastojen liikkumispistekustannuksia.

## Ohjelman käyttämät syötteet

Ohjelman syötteeksi käyvöt [Battle for Wesnoth -pelin](https://www.wesnoth.org/) karttamääritysten mukaiset karttatiedostot. Ohjelma tukee vain uusimman määrittelyn mukaisia karttoja, joissa on pelkät maastokoodit. Vanhantyyppiset kartat, joissa oli ennen maastokoodeja yleistietoja kartasta eivät käy.

Hakemistossa heksareitinhaku/src/test/resources on testikartta, jolla ohjelmaa voi kokeilla.
Lsiää karttoja löytyy [Battle for Wesnoth -pelin github-repositoriosta](https://github.com/wesnoth/wesnoth). Karttoja voi myös tehdä peliin kuuluvalla karttaeditorilla

