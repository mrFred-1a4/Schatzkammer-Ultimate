import greenfoot. *;
import java.util.Random;
public class Schatzkammer extends MyWorld {
    int iBreite;
    int iHoehe;
    int Wandgroesse;
    int Spielergroesse;    
    int Schatzanzahl;    
    int Spielerzahl;
    private GreenfootSound music = new GreenfootSound("Musik.mp3");
    public Schatzkammer(int Spieler, int ipBreite, int ipHoehe) {
        super(ipBreite, ipHoehe, 1);
        iBreite = ipBreite;
        iHoehe = ipHoehe;
        Spielerzahl = Spieler;
        prepare();
    }

    public void prepare() {
        setVariables();
        setBackround();
        setzeAusenwaende();
        setzeSchaetze();
        setzeHindernisse();
        setzeSpieler(Spielerzahl);
        music.setVolume(5);
        music.playLoop();
    }
    
    public void setVariables(){
        Wandgroesse = iHoehe / 36;
        Spielergroesse = iHoehe / 36;
        Schatzanzahl = iBreite  /  40;
    }
    
    public void setBackround(){
        GreenfootImage bg = new GreenfootImage("Sandboden.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
    }

    public void act() {
        spielEndet();
        schatzkisteAktualisieren();
    }

    public void setzeAusenwaende() {
        /*Oben*/
        for (int i = Wandgroesse  /  2  +  Wandgroesse ; i <= iBreite  -  Wandgroesse ; i = i  +  Wandgroesse){           
            addObject(new Wand(Wandgroesse), i, Wandgroesse  /  2);
        }
        /*Unten*/
        for (int i = Wandgroesse  /  2  +  Wandgroesse ; i <= iBreite  -  Wandgroesse;i = i  +  Wandgroesse){
            addObject(new Wand(Wandgroesse), i, iHoehe  -  Wandgroesse  /  2 );
        }
        /*Links*/
        for (int i = Wandgroesse  /  2 ; i  <= iHoehe ; i = i  +  Wandgroesse){
            addObject(new Wand(Wandgroesse), Wandgroesse  /  2, i);
        }
        /*Rechts*/
        for (int i = Wandgroesse / 2 ; i <= iHoehe ; i = i  +  Wandgroesse){
            addObject(new Wand(Wandgroesse), iBreite  -  Wandgroesse  /  2, i);
        }
        
    }

    /*Setzt die sch�tze zwischen den ausenw�nden an zuff�llige stelen*/
    public void setzeSchaetze() {
        Random random = new Random();
        for(int i = 0;i < (Schatzanzahl);i++)
            addObject(new Schatz(Wandgroesse), random.nextInt((iBreite  -  2  *  Wandgroesse) / Wandgroesse ) * Wandgroesse + Wandgroesse + Wandgroesse / 2, random.nextInt((iHoehe  -  2  *  Wandgroesse) / Wandgroesse) * Wandgroesse + Wandgroesse + Wandgroesse / 2);
    }

    /*Setzt die Hindernisse zwischen die ausenw�nde an zuf�lligenn stellen*/
    public void setzeHindernisse() {
        Random random = new Random();
        for ( int i = 0;
        i < (iBreite  /  20  *  1.5);
        i++)            
            addObject(new Wand(Wandgroesse), random.nextInt((iBreite  -  2  *  Wandgroesse) / Wandgroesse ) * Wandgroesse + Wandgroesse + Wandgroesse / 2, random.nextInt((iHoehe  -  2  *  Wandgroesse) / Wandgroesse) * Wandgroesse + Wandgroesse + Wandgroesse / 2);
    }

    /*Erstellung der Spieler 1 - 4*/
    public void setzeSpieler(int Tag) {
        if (Tag == 1)  {
            addObject(new Abenteurer("w","a","s","d"), Spielergroesse / 2 + Wandgroesse - 1, iHoehe - Spielergroesse / 2  -  Wandgroesse  +  1);
            addObject(new Waechter("up","down","left","right"), iBreite  -  Spielergroesse / 2 - Wandgroesse + 1, Spielergroesse / 2  +  Wandgroesse  -  1 );
        } else if (Tag == 2)  {
            addObject(new Waechter("up","down","left","right"), iBreite  -  Spielergroesse / 2 - Wandgroesse + 1, Spielergroesse / 2  +  Wandgroesse  -  1 );
            addObject(new Abenteurer("w","a","s","d"), Spielergroesse / 2 + Wandgroesse - 1, iHoehe - Spielergroesse / 2  -  Wandgroesse  +  1);
        } else if (Tag == 3)  {
            addObject(new Abenteurer("w","a","s","d"), Spielergroesse / 2 + Wandgroesse - 1, iHoehe - Spielergroesse / 2  -  Wandgroesse  +  1);
            addObject(new Waechter("up","down","left","right"), iBreite  -  Spielergroesse / 2 - Wandgroesse + 1, Spielergroesse / 2  +  Wandgroesse  -  1 );
            addObject(new Abenteurer("8","5","4","6"), Spielergroesse / 2 + Wandgroesse - 1 , Spielergroesse / 2  +  Wandgroesse  -  1);
        } else if (Tag == 4)  {
            addObject(new Abenteurer("w","a","s","d"), Spielergroesse / 2 + Wandgroesse - 1, iHoehe - Spielergroesse / 2  -  Wandgroesse  +  1);
            addObject(new Waechter("up","down","left","right"), iBreite  -  Spielergroesse / 2 - Wandgroesse + 1, Spielergroesse / 2  +  Wandgroesse  -  1 );
            addObject(new Abenteurer("8","5","4","6"), Spielergroesse / 2 + Wandgroesse - 1 , Spielergroesse / 2  +  Wandgroesse  -  1);
            addObject(new Waechter("i","k","j","l"), iBreite  -  Spielergroesse / 2 - Wandgroesse + 1 , iHoehe - Spielergroesse / 2  -  Wandgroesse  +  1);
        }

    }

    /*verschiedene m�glichkeiten wie das spiel beendet wird*/
    public void spielEndet() {
        if (Greenfoot.isKeyDown("escape"))  {
            Greenfoot.setWorld(new Startscreen(iBreite, iHoehe, Spielerzahl));
            music.stop();
        }

        if (count(Schatz.class) <= 2)  {
            addObject(new AbenteurerWin(), iBreite / 2 , iHoehe / 2);
            Greenfoot.delay(80);
            music.stop();
            Greenfoot.setWorld(new Startscreen(iBreite, iHoehe, Spielerzahl));
        }

        if (count(Abenteurer.class) == 0)  {
            addObject(new WaechterWin(), iBreite / 2 , iHoehe / 2 );
            Greenfoot.delay(80);
            music.stop();
            Greenfoot.setWorld(new Startscreen(iBreite, iHoehe, Spielerzahl));
        }

    }

    public void schatzkisteAktualisieren() {
        int Sch�tze = count(Schatz.class);
        if (Sch�tze <= Schatzanzahl && count(Schatzkiste.class) < 1)  {
            addObject(new Schatzkiste(Wandgroesse), Wandgroesse / 2, Wandgroesse / 2);
        }

        if (Sch�tze < Schatzanzahl  /  2&& count(Schatzkistemittel.class) < 1)  {
            addObject(new Schatzkistemittel(Wandgroesse), Wandgroesse / 2, Wandgroesse / 2);
        }

        if (Sch�tze < 4 && count(Schatzkistevoll.class) < 1)  {
            addObject(new Schatzkistevoll(Wandgroesse), Wandgroesse / 2, Wandgroesse / 2);
        }

    }

}