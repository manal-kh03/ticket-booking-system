# Ticket Booking System – Software Testing Semester Project

A Java-based Ticket Booking System developed as part of the Software Testing Semester Project. The project focuses on applying software testing principles, Test-Driven Development (TDD), dependency injection, and unit testing using JUnit 5 and Mockito.

## Project Overview

The system implements the core booking workflow through a `BookingManager` service.

The booking process includes:

1. Validating booking requests.
2. Checking whether an event is sold out.
3. Calculating the total booking amount.
4. Processing payment through an injected payment gateway.
5. Saving the booking after successful payment.
6. Sending a booking confirmation notification.

The system uses interfaces for external dependencies, allowing them to be mocked during unit testing.

## Technologies

- Java 17
- Apache Maven
- JUnit 5
- Mockito
- JaCoCo
- Git and GitHub

## Requirements

Make sure the following tools are installed:

- Java 17 or later
- Maven 3.9 or later
- Git

Check your installation:

```bash
java -version
mvn -version
git --version
```

## Getting Started

Clone the repository:

```bash
git clone https://github.com/manal-kh03/ticket-booking-system.git
```

Navigate to the project directory:

```bash
cd ticket-booking-system
```

Run all tests:

```bash
mvn clean test
```

## Test Coverage Report

The project uses JaCoCo to generate a test coverage report.

Run:

```bash
mvn clean test jacoco:report
```

Open the generated HTML report:

```text
target/site/jacoco/index.html
```

## Project Structure

```text
ticket-booking-system/
│
├── src/
│   ├── main/
│   │   └── java/edu/course/
│   │       ├── booking/
│   │       │   ├── Booking.java
│   │       │   ├── BookingManager.java
│   │       │   ├── BookingRequest.java
│   │       │   ├── BookingResult.java
│   │       │   ├── IEventRepository.java
│   │       │   ├── INotificationService.java
│   │       │   ├── IPaymentGateway.java
│   │       │   └── PaymentFailedException.java
│   │       └── example/
│   │
│   └── test/
│       └── java/edu/course/
│           ├── booking/
│           │   ├── BookingManagerTest.java
│           │   └── BookingRequestTest.java
│           └── example/
│
├── docs/
│   ├── guides/
│   └── templates/
│
├── scripts/
├── pom.xml
├── PROJECT_SPECIFICATION.md
└── README.md
```

## Main Components

| Component | Responsibility |
|---|---|
| `BookingRequest` | Stores booking information and validates request data. |
| `Booking` | Represents a successfully processed booking. |
| `BookingResult` | Represents the outcome of a booking operation. |
| `BookingManager` | Coordinates the booking workflow. |
| `IPaymentGateway` | Defines the payment processing contract. |
| `IEventRepository` | Defines event availability checking and booking storage. |
| `INotificationService` | Defines the booking confirmation contract. |
| `PaymentFailedException` | Represents payment processing failures. |

## Testing

The project includes unit tests covering:

- Successful booking workflow.
- Invalid booking requests.
- Sold-out events.
- Payment failures.
- Null dependency validation.

JUnit 5 is used for test execution and parameterized tests. Mockito is used to mock dependencies and verify interactions between components.

Tests verify both expected results and the interactions that should or should not occur during each scenario.

## Development Practices

The project applies:

- Constructor-based Dependency Injection.
- Separation of responsibilities through interfaces.
- Unit testing with JUnit 5.
- Mock-based testing with Mockito.
- Test-Driven Development where supported by the documented Git history.
- Version control using Git and GitHub.

## Project Scope

This milestone focuses on the core booking workflow and its unit tests.

The project does not include:

- Graphical user interface.
- Database implementation.
- REST API.
- Real payment gateway integration.
- Real email or SMS notification integration.
- Deployment or production infrastructure.

External services are represented by interfaces and mocked during testing.

## Team Workflow

- Team members contribute using their own GitHub accounts.
- Changes are committed and pushed to the shared project branch.
- Tests should be executed before pushing changes.
- The team contribution matrix and project report document the actual work completed by each member.

## AI Usage and Academic Integrity

AI-assisted work must be reviewed, understood, and verified by the team. Any material AI assistance must be disclosed according to the project specification.

The Git history must reflect genuine development work and must not be altered to simulate testing stages or contributions.

## Repository

[Ticket Booking System – GitHub](https://github.com/manal-kh03/ticket-booking-system)