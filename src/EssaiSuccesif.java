import java.util.Collections;
import java.util.Set;

public class EssaiSuccesif {
    private int n;              //Nombre de ligne brisé
    private double coutopt;     //Cout optimal
    private int X[];            //Solution courante
    private Point tab[];        //Liste des points
    private double cout;        //Cout courant
    private int ipred;          //i précédent
    private int Y[];            //Solution optimal

    public EssaiSuccesif(int n){
        this.n=n;
        coutopt=999999999.;
        int j=0;
        tab=new Point[n];
        Set<Point> points=Parser.recuperePoints();
        for(Point p: points){
            tab[j]=p;
            j++;
        }
        int i;
        Point cle;
        for (i = 1; i < tab.length; i++) {
            cle = tab[i];
            j = i;
            while ((j >= 1) && (tab[j - 1].getx() > cle.getx())) {
                tab[j]  = tab[j - 1] ;
                j = j - 1;
            }
            tab[j] = cle;
        }
        cout=0;
        ipred=1;
        X=new int[n];
        Y=new int[n];

    }

    public double distance(int i,int j){
        double distance=0;
        if(i<j){
            Point a=tab[i-1];
            Point b=tab[j-1];
            Point v=new Point(b.getx()-a.getx(),b.gety()-a.gety());
            for (int k=(i-1);k<=(j-1);k++){
                System.out.println("Point RANDOM=("+tab[k].getx()+","+tab[k].gety()+")");
                System.out.println("POINT DEP=("+a.getx()+","+a.gety()+")");
                System.out.println("Distance="+((tab[k].getx()-a.getx())*v.getx()+(tab[k].gety()-a.gety())*v.gety())/(Math.sqrt(v.getx()*v.getx()+v.gety()*v.gety())));
                distance+=((tab[k].getx()-a.getx())*v.getx()+(tab[k].gety()-a.gety())*v.gety())/(Math.sqrt(v.getx()*v.getx()+v.gety()*v.gety()));
            }
        }
        else{
            Point a=tab[j-1];
            Point b=tab[i-1];
            Point v=new Point(b.getx()-a.getx(),b.gety()-a.gety());
            for (int k=i;k<=j;k++){
                distance+=((tab[k].getx()-a.getx())*v.getx()+(tab[k].gety()-a.gety())*v.gety())/(Math.sqrt(v.getx()*v.getx()+v.gety()*v.gety()));
            }
        }
        return distance;

    }

    public static void main(String arg[]){
        EssaiSuccesif essaiSuccesif=new EssaiSuccesif(8);
        System.out.println(essaiSuccesif.distance(1,4));
    }


    public boolean satisfaisait(int xi){
        return true;
    }

    public boolean soltrouvee(int i){
        if(i==n-1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean optimal(){
        if(cout<coutopt){
            return true;
        }
        else{
            return false;
        }
    }

    public void enregistrer(int xi,int i){
        X[i]=xi;
        //cout=cout+xi*distance(ipred,i);
    }

    public void majvalopt(){
        for(int i=0;i<n;i++){
            Y[i]=X[i];
        }
        coutopt=cout;
    }

    public boolean optencorepossible(){
        if (cout>coutopt){
            return false;
        }
        else{
            return true;
        }
    }

    public void defaire(int i,int xi){
       // cout=cout-xi*distance(ipred,i);
    }


    public void appligbri(int i){
        for(int j=0;j<2;j++){
            if(satisfaisait(j)){
                enregistrer(j,i);
                if(soltrouvee(i)){
                    if(optimal()){
                        majvalopt();
                    }
                    else{
                        if(optencorepossible()){
                            appligbri(i+1);
                        }
                    }
                }
            }
        }
    }




}
