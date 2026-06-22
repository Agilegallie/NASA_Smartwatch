#!/bin/bash
# Copy HTML prototype into Wear app assets
set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"
DEST="$ROOT/wear/app/src/main/assets/screens"
mkdir -p "$DEST"
rsync -a --delete "$ROOT/screens/" "$DEST/" \
  --exclude '.DS_Store'
echo "Synced screens → wear/app/src/main/assets/screens"
