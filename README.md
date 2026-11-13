# Fluid Wave Android

Ek basic Android app jo naya notification aane par screen par 2.6 second ke liye animated fluid-wave overlay dikhata hai. App notification ka text ya personal content read/display nahi karta.

## GitHub se APK banana (mobile par)

1. GitHub par sign in karke **New repository** banao.
2. Is ZIP ko extract karo, phir iske andar ki files/folders repository ke root mein upload karo. `app`, `.github`, `build.gradle.kts`, `settings.gradle.kts` sab root level par hone chahiye.
3. Commit changes karo.
4. Repository ke **Actions** tab mein `Build Fluid Wave APK` workflow kholo.
5. **Run workflow** dabao (ya push ke baad automatic run hone do).
6. Build complete hone par workflow run kholo, neeche **Artifacts** mein `FluidWave-debug-apk` download karo.
7. ZIP artifact extract karke `app-debug.apk` phone par install karo. Android unknown-app install permission maang sakta hai; sirf apne banaye hue APK ke liye allow karo.

## Phone par permissions

1. App kholo.
2. **Allow display over other apps** enable karo.
3. Wapas app mein aakar **Enable notification access** kholo aur Fluid Wave ko enable karo.
4. Kisi doosri app se test notification aane par animation show hogi.

## Notes

- Yeh starter project hai; har Android manufacturer ka overlay behavior alag ho sakta hai.
- Battery saver/background restrictions animation ko rok sakti hain.
- APK debug-signed hota hai, Play Store release ke liye nahi.
- Notification access aur overlay permission Android Settings mein kabhi bhi band ki ja sakti hain.
