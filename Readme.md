

# User Management Spring Boot Application - Assesment Project

## Project Overview
A comprehensive user management system built with Spring Boot, featuring user registration, login, update, and deletion functionalities.

## Technologies Used
- Spring Boot
- Spring Security
- Spring Data JPA
- Maven
- JUnit
- Mockito

## Features
- User Registration
- User Login
- User Profile Update
- User Deletion
- User List Retrieval

## Prerequisites
- Java 11 or higher
- Maven
- Git

## Setup Instructions
1. Clone the repository
   ```
   git clone https://github.com/De-Hype/assesment-task.git
   ```

2. Navigate to the project directory
   ```
   cd assessment-tasks
   ```

3. Replace the environment variables in the application.properties with actual variables
   
4. Build the project
   ```
   mvn clean install
   ```

5. Run the application
  ```
   mvn spring-boot:run
   ```

## Running Tests
```
mvn test
```

## Endpoints
- POST /api/users/register
- POST /api/users/login
- PUT /api/users/update/{userId}
- DELETE /api/users/delete/{userId}
- GET /api/users/all

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
```