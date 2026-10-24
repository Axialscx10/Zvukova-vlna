# SoundWave – napojenie na Navidrome

Táto verzia je tvoj pôvodný dizajn z AI Studia + reálna logika.

## Čo je reálne
- Prihlásenie na tvoj server (Subsonic API, token = md5(heslo + soľ)); heslo sa neukladá, len token
- Home (náhodné skladby, naposledy/nedávno pridané albumy, playlisty, žánre ako filtre)
- Explore (vyhľadávanie na serveri, albumy, interpreti, žánre, playlisty, filtre FLAC / 24-bit / rok / bitrate)
- Knižnica (playlisty, obľúbené, albumy, interpreti, vytvorenie playlistu)
- Detail albumu / playlistu / interpreta / žánru / obľúbených
- Prehrávanie na pozadí (Media3 ExoPlayer + MediaSession, notifikácia, ovládanie zo zamknutej obrazovky)
- Obľúbené (srdiečko -> star/unstar), scrobble, texty piesní (synchronizované, ak ich server má), podobné skladby
- Kvalita streamu podľa siete (Wi‑Fi: pôvodná / MP3 320 / Opus 192 / Opus 128; mobilné dáta: kodek + bitrate z nastavení)
- Skenovanie knižnice na serveri, test latencie a streamu, vymazanie cache obalov

## Čo je zatiaľ len vzhľad (nič nerobí so zvukom)
- 10-pásmový ekvalizér, AutoEQ profily, crossfeed, sub-bass
- Bit-perfect / exclusive mód, ReplayGain, gapless, crossfade, max. vzorkovanie
- Prepínače VAAPI/QSV (nastavujú sa na serveri)
- Sťahovanie na offline počúvanie

## Build
1. Otvor priečinok v Android Studiu a nechaj prebehnúť Gradle sync (treba internet).
2. Run na telefóne (min. Android 7.0 / API 24).
3. Pri prvom spustení zadaj adresu servera (napr. `http://192.168.1.10:4533`), meno a heslo.

`debug.keystore` v koreni je len ladiaci kľúč, aby sa dal zostaviť debug build lokálne.
Povolené je aj `http://` (v manifeste `usesCleartextTraffic`), lebo domáce servery často nemajú HTTPS.
Ak používaš výhradne HTTPS, môžeš to v `AndroidManifest.xml` nastaviť na `false`.
