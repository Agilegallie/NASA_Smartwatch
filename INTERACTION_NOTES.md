# Crew Command — Pixel Watch 4 Interaction Notes

Hardware reference: **Google Pixel Watch** (41mm, 384×384 round OLED, crown + side button).

Design system: **Deep Space Command** (`Image 18.markdown`) — pure black backgrounds, Safety Orange (critical/action), Mission Green (nominal), Oxygen Blue (comms/data).

---

## App Architecture

**One app, five views** — master menu hub + four modules. Shared shell:

| Zone | Content |
|------|---------|
| Top | Signal + **MET clock** + battery |
| Center | Hub grid **or** active module content |
| Bottom | **5-icon nav bar** (Menu + 4 modules) |

**Entry point:** `hub.html` — 2×2 launcher with live status on each tile.

---

## Navigation

### Master menu (`hub.html`)
- **2×2 grid** of large tiles: Timeline · Cautions · Comms · Timers
- Each tile shows **glanceable status** (e.g. alert count, active timer)
- Tap any tile → opens that module
- Badge on C&W tile shows unacknowledged alert count

### Bottom nav bar (all screens)
| Icon | Screen |
|------|--------|
| ⌂ | **Master menu** (hub) |
| 📅 | Crew Timeline |
| ⚠ | Caution & Warnings |
| 📡 | Communication Status |
| ⏱ | Timers |

Tap any icon to jump directly. Active screen is highlighted (orange fill, or peach glow on ⌂).

### Horizontal swipe
Cycle: **Hub → Timeline → C&W → Comm → Timers → Hub**

- Swipe **left** → next screen
- Swipe **right** → previous screen

### Keyboard (desktop testing)
- **← / →** — swipe between screens
- **H** or **Esc** — return to master menu

### Digital crown
- **Timeline / C&W:** scroll agenda or alert list
- **Timers:** adjust procedure duration (when paused)
- **Comm:** no scroll (single glance view)

### Bottom dots
- Tap dot to jump directly to module
- Active module = **Safety Orange** filled circle

### Side button (long press)
- **C&W screen:** Acknowledge all active alerts
- **Timers:** Quick-lap / mark checkpoint

---

## Module: Crew Timeline

**Purpose:** Agenda view of crew day; navigate past/future expedition days.

| Interaction | Feedback |
|-------------|----------|
| Tap `‹` / `›` | Day label updates; task list cross-fades |
| Crown scroll | Reveals later tasks |
| Tap task card | Expands detail (location, duration, notes) |
| Active task | Orange left border + progress bar pulse |

**Glanceable data:** Current task name, time remaining, % complete.

**Watch adaptation from phone:** Drop "CURRENT EXPEDITION" subtitle on watch; show date only. Max 2 full cards + 1 compact row visible without scroll.

---

## Module: Caution & Warnings

**Purpose:** Color-coded cautions and warnings requiring crew attention.

| Severity | Color | Glow |
|----------|-------|------|
| **Warning** | Red `#93000a` | Pulsing red outer glow |
| **Caution** | Orange `#ff5c00` | Pulsing orange glow |
| **Advisory** | Grey `#2a2a2a` | None |

| Interaction | Feedback |
|-------------|----------|
| New alert | Strong haptic + screen edge flash |
| Tap card | Full detail + procedure reference |
| **ACKNOWLEDGE ALL** | Glow stops; cards desaturate; button → "ACKNOWLEDGED" |
| Long press side btn | Same as Acknowledge All |

**Innovation:** Peripheral glow visible at wrist glance angle — critical warnings use full-width pulsing border on round display.

---

## Module: Communication Status

**Purpose:** Whether vehicle can communicate with ground via **voice** and/or **video**.

| Link | Visual | State shown |
|------|--------|-------------|
| **S-Band (Voice)** | Cyan ring arc + mic icon | ACTIVE 82% |
| **KU-Band (Video)** | Dim ring + camera-off | SEARCHING / LOS |

| Interaction | Feedback |
|-------------|----------|
| Tap voice ring | Ground station ID, signal history |
| Tap video ring | Acquisition countdown (TDRS pass) |
| Vehicle icon | Subtle drift animation = link tracking |

**Telemetry chips:** Latency (blue) + Uplink rate (orange) — always visible below rings.

---

## Module: Timers

**Purpose:** Procedure timers and countdown to next scheduled activity.

| Element | Behavior |
|---------|----------|
| **Center ring** | Depletes clockwise; color shifts green → yellow → orange at 25% / 10% |
| **Main digits** | `HH:MM:SS` for active procedure (EVA-1 START) |
| **Next chip** | Countdown to next timeline event (e.g. SLEEP 06:12:00) |
| **STOP PROCEDURE** | Toggles run/pause; green when paused |

| Interaction | Feedback |
|-------------|----------|
| Tap STOP | Haptic click; button inverts green |
| Ring hits zero | Triple haptic + full-screen flash |
| Crown (paused) | +/- 1 minute adjustment |

**Innovation:** Dual-time display — procedure ring (inner focus) + next-activity chip (context) without leaving screen.

---

## Legibility (small round screen)

- **JetBrains Mono** for all numbers and MET
- Minimum touch target: **44px** height
- **12px side margins** — content stays in center 70% of circle
- No sharp rectangles — all **pill / rounded** shapes
- Pure **#000000** OLED black for battery savings and night vision

---

## Files

| File | Description |
|------|-------------|
| `screens/hub.html` | **Master menu** — start here |
| `screens/nav.js` | Shared swipe + bottom nav |
| `wireframes/00-hub-menu.png` | Hub launcher wireframe |
| `wireframes/*.png` | Module PNG deliverables |
| `screens/index.html` | Desktop launcher page |

---

## Regenerating wireframes

```bash
cd ~/crew-watch-wireframes
python3 generate_wireframes.py
```

Open interactive HTML:

```bash
open ~/crew-watch-wireframes/screens/hub.html
# or desktop launcher:
open ~/crew-watch-wireframes/screens/index.html
```
