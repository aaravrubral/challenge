# BlackRock Auto-Saving Challenge

🧾 BlackRock Backend Challenge – Spring Boot

This project implements the BlackRock backend challenge using Java 17 + Spring Boot.
It exposes REST APIs to parse transactions, validate them, apply temporal rules (q / p / k), and compute financial returns (NPS & Index).

🛠 Tech Stack

Java 17

Spring Boot 3.x

Maven

Jackson (JSON serialization)

JUnit 5

Docker (optional)

📁 Project Structure
src
├── main
│   ├── java/com/blackrock/challenge
│   │   ├── controller
│   │   ├── service
│   │   ├── model
│   │   └── BlackrockChallengeApplication.java
│   └── resources
│       └── application.yml
└── test
└── java/com/blackrock/challenge/service
🚀 Setup Instructions
✅ Prerequisites

Make sure the following are installed:

Java 17+

java -version

Maven 3.8+

mvn -v

(Optional)

Docker (only if you want to run in a container)

▶️ Run Locally (Recommended)

Clone the repository

git clone <repository-url>
cd blackrock-challenge

Build the project

mvn clean install

Start the application

mvn spring-boot:run

Application will start on

http://localhost:5477
🐳 Run with Docker (Optional)

Build Docker image

docker build -t blackrock-challenge .

Run container

docker run -p 5477:5477 blackrock-challenge
🔗 Base API URL
http://localhost:5477/blackrock/challenge/v1
📌 Available Endpoints
🔹 Transactions
Endpoint	Method	Description
/transactions/parse	POST	Parse expenses into transactions
/transactions/validator	POST	Validate transactions
/transactions/filter	POST	Apply q / p / k temporal rules
🔹 Returns
Endpoint	Method	Description
/returns/nps	POST	Calculate NPS
/returns/index	POST	Calculate Index
🧪 Running Tests

Run all unit tests:

mvn test

Tests cover:

Duplicate transaction detection

Negative amount validation

q / p / k rule correctness

📄 JSON Date Format

All dates follow the format:

yyyy-MM-dd'T'HH:mm:ss

Example:

"date": "2023-10-12T20:15:00"
📝 Notes for Reviewers

Endpoints use /parse, /validator, etc. instead of :parse for Spring compatibility

Validation is cumulative and enforced inside /transactions/filter

Jackson annotations ensure stable response field ordering

Services are fully unit tested without Spring context