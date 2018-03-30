# ProgramLeki2
Aplikacja do testowania wybranych funkcjonalności:

•	funkcjonalności ustawiania i zapisywania alarmów
•	funkcjonalności dla wywoływania aktywności przez ustawiony alarm podającej komunikaty dotyczące pobrania  leku, 
rozpoznawania poleceń głosowych i ich obsługi

Tak jak to wspólnie testowaliśmy te funkcjonalności działają, ale napotkałem pewien problem, polegający na tym że na 
emulatorze jest wszystko ok., ale w praktyce na moim telefonie (LG android w wersji 7.1.1 API 25) jest taki problem że 
gdy telefon jest w uśpieniu to po zadziałaniu alarmu następuję uruchomienie aktywności (Mian7Activity) w większości 
przypadków jest ok., ale zauważyłem że zdarza się też tak, że nie zawsze aktywności – Mian7Activity, daje rade 
przebić się przez blokadę ekranu i klawiatury, bywa że pozostaje ukryta pod blokadą.
