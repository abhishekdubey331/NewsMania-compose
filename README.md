# NewsFeed App

NewsFeed is a modern news application built with Jetpack Compose, following Clean Architecture principles. The app fetches and displays the latest news feeds, providing a seamless user experience with a responsive UI and robust architecture.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Contributing](#contributing)
- [License](#license)

## Features

- Fetch and display the latest news feed on the home screen.
- Modern, responsive UI built using Jetpack Compose.
- Clean Architecture with distinct layers for data, domain, and presentation.
- Efficient data fetching and state management using Coroutines and Flows.
- Dependency injection with Hilt for modular and testable code.
- Local data persistence using Room database.

## Architecture

The app follows Clean Architecture principles, ensuring clear separation of concerns, testability, and maintainability:

- **Presentation Layer**: Contains UI components built with Jetpack Compose, and ViewModels that manage UI state using StateFlow.
- **Domain Layer**: Contains business logic, including use cases that interact with repositories.
- **Data Layer**: Handles data operations, including fetching from remote APIs and caching in Room database.

## Tech Stack

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern toolkit for building native Android UIs with declarative code.
- **[Clean Architecture](https://proandroiddev.com/clean-architecture-data-flow-dependency-rule-615ffdd79e29)**: Ensures modular, testable, and maintainable code.
- **[Room](https://developer.android.com/training/data-storage/room)**: Persistence library for local data storage.
- **[Hilt](https://developer.android.com/training/dependency-injection/hilt-android)**: Dependency injection framework for Android.
- **[Coroutines & Flows](https://kotlinlang.org/docs/coroutines-overview.html)**: For asynchronous programming and reactive data streams.
- **[Retrofit](https://square.github.io/retrofit/)**: HTTP client for network requests.

## Screenshots

> Include screenshots showcasing the main features and UI of the app.

## Getting Started

Follow the instructions below to set up the project on your local machine.

### Prerequisites

- Android Studio Giraffe (2023.3.1) or later
- Kotlin 1.8.20 or later
- Minimum SDK 21

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/NewsFeedApp.git
   cd NewsFeedApp
