import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe utilisée pour produire des exemples
 */
public class Producteur {

    public static void main(String args[]){
        File f=new File("./tst46.txt"); //Changer le numéro de texte
        try{
            FileWriter fw=new FileWriter(f);
            for(int i=1;i<=46;i++){                     //Choisir le nombre de points
                int x=(int)(Math.random()*23+1);        //Choisir l'étendu des abscisses
                fw.write(i+" "+x+"\n");
            }
            fw.close();
        }
        catch (IOException e){}

    }

}
