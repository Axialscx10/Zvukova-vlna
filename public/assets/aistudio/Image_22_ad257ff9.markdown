---
name: Nocturne Sound
colors:
  surface: '#131313'
  surface-dim: '#131313'
  surface-bright: '#393939'
  surface-container-lowest: '#0e0e0e'
  surface-container-low: '#1c1b1b'
  surface-container: '#201f1f'
  surface-container-high: '#2a2a2a'
  surface-container-highest: '#353534'
  on-surface: '#e5e2e1'
  on-surface-variant: '#eabcb5'
  inverse-surface: '#e5e2e1'
  inverse-on-surface: '#313030'
  outline: '#b08781'
  outline-variant: '#5f3e3a'
  surface-tint: '#ffb4a9'
  primary: '#ffb4a9'
  on-primary: '#690002'
  primary-container: '#ff5545'
  on-primary-container: '#5c0002'
  inverse-primary: '#c00009'
  secondary: '#d3fbff'
  on-secondary: '#00363a'
  secondary-container: '#00eefc'
  on-secondary-container: '#00686f'
  tertiary: '#ddb7ff'
  on-tertiary: '#490080'
  tertiary-container: '#b76dff'
  on-tertiary-container: '#400071'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#ffdad5'
  primary-fixed-dim: '#ffb4a9'
  on-primary-fixed: '#410001'
  on-primary-fixed-variant: '#930005'
  secondary-fixed: '#7df4ff'
  secondary-fixed-dim: '#00dbe9'
  on-secondary-fixed: '#002022'
  on-secondary-fixed-variant: '#004f54'
  tertiary-fixed: '#f0dbff'
  tertiary-fixed-dim: '#ddb7ff'
  on-tertiary-fixed: '#2c0051'
  on-tertiary-fixed-variant: '#6900b3'
  background: '#131313'
  on-background: '#e5e2e1'
  surface-variant: '#353534'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '800'
    lineHeight: 48px
    letterSpacing: -0.03em
  headline-xl-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '800'
    lineHeight: 34px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.015em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 26px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 22px
    letterSpacing: 0em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0em
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0em
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
    letterSpacing: 0.01em
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 18px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 12px
    letterSpacing: 0.05em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-sm: 0.75rem
  margin: 1rem
  margin-tablet: 1.5rem
  margin-desktop: 2.5rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system expresses the nocturnal energy of modern music discovery and playback. Designed primarily as an immersive, mobile-first audio streaming client, it combines absolute black depth with vivid acoustic accents.

### Aesthetic Paradigm
- **Midnight Immersion:** An OLED-native void baseline gives center stage to album art, kinetic visualizers, and artist photography.
- **Electric Accentuation:** Punchy scarlet primary accents combined with synthetic neon cyan and violet undertones capture late-night live stages, club corridors, and dynamic spectrum meters.
- **Frosted Translucency:** Floating action zones, persistent navigation rails, and bottom-anchored mini-players leverage heavy frosted glass physics (real-time backdrop blur) to sit cleanly atop rapidly scrolling feeds and album artwork.
- **Tactile Softness:** Fluid transitions, subtle border micro-highlights, and pill geometries ensure immediate tap precision and ergonomics for one-handed thumb interaction.

## Colors

The palette is engineered to eliminate light bleed while maximizing dynamic range for colorful musical media.

### Color Tokens & Assignments
- **Canvas / Void (`#030303`):** Absolute root canvas color. Used for global screens, modal viewports, and full-screen player backgrounds.
- **Surface Level 1 (`#121212`):** Primary card background, drawer containers, bottom sheets, and inactive track rows.
- **Surface Level 2 (`#212121`):** Active card backgrounds, hover states, input surfaces, and popover dialogs.
- **Surface Level 3 (`#2E2E2E`):** Distinct pill containers, stroke lines, and elevated control surfaces.
- **Primary Accent (`#FF1A1A` / `#FF4E45`):** The signature sonic red. Applied to primary playback triggers, scrubber playheads, live badges, and critical interactive calls to action.
- **Secondary Aura (`#00F0FF`):** Electric neon cyan. Used for audio stream bitrate metrics, hi-res lossless tags, waveform peaks, and secondary status rings.
- **Tertiary Aura (`#A855F7`):** Synth violet. Used exclusively in ambient glow gradients, playlist mood badges, and visualizer highlights.
- **Text & Iconography:**
  - `Text Primary`: `#FFFFFF` (100% white for track titles, prominent headers).
  - `Text Secondary`: `#AAAAAA` (Subdued gray for artist credits, timestamps, view counts).
  - `Text Disabled`: `#555555` (Inactive or unavailable tracks).

## Typography

Plus Jakarta Sans brings balanced geometry, punchy numerals for track timings, and legible humanist terminals at micro-sizes.

- **Headlines:** Use heavy weights (`700` and `800`) with compact letter-spacing to reproduce modern editorial billboard aesthetics seen on streaming platforms.
- **Track & Artist Metadata:** Primary track titles utilize `headline-sm` or `body-md` in semi-bold (`600`), while artist tags, album lines, and release years sit in `body-sm` with lower luminance.
- **Numeric & Scrubber Data:** Timecodes (e.g., `03:42 / 04:15`) and queue position numbers use `label-md` with tabular numerals enabled to prevent layout jitter during active scrubbing.

## Layout & Spacing

A mobile-centric 4-column layout dynamically extends into 8 columns for tablet split-screen orientations and 12 columns for expanded landscape/desktop browser views.

