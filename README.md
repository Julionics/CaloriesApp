# CaloriesApp

Ky është një aplikacion për menaxhimin e kalorive dhe shpenzimeve ushqimore.
Aplikacioni lejon përdoruesit të shtojnë ushqime, të shohin statistika ditore/javore dhe të menaxhojnë shpenzimet.

## Teknologjitë e përdorura:
- Java 21
- Spring Boot 3.4.1
- MySQL
- Thymeleaf
- JUnit për testim
- Maven për menaxhimin e varësive

Hapat e Ekzekutimit të Projektit
1. Klono Repository-n
git clone <repository-link>
2. Hyr në Direktori
   cd CaloriesApp
3. Konfiguro Bazën e të Dhënave
4. Ndërto Projektin
   mvn clean install
5. Nis Aplikacionin
   mvn spring-boot:run
6. Hyr në Aplikacion
   http://localhost:8080/login
7. Kredencialet për Testim
   Gjatë testimit të aplikacionit, mund të përdorni kredencialet e mëposhtme:

Admin:

Email: admin@example.com

Password: adminpass

User:

Email: user@example.com

Password: userpass
8. Testimi i Projektit
   Ekzekuto testet me komandën:mvn test

## Funksionalitetet Kryesore:
- Regjistrimi dhe autentifikimi i përdoruesve.
- Shtimi dhe menaxhimi i hyrjeve ushqimore.
- Shfaqja e statistikave javore dhe mujore për kaloritë dhe shpenzimet.
- Panel i dedikuar për administratorët për të menaxhuar të dhënat.

## Struktura e Projektit:
- `src/main/java`: Përmban kodin burimor të aplikacionit.
- `src/test/java`: Përmban testet JUnit për aplikacionin.
- `src/main/resources`: Përmban skedarët e konfigurimit dhe skedarët statikë si `application.properties`.

Ne dosjen Image te krijuar brenda projektit , ndodhen screenshots dhe screen recording nga ekzekutimi i programit.
Screen recording mund te hapen duke i bere drag and drop ne desktop.
Ndersa screnshoots duke klikuar do ju hapen brenda intelliJ.
[Screen Recording 2025-01-21 at 23.30.42.mov](images/Screen%20Recording%202025-01-21%20at%2023.30.42.mov)
[Screen Recording 2025-01-21 at 23.38.28.mov](images/Screen%20Recording%202025-01-21%20at%2023.38.28.mov)
[Screen Recording 2025-01-21 at 23.39.17.mov](images/Screen%20Recording%202025-01-21%20at%2023.39.17.mov)
![Screenshot 2025-01-22 at 02.00.02.png](images/Screenshot%202025-01-22%20at%2002.00.02.png)
![Screenshot 2025-01-23 at 05.35.20.png](images/Screenshot%202025-01-23%20at%2005.35.20.png)
![Screenshot 2025-01-23 at 05.35.25.png](images/Screenshot%202025-01-23%20at%2005.35.25.png)
![Screenshot 2025-01-23 at 05.49.27.png](images/Screenshot%202025-01-23%20at%2005.49.27.png)
![Screenshot 2025-01-23 at 05.49.30.png](images/Screenshot%202025-01-23%20at%2005.49.30.png)
![Screenshot 2025-01-23 at 05.57.03.png](images/Screenshot%202025-01-23%20at%2005.57.03.png)
![Screenshot 2025-01-23 at 05.59.29.png](images/Screenshot%202025-01-23%20at%2005.59.29.png)
![Screenshot 2025-01-23 at 05.59.36.png](images/Screenshot%202025-01-23%20at%2005.59.36.png)
![Screenshot 2025-01-23 at 06.02.38.png](images/Screenshot%202025-01-23%20at%2006.02.38.png)


Dokumenti Pdf per Product Backlog dhe Sprint Backlog:
[ProductAndSprintBacklog.pdf](..%2F..%2FDownloads%2FProductAndSprintBacklog.pdf)
Nese doc nuk hapet, mund te gjendet ne projekt te path-i i caktuar si me poshte:
![Screenshot 2025-01-23 132038.png](..%2F..%2FPictures%2FScreenshots%2FScreenshot%202025-01-23%20132038.png)
 
CaloriesApp/src/main/Downloads/
