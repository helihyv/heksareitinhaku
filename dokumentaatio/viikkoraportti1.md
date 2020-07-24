# Viikkoraportti 1

## Mitä tehty ja miten edistytty

Tällä viikolla olen seurannut aloitusluennon, tutustunut kurssimateriaaliin, reitinhakualgoritmeihin ja niiden tehokkuuteen vaikuttaviin tekijöihin ja kuusiokulmioista koostuvan kartan käsittelyyn. Olen myös kirjoittanut määrittelydokumenttia.

Repositorio on luotu ja aihe on valittu ja ilmoitettu labtooliin. Määrittelydokumentti on valmis.

Käytin työhön viikon aikana 5 tuntia.

## Mitä opin

Tutustuin uusiin reitinhakualgoritmeihin ja opin myös paljon muuta uutta reitinhakuun liittyvää.

## Epäselvyyksiä, vaikeuksia, kysymyksiä

En ole varma, onko aiheen laajuus/haastavuus riittävä hyvään arvosanaan. Onko Fringe Search sopivan haastava kolmanneksi verrattavaksi reitinhakualgoritmiksi?

Myös se, muokkaanko reitinhaun ottamaan huomioon vuorot ja vuorossa enintään käytettävän liikepistemäärän nopeimman reitin valinnassa on vielä mietinnässä.

Käyttöliittymässä, jossa kartta olisi graafisena näkyvillä ja reitti merkittynä siihen olisi hieno, mutta Battle of Wesnoth-karttoja käytettäessä graafinen kartta voi olla melko työläs toteuttaa. Toisaalta siitä näkisi huomattavasti helpommin, onko haettu reitti järkevä, kuin tekstimuodossa luetelluista reitin koordinaateista. Tällä hetkellä olen kallistumassa sille kannalle, että graafinen kartta kannattaisi tehdä.

## Mitä seuraavaksi

Seuraavaksi luon gradle-projektin, konfiguroin testikattavuusraportoinnin, checkstylen ja javadoc-generoinnin. Seuraavan viikon aikana pyrin toteuttamaan ainakin kartan lukemisen tiedostosta, Djikstran algoritmin Javan valmista proriteettijonoa käyttäen ja sille yksikkötestit sekä ohjelmalle karkean käyttöliittymän. Jos ehdin alan toteuttamaan A\*-algoritmia valmista prioriteettionoa käyttäen ja sen yksikkötestejä. Fringe search - algoritmi yksikkötesteineen, linkitetty lista, itse toteutettu prioriteettjono ja suorituskykytestit jäävät myöhemmille viikoille.
