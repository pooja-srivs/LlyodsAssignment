# Cat Images App

## Overview
This is an app that provides a listing of cat images using the Cats API. The project is structured using MVI (Model-View-Intent) architecture combined with the CLEAN architecture principles. It leverages various modern Android development tools and libraries such as Retrofit, OkHttp, StateFlows, and Channels for managing state and effects within the ViewModel.

## Features
- Fetch and display a list of cat images.
- View details of a selected cat image.
- Navigate between the list and detail screens.
- Handle state management using MVI architecture.
- Manage side effects using Kotlin Channels.

## Architecture
### MVI (Model-View-Intent) + CLEAN
The app follows the MVI architecture pattern to manage the state and events. The architecture ensures a unidirectional data flow and separation of concerns, making the app scalable and maintainable.

### Libraries and Tools Used
- **Retrofit**: For making HTTP requests to the Cats API.
- **OkHttp**: For networking.
- **Asynchronous Programming**: Coroutines + Flow.
- **StateFlows**: For managing and observing state in a lifecycle-aware manner.
- **Channels**: For handling one-time effects in the ViewModel.
- **Hilt**: For dependency injection.

### Running the App
- On launching the app, you will see a list of cat images.
- Clicking on a cat image will navigate you to the detail screen, where you can view the selected cat image in detail.

### UI Layer
- **HomeScreen**: Displays the list of cat images.
- **DetailScreen**: Displays details of the selected cat image.

### ViewModel Layer
- **HomeViewModel**: Handles the business logic and state management for the Home and Detail screens.

### Model Layer
- **CatImageResponse**: Data class representing the response from the Cats API.
- **CatImagesUiModel**: UI model class for the cat images.

### Intent and State
- **HomeIntent**: Represents user actions.
- **HomeState**: Represents the state of the UI.
- **HomeEffect**: Represents one-time effects.

### Domain Layer

The Domain layer is a key component in Clean Architecture. It encapsulates the core business logic and rules of the application,
providing a separation of concerns and promoting a more maintainable and testable codebase.

- **Entities**: The basic business model objects that are independent of the data layer and UI.
- **Use Cases (Interactors)**: These are the business logic handlers. Each use case represents a specific feature or
  functionality of the application. The use cases orchestrate the flow of data to
  and from the entities and ensure the business rules are applied.

## Acknowledgements
- [The Cat API](https://thecatapi.com/) for providing the API service.
- [Public APIs](https://github.com/public-apis/public-apis) for listing useful APIs.

### Android Studio Version

- **Android Studio Jellyfish | 2023.3.1**
