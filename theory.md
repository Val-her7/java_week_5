# 🌐 Résumé : API, REST, RESTful et Spring Boot

## 🔶 1. C’est quoi une API ?
- Une **API** (Application Programming Interface) permet à des programmes de communiquer entre eux.
- Une **API web** est accessible via internet (souvent avec HTTP).
- Exemple : Une appli météo récupère la température via une API météo.

---

## 🔷 2. C’est quoi REST ?
- **REST** = Representational State Transfer
- C’est un **style d’architecture** pour créer des APIs web.
- REST impose quelques règles :
  - Utiliser les **méthodes HTTP** : GET, POST, PUT, DELETE
  - Utiliser des **URLs claires** représentant des ressources (`/users`, `/books/42`)
  - Utiliser le **format JSON** pour les échanges
  - Être **sans état** : chaque requête est indépendante

---

## 🟢 3. C’est quoi RESTful ?
- Une **API RESTful** est une API qui **respecte les règles REST**.
- Exemple RESTful :
  - `GET /books` → liste des livres
  - `POST /books` → ajouter un livre
  - `GET /books/1` → livre avec l'id 1
  - `PUT /books/1` → modifier
  - `DELETE /books/1` → supprimer
- RESTful = "qui parle bien REST"

---

## 🚀 4. Pourquoi Spring Boot ?
- Spring Boot permet de **créer rapidement une API RESTful** :
  - Annotations simples comme `@RestController`, `@GetMapping`, etc.
  - Serveur web intégré (Tomcat)
  - Conversion automatique Java ↔ JSON (via Jackson)
  - Clients REST intégrés (`RestTemplate`, `WebClient`)
  - Facile à tester avec Postman

---

## ✅ Exemple simple avec Spring Boot
```java
@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public List<Book> getAllBooks() { ... }

    @PostMapping
    public Book createBook(@RequestBody Book book) { ... }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) { ... }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) { ... }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) { ... }
}
```
---

# 🌐 Résumé des Méthodes HTTP en API REST

## 🔹 GET
- **But** : Récupérer une ressource.
- **Exemple** : `GET /users/123`
- **Effet** : Lecture seule, aucune modification.
- **Idempotent** : ✅ Oui

---

## 🔹 POST
- **But** : Créer une nouvelle ressource.
- **Exemple** : `POST /users`
- **Effet** : Ajoute un nouvel élément.
- **Idempotent** : ❌ Non

---

## 🔹 PUT
- **But** : Remplacer entièrement une ressource existante.
- **Exemple** : `PUT /users/123`
- **Effet** : Remplace tous les champs de la ressource.
- **Idempotent** : ✅ Oui

---

## 🔹 PATCH
- **But** : Modifier partiellement une ressource.
- **Exemple** : `PATCH /users/123`
- **Effet** : Met à jour certains champs seulement.
- **Idempotent** : ✅ Oui (en général)

---

## 🔹 DELETE
- **But** : Supprimer une ressource.
- **Exemple** : `DELETE /users/123`
- **Effet** : Enlève l'élément.
- **Idempotent** : ✅ Oui

---

## 🔸 Qu’est-ce que l’idempotence ?

> Une méthode est **idempotente** si l’effet d’un appel reste **le même**, même s’il est exécuté plusieurs fois.

### ✅ Idempotentes :
- GET
- PUT
- PATCH (dans la majorité des cas)
- DELETE

### ❌ Non idempotente :
- POST (chaque appel crée une nouvelle ressource)

---

# 🧱 Structure d'un projet Spring Boot (MVC)

Un projet Spring Boot bien organisé suit généralement l'architecture **MVC** (Model - View - Controller).

## 📁 Arborescence typique
```plaintext
src/
└── main/
    └── java/
        └── com/tonpackage/
            ├── controller/   ← gère les requêtes HTTP
            ├── service/      ← logique métier (business logic)
            ├── model/        ← classes de données (entités, DTO, etc.)
            ├── repository/   ← accès à la base de données
            └── TonApplication.java ← point d'entrée (main)
    └── resources/
        ├── application.properties ← config (port, BDD, etc.)
        └── static/ et templates/  ← si app web avec front (pas pour API REST)
```
## 🧭 Rôles de chaque dossier

### 🎮 `controller/`

Ce sont les **points d’entrée HTTP** de ton API :

- Reçoit les requêtes (`GET`, `POST`, etc.)
- Utilise le service pour traiter la logique
- Retourne la réponse au client (souvent du JSON)

```java
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getBooks() {
        return service.getAllBooks();
    }
}
```
### 🧠 service/

Contient la logique métier, c’est le cerveau 🧠 :

- Valide les données
- Gère les règles métier
- Appelle les repository pour interagir avec la base de données

```java
@Service
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }
}
```
### 📦 model/

Définit les données manipulées : entités, DTOs...

```java
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
}
```
### 🗃️ repository/

Utilisé pour accéder à la base de données :

- Interface qui hérite de `JpaRepository`
- Spring Boot crée le code automatiquement !

```java
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
```
### Résumé visuel
```plaintext
[Client HTTP]
     ↓
@Controller → @Service → @Repository → Base de données
     ↑
   JSON
```
---

## 🔷 1. Qu’est-ce que `ResponseEntity` ?

