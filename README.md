
# QuickMem

QuickMem is an Android application designed to help users manage their tasks and memories efficiently. The app leverages modern Android development practices, including Kotlin, Jetpack Compose, Dagger Hilt, and more.

## Features

- User authentication and onboarding
- Task and memory management
- Data storage using DataStore
- Dependency injection with Dagger Hilt
- Modern UI with Jetpack Compose
- Network operations with Ktor and Retrofit
- Image loading with Coil
- Logging with Timber

## Installation

### Prerequisites

- Android Studio Ladybug Feature Drop | 2024.2.2 Nightly 2024-09-16
- JDK 17
- Android SDK 29 or higher

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/pass-with-high-score/quick_mem.git
   cd quick_mem
   ```

2. Open the project in Android Studio.

3. Build the project:
   ```sh
   ./gradlew build
   ```

4. Run the app on an emulator or a physical device.

## Usage

1. Launch the app on your device.
2. Follow the onboarding process.
3. Start managing your tasks and memories.

## Project Structure

- `app/src/main/java/com/pwhs/quickmem/`: Contains the main application code.
- `app/src/main/res/`: Contains the resource files (layouts, strings, etc.).
- `app/build.gradle.kts`: Build configuration for the app module.
- `.github/workflows/`: Contains GitHub Actions workflows for CI/CD.

## Contributing
This is graduation project for the course of "Mobile Application Development" at the FPT Polytechnic College. Contributions are not accepted at the moment.

## Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Ktor](https://ktor.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [Coil](https://coil-kt.github.io/coil/)
- [Timber](https://github.com/JakeWharton/timber)