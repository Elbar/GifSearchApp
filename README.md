# GIF Search App 

A modern Android application for searching and viewing GIFs using the Giphy API. Built with Clean Architecture, MVVM pattern, and modern Android development practices.

## 🎯 Project Overview

This project demonstrates how to build a production-ready Android app using modern development practices:

- **Clean Architecture** with clear separation of concerns
- **MVVM Pattern** for reactive UI
- **Koin** for lightweight dependency injection
- **Jetpack Compose** for modern UI development
- **Kotlin Coroutines & Flows** for reactive programming
- **Paging 3** for infinite scrolling
- **MockK** for unit testing

## ✅ Requirements Implemented

### Primary Requirements
- ✅ **Kotlin** - Entire project built in Kotlin
- ✅ **Auto Search** - Search with 300ms debounce when user stops typing
- ✅ **Pagination** - Infinite scrolling with Paging 3
- ✅ **Error Handling** - Comprehensive error handling with retry functionality
- ✅ **Unit Tests** - Extensive test coverage

### UI Requirements
- ✅ **Multiple Views** - Search screen + Detail screen
- ✅ **Grid Display** - Responsive LazyVerticalGrid
- ✅ **Detail Activity** - Comprehensive GIF information screen
- ✅ **Loading Indicators** - Throughout the app
- ✅ **Error Display** - User-friendly error messages
- ✅ **GIF Playback** - Animated GIFs with play/pause controls and quality options

### Bonus Features
- ✅ **Modern Development** - Coroutines, Flows, Compose
- ✅ **Architecture Pattern** - Clean Architecture + MVVM
- ✅ **Single-Activity Architecture** - Navigation Compose
- ✅ **Network Monitoring** - Real-time connectivity status
- ✅ **Dependency Injection** - Koin framework

## 🏗️ Architecture

```
📁 Clean Architecture Layers:
├── presentation/     # UI Layer (Compose, ViewModels)
│   ├── search/       # Search screen
│   ├── detail/       # Detail screen
│   ├── navigation/   # Navigation setup
│   └── theme/        # UI theming
├── domain/           # Business Logic Layer
│   ├── model/        # Business entities
│   ├── repository/   # Repository interfaces
│   └── usecase/      # Business use cases
├── data/             # Data Layer
│   ├── remote/       # API & DTOs
│   ├── repository/   # Repository implementations
│   ├── mapper/       # Data mapping
│   ├── paging/       # Pagination sources
│   └── network/      # Network monitoring
└── di/               # Dependency Injection
```

## 🚀 Key Features

### Auto-Search with Debouncing
```kotlin
val gifs = searchQuery
    .debounce(300) 
    .distinctUntilChanged()
    .flatMapLatest { query ->
        if (query.isBlank()) {
            getTrendingGifsUseCase()
        } else {
            searchGifsUseCase(query)
        }
    }
```

### Network Connectivity Monitoring
```kotlin
class NetworkConnectivityObserver(context: Context) {
    fun observe(): Flow<NetworkStatus> = callbackFlow {
        // Real-time network status updates
    }
}
```

### Koin Dependency Injection
```kotlin
val networkModule = module {
    single<Retrofit> { /* Retrofit setup */ }
    single<GiphyApiService> { get<Retrofit>().create() }
}

val dataModule = module {
    single<GifRepository> { GifRepositoryImpl(get()) }
}

val domainModule = module {
    factory { SearchGifsUseCase(get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
}
```

## 🧪 Testing Strategy

### Unit Tests Coverage
- **Domain Layer**: Use cases and business logic
- **Data Layer**: Repository implementations and API interactions
- **Presentation Layer**: ViewModels and UI state management

### Testing Tools
- **MockK** - Kotlin-first mocking framework
- **Turbine** - Flow testing library
- **Coroutines Test** - Testing coroutines and flows

### Example Test
```kotlin
@Test
fun `search query change updates state correctly`() = runTest {
    // Given
    val query = "cats"
    
    // When
    viewModel.onSearchQueryChange(query)
    
    // Then
    viewModel.searchQuery.test {
        assertEquals(query, awaitItem())
    }
}
```

## 📱 UI/UX Features

### Search Screen
- Auto-search with visual feedback
- Infinite scrolling
- Network status indicators
- Error handling with retry
- **Animated GIF playback** in grid items

### Detail Screen
- High-quality GIF display
- Comprehensive metadata
- Clean information cards
- Responsive design
- **Quality toggle** (optimized vs HD)

## 🎬 GIF Playback Features

### **Automatic Animation**
- All GIFs in search grid animate automatically
- Smooth performance with optimized loading
- Crossfade transitions for professional feel

### **Interactive Controls**
- **Quality Switch**: Choose between optimized and original quality
- **Loading States**: Visual feedback during GIF loading

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | Clean Architecture + MVVM |
| **DI** | Koin |
| **Networking** | Retrofit + OkHttp |
| **Image Loading** | Coil |
| **Pagination** | Paging 3 |
| **Reactive** | Kotlin Flows + Coroutines |
| **Navigation** | Navigation Compose |
| **Testing** | JUnit, MockK, Turbine |

## 🚦 Getting Started

1. **Clone the project**
2. **Open in Android Studio**
3. **Build and run**

The app includes a demo Giphy API key. For production use, replace with your own key in `build.gradle.kts`.


## 📄 License

This project is a technical demonstration of modern Android development practices.