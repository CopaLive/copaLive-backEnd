# CopaLive Backend - Plateforme de Score en Direct

CopaLive est une application de suivi de scores de football en temps réel. Ce dépôt contient le code source du Backend développé avec Spring Boot, conçu pour fonctionner avec les dernières innovations de l'écosystème Java.

## 🚀 Spécifications Techniques
* **Java :** 25 (OpenJDK 25.0.2)
* **Framework :** Spring Boot 3.4.1
* **Gestionnaire de dépendances :** Maven 4.0.2 (via Wrapper 3.9.12)
* **Base de données :** MySQL 8.x

---

## 🛠 Lancement du projet

### Prérequis
* **JDK 25** installé et configuré (vérifiable via `java --version`).
* **MySQL** installé et une base de données nommée `copalive` créée.
* **IntelliJ IDEA** (recommandé) version 2025.3.2 ou supérieure.


### Installation du projet

```
./mvnw clean install
```

```
./mvnw spring-boot:run
```

Arbolescence du projet

```
src/main/java/fr/ensitech/copalive_backend/
├── config/             # Configuration CORS, Sécurité (JWT) et Beans
├── controller/         # API REST (Matchs, Auth, Favoris, Teams, Notifications)
├── dto/                # Objets de transfert (AuthRequest, GameResponse, FavoriteRequest)
├── entity/             # Modèles JPA (Match, Team, Player, Goal, User, Event...)
├── repository/         # Accès aux données MySQL (Spring Data JPA)
└── service/            # Logique métier (Auth, Favorites, Matchs, Simulation...)
```

Endpoints de l'API

Tous les endpoints sont préfixés par `/api`.

### Matchs & Compétition
| Méthode | Endpoint | Fonctionnalité |
| :--- | :--- | :--- |
| `GET` | `/games` | Liste tous les matchs et leurs scores en temps réel. |
| `GET` | `/game/{id}` | Détails complets, compositions et événements d'un match. |
| `GET` | `/teams` | Liste l'ensemble des équipes de la compétition. |
### Authentification
| Méthode | Endpoint | Fonctionnalité |
| :--- | :--- | :--- |
| `POST` | `/auth/register` | Inscription d'un nouvel utilisateur. |
| `POST` | `/auth/login` | Authentification et génération de token (JWT). |

### Utilisateurs & Favoris
| Méthode | Endpoint | Fonctionnalité |
| :--- | :--- | :--- |
| `POST` | `/favorites` | Ajouter une équipe ou un match aux favoris. |
| `GET` | `/favorites/{userId}` | Récupérer la liste des favoris d'un utilisateur. |
| `DELETE` | `/favorites/{id}` | Supprimer un favori via son ID. |

### Notifications
| Méthode | Endpoint | Fonctionnalité |
| :--- | :--- | :--- |
| `GET` | `/notifications/{userId}` | Liste des alertes et notifications pour un utilisateur. |
