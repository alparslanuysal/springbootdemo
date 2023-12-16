#!/bin/sh

if [ "$SKIP_STARTUP" = "true" ]; then
  echo "Skipping application startup."
else
  exec java org.springframework.boot.loader.JarLauncher
fi
