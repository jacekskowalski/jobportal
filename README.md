# jobportal
Jobportal to portal z ogłoszeniami pracy. Występują 3 typy użytkowników: kandydaci,
firmy wystawiające ogłoszenia oraz Administrator. Zarówno kandydaci jak i firmy mają
swoje identyfikatory, które mają zasięg sesji. Pozostałe dane przechowywane są w
zasięgu widoku.
Kandydat może korzystać z funkcji:
-aktualizacja danych osobowych
-przeglądanie wszystkich rozmów dotyczących tych ogłoszeń ,do których zadał pytanie
-zadawanie pytań do konkretnych firm i ogłoszeń
-przeglądanie bieżących ofert pracy
-dodanie pliku w formacie pdf
-przeglądanie historii zgłoszeń
Klient może korzystać z funkcji
-aktualizacja danych firmy
-dodawanie ogłoszeń
-przeglądanie listy zgłoszeń
-przeglądanie wszystkich rozmów,w których pytania są skierowane do niego
-odpowiadania na pytania
-pobranie pliku od aplikanta
Język: Java
Framework: JavaServerFaces 2.2
Layout: Primefaces
Technologie inne: Maven 3.5 ,Tomcat 9.0
Baza danych: MySQL ver.5.7.18- port 3306
Logowanie: do walidacji wykorzystano JavaScript i Primefaces
Login do email np. nowak10@gmail.com, softpol@soft.com
hasło: minimum 7 znaków np. p.nowak10, Soft123
Zarówno jedni jak i drudzy logują się z osobnych stronach
Administrator loguje się na stronie main123.xhtml
Rejestracja: do walidacji wykorzystano JavaScript i Primefaces
Tutaj także kandydaci i firmy rejestrują się na osobnych stronach
W katalogu resources znajduje się plik konfiguracyjny z loginem i hasłem dla adminaadmin.
properties. Ponadto należy wpisać odpowiednie hasło i login do połączenia z
lokalną bazą mysql na porcie 3306
Projekt zawiera folder DB, należy umieścić w katalogu domowym użytkownika
(„user.home”). Pliki przesłane przez użytkowników i pobierane są właśnie z tego
folderu,który jest poza Tomcatem ,aby trwale można było przechowywać pliki.
Baza danych „jobportal” obejmuje relacje:
Users- dane użytkowników -kandydatów
Client- dane firm
Offers- ogłoszenia pracy
mailDB- tabela nadrzędna dla Messenger
Messenger- wiadomości wysłane przez kandydatów i firmy
Application- zgłoszenia kandydatów na ogłoszenie z nazwą pliku, numerem
referencyjnym
Contact_Data- identyfikator użytkowników, którzy wysłali wiadomość przez formularz
ContactMessages- wiadomości przesłane przez formularz kontaktowy
History- zgłoszenia użytkowników na oferty pracy
Resume- dodane dokumenty(pliki) przez kandydata
