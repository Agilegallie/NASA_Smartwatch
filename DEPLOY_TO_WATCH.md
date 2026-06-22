# Run Crew Command Hub on Your Pixel Watch

Your `hub.html` is a **web prototype**. Android Studio only installs **Android/Wear OS apps** on the watch — not raw HTML files.

You have two practical options:

---

## Option A — Quick preview (Mac, ~30 seconds)

Good for layout/navigation testing, not on the physical watch:

```bash
open ~/crew-watch-wireframes/screens/hub.html
```

Use **← →** arrow keys to swipe between screens, **H** for menu.

In Chrome DevTools (F12) → toggle device toolbar → set size to **384×384** for Pixel Watch proportions.

---

## Option B — Run on your physical Pixel Watch (recommended)

Use the small **Wear OS wrapper app** in this folder: `wear/`

### 1. Prepare your watch

1. On the watch: **Settings → System → About →** tap **Build number** 7 times  
2. **Settings → Developer options →** enable:
   - **ADB debugging**
   - **Debug over Wi-Fi** (easiest)  
3. Note the **IP address + pairing code** shown under Debug over Wi-Fi

### 2. Connect watch to Android Studio

**In Android Studio:**

1. **View → Tool Windows → Device Manager**
2. Click **Pair using Wi-Fi** (or the `+` → Pair Wear OS)
3. Enter the pairing code from your watch

**Or in Terminal:**

```bash
adb pair WATCH_IP:PAIRING_PORT    # enter code when prompted
adb connect WATCH_IP:DEBUG_PORT
adb devices                       # should list your watch
```

> If Wi-Fi pairing fails, connect the watch through your **phone** with USB debugging enabled, or use **Wireless debugging** from the phone’s Developer options.

### 3. Open the Wear project

1. Android Studio → **File → Open**
2. Select: `~/crew-watch-wireframes/wear`
3. Wait for Gradle sync to finish

### 4. Run on the watch

1. In the device dropdown (top toolbar), select your **Pixel Watch**
2. Click the green **Run ▶** button
3. The app **Crew Command** installs and opens `hub.html` in a full-screen WebView

### 5. Navigate inside the app

- Tap the **2×2 tiles** on the hub
- Use the **bottom nav bar** (⌂ 📅 ⚠ 📡 ⏱)
- Swipe left/right between screens (on watch touchscreen)

---

## Troubleshooting

| Problem | Fix |
|--------|-----|
| **"android is not allowed"** on XML / red `AndroidManifest.xml` | You opened the **parent** folder. Close project → Open **`wear/`** only |
| **"Namespace prefix android is not bound"** | Same fix — parent folder is a plain Java module, not Android |
| Watch not in device list | Re-run `adb connect`; ensure same Wi-Fi as Mac |
| Blank white screen | Watch needs Wi-Fi for Google Fonts / icons; or wait a few seconds |
| Gradle sync fails | **File → Settings → Build Tools → Gradle** → use JDK 17 |
| “Run” is greyed out | Open the `wear` folder as project root, not `screens/` |
| Opened `hub.html` only | That’s a file preview — use the `wear/` project to deploy |

---

## What gets installed?

The Wear app bundles all files from `screens/` (`hub.html`, `nav.js`, `watch.css`, module pages) and loads:

```
file:///android_asset/screens/hub.html
```

No Play Store needed — it’s a debug build for design review.

---

## Updating the HTML after changes

Re-copy assets (or run the sync script), then **Run ▶** again:

```bash
cd ~/crew-watch-wireframes
./sync-assets.sh
```

Then in Android Studio: **Run ▶** to reinstall.
