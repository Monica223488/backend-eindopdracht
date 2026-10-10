# Installatiehandleiding

## Benodigdheden

Om de applicatie lokaal te kunnen draaien heb je het volgende nodig:

* Java JDK 21
* Een IDE of code-editor
* PostgreSQL
* Postman

## Database instellen

Maak in PostgreSQL een nieuwe database aan met de naam:

`eindopdrachtBackend`

Controleer vervolgens of de database-instellingen in `application.properties` overeenkomen met je lokale PostgreSQL-configuratie.

## Applicatie starten

Open het project in een IDE of code-editor en start `EindopdrachtBackendApplication`.

De applicatie draait standaard op:

`http://localhost:8080`

Wanneer lokaal een andere poort is ingesteld, moet deze ook worden aangepast in de Postman-requests.

## Testgebruikers

Voor het testen van de verschillende rollen zijn de volgende gebruikers beschikbaar:

| Naam          | E-mailadres                                 | Wachtwoord | Rol        |
| ------------- | ------------------------------------------- | ---------- | ---------- |
| Test Employee | [employee@test.nl](mailto:employee@test.nl) | welkom123  | `EMPLOYEE` |
| Test Customer | [customer@test.nl](mailto:customer@test.nl) | welkom123  | `CUSTOMER` |
| Test Designer | [designer@test.nl](mailto:designer@test.nl) | welkom123  | `DESIGNER` |

Deze gebruikers zijn bij het opstarten van de applicatie al beschikbaar in de database. Daarnaast kunnen via het registratie-endpoint nieuwe gebruikers worden aangemaakt.

## Postman

Bij het project is de Postman-collectie `Backend-eindopdracht.postman_collection.json` toegevoegd. Deze bevat de requests waarmee de verschillende endpoints getest kunnen worden.

Importeer de collectie in Postman om de requests te gebruiken.

### Authenticatie

Een nieuwe gebruiker kan worden geregistreerd via:

`POST /auth/register`

Bijvoorbeeld:

```json
{
  "name": "Anna Test",
  "email": "annadesigner@test.nl",
  "password": "test1234",
  "role": "DESIGNER"
}
```

Naast `DESIGNER` kan een gebruiker de rol `CUSTOMER` of `EMPLOYEE` krijgen.

Inloggen kan via:

`POST /auth/login`

Bijvoorbeeld:

```json
{
  "email": "annadesigner@test.nl",
  "password": "test1234"
}
```

Na succesvol inloggen wordt een JWT-token ontvangen. Dit token moet als Bearer Token worden meegestuurd bij beveiligde endpoints.

De meegeleverde Postman-collectie bevat hiervoor de variabelen:

* `customerToken`
* `designerToken`
* `employeeToken`

Hierdoor kunnen de endpoints eenvoudig met de verschillende gebruikersrollen worden getest. Welke rol toegang heeft tot welk endpoint staat beschreven in het overzicht van de REST-endpoints.


### Customer
#### Nieuwe klant aanmaken
|soort request | url | Authorization                         |
|--------------|-----|---------------------------------------|
|POST | /customer | Auth type: Bearer token <br> Vul in bij Token: {{employeeToken}} <br> Of <br> {{designerToken}} |

## REST-endpoints

De applicatie maakt gebruik van REST-endpoints voor het beheren van klanten, bestellingen, foto's, fotoboeken, afspraken en bonnen. De meeste endpoints zijn beveiligd met JWT-authenticatie en zijn alleen toegankelijk voor gebruikers met de juiste rol.

Binnen de applicatie worden drie rollen gebruikt:

* `CUSTOMER` – klant
* `DESIGNER` – ontwerper
* `EMPLOYEE` – medewerker

Voor beveiligde endpoints moet een geldig JWT-token als Bearer Token worden meegestuurd.

### Authenticatie

| Methode | Endpoint         | Omschrijving              | Autorisatie |
| ------- | ---------------- | ------------------------- | ----------- |
| POST    | `/auth/register` | Nieuw account registreren | Openbaar    |
| POST    | `/auth/login`    | Inloggen                  | Openbaar    |

### Klanten

| Methode | Endpoint          | Omschrijving             | Autorisatie            |
| ------- | ----------------- | ------------------------ | ---------------------- |
| POST    | `/customers`      | Nieuwe klant aanmaken    | `EMPLOYEE`, `DESIGNER` |
| GET     | `/customers`      | Alle klanten ophalen     | `EMPLOYEE`, `DESIGNER` |
| GET     | `/customers/{id}` | Specifieke klant ophalen | `EMPLOYEE`, `DESIGNER` |

### Bestellingen

