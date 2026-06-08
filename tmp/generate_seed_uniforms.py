from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter, ImageFont


ROOT = Path(r"C:\dev\RE_FORM_Shop_2605")
SEED_DIR = ROOT / "uploads" / "post" / "seed"
REFERENCE_IMAGE = Path(r"C:\Users\paulkim\Desktop\삼성유니폼.jpg")

SIZE = (1200, 1200)


TEAMS = [
    {
        "file": "tottenham-hotspur.webp",
        "team": "TOTTENHAM",
        "sub": "24/25 HOME",
        "body": "#F7F8FB",
        "trim": "#0F172A",
        "accent": "#94A3B8",
    },
    {
        "file": "fc-seoul.webp",
        "team": "FC SEOUL",
        "sub": "2025 HOME",
        "body": "#101010",
        "trim": "#C1121F",
        "accent": "#E5E7EB",
    },
    {
        "file": "jeonbuk-hyundai.webp",
        "team": "JEONBUK",
        "sub": "2025 HOME",
        "body": "#0D7A43",
        "trim": "#F2F4F5",
        "accent": "#C5A15B",
    },
    {
        "file": "ulsan-hd.webp",
        "team": "ULSAN HD",
        "sub": "2025 HOME",
        "body": "#0B67C2",
        "trim": "#F8FAFC",
        "accent": "#FDBA12",
    },
    {
        "file": "lg-twins.webp",
        "team": "LG TWINS",
        "sub": "2024 HOME",
        "body": "#FFFFFF",
        "trim": "#C30452",
        "accent": "#1F2937",
    },
    {
        "file": "kia-tigers.webp",
        "team": "KIA TIGERS",
        "sub": "2024 HOME",
        "body": "#121212",
        "trim": "#E11D48",
        "accent": "#F8FAFC",
    },
    {
        "file": "samsung-lions.webp",
        "team": "SAMSUNG LIONS",
        "sub": "2024 HOME",
        "body": "#FFFFFF",
        "trim": "#2563EB",
        "accent": "#1D4ED8",
    },
    {
        "file": "hanwha-eagles.webp",
        "team": "HANWHA",
        "sub": "2024 HOME",
        "body": "#151515",
        "trim": "#F97316",
        "accent": "#F3F4F6",
    },
    {
        "file": "seoul-sk-knights.webp",
        "team": "SK KNIGHTS",
        "sub": "24/25 CHAMP",
        "body": "#B91C1C",
        "trim": "#111827",
        "accent": "#F5C542",
    },
    {
        "file": "busan-kcc-egis.webp",
        "team": "KCC EGIS",
        "sub": "24/25 HOME",
        "body": "#1E3A8A",
        "trim": "#F8FAFC",
        "accent": "#93C5FD",
    },
    {
        "file": "t1-esports.webp",
        "team": "T1",
        "sub": "2025 WORLDS",
        "body": "#111111",
        "trim": "#E11D48",
        "accent": "#E5E7EB",
    },
    {
        "file": "gen-g-esports.webp",
        "team": "GEN.G",
        "sub": "2025 PRO KIT",
        "body": "#1F2937",
        "trim": "#D4B06A",
        "accent": "#F8FAFC",
    },
]


def load_font(size: int, bold: bool = False):
    candidates = [
        r"C:\Windows\Fonts\arialbd.ttf" if bold else r"C:\Windows\Fonts\arial.ttf",
        r"C:\Windows\Fonts\malgunbd.ttf" if bold else r"C:\Windows\Fonts\malgun.ttf",
    ]
    for candidate in candidates:
        path = Path(candidate)
        if path.exists():
            return ImageFont.truetype(str(path), size=size)
    return ImageFont.load_default()


FONT_BIG = load_font(110, bold=True)
FONT_MID = load_font(48, bold=True)
FONT_SMALL = load_font(34, bold=False)


def hex_to_rgb(value: str):
    value = value.lstrip("#")
    return tuple(int(value[i:i + 2], 16) for i in (0, 2, 4))


def add_noise(image: Image.Image, opacity: int = 22):
    noise = Image.effect_noise(image.size, 12).convert("L")
    noise = noise.point(lambda p: 128 + (p - 128) * 0.55)
    alpha = Image.new("L", image.size, opacity)
    overlay = Image.merge("RGBA", (noise, noise, noise, alpha))
    image.alpha_composite(overlay)


