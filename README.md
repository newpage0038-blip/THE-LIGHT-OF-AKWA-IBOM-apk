# THE LIGHT OF AKWA IBOM 🌟
> **Slogan:** *BRIGHTER WE SHINE!*

A modern, responsive, 100% offline-first Android application designed to unite the people of Akwa Ibom State, Nigeria, through unity, culture, community engagement, education, empowerment, and economic opportunities.

---

## 📖 Vision & Purpose
> *"We are a community united by hope, driven by purpose, and committed to making our state and our people shine brighter. Together, we celebrate our identity, share opportunities, support our talents, and build a brighter future for Akwa Ibom."*

### The Community Greeting
* **Leader:** `"THE LIGHT!"`
* **Members:** `"BRIGHTER WE SHINE!"`

This greeting represents collective hope, solidarity, and mutual commitment to lifting each other up across all 31 Local Government Areas.

---

## 📱 Core Features & Screens

1. **Welcoming Dashboard (Home):**
   - Community announcements and urgent state bulletins.
   - Quick navigation shortcuts to core portals.
   - Upcoming events carousel and community highlights.
   - Interactive call-and-response slogan chant.

2. **Culture & Arts:**
   - Traditional delicacies showcase with authentic photography and preparation recipes for **Ekpang Nkukwo** and **Afang Soup**.
   - Essential **Ibibio & Annang everyday language expressions** with phonetic pronunciation guides.
   - Historical celebrations: Orange September, Ekpe cultural festival, and Ekombi dance.

3. **Community Hub:**
   - Interactive discussions, idea sharing, and celebration of member achievements.
   - Live community polls with realtime percentage bars.
   - Post comments and respectful moderation with reporting mechanisms.

4. **Talents & Opportunities:**
   - Directory of scholarships, youth solar & digital fellowships, agricultural grants, and creative auditions.
   - Category filtering, search, bookmarking, and offline application guides.

5. **Business Directory & Promotion:**
   - Showcase of local businesses, artisans (Ikot Ekpene raffia crafts), food caterers, and software labs.
   - In-app **Register Your Business** feature.

6. **Events Calendar:**
   - Schedule of community assemblies, youth conferences, and cultural festivals.
   - 1-tap RSVP / "Add to My Schedule".

7. **Digital Membership Card:**
   - Official membership badge with verifiable QR simulation.
   - Member ID number (`AKW-2026-XXXX`).
   - Profile editor supporting all **31 Local Government Areas** of Akwa Ibom State.

8. **About Us:**
   - Vision, mission, collective leadership philosophy, and the **7 Core Community Values** (Unity, Respect, Peaceful conversations, Pride in identity, Equal opportunities, Transparency, Cooperation).

9. **Contact & Community Support:**
   - Official help desk channels, secretariat contact details in Uyo, and FAQs.
   - Suggestion and content reporting form.

10. **Administrator Console:**
    - Secured behind PIN authentication (`2026`).
    - Flagged post moderation queue (Approve / Delete).
    - Announcement publishing and event management.

---

## 🛠️ Technology Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material Design 3)
* **Local Persistence:** Room Database (SQLite) — 100% data-conscious and fully offline
* **Architecture:** MVVM (Model-View-ViewModel) with Kotlin Coroutines and StateFlow
* **Target OS:** Android 7.0 (API level 24) to Android 15+ (API level 36)

---

## 📦 APK Download & Installation

The pre-built debug APK is generated and ready to install:
* **File Location:** `app/build/outputs/apk/debug/app-debug.apk` (or `public/LightOfAkwaIbom-debug.apk`)
* **Size:** ~24 MB

### Installation Steps on Android:
1. Download `app-debug.apk` to your phone.
2. Tap the downloaded `.apk` in your notification bar or Files app.
3. If prompted to allow installation from unknown sources, toggle **Allow from this source** to ON.
4. Tap **Install** and enjoy!

---

## 💻 Building from Source

To compile and assemble the debug APK locally:

```bash
# Clone the repository
git clone https://github.com/newpage0038-blip/THE-LIGHT-OF-AKWA-IBOM-apk.git
cd THE-LIGHT-OF-AKWA-IBOM-apk

# Assemble the debug APK
gradle :app:assembleDebug
```

The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`.
