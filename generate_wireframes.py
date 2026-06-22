#!/usr/bin/env python3
"""Generate Pixel Watch 4 circular wireframe PNGs for Crew Command app."""

from PIL import Image, ImageDraw, ImageFont
import math
import os

SIZE = 384
CENTER = SIZE // 2
RADIUS = SIZE // 2 - 4
OUT = os.path.join(os.path.dirname(__file__), "wireframes")
SCREENS = os.path.join(os.path.dirname(__file__), "screens")

# Deep Space Command palette (Image 18.markdown)
BLACK = "#000000"
SURFACE = "#131313"
SURFACE_HIGH = "#2a2a2a"
SURFACE_LOW = "#1b1b1b"
ON_SURFACE = "#e2e2e2"
ON_SURFACE_VAR = "#e4beb1"
PRIMARY = "#ffb59a"
PRIMARY_CONTAINER = "#ff5c00"
SECONDARY = "#13ff43"
TERTIARY = "#4cd6ff"
ERROR_CONTAINER = "#93000a"
ON_ERROR = "#ffdad6"
OUTLINE = "#5b4137"


def load_fonts():
    paths = [
        "/System/Library/Fonts/SFNSMono.ttf",
        "/System/Library/Fonts/Supplemental/Menlo.ttc",
        "/Library/Fonts/JetBrainsMono-Regular.ttf",
        "/System/Library/Fonts/Helvetica.ttc",
        "/System/Library/Fonts/SFNS.ttf",
    ]
    mono = sans = None
    for p in paths:
        if os.path.exists(p):
            try:
                if "Mono" in p or "Menlo" in p:
                    mono = ImageFont.truetype(p, 14)
                else:
                    sans = ImageFont.truetype(p, 14)
            except OSError:
                pass
    default = ImageFont.load_default()
    if mono is None:
        mono = default
    if sans is None:
        sans = default

    def sized(size, mono_font=False):
        for p in paths:
            if os.path.exists(p):
                try:
                    if mono_font or "Mono" in p or "Menlo" in p:
                        return ImageFont.truetype(p, size)
                    return ImageFont.truetype(p, size)
                except OSError:
                    continue
        return default

    return sized


font = load_fonts()


def new_watch_canvas():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # Bezel ring
    draw.ellipse([4, 4, SIZE - 4, SIZE - 4], fill=BLACK)
    draw.ellipse([8, 8, SIZE - 8, SIZE - 8], fill=SURFACE, outline="#222222", width=2)
    return img, draw


def clip_circle(draw, img):
    mask = Image.new("L", (SIZE, SIZE), 0)
    md = ImageDraw.Draw(mask)
    md.ellipse([8, 8, SIZE - 8, SIZE - 8], fill=255)
    bg = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    bg.paste(img, mask=mask)
    return bg


def draw_met_header(draw, y=28):
    draw.text((CENTER, y), "MET 12:04:32", fill=PRIMARY, font=font(13, True), anchor="mm")


def draw_status_bar(draw):
    draw.text((36, 22), "▮▮▮", fill=PRIMARY, font=font(10, True))
    draw.text((SIZE - 36, 22), "⚡", fill=PRIMARY, font=font(12))


def draw_nav_dots(draw, active=0):
    """5 screens: Hub · Timeline · C&W · Comm · Timers"""
    icons = ["⌂", "📅", "⚠", "📡", "⏱"]
    start_x = CENTER - 72
    for i, icon in enumerate(icons):
        x = start_x + i * 36
        y = SIZE - 38
        r = 13 if i == active else 10
        fill = PRIMARY_CONTAINER if i == active else SURFACE_HIGH
        if i == 0 and i == active:
            fill = SURFACE_HIGH
        draw.ellipse([x - r, y - r, x + r, y + r], fill=fill, outline=PRIMARY if i == active else OUTLINE)
        draw.text((x, y), icon, font=font(9 if i == active else 8), anchor="mm")
    draw.text((CENTER, SIZE - 14), "← SWIPE · TAP ⌂ FOR MENU →", fill=ON_SURFACE_VAR, font=font(7, True), anchor="mm")


