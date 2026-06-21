# Local AI Agent

An **offline Android assistant** that runs on your phone — no cloud API keys, no data leaving the device.

> Built as a **learning project** to understand how Android AI agents work: tool calling, multi-agent routing, on-device LLMs (Gemini Nano), accessibility automation, notification intelligence, voice commands, and modular app architecture.

**Package:** `com.localai.agent` · **Min SDK:** 26 · **Kotlin + Jetpack Compose**

---

## Download APK

Install directly on your Android phone (no Play Store needed):

| Version | Download |
|--------|----------|
| **1.0.0** | [Local-AI-Agent-1.0.0.apk](releases/Local-AI-Agent-1.0.0.apk) |

1. Download the APK on your phone
2. Allow **Install from unknown sources** if asked
3. Open the file and tap **Install**

> Debug build for easy sharing. Requires Android 8.0+ (API 26).

---

## Quick start

### Requirements

- Android Studio (Ladybug or newer)
- JDK 17+ (Android Studio bundled JBR works)
- Android device or emulator (API 26+)
- Optional: Pixel 8+ / Samsung S24+ for **Gemini Nano** (other phones use built-in local assistant)

### Clone & run

```bash
git clone https://github.com/mohitbhusal11/LocalAIAgent.git
cd LocalAIAgent
./gradlew :app:assembleDebug
```

Or download the pre-built APK from [releases/Local-AI-Agent-1.0.0.apk](releases/Local-AI-Agent-1.0.0.apk) — no build needed.

Install on a connected device:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Or open the project in **Android Studio → Run ▶**.

### Build from terminal (macOS)

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew :app:assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

Copy a version-named APK into `releases/` for GitHub:

```bash
./gradlew :app:copyApkToReleases
```

Creates: `releases/Local-AI-Agent-<version>.apk`

---

## How to use

1. Open the app → grant permissions when asked (mic, contacts, etc.)
2. Use **Chat**, **Voice**, or say simple everyday commands
3. Open **Tools** for Memory, Notifications, Focus, Productivity, Research, Automation, Suggestions
4. Enable extra powers in **Settings** (notification listener, accessibility, usage access, overlay)

**Tabs:** Chat · Voice · Tools · Settings

---

## What this app can do

Everything below works **offline**. Chat/voice understands **simple, everyday language** — no technical commands needed.

---

### Chat & AI

| Feature | Helps you… |
|--------|------------|
| Multi-turn chat | Ask questions and follow up naturally |
| Conversation list | Pick up old chats anytime |
| Search conversations | Find a past chat by title |
| Export chat | Share or save a conversation as text |
| Delete conversations | Clean up old threads |
| Gemini Nano (supported devices) | Smarter on-device AI without internet |
| Local fallback assistant | Works on any phone when Nano isn't available |
| Streaming replies | See answers appear word by word |
| Simple language replies | Non-tech-friendly answers with examples |
| Multi-step commands | *"Open camera and set alarm after 1 hour"* |
| 7 specialized agents | Routes work to the right brain automatically |

---

### Voice

| Feature | Helps you… |
|--------|------------|
| Tap-to-speak | Control phone hands-free |
| Speech-to-text commands | Same power as chat, by voice |
| Voice-first mode | Say **"Hey Assistant"** to wake (when enabled) |
| Spoken results | Hear what the assistant did |

---

### Phone & apps (26 tools)

| Feature | Helps you… |
|--------|------------|
| Open any app by name | *"Open WhatsApp"* — no hunting icons |
| 40+ known app aliases | WhatsApp, YouTube, Instagram, Gmail, Maps, Paytm, PhonePe, Spotify, Telegram, etc. |
| Open camera | Quick photo / selfie |
| Open gallery / photos | View pictures instantly |
| Open browser / URL | Jump to a website |
| Share text | Send text through any app |
| Get battery level | Know if you need to charge |
| Get current time | No need to unlock for time |
| Get today's date | Day and date at a glance |
| Open system settings | WiFi, Bluetooth, display, general settings |

---

### Calls & contacts

| Feature | Helps you… |
|--------|------------|
| Call contact by name | *"Call Rahul"* — dials from contacts |
| Call phone number | Dial any number |
| Search contacts | Find someone quickly |
| Open contacts app | Browse your address book |

---

### Messages & email

| Feature | Helps you… |
|--------|------------|
| Compose SMS | Opens message with text ready |
| Send SMS to contact | Start texting faster |
| Compose email | Opens mail with subject/body |

---

### Maps & navigation

| Feature | Helps you… |
|--------|------------|
| Nearby places search | *"Show food places near me"* |
| Directions to a place | *"Directions to New Market"* |
| Go to / navigate / route | Natural map phrases (English + Hindi hints) |
| Where is… | Search a location on map |
| Turn-by-turn navigation | Opens Google Maps navigation |

---

### Alarms, timers & reminders

| Feature | Helps you… |
|--------|------------|
| Set alarm | *"Set alarm at 7 AM"* |
| Alarm after X hours/minutes | *"Wake me after 1 hour"* |
| Set timer | *"5 minute timer"* |
| Open stopwatch / clock | Quick access to clock app |
| Create reminder | Don't forget tasks |
| Hindi-friendly phrases | *bajao*, *kaam*, everyday speech |

