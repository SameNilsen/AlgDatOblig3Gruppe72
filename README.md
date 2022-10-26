# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Morten Nilsen, S371473, s371473@oslomet.no


# Oppgavebeskrivelse

##  Oppgave 1

I oppgave 1 kopierte jeg Programkode 5.2.3 a) fra kompendiet siden det var en fin implementasjon av leggInn metoden. For å få satt forelder pekeren brukte jeg bare konstuktøren til node-klassen slik at når jeg oppretter den nye noden blir forelder satt automatisk. 

##  Oppgave 2

I oppgave 2 skulle jeg lage metoden antall(verdi). Jeg brukte omtrent samme logikk som i inneholder() metoden siden de opererer nesten likt, bare at når den finner verdien så øker den en tellet istedet for å returnere med en gang. Siden duplikater blir sortert likt som større verdier, kan while løkka fortsette letingen der den slapp.

##  Oppgave 3

Her skulle jeg lage selve logikken til postordens traverseringen. Først metoden førstePostOrden som via en while løkke forsøker å gå så mye som mulig til venstre, men hvis det ikke finnes noe venstrebarn går den til høyre. Da vil den til slutt havne på en bladnode som vil være første noden i traverseringen. Deretter lagde jeg metoden nestePostOrden som ikke ble så veldig elegant, men den funker. I prinsipp er det en while løkke som kjører frem til den faller under et visst kriterie slik at man vet at det er neste node. Den sjekker de ulike situasjonene ved bruk av flere if-setninger og bestemmer om neste node den skal sjekke er opp eller til venstre eller til høyre.

##  Oppgave 4

I denne oppgaven skulle man implementere logikken fra oppgave 3) ved bruk av en iterativ metode og en rekursiv metode. I den iterative metoden finner den først første node i postorden før den så bruker nestePostOrden metoden i en while løkke til å traversere hele treet. For hver iterasjon utfører den en oppgave gitt av input argumentet. Den rekursive metoden sjekker først om basistilfellet er oppfyllt, altså om man har traversert hele treet og kommet tilbake til rotnoden og neste node da er null. Så Gjør den oppgaven gitt av input argumentet før den kaller på seg selv igjen med argument nestePostOrden(p) som da er et enklere kall. 

##  Oppgave 5

I denne oppgaven skulle jeg serialisere det binere treet til en arrayliste slik at den kan lagres i en fil. Jeg lagde metoden serialize med inspirasjon fra forelesningvideo der målet var å printe ut hver verdi til konsollen med en iterativ metoden som bruker en kø for å traversere treet i nivå orden. Istedet for å skrive til konsoll lagrer jeg verdien i en ArrayList. Ettersom det er lagret i nivå orden vil rot noden ligge først i lista, så når jeg skulle lage deserialize metoden trengte jeg kun å lage en for-løkke som itererer gjennom lista og legger til hver verdi i et nytt binær tre med leggInn metoden.

##  Oppgave 6

I oppgave 6 skulle jeg først lage metoden fjern(verdi). Jeg brukte Programkode 5.2.8 d) fra kompendiet som håndterer de tre ulike situasjonene for noden som skal fjernes. Jeg la til en linje slik at barnet til noden som skal fjernes får oppdatert sin forelder peker.  Jeg lagde fjernAlle metoden ved å bruke en while løkke som kjører frem til inneholder(verdi) blir false, altså til verdien ikke finnes lengre i treet. For hver iterasjon kaller den på metoden fjern(verdi) og oppdaterer en teller som til slutt blir returnert. Jeg lagde først metoden nullstill() ved å traversere treet ved å bruke postorden logikken fra tidligere, men jeg fant ut at den logikken bruker både foreldrenoder og barnnoder slik at når en node fjernes bryter logikken sammen. Derfor valgte jeg å heller bruke nivå orden traversering slik som i serialize metoden ettersom den kun bruker barnnoder i traverseringen. For hver iterasjon fjernes alle spor av current-noden ved å sette alle attributter til null. Til slutt når hele treet er nullstilt settes antall til null og rot attributten til null.
