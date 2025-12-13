# ea-gui

easistent odjemalec za Android

> [!WARNING]
> Zaradi ostalih projektov in nenehnih sprememb v na strani easistenta opuščam razvoj te aplikacije.

## The what
Delna adaptacija [ea-cli](https://github.com/timrosu/ea-cli) programa za Android 12+ (zaradi hard codanih Material You barv).

## The why
Za zaključno maturitetno nalogo za predmet računalništvo. Več o tem v mapi [_matura](/assets/matura).

## Funkcije
- prijava (s prikazom prejetih sporočil o napaki)
- prikaz:
	- ocen
	- ocenjevanj
	- izostankov
- izpis nekaj informacij o profilu
- slovenski in angleški prevod
- Material You dinamične barve

> [!NOTE]
> Zaradi nepremišljene implementacije Material You aplikacija deluje le na Android 12 in višje.

## Posnetki zaslona

| Ocene                                                  | Ocenjevanja                                               | Izostanki                                                  | Profil                                                                  | Prijava                                                |
|--------------------------------------------------------|-----------------------------------------------------------|------------------------------------------------------------|-------------------------------------------------------------------------|--------------------------------------------------------|
| ![Zavihek z ocenami](/assets/images/ea-gui_grades.png) | ![Zavihek z ocenjevanji](/assets/images/ea-gui_exams.png) | ![Zavihek z izostanki](/assets/images/ea-gui_absences.png) | ![Zavihek z informacijami o profilu](/assets/images/ea-gui_profile.png) | ![Prijavna aktivnost](/assets/images/ea-gui_login.png) |

> [!TIP]
> Trenutno ne deluje avtentikacija, saj (kolikor se spomnim) potrebuje namizna različica poslati 2 piškotka. To bi lahko rešili z migracijo na mobilni endpoint, ki tega ne zahteva.
