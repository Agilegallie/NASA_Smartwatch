#!/bin/bash
# Open the interactive watch prototype in your default browser (no Android Studio needed).
ROOT="$(cd "$(dirname "$0")" && pwd)"
echo "Opening Crew Command hub in browser..."
echo "  ← → arrow keys = swipe between screens"
echo "  H or Esc       = back to menu"
open "$ROOT/screens/hub.html"
