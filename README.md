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

#Images 
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/c01e4bee-0cc8-4a2d-8d54-865c8203c057" width="30%" />
  <img src="https://github.com/user-attachments/assets/c5956628-25e1-4959-9805-3569c43f27a5" width="30%" />
  <img src="https://github.com/user-attachments/assets/84e52e39-0f35-483f-920f-73a5a6b787fb" width="30%" />
</div>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/e4155b8f-9928-4516-8902-b9c42405a853" width="30%" />
  <img src="https://github.com/user-attachments/assets/460a98a4-d282-4d36-9834-0f7eb68d6471" width="30%" />
</div>

#App demonstration link 
Google Drive :  https://drive.google.com/file/d/1lTR4NBLunocNtAaq2WTCReTrfr6kGzB7/view?usp=sharing