| Methode | Endpoint       | Omschrijving                  | Autorisatie            |
| ------- | -------------- | ----------------------------- | ---------------------- |
| POST    | `/orders`      | Nieuwe bestelling aanmaken    | `EMPLOYEE`, `DESIGNER` |
| GET     | `/orders`      | Alle bestellingen ophalen     | `EMPLOYEE`, `DESIGNER` |
| GET     | `/orders/{id}` | Specifieke bestelling ophalen | `EMPLOYEE`, `DESIGNER` |

### Foto's

| Methode | Endpoint                | Omschrijving     | Autorisatie |
| ------- | ----------------------- | ---------------- | ----------- |
| POST    | `/photos`               | Foto uploaden    | `DESIGNER`  |
| GET     | `/photos/{id}`          | Foto ophalen     | `DESIGNER`  |
| GET     | `/photos/{id}/download` | Foto downloaden  | `DESIGNER`  |
| DELETE  | `/photos/{id}`          | Foto verwijderen | `DESIGNER`  |

Voor het uploaden van een foto wordt `multipart/form-data` gebruikt. Het bestand wordt meegestuurd onder de key `file`.

### Fotoboeken

| Methode | Endpoint                                     | Omschrijving                          | Autorisatie |
| ------- | -------------------------------------------- | ------------------------------------- | ----------- |
| POST    | `/photobooks`                                | Nieuw fotoboek aanmaken               | `DESIGNER`  |
| GET     | `/photobooks`                                | Alle fotoboeken ophalen               | `DESIGNER`  |
| GET     | `/photobooks/{id}`                           | Specifiek fotoboek ophalen            | `DESIGNER`  |
| POST    | `/photobooks/{photobookId}/photos/{photoId}` | Foto aan fotoboek toevoegen           | `DESIGNER`  |
| PATCH   | `/photobooks/{id}/photos/order`              | Volgorde van foto's aanpassen         | `DESIGNER`  |
| PATCH   | `/photobooks/{id}/ready-for-review`          | Fotoboek klaarzetten voor beoordeling | `DESIGNER`  |
| PATCH   | `/photobooks/{id}/approve`                   | Fotoboek goedkeuren                   | `CUSTOMER`  |
| PATCH   | `/photobooks/{id}/reject`                    | Fotoboek afkeuren                     | `CUSTOMER`  |
| PATCH   | `/photobooks/{id}/send-to-printer`           | Fotoboek naar de drukker sturen       | `DESIGNER`  |
| PATCH   | `/photobooks/{id}/ready-for-pickup`          | Fotoboek gereedmelden om af te halen  | `EMPLOYEE`  |

#### Statusverloop van een fotoboek

Een fotoboek doorloopt verschillende statussen. Niet iedere statuswijziging is vanuit iedere status toegestaan.

* **Klaar voor beoordeling:** mogelijk vanuit `DESIGNING` of `UPLOADING`.
* **Goedkeuren:** alleen mogelijk wanneer het fotoboek `READY_FOR_REVIEW` is.
* **Afkeuren:** alleen mogelijk wanneer het fotoboek `READY_FOR_REVIEW` is.
* **Naar de drukker sturen:** alleen mogelijk vanuit `APPROVED`.
* **Klaar om op te halen:** mogelijk vanuit `SENT_TO_PRINTER` of `PRINTED`.

Hierdoor kan een fotoboek alleen volgens de bedoelde workflow worden verwerkt.

### Afspraken

| Methode | Endpoint             | Omschrijving                | Autorisatie |
| ------- | -------------------- | --------------------------- | ----------- |
| POST    | `/appointments`      | Nieuwe afspraak aanmaken    | `EMPLOYEE`  |
| GET     | `/appointments`      | Alle afspraken ophalen      | `EMPLOYEE`  |
| GET     | `/appointments/{id}` | Specifieke afspraak ophalen | `EMPLOYEE`  |

### Bonnen

| Methode | Endpoint         | Omschrijving           | Autorisatie            |
| ------- | ---------------- | ---------------------- | ---------------------- |
| POST    | `/receipts`      | Nieuwe bon aanmaken    | `EMPLOYEE`, `DESIGNER` |
| GET     | `/receipts`      | Alle bonnen ophalen    | `EMPLOYEE`, `DESIGNER` |
| GET     | `/receipts/{id}` | Specifieke bon ophalen | `EMPLOYEE`, `DESIGNER` |

## API testen

De endpoints kunnen bijvoorbeeld met Postman worden getest. Voor beveiligde endpoints moet eerst worden ingelogd via `/auth/login`. Het ontvangen JWT-token wordt vervolgens als Bearer Token meegestuurd bij volgende requests.

Afhankelijk van het endpoint is een gebruiker met de rol `CUSTOMER`, `DESIGNER` of `EMPLOYEE` vereist.

 
