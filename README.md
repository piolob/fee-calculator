# fee-calculator
Aplikacja sluzy do obliczania oplat za transakcje danego klienta. W glownej mierze opiera sie na implementacji interfejsu `FeeCalculator`.

# Technologie:
* Spring Boot 
* Spring Shell - pozwala stworzyc aplikacje konsolowa
* univocity - najszybszy parser CSV
* Maven
* Mockito
* JUnit

# Sposob dzialania
Przed rozpoczeciem korzystania z aplikacji trzeba ja skonfigurowac poprzez edycje pliku `application.properties` znajdujacego sie  w katalogu `'/src/main/resources/'`. Poza polozeniem katalogu oraz nazw plikow powinno sie takze okreslic tryb pracy aplikacji (`STREAM_MODE`/`INMEMORY_MODE`).
W zależności od wybranego trybu do obliczania oplaty za transakcje bedzie wykorzystwana inna implementacja interfejsu `FeeCalculator`:
* `StreamFeeCalculator` - strumieniowo przetwarza dane zczytywane z pliku. 
* `InmemoryFeeCalculator` - docelowo przeznaczona do obslugi mniejszych plikow, zeby umozliwic szybszy dostep do elementow.

Dane z pliku `fees_discounts.csv` zczytywane sa do pamieci, poniewaz jest ich niewiele. W trakcie uruchamiania aplikacji wlaczany jest serwis `FileWatcherService` nasluchujacy zmiany w plikach. W przypadku gdy to nastapi resetowany jest Cache (standardowa Springowa implementacja), ktory wykorzysywany jest do przetrzymywania wynikow obliczen. Z cikawszych rzeczy wykorzystalem Spring Shella, ktory umozliwia wstepna walidacje danych wejsciowych oraz prace na konsoli.

# Uzycie
1. Konfiguracja application.properties oraz application-test.properties (ta wersja propertiesow jest przeznaczona do testow integracyjnych)
1. W konsoli uzycie metody `calculatefee (String customerId, String currency, BigDecimal amount)` na zasadzie: `calculatefee 123321123 PLN 150`
