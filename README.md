# Plant Donation System

A Java-based desktop application that facilitates plant donations and orders with secure user authentication, session management, and an admin dashboard for database interaction.

![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.4-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Required-2496ED?logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)
![Security](https://img.shields.io/badge/Encryption-AES-green.svg)
![Platform](https://img.shields.io/badge/Platform-Desktop-blue.svg)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Database Setup](#database-setup)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Usage](#usage)
- [Security Considerations](#security-considerations)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

Plant Donation System is a full-featured desktop application designed to manage plant inventory, process donations (both plants and money), and handle plant orders. The system includes secure user authentication with AES encryption, session-based access control, and a comprehensive admin dashboard for monitoring all transactions.

Built with Java Swing for the GUI and MySQL for data persistence, this application demonstrates secure coding practices including prepared statements to prevent SQL injection, encrypted password storage, and role-based access control.

![Project Overview](./src/Assets/home%20screen.png)

---

## Features

### User Authentication

- Secure login with username and password
- AES encryption for password storage
- Session management with automatic logout
- User registration with field validation

### Donation System

- Donate plants (Hibiscus, Mango, Neem, Banyan)
- Monetary donations
- Real-time inventory updates
- Donation history tracking

### Order Management

- Browse available plants with live inventory
- Place orders with quantity selection
- Multiple payment options (Cash, Card, UPI)
- Automatic price calculation and inventory deduction

### Admin Dashboard

- View all database tables in tabbed interface
- Real-time data visualization
- User management capabilities
- Transaction monitoring

### Security Features

- AES encryption for sensitive data
- Prepared statements to prevent SQL injection
- Session-based access control
- Admin role verification

---

## Tech Stack

| Layer                | Technologies            |
| -------------------- | ----------------------- |
| **Language**         | Java 17+                |
| **GUI**              | Java Swing              |
| **Database**         | MySQL 8.4 (Docker)      |
| **Containerization** | Docker & Docker Compose |
| **Encryption**       | AES (Java Cryptography) |
| **Database Driver**  | MySQL Connector/J       |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    PLANT DONATION SYSTEM - ARCHITECTURE                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                       JAVA SWING APPLICATION                         │   │
│  │                                                                      │   │
│  │    ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐     │   │
│  │    │   Login Page    │  │   Home Page     │  │   Admin Page    │     │   │
│  │    │                 │  │                 │  │                 │     │   │
│  │    │  • Auth login   │  │  • Donate       │  │  • View users   │     │   │
│  │    │  • Registration │  │  • Order        │  │  • View orders  │     │   │
│  │    │  • Session mgmt │  │  • History      │  │  • View storage │     │   │
│  │    └────────┬────────┘  └────────┬────────┘  └────────┬────────┘     │   │
│  │             │                    │                    │              │   │
│  │             └────────────────────┼────────────────────┘              │   │
│  │                                  │                                   │   │
│  │   ┌──────────────────────────────▼────────────────────────────────┐  │   │
│  │   │                       UTILITY LAYER                           │  │   │
│  │   │                                                               │  │   │
│  │   │   Session Util     │   EncryptionUtil    │    MySQL.java      │  │   │
│  │   │   Session mgmt     │   AES encryption    │   DB connection    │  │   │
│  │   └───────────────────────────────────────────────────────────────┘  │   │
│  │                                                                      │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                     │                                       │
│                                     │ JDBC                                  │
│                                     ▼                                       │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                          MYSQL DATABASE                              │   │
│  │                                                                      │   │
│  │        ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐        │   │
│  │        │  users   │  │ storage  │  │donations │  │  orders  │        │   │
│  │        │          │  │          │  │          │  │          │        │   │
│  │        │ accounts │  │ inventory│  │ history  │  │purchases │        │   │
│  │        └──────────┘  └──────────┘  └──────────┘  └──────────┘        │   │
│  │                                                                      │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
│       Secure Authentication → Encrypted Storage → SQL Injection Safe        │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Application Flow

1. User launches `LoginPage.java`
2. Authentication checks encrypted password against database
3. Session is created for authenticated user
4. User navigates to donation or order features
5. All database operations use prepared statements
6. Admin users can access the admin dashboard

---

## Getting Started

### Prerequisites

- **Visual Studio Code** ([Download](https://code.visualstudio.com/download/))
- **Extension Pack for Java** (Install from VS Code Extensions)
- **Java Development Kit (JDK) 17 or higher** ([Download](https://www.oracle.com/in/java/technologies/downloads/))
- **Docker Desktop** ([Download](https://www.docker.com/products/docker-desktop/))
- **MySQL Connector/J** (Already included in repository)

### Installation

#### Step 1: Clone the Repository

```bash
git clone https://github.com/tanishqmudaliar/Plant-Donation-System.git
cd Plant-Donation-System
```

#### Step 2: Open Project in VS Code

1. Launch **Visual Studio Code**
2. Go to **File → Open Folder**
3. Navigate to and select the `Plant-Donation-System` folder
4. Click **Select Folder**

#### Step 3: Install VS Code Java Extensions

1. Open the Extensions view (`Ctrl+Shift+X` or `Cmd+Shift+X` on Mac)
2. Search for "Extension Pack for Java"
3. Click **Install** on the official Microsoft extension pack
4. Wait for all Java extensions to install

#### Step 4: Verify Java Installation

```bash
java -version
```

Expected output (version may vary):

```
java version "17.0.x" or higher
Java(TM) SE Runtime Environment (build 17.0.x+xx-xxx)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+xx-xxx, mixed mode, sharing)
```

### Database Setup

#### A. Install Docker Desktop

1. Download Docker Desktop from [Docker Downloads](https://www.docker.com/products/docker-desktop/)
2. Run the installer and follow the setup wizard
3. Start Docker Desktop and ensure it's running

#### B. Create Environment Configuration

1. In the project root directory, create a `.env` file:

```bash
MYSQL_DB=plant_donation
MYSQL_PASSWORD=your_secure_password_here
MYSQL_PORT=3306
```

**⚠️ Important**: Replace `your_secure_password_here` with a strong password of your choice.

#### C. Docker Compose File

1. The `docker-compose.yml` file is already included in the repository root
2. Review the configuration to understand the setup:

```yaml
services:
  db:
    image: mysql:8.4
    container_name: plant_donation_db
    environment:
      MYSQL_DATABASE: ${MYSQL_DB}
      MYSQL_ROOT_PASSWORD: ${MYSQL_PASSWORD}
    ports:
      # Maps Docker MySQL port (3306) to your PC's port (3306)
      - "${MYSQL_PORT}:3306"
    volumes:
      # Automatically restores backup.sql on first startup
      - ./backup.sql:/docker-entrypoint-initdb.d/init.sql
      # Persist data even if you delete the container
      - db_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  db_data:
```

#### D. Prepare Database Backup

1. Place your `backup.sql` file in the project root directory
2. This file will automatically initialize the database on first startup

**Note**: If you don't have a `backup.sql`, create one with this content:

```sql
CREATE DATABASE IF NOT EXISTS plant_donation;
USE plant_donation;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile VARCHAR(15) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE storage (
    id INT PRIMARY KEY DEFAULT 1,
    hibiscus INT DEFAULT 0,
    mango INT DEFAULT 0,
    neem INT DEFAULT 0,
    banyan INT DEFAULT 0,
    money_collected INT DEFAULT 0
);

INSERT INTO storage (id, hibiscus, mango, neem, banyan, money_collected)
VALUES (1, 0, 0, 0, 0, 0);

CREATE TABLE donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    donation_type VARCHAR(50) NOT NULL,
    donation_detail VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hibiscus INT DEFAULT 0,
    mango INT DEFAULT 0,
    neem INT DEFAULT 0,
    banyan INT DEFAULT 0,
    price INT NOT NULL,
    address TEXT NOT NULL,
    payment_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### E. Start the Database

1. Open a terminal in the project root directory
2. Start the MySQL container:

```bash
docker compose up -d
```

3. Verify the database is running:

```bash
docker compose ps
```

You should see `plant_donation_db` with status `Up` and `healthy`.

#### F. Verify Database Connection

To check if the database is accessible:

```bash
docker compose exec db mysql -uroot -p${MYSQL_PASSWORD} -e "SHOW DATABASES;"
```

You should see `plant_donation` in the list.

### Configuration

#### Create Application Configuration File

1. Navigate to the `src/` directory
2. Copy `config.properties.example` to `config.properties`:

```bash
cp src/config.properties.example src/config.properties
```

3. Open `src/config.properties` and update with your settings:

```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/plant_donation
db.user=root
db.password=your_secure_password_here

# Application Settings
app.name=Plant Donation System
app.version=1.0
```

**⚠️ Important**:

- Use the **same password** you set in the `.env` file
- The hostname is `localhost` because Docker maps port 3306 to your local machine
- **Never commit** `config.properties` to version control

#### Verify Configuration

Your project structure should now include:

```
Plant-Donation-System/
├── docker-compose.yml          # ✓ Created
├── .env                        # ✓ Created (don't commit!)
├── backup.sql                  # ✓ Created/Placed
├── src/
│   └── config.properties       # ✓ Created (don't commit!)
```

### Running the Application

#### Add MySQL Connector Library

1. In VS Code, find the **Java Projects** section in Explorer
2. Expand **Referenced Libraries**
3. If `mysql-connector-j-x.x.x.jar` is not listed:
   - Click the **+** icon next to "Referenced Libraries"
   - Navigate to the project root or `lib/` folder
   - Select the `mysql-connector-j-x.x.x.jar` file

![Reference Image](./src/Assets/refference.png)

#### Run the Application

1. Navigate to `src/LoginPage.java`
2. Right-click anywhere in the file editor
3. Select **Run Java**
4. The Login window should appear

#### First Time Setup

1. Click **"Create Account"** to register a new user
2. Fill in all required fields
3. Check **"Register as Admin"** if you want admin privileges
4. After registration, log in with your credentials

---

## Project Structure

```
Plant-Donation-System/
├── src/
│   ├── Assets/                         # UI Images and Icons
│   │   ├── home screen.png
│   │   ├── heading.png
│   │   ├── donate.png
│   │   ├── buy.png
│   │   ├── donate frame head.png
│   │   ├── order frame head.png
│   │   └── refference.png
│   │
│   ├── AdminPage.java                  # Admin dashboard
│   ├── CenteredComboBoxRenderer.java   # UI component
│   ├── config.properties               # Database config (create from example)
│   ├── config.properties.example       # Configuration template
│   ├── EncryptionUtil.java             # AES encryption utility
│   ├── HomePage.java                   # Main user interface
│   ├── LoginPage.java                  # Application entry point
│   ├── MySQL.java                      # Database connection manager
│   ├── RegistrationPage.java           # User registration
│   ├── SessionUtil.java                 # Session management
│   └── WordWrapCellRenderer.java       # Table cell renderer
│
├── lib/                                # External libraries
│   └── mysql-connector-j-9.1.0.jar
│
├── .env                                # Environment variables (gitignored)
├── .env.example                        # Template for .env file
├── backup.sql                          # Database initialization script
├── docker-compose.yml                  # Docker MySQL configuration
├── .gitignore                          # Git ignore rules
├── LICENSE                             # MIT License
└── README.md                           # Project documentation
```

---

## Database Schema

### Users Table

| Column   | Type         | Description                 |
| -------- | ------------ | --------------------------- |
| id       | INT          | Primary key, auto-increment |
| username | VARCHAR(50)  | Unique username             |
| name     | VARCHAR(100) | Full name                   |
| email    | VARCHAR(100) | Unique email                |
| mobile   | VARCHAR(15)  | Phone number                |
| dob      | DATE         | Date of birth               |
| gender   | VARCHAR(10)  | Gender                      |
| password | VARCHAR(255) | AES encrypted password      |
| is_admin | BOOLEAN      | Admin role flag             |

### Storage Table

| Column          | Type | Description              |
| --------------- | ---- | ------------------------ |
| id              | INT  | Primary key              |
| hibiscus        | INT  | Hibiscus plant count     |
| mango           | INT  | Mango plant count        |
| neem            | INT  | Neem plant count         |
| banyan          | INT  | Banyan plant count       |
| money_collected | INT  | Total monetary donations |

### Donations Table

| Column          | Type         | Description                 |
| --------------- | ------------ | --------------------------- |
| id              | INT          | Primary key, auto-increment |
| user_id         | INT          | Foreign key to users        |
| donation_type   | VARCHAR(50)  | Type of donation            |
| donation_detail | VARCHAR(255) | Donation details            |
| created_at      | TIMESTAMP    | Donation timestamp          |

### Orders Table

| Column       | Type        | Description                 |
| ------------ | ----------- | --------------------------- |
| id           | INT         | Primary key, auto-increment |
| user_id      | INT         | Foreign key to users        |
| hibiscus     | INT         | Hibiscus quantity ordered   |
| mango        | INT         | Mango quantity ordered      |
| neem         | INT         | Neem quantity ordered       |
| banyan       | INT         | Banyan quantity ordered     |
| price        | INT         | Total price                 |
| address      | TEXT        | Delivery address            |
| payment_type | VARCHAR(50) | Payment method              |
| created_at   | TIMESTAMP   | Order timestamp             |

---

## Usage

1. **Register**: Create a new account from the login page
2. **Login**: Use your credentials to access the system
3. **Donate**: Choose to donate plants or money from the home page
4. **Order**: Browse available plants and place orders
5. **Admin**: Access admin dashboard (admin users only) to view all data

---

## Security Considerations

| Feature                | Implementation                         |
| ---------------------- | -------------------------------------- |
| **Password Storage**   | AES encryption for all passwords       |
| **SQL Injection**      | Prepared statements for all queries    |
| **Session Management** | Session-based access control           |
| **Role Verification**  | Admin role check for dashboard access  |
| **Configuration**      | Sensitive data in separate config file |

### Production Recommendations

- Change the AES encryption key in `EncryptionUtil.java` for production use
- Never commit `config.properties` with real credentials to version control
- Keep `config.properties.example` as a template with placeholder values
- Use environment variables or secure vault systems for production deployments
- Implement HTTPS if deploying as a web application
- Regular database backups recommended

---

## Troubleshooting

### "No suitable driver found for jdbc:mysql://localhost:3306/plant_donation"

**Solution**: Ensure MySQL Connector/J is added to Referenced Libraries in VS Code.

### "Access denied for user 'root'@'localhost'"

**Solution**: Verify that the password in `src/config.properties` matches the password in `.env` file.

### "Table 'plant_donation.users' doesn't exist"

**Solution**:

1. Ensure `backup.sql` is in the project root
2. Restart the Docker container: `docker compose down && docker compose up -d`
3. The database will be initialized automatically on startup

### "Communications link failure" or "Connection refused"

**Solution**:

1. Check if Docker Desktop is running
2. Verify the database container is running: `docker compose ps`
3. Check container health: `docker compose logs db`
4. Restart the container: `docker compose restart db`

### Database not initializing from backup.sql

**Solution**:

1. Stop and remove the container and volumes:

```bash
   docker compose down -v
```

2. Start fresh (this will re-initialize from backup.sql):

```bash
   docker compose up -d
```

### Images not showing

**Solution**: Update image paths in `HomePage.java` to use relative paths starting with `src/Assets/`.

### "Could not find or load main class"

**Solution**:

- Ensure Java extension pack is installed
- Reload VS Code window (`Ctrl+Shift+P` → "Developer: Reload Window")
- Check that JDK is properly configured

### Docker Commands Reference

| Command                                  | Description                                 |
| ---------------------------------------- | ------------------------------------------- |
| `docker compose up -d`                   | Start the database in background            |
| `docker compose down`                    | Stop and remove the database container      |
| `docker compose down -v`                 | Stop and remove container + delete all data |
| `docker compose ps`                      | Check container status                      |
| `docker compose logs db`                 | View database logs                          |
| `docker compose restart db`              | Restart the database                        |
| `docker compose exec db mysql -uroot -p` | Connect to MySQL shell                      |

---

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## License

This project is open source and available under the [MIT License](LICENSE).

---

Made with ❤️ for plant lovers

> **Note**: This is an educational project. For production use, implement additional security measures and follow best practices for database management and password storage.
