# 🏦 Backend E-Banking — `ebanking-backend-ibdcc`

Ce projet représente le service backend de la plateforme **E-Banking**, développé avec **Spring Boot**. Il fournit des API REST sécurisées pour la gestion des clients, des comptes bancaires, des opérations et de l’authentification.

---

## 📁 Structure du Projet

```
src/main/java/ma/enset/ebankingbackendibdcc/
├── dtos/             # Objets de transfert de données pour les échanges API
├── entities/         # Entités JPA mappées aux tables de la base de données
├── enums/            # Énumérations métier (ex : StatutCompte, TypeOperation)
├── exceptions/       # Exceptions personnalisées
├── mappers/          # Mappers pour convertir DTO ↔ Entité
├── repositories/     # Repositories Spring Data JPA
├── security/         # Configuration de sécurité basée sur JWT
├── services/         # Couche métier
└── web/              # Contrôleurs REST (points d’accès API)
```

---

## 🚀 Fonctionnalités

- 🔐 **Authentification JWT** avec Spring Security
- 🧾 **Gestion des clients et des comptes** (CRUD)
- 💸 **Opérations bancaires** : crédit, débit, virement
- 📊 **Historique des comptes** avec pagination
- 📁 **API RESTful** documentée avec Swagger/OpenAPI
- 🧠 Architecture basée sur les **DTO** pour une séparation claire des responsabilités

---

## 🧠 Modèle Métier

### 🧬 Entités

- `Customer` : représente un client bancaire
- `BankAccount` : classe abstraite pour les comptes
  - `CurrentAccount`
  - `SavingAccount`
- `AccountOperation` : enregistre les opérations (débit, crédit, virement)

### 📦 DTOs

- `CustomerDTO`, `BankAccountDTO`, `AccountOperationDTO`
- `CreditDTO`, `DebitDTO`, `TransferRequestDTO`
- `AccountHistoryDTO` : historique paginé des opérations

---

## 🔐 Sécurité

- Configuration avec **Spring Security**
- Authentification sans état via **JWT**
- Contrôleur `SecurityController` pour la connexion et la génération de jetons

---

## 📁 Points d’API

Exposés via `BankAccountRestAPI` et `CustomerRestController` :

| Méthode | Endpoint                         | Description                         |
|---------|----------------------------------|-------------------------------------|
| GET     | `/api/customers`                | Liste des clients                   |
| POST    | `/api/accounts/credit`          | Créditer un compte                  |
| POST    | `/api/accounts/debit`           | Débiter un compte                   |
| POST    | `/api/accounts/transfer`        | Virement entre comptes              |
| GET     | `/api/accounts/{id}/history`    | Historique des opérations du compte |

> Documentation complète disponible via Swagger UI : `/swagger-ui.html`

---

## ⚙️ Installation & Exécution

### 🔧 Prérequis

- Java 17+
- Maven
- Base de données MySQL ou PostgreSQL (à configurer dans `application.properties`)

### ▶️ Lancer l’application

```bash
./mvnw spring-boot:run
```

Ou avec Maven installé :

```bash
mvn spring-boot:run
```

---

## 🧪 Tests

Les tests unitaires et d’intégration se trouvent dans :

```
src/test/java/ma/enset/ebankingbackendibdcc/
```

Exécuter les tests avec :

```bash
mvn test
```

---

# 🖥️ Frontend — ebanking-frontend-ibdcv

Ce projet Angular constitue l’interface utilisateur du système eBanking
. Il consomme les APIs REST exposées par le backend Spring Boot 
et offre une expérience fluide et sécurisée aux utilisateurs.

---

## 🚀 Fonctionnalités principales

- 🔐 Authentification avec gestion des rôles (admin, user)
- 👤 Gestion des clients : création, consultation
- 💰 Gestion des comptes bancaires : visualisation, opérations
- 🧭 Navigation dynamique avec affichage conditionnel selon les rôles
- 🛡️ Sécurité via JWT, guards et interceptors Angular

---

## 🛠️ Technologies utilisées

| Technologie       | Version    | Description                          |
|-------------------|------------|--------------------------------------|
| Angular           | 20.1.2     | Framework principal                  |
| Angular CLI       | 20.1.1     | Outils de développement              |
| RxJS              | 7.8.2      | Programmation réactive               |
| TypeScript        | 5.8.3      | Langage principal                    |
| zone.js           | 0.15.1     | Gestion du contexte d'exécution      |

---

## 📁 Structure du projet

```
ebanking-frontend-ibdcv/
├── accounts/
├── customers/
├── customer-accounts/
├── new-customer/
├── login/
├── navbar/
├── not-authorized/
├── admin-template/
├── services/
│   ├── account.service.ts
│   ├── customer.service.ts
│   ├── auth.service.ts
│   └── app.config.ts
├── model/
│   ├── account.model.ts
│   └── customer.model.ts
```

> Les dossiers `guards/` et `interceptors/` sont utilisés pour la sécurité, 

---

## ⚙️ Installation et exécution

### Prérequis

- Node.js `>= 22.12.0`
- npm `>= 10.9.0`
- Angular CLI `>= 20.1.1`

### Étapes

```bash

# Installer les dépendances
npm install

# Lancer le serveur Angular
ng serve

# Accéder à l'application
http://localhost:4200/
```

---

## 🔗 Intégration avec le backend

Ce frontend consomme les APIs REST exposées par le backend Spring Boot (`ebanking-backend-ibdcv`). Les appels HTTP sont sécurisés via JWT,
et les rôles sont utilisés pour contrôler l’accès aux différentes fonctionnalités.


# DEMO

https://github.com/user-attachments/assets/5853c8ab-daa6-44c0-9356-5c4c4d5dfd6c
---
## 👨‍💻 Auteur



Développé par **Ayman**  
Passionné par Angular, Spring Boot, et les architectures modernes sécurisées.

---


## 🤝 Contribution

Les contributions sont les bienvenues !  
Pour les changements majeurs, merci d’ouvrir une issue pour en discuter au préalable.
