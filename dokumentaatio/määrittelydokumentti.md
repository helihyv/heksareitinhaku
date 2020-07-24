# Heksareitinhaku – määrittelydokumentti

## Ratkaistava ongelma

Ratkaistavana ongelmana on nopeimman reitin löytäminen kahden "ruudun" välillä strategiapeleille tyypillisellä kuusikulmaisista "ruuduista" koostuvalla kartalla. Kartassa on erilaisia maastoja, joiden läpi liikkuminen vaatii eri määrän "liikkumispisteitä". Mahdollisesti reitinhaku tehdään ottamaan huomioon pelivuorot ja yksikön liikkumispisteet pelivuoroa kohti. (Tällöin jos jäljellä olevilla liikkumispisteillä ei pääse seuraavaan ruutuun, ylijääneet liikkumispisteet jäävät käyttämättä ja ruuttun siirrytään seuraavan vuoron liikkumispisteillä. Tällöin reitti voi olla pidempi, kuin jos vuoroja ei huomioitaisi.) Aineistona käytetään Battle of Wesnoth - pelin karttoja. Työssä vertaillaan eri reitinhakualgoritmien tehokkuutta tehtävässä. Ohjelma toteutetaan Javalla.

## Toteutettavat algoritmit ja tietorakenteet

Työssä toteutetaan vertailtavat reitinhakualgorit: Djikstran algoritmi, A*-algoritmi ja "fringe search"-algoritmi. A*-algoritmin tarvitsemana heuristisena arviona jäljellä olevasta kuljettavasta matkasta, jonka tulisi olla algoritmin nopeuden takia mahdollisimman suuri, mutta lyhimmän reitin löytämiseksi ei koskaan lyhimmän reitin käsiteltävänä olevasta sijainnista kohteeseen pituutta suurempi, käytetään kuusikulmiokarttaan sovitettua Manhattan-etäisyyttä. Tämä saadaan laskettua suoraan koordinaateista ja on pienin mahdollinen lyhimmän reitin pituus, kun vaadittavien liikkumispisteiden määrä on jokaisella tiilellä vähintään 1.

Djikstra on valittu, koska se on reitinhaun perusalgoritmeja, johon muita on hyvä verrata ja sitä suositeltiin kurssimateriaalissa. A\* on valittu, koska se on paljon käytetty ja sitä pidetään hyvänä valintana useimpiin reitinhakutilanteisiin ja sitä suositeltiin kurssimateriaalissa. Fringe search on valittu, koska sen on raportoitu olevan tehokas erityisesti peleille tyypillisillä ruudukoilla (grid) ja se lisää työhön myös uuden tietorakenteen.

Toteutettavia tietorakenteita ovat Djikstran algoritmin ja A\*-algoritmin tarvitsema (binäärikekona toteutettava) prioriteettijono ja "fringe search"-algoritmin tarvitsema kaksisuuntaisesti linkitetty lista.

Karttoja käsitellään kaksiulotteisina taulukoina, joissa parilliset sarakkeet tulkitaan "pudotettaviksi puoli riviä alas", jolloin saadaan simuloitua kuusikulmioista koostuva kartta. (Tämä on Battle of Wesnoth-pelin tulkintatapa karttatiedostoilleen.) Tällöin parilllisessa sarakkeessa olevan ruudun (2,2) naapuri koillisessa on samalla rivillä (2,3) ja kaakossa alemmalla rivillä(3,3). Vastaavasti parittomassa sarakkeessa olevan ruudun (2,3) naapuri koillisessa on ylemmällä rivillä oleva ruutu (1,4) ja kaakossa samalla rivillä oleva (2,4). Koska kartta esitetään kaksiulotteisenä taulukkona, algoritmien tarvitsemat aputietorakenteet esim. tiedolle siitä onko tiilessä/ruudussa jo käyty voidaan toteuttaa tehokkaasti käyttämällä kaksiulotteisia taulukoita, joiden indeksit vastaavat kartan indeksejä.

## Syötteet

