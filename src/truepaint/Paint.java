package truepaint;

import java.awt.Color;

import basiX.BeschriftungsFeld;
import basiX.Dialog;
import basiX.Farbe;
import basiX.Fenster;
import basiX.Hilfe;
import basiX.Knopf;
import basiX.Leinwand;
import basiX.Maus;
import basiX.Rollbalken;
import basiX.Stift;
import basiX.Tastatur;
import basiX.ZahlenFeld;

public class Paint {

	int fb = 1600, fh = 900;
	int penstate = 0;
	int linstate = 0;
	int y = 0, x = 0;
	int sd = 2;
	private boolean ende;
	
	private Fenster f, ff, ffo;
	private Maus m;
	private Stift s;
	private Knopf endknopf, farbe, farbeende, linie, radieren, farbleinwand, runter, spray, hoch, pipette, farbvorschau,
			form, hotkeys, Optionen, optionende, speichern, laden, randomfarbe, farbfüllung;
	private Leinwand lw;
	private Tastatur t;
	private Rollbalken rgbbalken1, rgbbalken2, rgbbalken3;
	private ZahlenFeld z1;
	private BeschriftungsFeld beschriftung1;
	private String ausw;
	private String ffül;
	
	public Paint() {
		f = new Fenster("TruePaint by Till W", fb, fh);
		ff = new Fenster("Farbauswahl", 1000, 500, false);
		ffo = new Fenster("Optionen", 800, 600, false);
		endknopf = new Knopf("Ende", fb - 150, 0, 150, 50, f);
		farbeende = new Knopf("Schließen", 850, 450, 150, 50, ff);
		farbe = new Knopf("Farbauswahl", 150, 0, 150, 50, f);
		radieren = new Knopf("Radieren", 300, 0, 150, 50, f);
		optionende = new Knopf("Uebernehmen", 650, 550, 150, 50, ffo);
		pipette = new Knopf("Pipette", 300, 50, 150, 50, f);
		linie = new Knopf("Linie", 450, 0, 150, 50, f);
		spray = new Knopf("Sparytool", 450, 50, 150, 50, f);
		hoch = new Knopf("", 725, 50, 25, 25, f);
		Optionen = new Knopf("Optionen", fb - 150, 50, 150, 50, f);
		farbvorschau = new Knopf("", 150, 50, 150, 50, f);
		form = new Knopf("Formen", 0, 50, 150, 50, f);
		runter = new Knopf("", 725, 75, 25, 25, f);
		lw = new Leinwand(0, 100, fb, fh - 100, f);
		speichern = new Knopf("Bild Speichern",0,50,150,50,ffo);
		laden = new Knopf("Bild Laden",0,0,150,50,ffo);
		farbleinwand = new Knopf("", 75, 75, 350, 350, ff);
		farbfüllung = new Knopf("",fb-300,50,150,50,f);
		randomfarbe = new Knopf("Zufällige Farbe", 800,175,150,150,ff);
		rgbbalken1 = new Rollbalken(false, 500, 50, 50, 400, ff);
		rgbbalken2 = new Rollbalken(false, 600, 50, 50, 400, ff);
		rgbbalken3 = new Rollbalken(false, 700, 50, 50, 400, ff);
		m = new Maus(lw);
		s = new Stift(lw);
		t = new Tastatur();
		ende = false;
		rgbbalken1.setzeWerte(0, 255, 0);
		rgbbalken2.setzeWerte(0, 255, 0);
		rgbbalken3.setzeWerte(0, 255, 0);
		beschriftung1 = new BeschriftungsFeld("Dicke des Stiftes", 600, 0, 150, 50, f);
		beschriftung1.setzeSchriftGroesse(15);
		beschriftung1.setzeSchriftFarbe(Farbe.SCHWARZ);
		beschriftung1.setzeRand(Farbe.SCHWARZ, 2);
		z1 = new ZahlenFeld(600, 50, 125, 50, f);
		z1.setzeSchriftFarbe(Farbe.SCHWARZ);
		z1.setzeSchriftGroesse(50);
		hotkeys = new Knopf("Hotkeys", 0, 0, 150, 50, f);
		z1.setzeRand(Farbe.SCHWARZ, 2);
		lw.setzeRand(Farbe.SCHWARZ, 2);
		hoch.setzeIcon("/truepaint/hoch.png");
		runter.setzeIcon("/truepaint/runter.png");
//		farbfüllung.setzeIcon("/truepaint/fafü.png");
		farbfüllung.setzeText("Füllen");
		farbvorschau.setzeBenutzbar(false);
		farbvorschau.setzeRand(Farbe.SCHWARZ, 2);
		farbleinwand.setzeBenutzbar(false);
		z1.setzeZahl(2);
		f.setzeHintergrundFarbe(Farbe.SCHWARZ);
	}
	public void mainmethod() {	
		while (!ende) {
			Hilfe.kurzePause();
			fb = f.breite();
			fh = f.hoehe();
			lw.setzeGroesse(fb, fh - 100);

			if (m.wurdeBewegt()) {
				s.bewegeBis(m.hPosition(), m.vPosition());
			}

			if (endknopf.wurdeGedrueckt()) {
				ende = true;
			}

			this.spray();
			this.pipettte();
			this.pfeile();
			this.linie();
			this.intov();
			this.formen();
			this.stift();
			this.hotkeys();
			this.optionen();
			this.farbe();
			this.keybinding();
			this.radieren();
			this.farbfüllknopfswitch();
		}
		System.exit(0);
	}

