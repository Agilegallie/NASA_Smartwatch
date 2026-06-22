# Crew Command — START HERE

Your **PNG wireframes are complete** and are the main design deliverable.  
You do **not** need Android Studio or a physical watch to submit those.

---

## ✅ Easiest path (works now)

### 1. Submit the wireframe images

Folder: `wireframes/`

| File | What it shows |
|------|----------------|
| `00-all-screens.png` | All screens on one sheet |
| `00-hub-menu.png` | Master menu |
| `01-crew-timeline.png` | Timeline |
| `02-caution-warnings.png` | Cautions & warnings |
| `03-communication-status.png` | Comms |
| `04-timers.png` | Timers |

### 2. Preview the interactive prototype (Mac browser)

```bash
cd ~/crew-watch-wireframes
chmod +x open-preview.sh
./open-preview.sh
```

Or double-click: `screens/hub.html`

- **Tap** tiles or bottom icons to navigate  
- **← →** keys swipe between modules  
- **H** returns to menu  

Resize the browser window narrow (~400px) to approximate watch proportions.

---

## ⌚ Only if you still want it on the Pixel Watch

The HTML hub **cannot** run on the watch by itself. It needs the small Android wrapper in `wear/`.

### Checklist (do these in order)

1. **Close** any Android Studio project open on `crew-watch-wireframes` (parent folder).
2. **Open only:** `~/crew-watch-wireframes/wear`
3. Wait for **Gradle sync** to finish (no red errors in Build tab).
4. On watch: **Developer options → ADB debugging + Debug over Wi-Fi ON**
5. Android Studio: **Device Manager → Pair using Wi-Fi**
6. Select **Pixel Watch** in the device dropdown → **Run ▶**

### If Run is greyed out

- Top-left run config must say **app**, not "Add Configuration"
- **Build → Make Project** first
- JDK: **Settings → Build, Execution, Deployment → Build Tools → Gradle → JDK 17 or 21**

### If Gradle sync fails

Copy the **first red error line** (not the deprecation footer at the bottom).  
Common fix: **SDK Manager → install Android 14 (API 35)**.

### Build from Terminal (to see the real error)

```bash
cd ~/crew-watch-wireframes/wear
./gradlew :app:assembleDebug
```

APK output: `wear/app/build/outputs/apk/debug/app-debug.apk`

Install manually if Run fails but build succeeds:

```bash
~/Library/Android/sdk/platform-tools/adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## What went wrong before

| Mistake | Result |
|---------|--------|
| Opened parent `crew-watch-wireframes/` folder | "android is not allowed" on XML |
| Opened `hub.html` in Android Studio | File preview only — not runnable |
| Gradle deprecation footer only | Often just a warning, not the real failure |

---

## Files map

```
crew-watch-wireframes/
├── wireframes/          ← PNG deliverables (submit these)
├── screens/             ← Interactive HTML prototype
│   ├── hub.html         ← Master menu (start here in browser)
│   └── nav.js           ← Shared navigation
├── open-preview.sh      ← One-click browser preview
├── INTERACTION_NOTES.md ← UX spec for your write-up
└── wear/                ← Optional Android app (only for physical watch)
```

---

## For your assignment write-up

You can reference:

- **Hardware:** Google Pixel Watch, 384×384 round OLED  
- **Single app:** Hub menu + 4 modules via swipe  
- **Design system:** Deep Space Command (black OLED, orange/green/blue status colors)  
- **Innovation:** Circular timer ring, dual comm status rings, glow-coded alerts  

The pictures in `wireframes/` demonstrate layout, navigation, interaction, and look & feel.