Ohjelma saa syötteenä karttatiedoston (tiedostonnimi polkuineen), lähtökoordinaatit ja maalikoordinaatit. Mahdollisesti myös liikkumispisteiden määrän vuoroa kohti.

Ohjelma käyttää Battle of Wesnoth- pelin karttatiedostoja. Pelin valmiita karttoja on saatavilla GPL-lisenssillä pelin github-repositoriosta https://github.com/wesnoth/wesnoth ja niitä voi tehdä karttaeditorilla tai (pieniä karttoja testejä varten) käsin. Kartta kuvaa yksittäisen ruudun (heksan) merkkijonona. Ohjelma käyttää tästä merkkijonosta vain liikkumisen kannalta oleellista tietoa, ja tulkinta on ohjelman oma, ei välttämättä pelin toiminnan mukainen. Kunkin maastotyypin vaatima liikkumispistemäärä on aina vähintään 1 ja ne on kovakoodattu ohjelmaan.

Ohjelma tulostaa kullekin reitinhakualgoritmille sen löytämän reitin, reitin pituuden ja hakuun kuluneen ajan.

## tavoitteena olevat aika-ja tilavaativuudet

Djikstran algoritmin osalta tavoitteena oleva aikavaativuus on tirakirjan mukainen Djikstran aikavaativuus O(n+mlog(n), jossa n on solmujen (heksojen) määrä ja m kaarien eli heksojen välisten yhteyksien määrä. Kuusikulmioista koostuvalla kartalla ei voi olla enempää kuin 6n yhteyttä kuusikulmioiden välillä, joten aikavaativuus voidaan kirjata myös muodossa O(n+nlog(n)). Djikstran algoritmi tarvitsee itse kartan (taulukko, keon, ja taulukot etäisyyksille, tiedolle siitä onko solmua jo käsitelty ja tiedolle siitä mistä solmuun tultiin. Taulukoiden koko on n, keossa ei voi olla ainakaan enempäää kuin jokaisen heksan yhteys kaikkiin kuuteen heksaan elin nm = 6n kaarta kerralla. Tilavaativuustavoite on tämän peusteella O(n).

A*-algoritmi on toiminnaltaan hyvin lähellä Djikstran algoritmia, minkä vuoksi sen aikavaatimuustavoite on sama kuin Djikstran algoritmilla, O(n+nlog(n)). A*-algoritmi käyttää samoja tietorakenteta kuin Djikstran algoritmi, joten sen tilavaativuustavoitekin on sama kuin Djikstran algoritmillla eli O(n).

Fringe search -algortmi tutkii osan solmuista useamman kerran, joten se voi pahimmillaan tutkia > n solmua. Toisaalta se ei kuluta aikaa järjestyksen ylläpitoon. Kutakin solmua tutkittaessa saatetaan lisätä ja poistaa linkitetystä listasta (molemmat O(1) ja etsiä linkitetystä listasta O(n). Aikavaativuustavoite on O(n^2 ). Tilaa tarvitaan itse kartalle, käsittelyä samalla tai seuraavalla kierroksella odottavien solmujen listalle, "välimuistille", aputaulukolle, joka kertoo onko solmu listalla ja etäisyys taulukolle. Kaikissa näissä tilantarve on suhteessa heksojen tai niiden välisten yhteyksien määrään (joka on max 6 n), joten tilavaativuustavoite on O(n).

## Lähteet

Björnsson, Yngvi, Enzrnberger, Markus, Holte, Robert C. & Schaeffer, Jonathan 2005: Fringe Search: Beating A\* at Pathfinding on Game Maps
http://www.cs.ualberta.ca/~games/pathfind/publications/cig2005.pdf

Laaksonen, Antti 2019: Tietorakenteet ja algoritmit

Patel, Amit 2020: Implementation Notes http://theory.stanford.edu/~amitp/GameProgramming/ImplementationNotes.html

Patel, Amit 2020: Introduction to A\* https://www.redblobgames.com/pathfinding/a-star/introduction.html

Red Blob Games 2020: Hexagonal Grids https://www.redblobgames.com/grids/hexagons/

Terrain Codes WML 2020: https://wiki.wesnoth.org/TerrainCodesWML