	public void keybinding() {
		if (t.wurdeGedrueckt()) {

			t.holeZeichen();

			switch ((int) t.aktuellesZeichen()) {
			case 15:
				lw.ladeBild();
				break;
			case 19:
				lw.speichere();
				break;
			case 27:
				lw.loescheAlles();
				break;
			case 43:
				z1.setzeZahl(z1.ganzZahl() + 1);
				break;
			case 45:

				z1.setzeZahl(z1.ganzZahl() - 1);
				break;
			}
		}
	}

	public void farbe() {
		if (rgbbalken1.wurdeBewegt() || rgbbalken2.wurdeBewegt() || rgbbalken3.wurdeBewegt()) {
			farbvorschau.setzeHintergrundFarbe(Farbe.rgb(rgbbalken1.wert(), rgbbalken2.wert(), rgbbalken3.wert()));
			farbleinwand.setzeHintergrundFarbe(Farbe.rgb(rgbbalken1.wert(), rgbbalken2.wert(), rgbbalken3.wert()));
			s.setzeFarbe(farbleinwand.hintergrundFarbe());
		}

		if (farbe.wurdeGedrueckt()) {
			ff.setzeSichtbar(true);
			farbeende.setzeSichtbar(true);

		}

		if (farbeende.wurdeGedrueckt()) {

			farbeende.setzeSichtbar(false);
			ff.setzeSichtbar(false);

		}
		
		if(randomfarbe.wurdeGedrueckt()) {
			rgbbalken1.setzeWert(Hilfe.zufall(0, 255));
			rgbbalken2.setzeWert(Hilfe.zufall(0, 255));
			rgbbalken3.setzeWert(Hilfe.zufall(0, 255));
		}

	}

	public void optionen() {
		if (Optionen.wurdeGedrueckt()) {
			ffo.setzeSichtbar(true);
		}

		if (optionende.wurdeGedrueckt()) {
			ffo.setzeSichtbar(false);
		}
		
		if (speichern.wurdeGedrueckt()) {
			f.speichereZeichenflaeche();
		}
		
		if(laden.wurdeGedrueckt()) {
			f.ladeBildInZeichenflaeche();
		}
		
		
		
	}

	public void hotkeys() {
		if (hotkeys.wurdeGedrueckt()) {
			Dialog.info("Hotkeys",
					" Strg+s=Speichern \n Strg+o=Laden \n Esc=alles Löschen \n \"+\"=Stiftdicke erhöhen \n \"-\"=Stiftdicke verringern ");
		}

	}

	public void stift() {
		switch (penstate) {
		case 0:
			if (m.istGedrueckt()) {
				s.runter();
			} else {
				s.hoch();
			}

			break;

		case 1:
			if (m.istGedrueckt()) {

				if (linstate == 0) {
					x = m.hPosition();
					y = m.vPosition();
					linstate = 1;
				} else {
					s.zeichneLinie(x, y);
					x = m.hPosition();
					y = m.vPosition();
				}
			}
			break;
		case 2:
			if (m.istGedrueckt()) {
				s.setzeFarbe(lw.farbeVon(m.hPosition(), m.vPosition()));
				farbvorschau.setzeHintergrundFarbe(s.farbe());
			}

			break;
		case 3:
			this.farbfüllung();
			break;
		}

		if (z1.textWurdeGeaendert()) {
			sd = z1.ganzZahl();
			s.setzeLinienBreite(sd);
		}

	}

	private void farbfüllung() {
		if(m.wurdeGeklickt()) {
			Dialog.info("WIP", "Work In Progress");
			s.hoch();
			s.bewegeAuf(m.hPosition(), m.vPosition());
			int fxo = m.hPosition();
			int fyo = m.vPosition();		//fy/fx Original
			Color testc = f.farbeVon(fxo, fyo);
			farbvorschau.setzeHintergrundFarbe(testc);
		}				
	}
				

	
	
	private void farbfüllknopfswitch() {
		if(farbfüllung.wurdeGedrueckt()) {
			if(farbfüllung.text().equals("Füllen")) {
				farbfüllung.setzeText("Stift");
				penstate=3;
			}else {
				farbfüllung.setzeText("Füllen");
				penstate=1;
			}
		}
		
	}
	