### Screen Adaptation Rules
- **Mobile (<600px):**
  - Edge margin: `1rem` (16px).
  - Horizontal scrolling shelves (Quick Picks, Recommended Stations) extend full-bleed with negative outer margins and an initial content indent matching the edge margin.
  - Floating bottom player dock maintains fixed offset `space-sm` (8px) above bottom navigation bar or screen bottom safe area.
- **Tablet (600px - 1024px):**
  - Edge margin: `1.5rem` (24px).
  - 2-column split allows simultaneous view of the active queue/lyrics alongside browsing shelves.
- **Desktop / Large Displays (>1024px):**
  - Maximum content constraint width: 1440px centered, with `2.5rem` margin.
  - Persistent left-side navigation rail (80px collapsed / 240px expanded).

## Elevation & Depth

Visual hierarchy does not rely on harsh drop shadows; instead, it uses tonal card steps, selective neon back-glows, and frosted glass diffusion.

### Surface Tiers
- **Tier 0 (Root Void):** `#030303`. The infinite background.
- **Tier 1 (Resting Media Surface):** `#121212`. Cards, shelves, and listing items. Subtly outlined with a 1px border of `rgba(255, 255, 255, 0.05)`.
- **Tier 2 (Popovers, Snackbars & Drawers):** `#212121`. Ambient diffusion shadow: `0 8px 32px rgba(0, 0, 0, 0.85)`. Border stroke: `rgba(255, 255, 255, 0.08)`.

### Glassmorphism & Translucent Player Docks
- **Persistent Floating Dock (Mini-Player):**
  - Background fill: `rgba(18, 18, 18, 0.72)`.
  - Backdrop filter: `blur(24px) saturate(160%)`.
  - Border: 1px continuous top and lateral stroke of `rgba(255, 255, 255, 0.1)`.
- **Contextual Neon Back-Glow:**
  - Key artwork highlights and floating play heads project a soft ambient bloom: `box-shadow: 0 12px 40px -8px rgba(255, 26, 26, 0.25)` or `box-shadow: 0 8px 30px -4px rgba(0, 240, 255, 0.20)`.

## Shapes

The geometric signature uses unified curvature: standard media cards, modals, and container segments sit at `8px` (`rounded`), while secondary groupings sit at `16px` (`rounded-lg`).

- **Pill Philosophy:** Every actionable non-card interactive control (filter tags, CTA buttons, volume thumbs, active audio badges) utilizes full pill border-radius (`9999px` or `rounded-full`).
- **Media Art:** Album covers retain a uniform `rounded` (8px) corner to respect canonical record dimensions without clipping corner details.

## Components

### Buttons
- **Primary Play Button:** Pill-shaped or circular. Filled with Crimson (`#FF1A1A`), dark icon/text (`#030303` or pure `#FFFFFF`). Features hover/active scale micro-interaction (`scale: 0.96` on touch).
- **Secondary Actions:** Semi-transparent surface pill (`rgba(255, 255, 255, 0.1)`), white label, 1px border of `rgba(255, 255, 255, 0.15)`.
- **Icon Buttons (Shuffle, Repeat, More, Heart):** Unbounded circular tap targets (minimum 44x44px for thumb accessibility). Resting state `#AAAAAA`, active/liked state illuminated in Primary Crimson or Neon Cyan with a brief ripple glow.

### Filter Chips
- **Geometry:** Pill container, height 32px, horizontal padding `space-md` (16px).
- **Resting:** Background `#212121`, border 1px solid `rgba(255, 255, 255, 0.08)`, text `#AAAAAA` (`label-md`).
- **Selected:** Background `#FFFFFF`, text `#030303` (`label-md` bold), zero border. Instantly reprioritizes the active feed.

### Lists & Track Rows
- **Dimensions:** 56px default row height.
- **Composition:**
  - Left: 40x40px rounded album thumbnail or track index numeral.
  - Center: Stacked column (Track title in `#FFFFFF`, artist name & album in `#AAAAAA`).
  - Right: Context menu (three dots) and optional explicit tag (16x16px rounded-sm gray box with white `E`).
- **Active / Playing State:** Track title shifts to `#FF4E45`; an animated 3-bar neon equalizer replaces the track number.

### Input Fields & Search Bars
- **Surface:** `#1E1E1E` background with `rounded-lg` (16px) or pill outline.
- **Leading Icon:** Search magnifier in `#AAAAAA`.
- **Text:** 14px Regular in `#FFFFFF`, placeholder in `#666666`.
- **Trailing Action:** Clear cross button or voice search microphone icon in `#AAAAAA` shifting to `#00F0FF` when active.

### Cards & Carousels
- **Music Shelf Card:** Square aspect ratio (1:1) cover thumbnail over `#121212` container.
- **Typography:** Two-line truncation for playlist/album titles, single-line for curator or artist info.
- **Quick Picks Card:** Horizontal card featuring a 48x48px thumbnail, title, artist, and trailing 32px round play button.

### Mini-Player Dock & Full Player Sheet
- **Mini-Player:** Floating glass dock pinned above bottom navigation. Contains:
  - 40x40px album preview.
  - Linear horizontal scrubbing progress line (2px height, Crimson active fill, positioned at the very top or bottom of the dock).
  - Play/Pause toggle and fast-forward track skip.
  - Upward swipe gesture initiates transition into the full-screen playback module.
- **Full-Screen Player:** Full-bleed `#030303` canvas with localized ambient artwork back-diffusion, multi-point scrubbing bar with distinct pill thumb, real-time karaoke lyrics drawer, and hi-res stream badge (`#00F0FF`).