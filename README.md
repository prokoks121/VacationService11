# VacationService11

https://github.com/prokoks121/VacationService11.git
Branch: Master

Data Import Service
PORT: 8123
Svaki End point ima key autentifikacuju, ono sto je potrebno podesiti za svaki poziv jeste 
 
Unutar hedera proslediti kljuc “api_key” uz vrednost kljuca “123123”

1.Unos zaposlenih
POST: /upload/employee
http://localhost:8123/upload/employee
Potrebno je proslediti csv fajl sa zaposlenima
 
Unutar body-a treba proslediti fajl sa sadrzajem zaposlenih pod kljucem “file”

2.Unos koriscenog odmora
POST: /upload/used-vacation
http://localhost:8123/upload/used-vacation
Isto kao I za Unos zaposlenih samo sto prosledjujemo fajl sa sadrzajem koriscenog odmora

3.Unos broja dana godisnjeg odmora
POST: /upload/vacation
http://localhost:8123/upload/vacation
Isto kao I za Unos zaposlenih samo sto prosledjujemo fajl sa sadrzajem koriscenog odmora


Data Search Service
PORT: 8223
Svaki End point ima basic autentifikacuju, tako da svaki zaposleni koji je unet u bazu podataka preko
dataImportService/upload/employee moze da se prijavi I koristi end point

 

Korisnik moze da vidi samo svoje podatke

1.Prikaz ukupnog broja slobodnih dana, iskoriscenih dana I preostalih slobodnih dana
GET: /api/employee/vacation
http://localhost:8223/api/employee/vacation

{
    "total_used_vacation": 53,
    "total_vacation": 63,
    "total_free_vacation": 10
}

Takodje mozemo da prosledimo paramtar (nije obavezan) “type” sa jednim od sledecih 3 vrednosti
-“total” – prikazuje samo total_vacation
-“totalUsed” – prikazuje samo total_used_vacation
-“totalFree” – prikazuje samo total_free_vacation

2. Prikaz koriscenog odmora
GET: /api/employee/vacation/used
http://localhost:8223/api/employee/vacation/used
http://localhost:8223/api/employee/vacation/used?dateStart=2019-01-01&dateEnd=2019-12-30
Obavezni parametric:
“dateStart” – od kog datuma se vrsi pretraga
“dateEnd” – do kog datuma se vrsi pretraga
Obavezan format za oba parametra je “yyyy-MM-dd” tj 2019-12-30

 [
    {
        "id": 100,
        "employeeEmail": "user1@rbt.rs",
        "vacationStart": "2019-08-30",
        "vacationEnd": "2019-09-11"
    },
    {
        "id": 101,
        "employeeEmail": "user1@rbt.rs",
        "vacationStart": "2019-10-24",
        "vacationEnd": "2019-10-24"
    }
]

3. Dodavanje koriscenog odmora
POST: /api/employee/vacation/used
http://localhost:8223/api/employee/vacation/used
potrebno je unutar body-a proslediti json u format
[
    "2019-01-01",
    "2019-01-02"
]
Oba parametra su String u format “yyyy-MM-dd”
 
Izlaz:
{
    "response": {
        "status": "CREATED",
        "message": "Successfully added uesd vacation"
    },
    "used_vacation": {
        "id": 789,
        "employeeEmail": "user1@rbt.rs",
        "vacationStart": "2019-01-01",
        "vacationEnd": "2019-01-02"
    }
}

DOCKER
https://github.com/prokoks121/VacationService11.git
Branch: Docker
Projekat sa izmenjenim properties fajlovima, dockerfile I docker-compose.yml se nalazi na istom gitu ali pod Docker
Dockerfile za Data Import Service se nalazi unutar svog projekta
Dockerfile za Data Search Service se nalazi unutar sbog projekta
FROM openjdk:8
COPY /target/DataSearchService-0.0.1-SNAPSHOT.jar DataSearchService-0.0.1-SNAPSHOT.jar
EXPOSE 8223
ENTRYPOINT ["java","-jar","/DataSearchService-0.0.1-SNAPSHOT.jar"]

Docker-compose.yml se nalazi unutar projekta Data import Service
version: '3.1'
services:
  data-import-service:
    container_name: data-import
    image: data-import
    build: ./
    ports:
      - "8123:8123"
    depends_on:
      - dbpostgresql
  app:
    container_name: app-springboot-postgresql
    image: app-springboot-postgresql
    build: ../DataSearchService
    ports:
      - "8223:8223"
    depends_on:
      - dbpostgresql
  dbpostgresql:
    image: postgres
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_PASSWORD=password
      - POSTGRES_USER=postgres
      - POSTGRES_DB=vacation


