# GIF Search App 

A modern Android application for searching and viewing GIFs using the Giphy API. Built with Clean Architecture, MVVM pattern, and modern Android development practices.

https://github.com/user-attachments/assets/a8a4d683-4bf8-4f25-8fde-ed31bb63ee08


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

## 🏗️ Architecture

```
📁 Clean Architecture Layers:
├── presentation/     # UI Layer (Compose, ViewModels)
│   ├── di/presentationModule
│   ├── components/   # GifPlayer
│   ├── composables/  # Set of reusable composables
│   ├── search/       # Search screen
│   ├── extensions/   # Extensions
│   ├── detail/       # Detail screen
│   ├── navigation/   # Navigation setup
│   └── theme/        # UI theming
├── domain/           # Business Logic Layer
│   ├── di/domainModule
│   ├── model/        # Business entities
│   ├── network/      # ConnectivityObserver
│   ├── repository/   # Repository interfaces
│   └── usecase/      # Business use cases
├── data/             # Data Layer
│   ├── di/dataModule
│   ├── remote/       # API & DTOs
│   ├── repository/   # Repository implementations
│   ├── mapper/       # Data mapping
│   ├── paging/       # Pagination sources
│   └── network/      # Network monitoring
```

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


## 🧪 Testing Strategy

### Unit Tests Coverage
- **Domain Layer**: Use cases and business logic
- **Data Layer**: Repository implementations and API interactions
- **Presentation Layer**: ViewModels and UI state management

### Testing Tools
- **MockK** - Kotlin-first mocking framework
- **Turbine** - Flow testing library
- **Coroutines Test** - Testing coroutines and flows

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

## 🚦 Getting Started

1. **Clone the project**
2. **Open in Android Studio**
3. **Build and run**

The app includes a demo Giphy API key. For production use, replace with your own key in `build.gradle.kts`.

## 📄 License

This project is a technical demonstration of modern Android development practices.
