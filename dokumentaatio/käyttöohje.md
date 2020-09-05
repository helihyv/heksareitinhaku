# Käyttöohje – Heksareitinhaku

Ohjelma hakee annatulta  [Battle for Wesnoth -pelin](https://www.wesnoth.org/) kartalta reitin annetusta alkupisteestä annettuun kohteeseen. Ohjelma käyttää kolmea reitinhakualgoritmia: Djikstarn algoritmia, A*-algoritmia ja Fringe Search-algoritmia. Kartat koostuvat kuusikulmioista ja niihin siirtymiseen tarvittavat liikkumispisteet vaihtelevat maastojen mukaan.

## Ohjelman käyttöönotto

Sovellus asennetaan lataamalla [github-releasessa](https://github.com/helihyv/heksareitinhaku/releases/tag/loppupalautus) oleva zip-paketti ja purkamalla se haluttuun paikkaan. Sovellus käynnistetään ajamalla puretun paketin kansiosta heksareitinhaku/bin/ tiedosto heksareitinhaku . (Sovelluksen oman koodin sisältävä jar-tiedosto ja tarvittavat JavaFX-jar-tiedostot ovat hakemistossa libs. Tämä on gradlen ohjeiden suositteleman application-pluginin tapa java-sovelluksen julkaisuun.)

Vaihtehtoisesti ohjelman voi käynnistää lataamalla tai kloonaamalla projektin ja antamalla komennon ./gradlew run alihakemistossa heksareitinhaku . (Tai netbeansista jos gradle-tuki on käytössä ja ḱonfiguroitu käyttämään gradle wrapperia.)   

## Ohjelman käyttämät syötteet

Ohjelman syötteeksi käyvät [Battle for Wesnoth -pelin](https://www.wesnoth.org/) karttamääritysten mukaiset karttatiedostot. Ohjelma tukee vain uusimman määrittelyn mukaisia karttoja, joissa on pelkät maastokoodit. Vanhantyyppiset kartat, joissa oli ennen maastokoodeja yleistietoja kartasta eivät käy. Sovellus myös olettaa, että maastokoodirivit on erotettu toisistaan rivinvaihdoilla, eikä ylimääräisiä rivinvaihtoja ole.

Sovelluksen käyttöön tarvittavat kartat eivät tule releasen mukana. Niitä on projektin hakemistoissa heksareitinhaku/src/test/resources (pienet testikartat) ja heksareitinhaku/data (suorituskykytesteissä käytetyt suuret ja keskisuuret kartat omissa kansioissaan).

Lsiää karttoja löytyy [Battle for Wesnoth -pelin github-repositoriosta](https://github.com/wesnoth/wesnoth). Karttoja voisi myös tehdä peliin kuuluvalla karttaeditorilla, tosin se tuottaa vanhantyyppisiä karttoja. Niitä käytettessä pitäisi poistaa karttatiedoston alusta yleistiedot.

## Ohjelman toiminta

Ohjelmaa käytettäessä on ensin ladattava kartta tiedostosta (Valitse karttatiedosto -nappi). Tämän jälkeen valitaan hiirellä kartalta kohta, josta haku aloitetaan ja sen jälkeen kohta, johon pyritään. Haku käynnistyy automaattisesti kun määränpää on valittu. 

Kaikkien algoritmien hakemat reitit näytetään kartalla. Haettujen reitten pituudet ja hakuun kulunut aika näytetään kaikista algoritmeista.

Uusi haku -nappi tyhjentää aiemman reitin ja päästää valitsemaan uudet alku-ja määränpääheksat. 

Haetut reitit eivät vastaa liikkumista itse pelissä, vaan ohjelma käyttää omia maastojen liikkumispistekustannuksia.

## Kartan värikoodit

Sovelluksessa esitetään kartan maastotyypit hyvin karkeasti värikoodeina.  Monet liikkumispisteisiin vaikuttamattomat asiat kartalla, kuten kylät ja koristeet, jätetään kokonaan näyttämättä.

### Kulkukelvottomat
* Vesi  (sininen)
* Muut läpitunkemattomat maastot (musta)

### 5 liikkumispistettä
* Vuoret (tummanharmaa)

### 4 liikkumispistettä

* Suot (vaaleansininen)

### 3 liikkumispistettä

* Mäet (ruskea)
* Metsäiset maastot (tummanvihreän sävyt, kellanvihreä)
(Kaikissa maastoissa kartan visualisointi ei tarkista onko metsää. Linnamaastoihin tai maan alle sijoitetun metsän liikkumispistevaatimus olisi kolme, vaikkei metsää näytetäkään.)
* Arktinen (valkoinen)

### 2 liikkumispistettä
* aavikko (keltainen)
* maanalaiset tilat (suklaanruskea)

### 1 liikkumispiste
* ruohikot (vaaleanvihreä)
* tiet ja sillat (vaalea beige)
Sillat esitetään kartalla sillan suunnan osoittavina suorakulmioina. Sillalle pääsee vain niistä kuusikulmioista, joihin sen päät osoittavat.
* linnat, linnoituksen päätornit ja "muut maastot" (vaaleanharmaan sävyt)
(Muita maastoja ovat sisätilat, skenaariokohtaiset maastot, järjestelmän erikoismaastot ja tunnistamattomat maastot.)



