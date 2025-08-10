# ValoTrack 🎯

A native Android application built in Kotlin to track player stats for Valorant.

---
## Tech Stack

This project is a native Android app and uses the following technologies:

* **Language:** Kotlin
* **Networking:** Retrofit (for making API calls)
* **JSON Parsing:** Gson
* **API:** [HenrikDev Valorant API](https://docs.henrikdev.xyz/)

---
## Setup & Installation

To get a local copy up and running on your machine, follow these steps.

1.  **Clone the repository.**
    ```sh
    git clone [https://github.com/swayamn72/ValoTrack.git](https://github.com/swayamn72/ValoTrack.git)
    ```
2.  **Open the project** in Android Studio.
3.  **Create `local.properties` file:** In the root directory of the project, create a file named `local.properties`.
4.  **Add your API Key:** Get your own API key from the [HenrikDev Discord](https://discord.gg/henrikdev) and add it to your `local.properties` file like this:
    ```properties
    API_KEY=YOUR_OWN_API_KEY_HERE
    ```
5.  **Sync Gradle** and run the app. The project is configured to read your key from this file.
