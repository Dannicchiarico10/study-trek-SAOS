# Study Trek

<p align="left">
  <img src="frontend/src/assets/logo-studytrek.png" alt="Study Trek Logo" width="50" style="vertical-align:middle;">
</p>

Study Trek is an educational platform designed to help users navigate their learning journey efficiently. Built with Angular for the frontend and Spring Boot for the backend, we offer a robust solution for managing courses across various education platforms and study materials. We foster a community-centric approach, allowing users to add friends and engage in discussions via a forum, promoting collaborative learning.

## Table of Contents
- [Main Features](#main-features)
- [Premium Membership](#premium-membership)
- [Security Features](#security-features)
- [Usage](#usage)
- [Technologies](#technologies)
- [Image Credits](#image-credits)

## Main Features
- **🔎 Course Search**: Gain access to 50,000+ courses from Udemy and Coursera in tech, design, and business, all in one place. 
- **💻 Study Hub Forum**: Engage in discussions with a community of learners.
- **👭 Find Your Friends**: If you want to go far, go together; add friends to see what they're learning.
- **💯 Progress Tracker**: Check off lectures to track course progress.
- **📝 Note Taker**: Add personalised notes for all courses taken.
- **🗓️ Interactive Calendar**: Plan and schedule study sessions and course deadlines efficiently.
- **🌚 Dark Mode Toggle**: Alleviating eye strain 👀. Also, it just looks better.
- **💳 Stripe Integration**: Make payments safely and securely using Stripe API. 

## Premium Features
- **🤖 AI Course Navigator**: Get personalised course recommendations powered by OpenAI.
- **💬 Study Buddy Telegram Bot**: A bot to send reminders and for accessing Course Navigator at your fingertips.

## Security and Performance Features
- **Authentication and Authorization**: Utilizes JWT for secure authentication and authorization to protect user sessions and sensitive data.
- **Data Encryption**: Sensitive user data is encrypted using hashing to ensure privacy and security.
- **Caffine Cache**: High-performance caching library to reduce calls to external APIs, improving loading times and app performance.


## Screenshots
<p align="left">
  <img src="frontend/src/assets/screenshot-home.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-home2.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-1.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-2.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-3.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-4.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-5.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-6.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>
<p align="left">
  <img src="frontend/src/assets/screenshot-7.png" alt="Study Trek Logo" width="700" style="vertical-align:middle;">
</p>

## Usage 

### Prerequisites
- Node.js
- Angular CLI
- Java JDK 11+
- Maven

### Backend Setup
```bash
cd backend
mvn spring-boot:run
```

### Technologies
- **Tailwind CSS**: [Visit Tailwind CSS](https://tailwindcss.com/)
- **Angular Material**: [Visit Angular Material](https://material.angular.io/)
- **DaisyUI**: [Visit DaisyUI](https://daisyui.com/)

### Image Credits
- Illustrations by Camilo Huinca: [Profile](https://agentpekka.com/artist/camilo-huinca/)

---
# Modifiche effettuate da Dannicchiarico10

## Prerequisiti aggiuntivi
- Mongo.db
- MySQL
- Docker
### Frontend Setup
Avvio del Frontend (Angular)Questa applicazione frontend è stata generata con Angular CLI e si connette al server backend (Spring Boot) in esecuzione sulla porta 8080.

### Prerequisiti
Assicurati che i seguenti strumenti siano installati sul tuo sistema:
- Node.js e NPM: La versione LTS (Long-Term Support) è consigliata.
- Angular CLI: Lo strumento a riga di comando di Angular.
- Backend: Il servizio Docker (MySQL/MongoDB) e il server Spring Boot devono essere già in esecuzione.

### Installazione delle Dipendenze:

Installa Angular CLI a livello globale (solo la prima volta):
```bash
npm install -g @angular/cli
```
Naviga nella directory del frontend:
```Bash
cd C:\Users\User\Desktop\study-trek\frontend
```
Installa le dipendenze del progetto:
```Bash
npm install
```
3. Avvio del Server di Sviluppo. Esegui il comando ng serve per avviare il server di sviluppo Angular.
   Bash
   ng serve

#### Accesso: 
Una volta che la compilazione è completata, apri il tuo browser e naviga su:
```Bash
http://localhost:4200/
```
## Modifiche
### In UserRepository:
Convertito l'import dei DB da TimeStamp a DateTime

### In Application.properties:

Sono state rimosse le chiavi placeholder in chiaro e inserite all'interno di un file
```Bash
.env
```
Aggiunte le configurazione del DB MySQL in locale:
```Bash
# --- MySQL ---
spring.datasource.url=jdbc:mysql://localhost:3306/study_trek?useSSL=false&allowPublicKeyRetrieval=true&useJDBCCompliantTimezoneShift=true
spring.datasource.username=newuser
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
Fatto in modo che si accettino richieste solo dall'origine del frontend:
```Bash
app.cors.allowed-origin=http://localhost:4200
```
A livello di Endpoint ora 
### In SecurityConfig.java
È stato rimosso:
```Bash
/api/courses/**
``` 
Ora sono accessibili a tutti solo gli Endpoint delle Api:
- Auth per login e registrazione
- telegram per l'integrazione con il bot Telegram

Tutti gli altri endpoint richiedono un utente autenticato e un token valido.

Il controllo ora avviene nel Service Layer (Directory Services) prima di eseguire una qualsiasi operazione di scrittura o modifica nel DB. In ordine:
1. Il Service recupera l'ID dell'utente autenticato (il Principal) dal SecurityContextHolder.
2. Il Service recupera la risorsa (es. Nota, Evento) dal database.
3. Viene eseguito un confronto tra il userId della risorsa e l'ID dell'utente autenticato.

### Livello DB per MySQL
È stato creato il file "schema.sql" per avere in modo automatizzato la creazione delle tabelle all'interno del DB MySQL chiamato "study_trek" in localhost.
Basterà eseguire il file sul DB per create tutte le tabelle necessarie.