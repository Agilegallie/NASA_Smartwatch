# Crew Command — Wireframes + Wear deploy

## ⚠️ Android Studio: open the correct folder

If you see errors like **"android is not allowed"** or **"namespace android is not bound"** on XML files, you opened the **wrong folder**.

| Folder | Use for |
|--------|---------|
| `crew-watch-wireframes/` (this folder) | HTML wireframes only — **not** an Android project |
| `crew-watch-wireframes/wear/` | **Open THIS in Android Studio** to run on Pixel Watch |

### Fix in Android Studio

1. **File → Close Project**
2. **File → Open** → select `wear` (not the parent folder)
3. Wait for Gradle sync
4. Run ▶ on your Pixel Watch

See [DEPLOY_TO_WATCH.md](DEPLOY_TO_WATCH.md) for full steps.

## Quick links

- Interactive HTML: `screens/hub.html`
- PNG wireframes: `wireframes/`
- Sync HTML into app: `./sync-assets.sh`
