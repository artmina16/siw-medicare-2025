✅ UC1 - Autenticazione
Attore: Paziente / Dottore / Admin
Descrizione: L’utente accede al sistema con credenziali, tramite form o OAuth2.
Estensioni:

UC1.1: Registrazione Paziente

UC1.2: Login con Google (OAuth2)

📑 UC2 - Visualizzazione Specializzazioni
Attore: Paziente
Descrizione: Il paziente accede alla lista delle specializzazioni disponibili.

📑 UC3 - Visualizzazione Tipi di Visita per Specializzazione
Attore: Paziente
Descrizione: Il paziente seleziona una specializzazione e visualizza i tipi di visita associati.

🗓 UC4 - Prenotazione Visita
Attore: Paziente
Descrizione: Il paziente seleziona un tipo di visita, sceglie un dottore tra quelli disponibili, vede le sue disponibilità e prenota.
Inclusioni:

UC5: Visualizzazione Disponibilità del Dottore

UC6: Selezione Dottore e Disponibilità

UC7: Conferma Prenotazione

📋 UC5 - Gestione Disponibilità
Attore: Dottore
Descrizione: Il dottore inserisce/modifica/rimuove le proprie disponibilità da un calendario interattivo nella dashboard.

📋 UC6 - Visualizzazione Appuntamenti
Attore: Dottore / Paziente
Descrizione:

Il dottore visualizza tutti gli appuntamenti prenotati.

Il paziente visualizza tutti gli appuntamenti effettuati e prenotati.

🧑‍⚕️ UC7 - Gestione Profilo Dottore
Attore: Dottore
Descrizione: Il dottore può modificare il numero di telefono e visualizzare dati del proprio profilo.

🧑‍⚕️ UC8 - Gestione Dottori
Attore: Admin
Descrizione: L’admin può creare, modificare, associare strutture e specializzazioni ai dottori.

🏥 UC9 - Gestione Strutture Sanitarie
Attore: Admin
Descrizione: L’admin crea/aggiorna strutture sanitarie e le associa ai dottori.

📚 UC10 - Visualizzazione Dashboard Personalizzata
Attore: Paziente / Dottore / Admin
Descrizione: Ogni utente vede una dashboard differente con le sue funzionalità disponibili.