	public void spray() {
		if (spray.wurdeGedrueckt()) {
			if (spray.text().equals("Spraytool")) {
				s.wechsle();
				spray.setzeText("Stift");
			} else {
				s.normal();
				spray.setzeText("Spraytool");
			}
		}

	}

	public void pipettte() {
		if (pipette.wurdeGedrueckt()) {
			if (pipette.text().equals("Pipette")) {
				penstate = 2;
				pipette.setzeText("Stift");
			} else {
				penstate = 0;
				pipette.setzeText("Pipette");
			}
		}

	}

	public void linie() {
		if (linie.wurdeGedrueckt()) {
			if (linie.text().equals("Linie")) {
				penstate = 1;
				linie.setzeText("Normaler Stift");
			} else {
				penstate = 0;
				linstate = 0;
				linie.setzeText("Linie");
			}
		}
	}

	public void pfeile() {
		if (hoch.wurdeGedrueckt()) {
			if (z1.ganzzahl() >= 50) {
				z1.setzeZahl(50);
			} else {
				z1.setzeZahl(z1.ganzzahl() + 1);
			}
		}

		if (runter.wurdeGedrueckt()) {
			if (z1.ganzZahl() <= 0) {
				z1.setzeZahl(1);
			} else {
				z1.setzeZahl(z1.ganzzahl() - 1);
			}

		}

	}

	public void intov() {
		if (sd >= 255) {
			z1.setzeZahl(255);
			s.setzeLinienBreite(255);
			sd = 255;
		}

		if (sd <= 0) {
			z1.setzeZahl(1);
		}

	}

	public void formen() {
		if (form.wurdeGedrueckt()) {
			s.hoch();
			ausw = Dialog.auswahl("Form", "Kreis", "Dreieck", "Quadrat", "Rechteck", "mehr");
			switch (ausw) {
			case "Kreis":
				int sr = Dialog.eingabeINT("Kreis", "Welchen Radius soll der Kreis haben?");
				Dialog.info("Infoi", "Klicken sie nun wo sie den Kreis möchten");
				Hilfe.warte(250);
				while (!m.wurdeGeklickt())
					;
				s.bewegeAuf(m.hPosition(), m.vPosition());
				s.zeichneKreis(sr);
				break;
			case "Dreieck":
				s.hoch();
				Dialog.info("Info", "Klicken sie auf den ersten eckpunkt");

				while (!m.istGedrueckt()) {
					Hilfe.kurzePause();
				}
				Hilfe.warte(100);
				s.bewegeAuf(m.hPosition(), m.vPosition());
				int dx1 = m.hPosition();
				int dy1 = m.vPosition();

				Dialog.info("Info", "Klicken sie auf den zweiten eckpunkt");

				while (!m.istGedrueckt()) {
					Hilfe.kurzePause();
				}
				Hilfe.warte(100);
				s.bewegeAuf(m.hPosition(), m.vPosition());
				int dx2 = m.hPosition();
				int dy2 = m.vPosition();
				Dialog.info("Info", "Klicken sie auf den dritten eckpunkt");

				while (!m.istGedrueckt()) {
					Hilfe.kurzePause();
				}
				Hilfe.warte(100);

				s.bewegeAuf(m.hPosition(), m.vPosition());
				int dx3 = m.hPosition();
				int dy3 = m.vPosition();

				s.runter();
				s.dreieck(dx1, dy1, dx2, dy2, dx3, dy3);
				s.hoch();
				break;
			case "Quadrat":
				int qul = Dialog.eingabeINT("Eingabe", "Wie groß sollen die linien sein?");
				Dialog.info("Info", "Klicken sie wo die obere linke Ecke des Quadrats sein soll");
				while (!m.istGedrueckt()) {
					Hilfe.kurzePause();
				}
				s.hoch();
				s.bewegeAuf(m.hPosition(), m.vPosition());
				s.runter();
				s.dreheBis(0);
				s.zeichneRechteck(qul, qul);
				s.hoch();
				break;
			case "Rechteck":
				int rl = Dialog.eingabeINT("Ringabe", "geben sie die Länge des Rechtecks an");
				int rb = Dialog.eingabeINT("Ringabe", "geben sie die Breite des Rechtecks an");
				Dialog.info("Info", "Klicken sie auf die obere rechte ecke des Rechtecks");
				while (!m.istGedrueckt()) {
					Hilfe.kurzePause();
				}
				s.hoch();

				s.bewegeAuf(m.hPosition(), m.vPosition());
				s.dreheBis(0);
				s.runter();
				s.zeichneRechteck(rb, rl);
				s.hoch();
				break;
			case "mehr":
				Dialog.info("Info", "todo");
				break;
			}

		}
	}

	public void radieren() {
		if (radieren.wurdeGedrueckt()) {

			if (radieren.text().equals("Radieren")) {
				s.radiere();
				radieren.setzeText("Stift");
			} else {
				s.normal();
				radieren.setzeText("Radieren");
			}
		}
	}


}
