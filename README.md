
#  Zeitplaner REST API

Alle Endpunkte erreichbar unter `/api`.  
Alle Zeitangaben im ISO-Format: `yyyy-MM-dd'T'HH:mm:ss`

---

##  Aufgaben

### Aufgabe anlegen

**POST** `/api/aufgaben`

**Request Body**
```json
{
  "titel": "Präsentation vorbereiten",
  "deadline": "2025-12-03T10:15:30",
  "prioritaet": "MEDIUM",
  "kategorieId": 1
}
````

**Response**

```json
{
  "id": 4,
  "titel": "Präsentation vorbereiten",
  "deadline": "2025-12-03T10:15:30",
  "prioritaet": "MEDIUM",
  "kategorieId": 1
}
```

---

### Alle Aufgaben abrufen

**GET** `/api/aufgaben`

**Response**

```json
[
  {
    "id": 1,
    "titel": "Hausaufgabe",
    "deadline": "2025-11-01T14:00:00",
    "prioritaet": "HIGH",
    "kategorieId": 2
  }
]
```

---

### Einzelne Aufgabe abrufen

**GET** `/api/aufgaben/{id}`

**Response**

```json
{
  "id": 1,
  "titel": "Hausaufgabe",
  "deadline": "2025-11-01T14:00:00",
  "prioritaet": "HIGH",
  "kategorieId": 2
}
```

---

### Aufgabe aktualisieren

**PUT** `/api/aufgaben/{id}`

**Request Body**

```json
{
  "id": 1,
  "titel": "Hausaufgabe überarbeiten",
  "deadline": "2025-11-05T15:00:00",
  "prioritaet": "MEDIUM",
  "kategorieId": 2
}
```

**Response**

```json
{
  "id": 1,
  "titel": "Hausaufgabe überarbeiten",
  "deadline": "2025-11-05T15:00:00",
  "prioritaet": "MEDIUM",
  "kategorieId": 2
}
```

---

### Aufgabe löschen

**DELETE** `/api/aufgaben/{id}`

**Response**

```
HTTP 204 No Content
```

---

### Aufgaben nach Deadline suchen

**GET** `/api/aufgaben/suche?von=2025-12-02T10:15:30&bis=2025-12-08T10:15:30`

**Response**

```json
[
  {
    "id": 3,
    "titel": "Projekt abgeben",
    "deadline": "2025-12-05T12:00:00",
    "prioritaet": "HIGH",
    "kategorieId": 1
  }
]
```

---

### Aufgaben nach Kategorie-Name suchen

**GET** `/api/aufgaben/suche-kategorie?name=Arbeit`

**Response**

```json
[
  {
    "id": 7,
    "titel": "Bericht schreiben",
    "deadline": "2025-12-09T17:00:00",
    "prioritaet": "MEDIUM",
    "kategorieId": 2
  }
]
```

---

##  Kategorien

### Kategorie anlegen

**POST** `/api/kategorien`

**Request Body**

```json
{
  "name": "Uni"
}
```

**Response**

```json
{
  "id": 2,
  "name": "Uni"
}
```

---

### Alle Kategorien abrufen

**GET** `/api/kategorien`

**Response**

```json
[
  {
    "id": 1,
    "name": "Arbeit"
  },
  {
    "id": 2,
    "name": "Uni"
  }
]
```

---

### Einzelne Kategorie abrufen

**GET** `/api/kategorien/{id}`

**Response**

```json
{
  "id": 2,
  "name": "Uni"
}
```

---

### Kategorie aktualisieren

**PUT** `/api/kategorien/{id}`

**Request Body**

```json
{
  "id": 2,
  "name": "Uni - Überarbeitet"
}
```

**Response**

```json
{
  "id": 2,
  "name": "Uni - Überarbeitet"
}
```

---

### Kategorie löschen

**DELETE** `/api/kategorien/{id}`

**Response**

```
HTTP 204 No Content
```

---

### Kategorie nach Name suchen

**GET** `/api/kategorien/suche?name=Arbeit`

**Response**

```json
{
  "id": 1,
  "name": "Arbeit"
}
```

---

##  Termine

### Termin anlegen

**POST** `/api/termine`

**Request Body**

```json
{
  "start": "2025-12-02T10:15:30",
  "ende": "2025-12-02T11:15:30",
  "titel": "Teammeeting",
  "kategorieId": 1,
  "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
}
```

**Response**

```json
[
  {
    "id": 1,
    "start": "2025-12-02T10:15:30",
    "ende": "2025-12-02T11:15:30",
    "titel": "Teammeeting",
    "kategorieId": 1,
    "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
  }
]
```

---

### Alle Termine abrufen

**GET** `/api/termine`

**Response**

```json
[
  {
    "id": 1,
    "start": "2025-12-02T10:15:30",
    "ende": "2025-12-02T11:15:30",
    "titel": "Teammeeting",
    "kategorieId": 1,
    "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
  }
]
```

---

### Einzelnen Termin abrufen

**GET** `/api/termine/{id}`

**Response**

```json
{
  "id": 1,
  "start": "2025-12-02T10:15:30",
  "ende": "2025-12-02T11:15:30",
  "titel": "Teammeeting",
  "kategorieId": 1,
  "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
}
```

---

### Termin aktualisieren

**PUT** `/api/termine/{id}`

**Request Body**

```json
{
  "id": 1,
  "start": "2025-12-03T10:00:00",
  "ende": "2025-12-03T11:00:00",
  "titel": "Teammeeting Update",
  "kategorieId": 1,
  "wiederholungsRegel": "FREQ=MONTHLY;INTERVAL=1;COUNT=2"
}
```

**Response**

```json
{
  "id": 1,
  "start": "2025-12-03T10:00:00",
  "ende": "2025-12-03T11:00:00",
  "titel": "Teammeeting Update",
  "kategorieId": 1,
  "wiederholungsRegel": "FREQ=MONTHLY;INTERVAL=1;COUNT=2"
}
```

---

### Termin löschen

**DELETE** `/api/termine/{id}`

**Response**

```
HTTP 204 No Content
```

---

### Termine nach KategorieId suchen

**GET** `/api/termine/kategorie/{kategorieId}`

**Response**

```json
[
  {
    "id": 1,
    "start": "2025-12-02T10:15:30",
    "ende": "2025-12-02T11:15:30",
    "titel": "Teammeeting",
    "kategorieId": 1,
    "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
  }
]
```

---

### Termine nach Kategorie-Name suchen

**GET** `/api/termine/kategorie-name/{name}`

**Response**

```json
[
  {
    "id": 5,
    "start": "2025-12-05T09:00:00",
    "ende": "2025-12-05T10:00:00",
    "titel": "Besprechung",
    "kategorieId": 3,
    "wiederholungsRegel": null
  }
]
```

---

### Termine nach Zeitraum suchen

**GET** `/api/termine/zeitraum?von=2025-06-01T00:00:00&bis=2025-06-20T23:59:59`

**Response**

```json
[
  {
    "id": 2,
    "start": "2025-06-10T09:00:00",
    "ende": "2025-06-10T10:00:00",
    "titel": "Klausur",
    "kategorieId": 2,
    "wiederholungsRegel": null
  }
]
```

---

##  Reports

### Zeitnutzung nach Zeitraum

**GET** `/api/report/time-usage?von=2025-06-01T00:00:00&bis=2025-06-20T23:59:59`

**Response**

```json
[
  {
    "kategorieId": 1,
    "kategorieName": "Arbeit",
    "minuten": 240
  }
]
```

---

### Zeitnutzung nach Kategorie-Name und Zeitraum

**GET** `/api/report/time-usage-name?name=Arbeit&von=2025-06-01T00:00:00&bis=2025-06-20T23:59:59`

**Response**

```json
[
  {
    "kategorieId": 1,
    "kategorieName": "Arbeit",
    "minuten": 240
  }
]
```

---



