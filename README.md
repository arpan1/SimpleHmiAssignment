Markdown
# Automotive HMI Dashboard (Android / AAOS Simulation)

A modern, production-grade **Human-Machine Interface (HMI) Dashboard** built for automotive head-units and embedded digital cockpits. This project demonstrates Clean Architecture, reactive telemetry streaming, simulated vehicle physics, and state management using **Jetpack Compose**, **Kotlin Coroutines/Flow**, and **Dagger Hilt**.

Designed to run seamlessly on standard Android phone/tablet emulators in landscape mode or native Android Automotive OS (AAOS) virtual instances—without requiring physical vehicle hardware or `android.car` APIs.

---

## Architecture & Design Patterns

The application follows **Clean Architecture** principles split into three distinct layers with strict dependency inversion, following the **MVVM (Model-View-ViewModel)** pattern with Unidirectional Data Flow (UDF).


## Layer Breakdown
Data Layer (data): Contains the SimulatedVehicleDataSource, which leverages Kotlin Flow to stream real-time telemetry metrics (speedKmh, isMotorOn, isKillSwitchActive, rideMode). It includes a physics engine simulating realistic vehicle coasting when the accelerator is disengaged.

Domain Layer (domain): Encapsulates business rules and data transformations via GetVehicleTelemetryUseCase. Completely independent of any Android framework or UI dependencies.

Presentation Layer (presentation): Features a high-contrast, low-distraction automotive dark-mode UI built with Jetpack Compose. Displays speed gauges, motor/kill-switch indicators, dynamic warnings, and mode toggles.

## Tech Stack & Tooling
Language: Kotlin 2.0+

UI Framework: Jetpack Compose (Material 3)

Dependency Injection: Dagger Hilt

Asynchronous & Reactive: Kotlin Coroutines & StateFlow

Architecture: MVVM + Clean Architecture (UDF)

Minimum SDK: API 26 (Android 8.0 Oreo) / Target SDK: API 36

Testing: JUnit 4, MockK (for mocking reactive streams), Coroutines Test Framework

## Key Features & Business Rules
Reactive Telemetry Engine: Simulated real-time streaming of vehicle metrics using cold/hot flow transformations.

Coasting Physics Simulation: Decoupled speed and motor states allow realistic speed decay (coasting) when the motor disengaging or kill-switch is activated.

Signal Sanitization & Clamping: Automatic handling of malformed telemetry (e.g., negative speed signals are safely clamped to 0.0 km/h with a UI warning banner triggered).

Landscape-Only HMI Layout: Configured for automotive displays with high contrast to reduce driver cognitive load.

## Getting Started
Prerequisites
Android Studio: Koala / Ladybug (or newer)

JDK: Java 11 or higher

Android SDK: API 36 installed

## Setup & Run Instructions
Clone the repository:
git clone [https://github.com/your-username/automotive-hmi-dashboard.git](https://github.com/your-username/automotive-hmi-dashboard.git)
cd automotive-hmi-dashboard

Open in Android Studio: Allow Gradle to import dependencies and sync.

## Launch on Emulator:

Launch any standard Android Phone/Tablet Virtual Device (AVD).

Ensure the emulator is rotated to Landscape (the app enforces landscape mode via AndroidManifest.xml).

Click Run (Shift + F10).

The project contains unit tests verifying HmiViewModel state transitions, coroutine dispatching, telemetry edge cases, and exception handling.

## Running Tests via Command Line
To run all local unit tests and display live status (PASSED / FAILED):

Bash
./gradlew cleanTest testDebugUnitTest

## Running Tests in Android Studio
Navigate to app/src/test/java/com/automotive/hmi/dashboard/presentation/HmiViewModelTest.kt.

Click the green Play gutter icon next to the test class name to execute all unit tests.

## Gradle Configuration Notes
Hilt Build Error Fix: Includes enableAggregatingTask = false in build.gradle.kts to resolve JavaPoet class loader conflicts during annotation processing.

MinSDK 26 Requirement: Set to API 26+ to fully support modern Adaptive Icons, Jetpack Compose, and modern Android Automotive features.