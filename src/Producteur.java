import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe utilisée pour produire des examples
 */
public class Producteur {

    public static void main(String args[]){
        File f=new File("./tst3.txt");
        try{
            FileWriter fw=new FileWriter(f);
            for(int i=1;i<=20;i++){
                int x=(int)(Math.random()*10+1);
                fw.write(i+" "+x+"\n");
            }
            fw.close();
        }
        catch (IOException e){}

    }

}