def draw_hub(draw):
    """Master menu — 2x2 module launcher."""
    draw_met_header(draw)
    draw_status_bar(draw)
    draw.text((CENTER, 52), "CREW COMMAND", fill=PRIMARY, font=font(11, True), anchor="mm")
    draw.text((CENTER, 66), "SELECT MODULE", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    tiles = [
        (52, 78, "📅", "TIMELINE", "EVA ACTIVE", PRIMARY, None),
        (200, 78, "⚠", "CAUTIONS", "CRITICAL", ERROR_CONTAINER, "4"),
        (52, 178, "📡", "COMMS", "VOICE OK", TERTIARY, None),
        (200, 178, "⏱", "TIMERS", "00:42:13", SECONDARY, None),
    ]
    for x, y, icon, label, status, color, badge in tiles:
        rounded_rect(draw, [x, y, x + 132, y + 88], 16, SURFACE_LOW, outline=color, width=2)
        draw.text((x + 66, y + 22), icon, font=font(22), anchor="mm")
        draw.text((x + 66, y + 48), label, fill=ON_SURFACE, font=font(9, True), anchor="mm")
        draw.text((x + 66, y + 64), status, fill=color, font=font(7, True), anchor="mm")
        if badge:
            draw.ellipse([x + 108, y + 8, x + 124, y + 24], fill=ERROR_CONTAINER)
            draw.text((x + 116, y + 16), badge, fill=ON_ERROR, font=font(8, True), anchor="mm")

    draw.text((CENTER, 278), "TAP TILE TO OPEN", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")
    draw_nav_dots(draw, active=0)


def rounded_rect(draw, xy, radius, color, outline=None, width=1):
    draw.rounded_rectangle(xy, radius=radius, fill=color, outline=outline, width=width)


def draw_timeline(draw):
    draw_met_header(draw)
    draw_status_bar(draw)
    # Date nav
    draw.text((CENTER, 52), "◀  MAY 24, 2024  ▶", fill=ON_SURFACE_VAR, font=font(10, True), anchor="mm")
    draw.text((CENTER, 66), "CREW TIMELINE", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    # Active task
    rounded_rect(draw, [32, 78, SIZE - 32, 148], 16, "#2a1a10", outline=PRIMARY, width=2)
    draw.rectangle([32, 78, 36, 148], fill=PRIMARY)
    draw.text((48, 88), "09:00 — NOW", fill=PRIMARY, font=font(9, True))
    rounded_rect(draw, [260, 84, 318, 98], 8, PRIMARY)
    draw.text((289, 91), "ACTIVE", fill=BLACK, font=font(7, True), anchor="mm")
    draw.text((48, 106), "EVA Suit Prep", fill=ON_SURFACE, font=font(13, True))
    draw.text((48, 124), "& Pre-breathe", fill=ON_SURFACE, font=font(11, True))
    rounded_rect(draw, [48, 134, 300, 140], 3, SURFACE_HIGH)
    draw.rectangle([48, 134, 220, 140], fill=PRIMARY)
    draw.text((310, 137), "65%", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    # Next tasks (arc-style compact)
    rounded_rect(draw, [40, 158, SIZE - 40, 198], 12, SURFACE_LOW, outline=OUTLINE)
    draw.text((52, 166), "11:15 — IN 42M", fill=ON_SURFACE_VAR, font=font(8, True))
    draw.text((52, 180), "BioFabrication", fill=ON_SURFACE, font=font(11, True))
    draw.text((52, 194), "LAB-1", fill=TERTIARY, font=font(8, True))

    rounded_rect(draw, [48, 208, SIZE - 48, 240], 10, SURFACE_LOW, outline=OUTLINE)
    draw.text((58, 214), "12:30  Nutrition · GALLEY", fill=ON_SURFACE_VAR, font=font(9, True))

    draw.text((CENTER, 258), "↕ CROWN SCROLL", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")
    draw_nav_dots(draw, active=1)


def draw_cautions(draw):
    draw_met_header(draw)
    draw_status_bar(draw)
    draw.text((CENTER, 52), "C&W STATUS: CRITICAL", fill=ON_ERROR, font=font(9, True), anchor="mm")
    draw.line([(CENTER - 24, 60), (CENTER + 24, 60)], fill=ERROR_CONTAINER, width=2)

    alerts = [
        (ERROR_CONTAINER, ON_ERROR, "WARNING", "12:03", "FIRE IN NODE 1", True),
        (ERROR_CONTAINER, ON_ERROR, "WARNING", "12:01", "CABIN PRESS LOW", True),
        (PRIMARY_CONTAINER, "#521800", "CAUTION", "11:58", "CO2 SCRUB A FAIL", True),
        (SURFACE_HIGH, ON_SURFACE_VAR, "CAUTION", "11:55", "COMM LINK LAG", False),
    ]
    y = 72
    for bg, fg, level, time, msg, glow in alerts:
        h = 52
        rounded_rect(draw, [28, y, SIZE - 28, y + h], 12, bg, outline=fg if glow else OUTLINE, width=2 if glow else 1)
        draw.text((40, y + 8), level, fill=fg, font=font(8, True))
        draw.text((SIZE - 40, y + 8), time, fill=fg, font=font(8, True), anchor="rm")
        draw.text((40, y + 24), msg, fill=fg, font=font(11, True))
        y += h + 6

    rounded_rect(draw, [40, y + 4, SIZE - 40, y + 36], 18, "#ffb4ab")
    draw.text((CENTER, y + 20), "ACKNOWLEDGE ALL", fill=ERROR_CONTAINER, font=font(9, True), anchor="mm")

    draw.text((CENTER, 318), "TAP CARD = DETAIL", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")
    draw_nav_dots(draw, active=2)


def draw_comm(draw):
    draw_met_header(draw)
    draw_status_bar(draw)
    draw.text((CENTER, 52), "GROUND COMMS", fill=TERTIARY, font=font(8, True), anchor="mm")

    # Vehicle orientation ring
    draw.ellipse([CENTER - 42, 62, CENTER + 42, 146], outline=OUTLINE, width=1)
    draw.ellipse([CENTER - 30, 74, CENTER + 30, 134], outline=TERTIARY, width=1)
    draw.text((CENTER, 108), "🚀", font=font(28), anchor="mm")
    draw.text((CENTER, 152), "ALIGN: NOMINAL", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    # Voice ring - active
    vx, vy = 72, 200
    draw.ellipse([vx - 28, vy - 28, vx + 28, vy + 28], outline=SURFACE_HIGH, width=3)
    draw.arc([vx - 28, vy - 28, vx + 28, vy + 28], 0, 300, fill=TERTIARY, width=4)
    draw.text((vx, vy - 6), "🎙", font=font(16), anchor="mm")
    draw.text((vx, vy + 38), "VOICE", fill=TERTIARY, font=font(8, True), anchor="mm")
    draw.text((vx, vy + 50), "82% ACTIVE", fill=TERTIARY, font=font(7, True), anchor="mm")

    # Video ring - searching
    vx2 = SIZE - 72
    draw.ellipse([vx2 - 28, vy - 28, vx2 + 28, vy + 28], outline=SURFACE_HIGH, width=3)
    draw.arc([vx2 - 28, vy - 28, vx2 + 28, vy + 28], 90, 180, fill=ON_SURFACE_VAR, width=3)
    draw.text((vx2, vy - 6), "📹", font=font(16), anchor="mm")
    draw.text((vx2, vy + 38), "VIDEO", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")
    draw.text((vx2, vy + 50), "SEARCHING", fill=ON_SURFACE_VAR, font=font(7, True), anchor="mm")

    # Telemetry chips
    rounded_rect(draw, [36, 268, 172, 298], 10, SURFACE_LOW, outline=OUTLINE)
    draw.text((104, 276), "LATENCY", fill=ON_SURFACE_VAR, font=font(7, True), anchor="mm")
    draw.text((104, 290), "240ms", fill=TERTIARY, font=font(11, True), anchor="mm")
    rounded_rect(draw, [212, 268, 348, 298], 10, SURFACE_LOW, outline=OUTLINE)
    draw.text((280, 276), "UPLINK", fill=ON_SURFACE_VAR, font=font(7, True), anchor="mm")
    draw.text((280, 290), "12.4Mb", fill=PRIMARY, font=font(11, True), anchor="mm")

    draw_nav_dots(draw, active=3)


def draw_timers(draw):
    draw_met_header(draw)
    draw_status_bar(draw)

    # Progress ring
    cx, cy, r = CENTER, 155, 88
    draw.ellipse([cx - r, cy - r, cx + r, cy + r], outline=SURFACE_HIGH, width=4)
    # Green arc ~75%
    for a in range(-90, 180, 2):
        rad = math.radians(a)
        x1 = cx + (r - 2) * math.cos(rad)
        y1 = cy + (r - 2) * math.sin(rad)
        x2 = cx + (r + 2) * math.cos(rad)
        y2 = cy + (r + 2) * math.sin(rad)
        draw.line([(x1, y1), (x2, y2)], fill=SECONDARY, width=4)

    draw.text((CENTER, 118), "EVA-1 START", fill=ON_SURFACE_VAR, font=font(9, True), anchor="mm")
    draw.text((CENTER, 158), "00:42:13", fill=SECONDARY, font=font(28, True), anchor="mm")
    draw.text((CENTER, 188), "▲ NOMINAL", fill=SECONDARY, font=font(9, True), anchor="mm")

    rounded_rect(draw, [44, 252, SIZE - 44, 286], 14, SURFACE_HIGH, outline=OUTLINE)
    draw.text((58, 260), "NEXT: SLEEP", fill=ON_SURFACE_VAR, font=font(8, True))
    draw.text((58, 274), "06:12:00", fill=ON_SURFACE, font=font(14, True))

    rounded_rect(draw, [56, 296, SIZE - 56, 326], 16, PRIMARY_CONTAINER)
    draw.text((CENTER, 311), "STOP PROCEDURE", fill="#521800", font=font(9, True), anchor="mm")

    draw_nav_dots(draw, active=4)


def draw_overview(draw):
    """Navigation & interaction overview wireframe."""
    draw.text((CENTER, 24), "CREW COMMAND", fill=PRIMARY, font=font(12, True), anchor="mm")
    draw.text((CENTER, 42), "PIXEL WATCH 4 · 384×384", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    modules = [
        ("1", "TIMELINE", "Agenda + day nav", PRIMARY),
        ("2", "C&W", "Color-coded alerts", ERROR_CONTAINER),
        ("3", "COMM", "Voice / video status", TERTIARY),
        ("4", "TIMERS", "Procedure countdown", SECONDARY),
    ]
    y = 58
    for num, name, desc, color in modules:
        rounded_rect(draw, [36, y, SIZE - 36, y + 52], 12, SURFACE_LOW, outline=color, width=2)
        draw.ellipse([48, y + 14, 72, y + 38], fill=color)
        draw.text((60, y + 26), num, fill=BLACK, font=font(11, True), anchor="mm")
        draw.text((84, y + 14), name, fill=ON_SURFACE, font=font(11, True))
        draw.text((84, y + 32), desc, fill=ON_SURFACE_VAR, font=font(8, True))
        y += 60

    draw.text((CENTER, 318), "HORIZONTAL SWIPE BETWEEN MODULES", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")
    draw.text((CENTER, 334), "CROWN = SCROLL · LONG PRESS = ACK", fill=ON_SURFACE_VAR, font=font(8, True), anchor="mm")

    # Flow arrows
    for i, x in enumerate([72, 132, 192, 252, 312]):
        if i < 4:
            draw.text((x, 298), "●", fill=PRIMARY_CONTAINER if i == 0 else SURFACE_HIGH, font=font(8))


def save_screen(name, draw_fn):
    img, draw = new_watch_canvas()
    draw_fn(draw)
    img = clip_circle(draw, img)
    path = os.path.join(OUT, f"{name}.png")
    img.convert("RGB").save(path, "PNG", optimize=True)
    print(f"Saved {path}")


def main():
    os.makedirs(OUT, exist_ok=True)
    screens = [
        ("00-hub-menu", draw_hub),
        ("01-crew-timeline", draw_timeline),
        ("02-caution-warnings", draw_cautions),
        ("03-communication-status", draw_comm),
        ("04-timers", draw_timers),
    ]
    for name, fn in screens:
        save_screen(name, fn)

    # Combined sheet: hub + 4 modules (2×3)
    sheet = Image.new("RGB", (SIZE * 3 + 40, SIZE * 2 + 60), "#0a0a0a")
    files = [f"{n}.png" for n, _ in screens]
    positions = [
        (10, 30), (SIZE + 20, 30), (SIZE * 2 + 30, 30),
        (SIZE // 2 + 15, SIZE + 40), (SIZE + SIZE // 2 + 25, SIZE + 40),
    ]
    for pos, f in zip(positions, files):
        im = Image.open(os.path.join(OUT, f)).convert("RGB")
        sheet.paste(im, pos)
    sd = ImageDraw.Draw(sheet)
    sd.text((SIZE * 1.5 + 15, 8), "CREW COMMAND — HUB + MODULES · PIXEL WATCH 4", fill="#ffb59a")
    sheet.save(os.path.join(OUT, "00-all-screens.png"), "PNG")
    print("Saved combined sheet")


if __name__ == "__main__":
    main()
