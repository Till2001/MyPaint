package truepaint;

import java.awt.Color;

import basiX.*;
public class Paint {

	int fb = 1400, fh = 750;
	int penstate = 0;
	int linstate = 0;
	int y = 0, x = 0;
	int sd = 2;
	int lin2fx = 0;
	int lin2fy = 0;
	int lin2fx2 = 0;
	int lin2fy2 = 0;
	int fuex = 0;
	int fuey = 0;
	int füy1 = 0;
	int füx2 = 0;
	private boolean ende;
	private boolean lin2w;
	
	private Fenster f, ff, ffo;
	private Maus m;
	private Stift s;
	private Knopf endknopf, farbe, farbeende, linie, radieren, farbleinwand, runter, spray, hoch, pipette, farbvorschau,
			form, hotkeys, Optionen, optionende, speichern, laden, randomfarbe, farbfüllung, lin2;
	private Leinwand lw;
	private Tastatur t;
	private Rollbalken rgbbalken1, rgbbalken2, rgbbalken3;
	private ZahlenFeld z1;
	private BeschriftungsFeld beschriftung1;
	private ListAuswahl schriftwahl;
	private String ausw;
	private String test;
	
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
		schriftwahl = new ListAuswahl(500,0,200,25,ffo);
		lin2 = new Knopf("Lin2",600,0,150,50,f);
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
		lin2w = false;
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
		farbfüllung.setzeText("Füllen");
		farbvorschau.setzeBenutzbar(false);
		farbvorschau.setzeRand(Farbe.SCHWARZ, 2);
		farbleinwand.setzeBenutzbar(false);
		z1.setzeZahl(2);
		f.setzeHintergrundFarbe(Farbe.SCHWARZ);
		schriftwahl.fuegeAn("Comic Sans");
		schriftwahl.fuegeAn("new times Roman");
		schriftwahl.fuegeAn("Ittalic");
		test = Schrift.STANDARDSCHRIFTART;
	}
	public void mainmethod() {	
		while (!ende) {
//			Hilfe.kurzePause();
			fb = f.breite();
			fh = f.hoehe();
			lw.setzeGroesse(fb, fh - 100);
			if (m.wurdeBewegt()) {
				s.bewegeBis(m.hPosition(), m.vPosition());
			}

			if (endknopf.wurdeGedrueckt()) {
				ende = true;
			}

			this.funktionen();
		}
		System.exit(0);
	}

	private void funktionen() {
		this.spray();
		this.pipettte();
		this.pfeile();
		this.linie();
		this.intov();
		this.formen();
		this.stift();
		this.optionen();
		this.farbe();
		this.keybinding();
		this.radieren();
		this.farbfüllknopfswitch();
	}
	
	public void keybinding() {
		
		if (hotkeys.wurdeGedrueckt()) {
			Dialog.info("Hotkeys"," Strg+s=Speichern \n Strg+o=Laden \n Esc=alles Löschen \n \"+\"=Stiftdicke erhöhen \n \"-\"=Stiftdicke verringern ");
		}
		
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
		
		if(schriftwahl.wurdeGewaehlt()) {
			schriftwahl.setzeGroesse(200, 100);
			if(schriftwahl.gewaehlterText().equals(test)){
				schriftwahl.setzeGroesse(200, 25);
			}else {
				schriftwahl.setzeSchriftArt(schriftwahl.gewaehlterText());
				test = schriftwahl.gewaehlterText();
				schriftwahl.setzeGroesse(200, 25);
			}
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
			if(m.istGedrueckt()) {
				boolean fuew = true;
				int i = 0;
				s.hoch();
				s.bewegeAuf(m.hPosition(), m.vPosition());
				fuey = m.vPosition();
				fuex = m.hPosition();
				while (fuew) {
//					if(!lw.farbeVon(fuex,fuey).equals(s.farbe())) {
						if(!lw.farbeVon(fuex, fuey).equals(Color.BLACK)) {
							//lw.setzeFarbeBei(fuex+i, fuey, s.farbe());
							lw.setzeFarbeBei(fuex, fuey, Color.BLACK);
							fuex++;
							lw.setzeGroesse(fb+1, fh-100);
							lw.setzeGroesse(fb, fh-100);
							
							//System.out.println(String.valueOf(i));
						}else {
							fuew = false;
						}
				
					}
//				}
			}
			
			break;
		case 4:
			this.lin2f();
			break;
		}

		if (z1.textWurdeGeaendert()) {
			sd = z1.ganzZahl();
			s.setzeLinienBreite(sd);
		}

	}

	public void lin2f() {
		
		if(m.istGedrueckt()) {
			if(lin2w==false) {
			lin2fx =m.hPosition();
			lin2fy =m.vPosition();	
			lin2w = true;
			}else {
				s.linie(lin2fx, lin2fy, lin2fx2, lin2fy2);
				lin2w = false;
				
			}
			}
		
		if(lin2w==true) {
			s.linie(lin2fx, lin2fy, m.hPosition(), m.vPosition());
			lin2fx2=m.hPosition();
			lin2fy2=m.vPosition();
			s.radiere();
			s.linie(lin2fx, lin2fy, lin2fx2, lin2fy2);
			s.normal();
		}
	}
	
				
	public void farbfüllknopfswitch() {
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
		
		if(lin2.wurdeGedrueckt()) {
			if(lin2.text().equals("Lin2")) {
				penstate = 4;
				lin2.setzeText("Normaler Stift");
			}else {
				penstate = 0;
				lin2.setzeText("Lin2");
			}
		}
		
		
		
	}

	public void pfeile() {
		if (hoch.wurdeGedrueckt()) {
				z1.setzeZahl(z1.ganzzahl() + 1);
		}

		if (runter.wurdeGedrueckt()) {
				z1.setzeZahl(z1.ganzzahl() - 1);
		}
	}

	public void intov() {
		if (z1.ganzzahl() >= 255) {
			z1.setzeZahl(255);
		}

		if (z1.ganzzahl() <= 0) {
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
				Dialog.info("Info", "Klicken sie auf die obere linke ecke des Rechtecks");
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
