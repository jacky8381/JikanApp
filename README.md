📱 JikanApp — Anime Explorer

A modern anime discovery app built with Kotlin, Jetpack Compose, and Clean MVVM Architecture, featuring offline caching, smooth UI, and dynamic detail screens.

	
🚀 Tech Stack

Kotlin

Jetpack Compose

Clean MVVM Architecture

Coroutines + StateFlow

Retrofit

Room Database

Coil for images

Navigation Compose

🏗️ Architecture Overview

JikanApp follows Clean Architecture + MVVM, ensuring separation of concerns, testability, and long-term scalability.

presentation/   → Jetpack Compose UI + ViewModels + UI States
domain/         → UseCases + Business Models + Repository Interfaces
data/           → Retrofit API + Room Database + DTOs + Repository Impl

✔ Benefits

Highly modular

Clean, readable code

Supports offline usage

Easy to add features

Stable & lifecycle-safe

🌐 Internet Handling
🔔 No Internet Alert Dialog (First App Launch)

If the user opens the app for the very first time without an internet connection:

A No Internet Dialog is displayed

User is asked to Retry

Prevents empty screens or crashes

This ensures smooth onboarding and eliminates confusion when data cannot load.

This dialog is only shown on initial load, not on cached detail pages.

📦 Detail Page Caching (Offline Support)

✔ How it works:

When a user opens an anime detail page for the first time, data is fetched from the API.

The fetched data is then cached locally using Room Database.

On future visits to the same detail page:

Cached data loads instantly (even offline)

Meanwhile, the app silently refreshes the data from the API

Updates are saved to the database automatically

✔ Benefits:

Super-fast detail screen loading

Users can revisit viewed anime without internet

Fewer API calls → more efficient

Smooth experience for anime enthusiasts

🎨 UI Highlights

Modern dark theme

Anime posters, ratings, ranks, and genres

Smooth detail page transitions

Neon-like theme accents & rounded cards

Auto-sorted Top Anime list based on rank

Responsive layouts