def draw_textured_jersey(team_data: dict):
    bg = Image.new("RGBA", SIZE, "#f6f7fb")
    draw = ImageDraw.Draw(bg)

    draw.ellipse((820, 70, 1130, 380), fill="#ebeef5")
    draw.ellipse((70, 860, 390, 1180), fill="#eceff6")

    shadow = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    shadow_draw = ImageDraw.Draw(shadow)
    shadow_draw.rounded_rectangle((280, 210, 920, 1040), radius=90, fill=(0, 0, 0, 90))
    shadow = shadow.filter(ImageFilter.GaussianBlur(45))
    bg.alpha_composite(shadow)

    jersey = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    jdraw = ImageDraw.Draw(jersey)

    body_color = hex_to_rgb(team_data["body"])
    trim_color = hex_to_rgb(team_data["trim"])
    accent_color = hex_to_rgb(team_data["accent"])

    torso = [(355, 245), (500, 195), (560, 320), (640, 320), (700, 195), (845, 245), (905, 500), (815, 565), (790, 1040), (410, 1040), (385, 565), (295, 500)]
    jdraw.polygon(torso, fill=body_color)

    left_sleeve = [(295, 500), (210, 565), (185, 500), (265, 310), (355, 245), (410, 335)]
    right_sleeve = [(905, 500), (990, 565), (1015, 500), (935, 310), (845, 245), (790, 335)]
    jdraw.polygon(left_sleeve, fill=body_color)
    jdraw.polygon(right_sleeve, fill=body_color)

    collar = [(520, 195), (680, 195), (642, 290), (558, 290)]
    jdraw.polygon(collar, fill=(248, 248, 248, 255))
    jdraw.line([(520, 195), (600, 280), (680, 195)], fill=trim_color, width=10)
    jdraw.line([(536, 210), (600, 272), (664, 210)], fill=accent_color, width=5)

    if body_color[0] > 200 and body_color[1] > 200 and body_color[2] > 200:
        seam = (214, 220, 228, 255)
    else:
        seam = tuple(min(255, c + 35) for c in trim_color) + (255,)

    jdraw.line([(600, 292), (600, 1042)], fill=seam, width=3)
    for y in range(375, 970, 82):
        jdraw.ellipse((587, y, 613, y + 26), fill=(245, 245, 245, 255), outline=seam, width=2)

    for y in (558, 978):
        jdraw.line([(386, y), (814, y)], fill=trim_color, width=8)
        jdraw.line([(386, y + 16), (814, y + 16)], fill=accent_color, width=4)

    for sleeve_y in (542,):
        jdraw.line([(205, sleeve_y), (320, sleeve_y)], fill=trim_color, width=8)
        jdraw.line([(205, sleeve_y + 16), (320, sleeve_y + 16)], fill=accent_color, width=4)
        jdraw.line([(880, sleeve_y), (995, sleeve_y)], fill=trim_color, width=8)
        jdraw.line([(880, sleeve_y + 16), (995, sleeve_y + 16)], fill=accent_color, width=4)

    logo_strip = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    ldraw = ImageDraw.Draw(logo_strip)
    ldraw.rounded_rectangle((420, 430, 785, 560), radius=34, fill=(255, 255, 255, 38))
    jersey.alpha_composite(logo_strip)

    add_noise(jersey, opacity=18)

    jersey = jersey.filter(ImageFilter.GaussianBlur(0.4))
    bg.alpha_composite(jersey)

    tdraw = ImageDraw.Draw(bg)
    stroke_fill = "#0f172a" if sum(body_color) > 500 else "#f8fafc"
    chest_fill = "#0f172a" if sum(body_color) > 650 else "#ffffff"

    tdraw.text((600, 430), team_data["team"], font=FONT_BIG, anchor="mm", fill=chest_fill, stroke_width=5, stroke_fill=team_data["trim"])
    tdraw.text((600, 520), team_data["sub"], font=FONT_MID, anchor="mm", fill=stroke_fill)
    tdraw.text((472, 360), "AUTHENTIC", font=FONT_SMALL, anchor="mm", fill=team_data["trim"])

    badge_fill = team_data["trim"]
    badge_text = "#ffffff"
    tdraw.rounded_rectangle((230, 430, 315, 515), radius=24, fill=badge_fill)
    tdraw.text((272, 472), "R", font=FONT_MID, anchor="mm", fill=badge_text)
    tdraw.rounded_rectangle((885, 430, 970, 515), radius=24, fill=badge_fill)
    tdraw.text((928, 472), "25", font=FONT_SMALL, anchor="mm", fill=badge_text)

    bg = bg.filter(ImageFilter.UnsharpMask(radius=1.6, percent=130, threshold=3))
    return bg.convert("RGB")


def convert_reference_to_webp():
    if not REFERENCE_IMAGE.exists():
        raise FileNotFoundError(f"Reference image not found: {REFERENCE_IMAGE}")

    img = Image.open(REFERENCE_IMAGE).convert("RGB")
    canvas = Image.new("RGB", SIZE, "#f8f8fb")
    img.thumbnail((880, 980))
    x = (SIZE[0] - img.width) // 2
    y = (SIZE[1] - img.height) // 2 + 10
    shadow = Image.new("RGBA", SIZE, (0, 0, 0, 0))
    sdraw = ImageDraw.Draw(shadow)
    sdraw.rounded_rectangle((x + 20, y + 18, x + img.width - 20, y + img.height - 10), radius=28, fill=(0, 0, 0, 55))
    shadow = shadow.filter(ImageFilter.GaussianBlur(30))
    canvas = Image.alpha_composite(canvas.convert("RGBA"), shadow).convert("RGB")
    canvas.paste(img, (x, y))
    canvas.save(SEED_DIR / "samsung-lions.webp", format="WEBP", quality=84, method=6)


def main():
    SEED_DIR.mkdir(parents=True, exist_ok=True)
    convert_reference_to_webp()

    for team in TEAMS:
        output = SEED_DIR / team["file"]
        if team["file"] == "samsung-lions.webp":
            continue
        image = draw_textured_jersey(team)
        image.save(output, format="WEBP", quality=84, method=6)


if __name__ == "__main__":
    main()