---

### Productivity

| Feature | Helps you… |
|--------|------------|
| Create tasks / todos | Track what you need to do |
| Task list screen | View active tasks in app |
| Calendar events | Create meetings / appointments |
| Save notes | Quick note from chat or voice |
| Productivity screen | Manage tasks in one place |

---

### Memory & notes

| Feature | Helps you… |
|--------|------------|
| Save notes offline | Store ideas locally in Room DB |
| Remember commands | Agent learns what you ask often |
| Memory screen | Add, view, delete saved notes |
| Local memory search | Research tab searches your saved data |
| Routine tracking | Detects patterns in what you do daily |

---

### Notifications

| Feature | Helps you… |
|--------|------------|
| Capture all notifications | Reads alerts from other apps (with permission) |
| Notification summary | *"Summarize my notifications"* |
| Priority detection | Highlights important alerts |
| Smart reply suggestions | Quick reply ideas for messages |
| Unread count | Know how many you missed |
| Notifications screen | Browse stored notifications in app |

---

### Focus & screen time

| Feature | Helps you… |
|--------|------------|
| App usage tracking | See time on YouTube, Instagram, TikTok, Reels |
| Daily limits | Set max minutes per app |
| 80% warning | Nudge before you waste the day |
| 100% block alert | Hard stop when limit hit |
| Focus screen | View usage report and set limits |
| Doomscroll detection | Fights endless scrolling habits |

---

### Research (offline)

| Feature | Helps you… |
|--------|------------|
| Summarize text | Shorten long paragraphs locally |
| Compare two things | *"Compare X and Y"* |
| Search local memory | Find saved notes and history |
| Generate reports | Offline research on a topic |
| Research screen | UI for summarize + local search |

---

### Accessibility automation

| Feature | Helps you… |
|--------|------------|
| Scroll down / up | Hands-free scrolling in apps |
| Tap / click by text | *"Click Send"* |
| Read screen | Hear what's visible on screen |
| Back / Home buttons | Navigate without touching |
| Foreground app info | Know which app is open |
| WhatsApp, Instagram, YouTube, Chrome | Automation-aware apps |

---

### Smart suggestions

| Feature | Helps you… |
|--------|------------|
| Routine suggestions | *"You usually do X at this time"* |
| Focus warnings | Alerts when screen time is high |
| Morning reminders | Check tasks and schedule |
| Suggestions screen | Proactive tips in one place |
| Background worker | Suggestions update automatically |

---

### Quick access & shortcuts

| Feature | Helps you… |
|--------|------------|
| Home screen widget | Open assistant from home |
| Quick Settings tile | One-tap from notification shade |
| Floating bubble | Overlay button on any screen |
| Persistent notification | Always one tap away |
| App shortcuts | Voice, Chat, Notes from launcher long-press |
| Shake to open | Shake phone to launch |
| Double volume press | Hardware shortcut |
| Bluetooth trigger | Open when BT device connects |
| Headset / lock screen hooks | Open from more entry points |
| Boot receiver | Restore services after reboot |

---

## Example commands

Say or type these naturally:

```
Call Mom
Open WhatsApp
Set alarm after 1 hour
What time is it?
How much battery is left?
Show restaurants near me
Directions to Connaught Place
Summarize my notifications
Create task buy milk
Remember my wifi password is abc123
Scroll down
Compare iPhone and Samsung
Open camera and set timer for 5 minutes
```

---

## Permissions (enable for full power)

| Permission | Why |
|-----------|-----|
| Microphone | Voice commands |
| Contacts | Call / search by name |
| Notification listener | Read & summarize notifications |
| Accessibility | Scroll, tap, read screen |
| Usage access | Screen time & focus mode |
| Display over other apps | Floating bubble |
| Alarms | Set alarms via clock app |

Grant these from **Settings** tab or Android system settings when prompted.

---

## Architecture (learning focus)

Modular **Clean Architecture + MVVM + Hilt + Room + Compose** — 21 Gradle modules:

```
app
├── core, core-ui, core-network, core-ai, core-database
├── core-agent, core-memory, core-voice, core-permissions
└── feature-chat, feature-voice, feature-memory, feature-notifications
    feature-automation, feature-accessibility, feature-research
    feature-settings, feature-shortcuts, feature-productivity, feature-doomscroll
```

**7 agents:** Planner · Phone · Memory · Research · Notification · Automation · Productivity

**AI stack:** Gemini Nano (ML Kit) → LocalAssistantEngine fallback → 26 phone tools

See [ARCHITECTURE.md](ARCHITECTURE.md) for phase breakdown.

---

## Tech stack

| Layer | Tools |
|-------|-------|
| Language | Kotlin 2.2 |
| UI | Jetpack Compose, Material 3 |
| DI | Hilt |
| DB | Room |
| Async | Coroutines, Flow |
| Navigation | Navigation Compose |
| Background | WorkManager |
| On-device AI | ML Kit Gemini Nano |
| Widget | Glance |

---

## Disclaimer

Personal learning project. Not a replacement for Google Assistant. Some features need manufacturer-specific AI support (Gemini Nano). Automation and notification access require explicit user consent.

---

## License

Add your license here if you publish the repo.
