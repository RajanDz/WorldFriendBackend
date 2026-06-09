# WorldFriend - Travel & Location Discovery Platform

WorldFriend is a full-stack travel and location discovery application built with Spring Boot and React. The platform allows users to explore cities and locations around the world through advanced search, filtering, and recommendation features.

## Features

### Authentication & User Management
- User registration
- Password encryption
- Role-based access control
- Input validation
- Username and email uniqueness checks

### City Management
- Browse cities
- View city details
- Recommended cities
- Search cities by name
- Upload city images
- Country-based filtering

### Location Management
- Create locations
- View location details
- Upload location images
- Find locations by city
- Find locations by country
- Recommended locations
- Location categorization

### Advanced Search System
- Dynamic filtering using JPA Specifications
- Search by:
  - Name
  - Country
  - City
  - Location Type
- Pagination support
- Combined filters

### Media Management
- City image upload
- Location image upload
- Unique file generation
- Local file storage

---

## Technologies

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL

### Frontend
- React
- JavaScript
- HTML
- CSS

### Tools
- Postman
- Git
- GitHub

---

## Architecture

The application follows a layered architecture:

- Controller Layer
- Service Layer
- Repository Layer
- Database Layer

Business logic is separated from persistence logic to ensure maintainability and scalability.

---

## Core Modules

### Authentication Module

- User registration
- Password hashing
- Validation rules
- Role assignment

### City Module

- Recommended cities
- City search
- City image management
- Country filtering

### Location Module

- Create locations
- Location recommendations
- City-based location search
- Country-based location search
- Location image management

### Search Module

- Dynamic query building
- Multi-criteria filtering
- Pagination support
- JPA Specification implementation

---

## Database Design

### User
- id
- firstname
- lastname
- username
- email
- password

### Role
- id
- name

### City
- id
- name
- country
- imageUrl

### Location
- id
- name
- description
- type
- country
- city
- latitude
- longitude

---

## Implemented Concepts

- Spring Security
- Password Encryption
- Role-Based Access Control (RBAC)
- DTO Pattern
- Pagination
- Dynamic Filtering
- JPA Specifications
- File Upload Management
- Entity Relationships
- Validation Rules
- Exception Handling
- Layered Architecture

---

## Advanced Features

### Dynamic Search Filters

Users can combine multiple filters simultaneously:

- Name
- Country
- City
- Location Type

The filtering system is implemented using Spring Data JPA Specifications, allowing flexible and scalable query generation.

### Pagination

Large result sets are handled using Spring Pagination, improving performance and user experience.

---

## Future Improvements

- JWT Authentication
- Favorites System
- User Reviews
- Ratings
- Google Maps Integration
- Geolocation Search
- AWS S3 Image Storage
- Docker Deployment

---

## Author

**Rajan Dz**