`ResponseEntity<T>` est une classe de Spring qui te permet de :

- Définir le **corps** (body) de la réponse HTTP (ex. un objet JSON)
- Définir le **statut HTTP** à renvoyer (`200`, `201`, `404`, etc.)
- Ajouter éventuellement des **headers HTTP**

Cela donne un **contrôle total** sur ce que tu renvoies à ton client.

---

## 🔷 2. Structure de base

### Syntaxe complète
```java
return new ResponseEntity<>(body, headers, status);
```

### Syntaxe fluide et lisible
```java
return ResponseEntity.ok(body); // 200 OK avec corps
return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 sans corps
```

### 🔷 3. Principaux codes HTTP utiles dans une API
```plaintext
| Code | Nom                   | Quand l’utiliser                                      |
|------|-----------------------|-----------------------------------------------------|
| 200  | OK                    | Tout s’est bien passé, on renvoie une ressource     |
| 201  | Created               | Une ressource a été créée avec succès                |
| 204  | No Content            | Pas de contenu à renvoyer, mais tout s’est bien passé |
| 400  | Bad Request           | Mauvaise requête du client (ex : données invalides) |
| 401  | Unauthorized          | Authentification requise                             |
| 403  | Forbidden             | Accès refusé même avec authentification              |
| 404  | Not Found             | La ressource demandée n’existe pas                   |
| 409  | Conflict              | Conflit avec l’état actuel (ex : doublon)            |
| 500  | Internal Server Error | Erreur interne du serveur                            |
```
### 🔷 4. Utiliser ResponseEntity pour chaque code

#### ✅ 200 OK (GET réussi)
```java
return ResponseEntity.ok(doctor);
```
#### ✅ 201 Created (POST réussi)
```java
return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
```
#### ✅ 201 Created sans corps
```java
return ResponseEntity.status(HttpStatus.CREATED).build();
```
#### ✅ 204 No Content (DELETE ou PUT sans réponse)
```java
return ResponseEntity.noContent().build();
```
#### ✅ 400 Bad Request (mauvais input)
```java
return ResponseEntity.badRequest().body("Invalid data");
```
#### ✅ 404 Not Found (objet non trouvé)
```java
return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
```
#### ✅ 409 Conflict (doublon ou conflit)
```java
return ResponseEntity.status(HttpStatus.CONFLICT).body("Doctor already exists");
```
#### ✅ 500 Internal Server Error
```java
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
```
### 🔷 5. Exemple complet
```java
@GetMapping("/{id}")
public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
    Doctor doctor = doctorService.getDoctorById(id);
    if (doctor == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(doctor);
}
```
---

## Qu’est-ce que Lombok ?

Lombok est une bibliothèque Java qui **génère automatiquement** du code répétitif comme les getters, setters, constructeurs, `toString()`, `equals()`, etc., grâce à des annotations.

### Annotations courantes

- `@Getter` : génère les getters

- `@Setter` : génère les setters

- `@Data` : getters + setters + toString + equals + hashCode

- `@NoArgsConstructor` : constructeur vide (indispensable pour JPA)

- `@AllArgsConstructor` : constructeur avec tous les champs

- `@Builder` : design pattern builder
  
### Pourquoi utiliser Lombok ?

- Réduit considérablement la quantité de code répétitif.
- Améliore la lisibilité de tes classes.
- Évite les erreurs liées à l’écriture manuelle des getters/setters.
- Permet d’utiliser facilement des design patterns (ex : `@Builder`).

### Remarques

- Lombok génère le code à la compilation, donc le code généré n’est pas visible dans les fichiers sources.
- Certains IDE nécessitent un plugin Lombok pour bien reconnaître les annotations (ex : IntelliJ, Eclipse).
- Pour les entités JPA, il faut toujours un constructeur sans argument (`@NoArgsConstructor`) car JPA en a besoin.

---
## 🧩 Qu’est-ce que JPA ?

**JPA (Java Persistence API)** est une **spécification Java** (pas une bibliothèque) permettant de mapper des objets Java à des **tables de base de données relationnelles**.

Fait partie de **Jakarta EE** (ex Java EE).

➡️ Elle permet de faire du **ORM (Object-Relational Mapping)** : enregistrer, lire, modifier ou supprimer des objets Java **sans écrire de SQL** directement.

---

### 🔧 JPA + Implémentation concrète

JPA est **une interface**. Pour l’utiliser, on a besoin d’une **implémentation**.

💡 La plus utilisée : **Hibernate**.

> Dans 99% des cas (ex : Spring Boot), quand on dit "JPA", on utilise **JPA + Hibernate**.

---

### 📦 Exemple d'entité JPA

```java
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Constructeurs, getters, setters...
}
```
### 🧪 Explication des annotations JPA

| Annotation               | Rôle                                                                 |
|--------------------------|----------------------------------------------------------------------|
| `@Entity`               | Indique que cette classe est une entité JPA (donc une table)         |
| `@Table(name = "users")`| Nom de la table dans la base de données (optionnel si le même nom)   |
| `@Id`                   | Indique la clé primaire                                               |
| `@GeneratedValue(...)`  | Génère automatiquement l’ID (auto-incrémenté)                         |
| `@Column(...)`          | (Optionnelle) Personnalise une colonne : nom, nullabilité, longueur, etc. |
---
