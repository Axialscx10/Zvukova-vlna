# Pravidlá ochrany súkromia / Privacy Policy – SoundWave (Zvuková vlna)

**Dátum poslednej aktualizácie / Last updated:** 26. október 2026

---

## 🇸🇰 Slovenská verzia (Slovak)

### 1. Úvod
Aplikácia **SoundWave (Zvuková vlna)** (balíček `com.aistudio.soundwave.nctrn`) je nezávislý streamovací hudobný prehrávač určený na pripojenie k vlastnému serveru používateľa kompatibilnému s protokolom **Navidrome / Subsonic**. Rešpektujeme vaše súkromie a zaväzujeme sa ho chrániť.

### 2. Zhromažďovanie a používanie údajov
Aplikácia **SoundWave nezhromažďuje, neukladá na serveroch vývojára, nepredáva ani nezdieľa žiadne osobné údaje používateľov** s tretími stranami.

Všetky údaje zadané v aplikácii sú spracovávané **výhradne lokálne vo vašom zariadení**:
- **Prihlasovacie údaje k serveru (URL adresa servera, používateľské meno a autentifikačný token MD5 + salt):** Ukladajú sa výlučne do lokálneho úložiska zariadenia (`SharedPreferences`), aby ste sa nemuseli pri každom spustení aplikácie znova prihlasovať. Samotné heslo sa v zariadení neukladá. Tieto údaje sú odosielané výhradne priamo na server, ktorý ste si sami nastavili.
- **História prehrávania a nastavenia aplikácie:** Ukladajú sa iba lokálne vo vašom zariadení a sú vylúčené z automatického cloudového zálohovania (`allowBackup` / `backup_rules.xml`).

### 3. Povolenia aplikácie
Aplikácia vyžaduje iba technicky nevyhnutné povolenia pre prehrávanie hudby:
- `INTERNET` a `ACCESS_NETWORK_STATE` – na pripojenie k vášmu hudobnému serveru Navidrome/Subsonic.
- `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_MEDIA_PLAYBACK` a `WAKE_LOCK` – na plynulé prehrávanie hudby na pozadí a pri vypnutej obrazovke.
- `POST_NOTIFICATIONS` – na zobrazenie ovládacieho panela prehrávača v oblasti upozornení a na zamknutej obrazovke.

### 4. Služby tretích strán a reklamy
- Aplikácia **neobsahuje žiadne reklamy**.
- Aplikácia **nepoužíva žiadne analytické ani sledovacie nástroje tretích strán**.

### 5. Uchovávanie a vymazanie údajov
Keďže všetky nastavenia a prihlasovacie tokeny sú uložené výlučne vo vašom zariadení, môžete ich kedykoľvek vymazať odhlásením sa v nastaveniach aplikácie, vymazaním údajov aplikácie v nastaveniach systému Android alebo odinštalovaním aplikácie.

### 6. Ochrana súkromia detí
Aplikácia nie je cielene zameraná na deti mladšie ako 13 rokov a vedome od nich nezhromažďuje žiadne osobné údaje.

### 7. Kontakt
Ak máte akékoľvek otázky týkajúce sa týchto pravidiel ochrany súkromia, môžete nás kontaktovať na e-mailovej adrese:
- **E-mail:** hodul86@gmail.com
- **GitHub:** [https://github.com/Axialscx10/Zvukova-vlna](https://github.com/Axialscx10/Zvukova-vlna)

---

## 🇬🇧 English Version

### 1. Introduction
**SoundWave (Zvuková vlna)** (package ID `com.aistudio.soundwave.nctrn`) is a self-hosted music streaming client designed to connect to a user-specified **Navidrome / Subsonic** compatible server. We respect your privacy and are committed to protecting it.

### 2. Data Collection and Use
**SoundWave does not collect, store on developer servers, sell, or share any personal user data** with third parties.

All data entered into the application is processed and stored **strictly locally on your device**:
- **Server Credentials (Server URL, username, and Subsonic MD5 authentication token + salt):** Stored solely in your device's local private storage (`SharedPreferences`) to keep you signed in across app restarts. Your plaintext password is never stored. Credentials are transmitted exclusively to the server URL you explicitly configure.
- **Playback State and App Preferences:** Stored locally on your device and excluded from cloud backups.

### 3. Permissions Used
The app requests only permissions strictly required for audio streaming and playback:
- `INTERNET` and `ACCESS_NETWORK_STATE` – to stream audio and fetch library metadata from your configured Navidrome/Subsonic server.
- `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_MEDIA_PLAYBACK`, and `WAKE_LOCK` – to support continuous background audio playback.
- `POST_NOTIFICATIONS` – to display media playback controls in the notification shade and on the lock screen.

### 4. Third-Party Services and Advertising
- The app **contains no advertisements**.
- The app **does not use any third-party analytics or tracking SDKs**.

### 5. Data Retention and Deletion
Because all data is stored locally on your device, you can delete it at any time by logging out within the app, clearing the app's storage in Android system settings, or uninstalling the application.

### 6. Children's Privacy
This application does not knowingly collect any personal information from children under the age of 13.

### 7. Contact Us
If you have any questions about this Privacy Policy, please contact the developer at:
- **Email:** hodul86@gmail.com
- **GitHub:** [https://github.com/Axialscx10/Zvukova-vlna](https://github.com/Axialscx10/Zvukova-vlna)
