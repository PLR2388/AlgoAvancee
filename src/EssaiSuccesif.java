import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class EssaiSuccesif {
    private int n;              //Nombre de points
    private double coutopt;     //Cout optimal
    private int X[];            //Solution courante
    private Point tab[];        //Liste des points
    private double cout;        //Cout courant
    private int Y[];            //Solution optimal
    private double C;           //Poids arbitraire

    public EssaiSuccesif(int n,double C){
        this.n=n;
        this.C=C;
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
        X=new int[n];
        X[0]=1;
        X[n-1]=1;
        Y=new int[n];
        Y[0]=1;
        Y[n-1]=1;
        coutopt=distance(0,n-1)+C;
    }


    public int ipred(int i){
        for(int j=(i-1);j>=0;j--){
            if(X[j]==1){
                return j;
            }
        }
        return 0;
    }

    public double distance(int i,int j){ //i<j
        double distance=0;
        Point a=tab[i];
        Point b=tab[j];
        double m=(b.gety()-a.gety())/(b.getx()-a.getx());
        double p=(a.gety()-m*a.getx());
        for (int k=i;k<=j;k++){
            double yA=tab[k].gety();
            double xA=tab[k].getx();
            distance+=Math.abs(yA-m*xA-p)/Math.sqrt(1+m*m);
        }
        return distance;

    }

    public static void main(String arg[]){
        EssaiSuccesif essaiSuccesif=new EssaiSuccesif(8,1.5);
        long debut = System.currentTimeMillis();
        essaiSuccesif.appligbri(1);
        System.out.println("Durée d'exécution en miliseconde d'essai successif:"+(System.currentTimeMillis()-debut));
        Set<Point> points=new HashSet<Point>();
        Set<Ligne> lignes=new HashSet<Ligne>();
        int iprede=0;
        for(int i=0;i<essaiSuccesif.n;i++){
            points.add(essaiSuccesif.tab[i]);
            if(essaiSuccesif.Y[i]==1){
                if(i!=iprede){
                    Ligne l=new Ligne(essaiSuccesif.tab[iprede],essaiSuccesif.tab[i]);
                    iprede=i;
                    lignes.add(l);
                }
            }
        }
        Visu v=new Visu(points,lignes,"Essai successif : coutopt="+essaiSuccesif.coutopt);
      /* System.out.println("FORCE BRUTE");
       debut=System.currentTimeMillis();
       essaiSuccesif.force_brute();
        System.out.println("Durée d'exécution en milisecondes de force brute:"+(System.currentTimeMillis()-debut));
        points=new HashSet<Point>();
        lignes=new HashSet<Ligne>();
        iprede=0;
        for(int i=0;i<essaiSuccesif.n;i++){
            points.add(essaiSuccesif.tab[i]);
            if(essaiSuccesif.Y[i]==1){
                if(i!=iprede){
                    Ligne l=new Ligne(essaiSuccesif.tab[iprede],essaiSuccesif.tab[i]);
                    iprede=i;
                    lignes.add(l);
                }
            }
        }
        v=new Visu(points,lignes,"FORCE BRUTE : coutopt="+essaiSuccesif.coutopt);
        System.out.print("coutopt="+essaiSuccesif.coutopt);*/
    }


    public boolean satisfaisait(int xi){
        return true;
    }

    public boolean soltrouvee(int i){
        if(i==n-2){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean optimal(){
        cout=cout+distance(ipred(n-1),n-1)+C;
        if(cout<coutopt){
            return true;
        }
        else{
            return false;
        }
    }

    public void enregistrer(int xi,int i){
        X[i]=xi;
        cout=cout+xi*distance(ipred(i),i)+xi*C;
    }

    public void majvalopt(){
        for(int i=0;i<n;i++){
            Y[i]=X[i];
        }
        coutopt=cout;
    }

    public boolean optencorepossible(int i){
        if (cout>coutopt){
            return false;
        }
        else{
            return true;
        }
    }

    public void defaire(int i,int xi){
        cout=cout-xi*distance(ipred(i),i)-xi*C;
    }


    public void appligbri(int i){
        for(int j=0;j<2;j++){
            if(satisfaisait(j)){
                enregistrer(j,i);
                if(soltrouvee(i)) {
                    if (optimal()) {
                        majvalopt();
                    }
                    cout=cout-distance(ipred(n-1),n-1)-C;
                }
                else{
                    if(optencorepossible(i+1)){
                        appligbri(i+1);
                    }
                }
                defaire(i,j);
            }
        }
    }

    /*************************BRUTE FORCE*****************************/

    public int[] binaire(int x){
        int tab[]=new int[n-2];
        int i=1;
        while(x!=0){
            if(x%2==1){
                tab[n-2-i]=1;
            }
            x=x/2;
            i++;
        }
        return tab;
    }

    public void force_brute(){
        coutopt=999999999;
        for(int k=0;k<=Math.pow(2,n-2)-1;k++){
            cout=0;
            int lol[]=binaire(k);
            LinkedList<Integer> positionUn=new LinkedList<Integer>();
            positionUn.add(0);
            for(int j=0;j<lol.length;j++){
                X[j+1]=lol[j];
                if(lol[j]==1){
                    positionUn.add(j+1);
                }
            }
            positionUn.add(n-1);
            for(int j=0;j<positionUn.size()-1;j++){
                cout+=distance(positionUn.get(j),positionUn.get(j+1))+C;
            }
            if(cout<coutopt){
                coutopt=cout;
                for (int j=0;j<X.length;j++){
                    Y[j]=X[j];
                }
            }

        }


    }



}
