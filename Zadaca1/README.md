---



# Zadaca iz predmeta RMAS2025 - 1A



## Struktura



* Interface osoba: posjeduje 2 metode i kreirana je kao pomoc implementacije ostalih klasa

* Programer (open class): implementira `Osoba`, sadrži `ime`, `prezime`, `titula`, `godineIskustva`, `zemlja`, `jezici`, kao i overridane metode getId, getTitle ali i init koji provjerava unos. Init je odradjen uz pomoc AI alata

* BackendDeveloper / FrontendDeveloper: nasljeđuju `Programer`, dodaju `backendFramework` ili `frontendFramework`, ovisno o vjestinama programera



* Funkcije:



* `brojProgrameraPoJeziku1/2` – broj programera po jeziku, implementirano koristeci odredjene metode navedene u postavci zadace (1) ili manualno (2)

* `prosjecnoIskustvo1/2` – prosječno iskustvo po jeziku, isto implementirano na dva nacina

* `filtrirajFramework` – filtrira po frameworku

* `ispisiProgramere` – ispis informacija



## Pokretanje



1. Ubaciti kod u odabrani softver i pokrenuti kao Kotlin projekt. (Za izradu zadace koristen je kotlin playground)

2. Rezultati se ispisuju na konzoli: lista programera, statistike jezika i filtrirani po frameworku.



## Uporedna analiza



* Sa grupisanjem (`groupingBy`, `groupBy`)\*\*: kraći, čitljiviji kod

* Bez grupisanja (manualno)\*\*: više linija, komplikovanije u smislu prolaza kroz elemente i rada nad podacima ali jednostavnije za pocetnike koji nisu upoznati sa koristenjem metoda ili koji tek pocinju uciti Kotlin

