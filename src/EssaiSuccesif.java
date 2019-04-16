import java.util.Collections;
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
        essaiSuccesif.appligbri(1);
        for(int i=0;i<8;i++){
            System.out.println("Y["+i+"]="+essaiSuccesif.Y[i]);

        }
        System.out.println("cout_opt="+essaiSuccesif.coutopt);
        System.out.println("FORCE BRUTE");
       essaiSuccesif.force_brute();
    }


    public boolean satisfaisait(int xi){
        System.out.println("SATISFAISANT");
        System.out.println(xi+" est valide");
        return true;
    }

    public boolean soltrouvee(int i){
        if(i==n-2){
            System.out.println("SOLTROUVEE");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean optimal(){
        cout=cout+distance(ipred(n-1),n-1)+C;
        System.out.println("cout="+cout);
        if(cout<coutopt){
            System.out.println("OPTIMAL");
            return true;
        }
        else{
            return false;
        }
    }

    public void enregistrer(int xi,int i){
        System.out.println("ENREGISTREMENT");
        X[i]=xi;
        cout=cout+xi*distance(ipred(i),i)+xi*C;
        System.out.println("cout courant="+cout);
    }

    public void majvalopt(){
        System.out.println("MAJVALOPT");
        System.out.println("Nouvelle Solution:");
        for(int i=0;i<n;i++){
            Y[i]=X[i];
            System.out.print(Y[i]);
        }
        System.out.println("");
        coutopt=cout;
    }

    public boolean optencorepossible(){
        if (cout>coutopt){
            return false;
        }
        else{
            System.out.println("ENCOREPOSSIBLE");
            System.out.println("CoutOpt="+coutopt+" Cout_courant="+cout);
            return true;
        }
    }

    public void defaire(int i,int xi){
        System.out.println("DEFAIRE");
        cout=cout-xi*distance(ipred(i),i)-xi*C;
       System.out.println("Nouveau cout="+cout);
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
                    if(optencorepossible()){
                      /*  if(j*i!=0){
                            ipred=j*i;
                            System.out.println("ipred="+ipred);
                        }*/
                        System.out.println("ipred="+ipred(i));
                        appligbri(i+1);
                    }
                }
                defaire(i,j);
            }
        }
    }

    /*************************BRUTE FORCE*****************************/

    public int[] binaire(int x){
        int taille=(int)Math.ceil(Math.log(x)/Math.log(2));
        int tab[]=new int[6];
        int i=1;
        while(x!=0){
            if(x%2==1){
                tab[6-i]=1;
            }
            x=x/2;
            i++;
        }
        return tab;
    }

    public void force_brute(){
        coutopt=999999999;
        for(int k=0;k<=Math.pow(2,6)-1;k++){
            //System.out.println("Nombre="+k);
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
               // System.out.println("("+positionUn.get(j)+","+positionUn.get(j+1)+")");
                cout+=distance(positionUn.get(j),positionUn.get(j+1))+C;
            }
            //System.out.println("cout="+cout);
            //for(int i=0;i<8;i++){
              //  System.out.print(X[i]);
            //}
           // System.out.println("");
            if(cout<coutopt){
                coutopt=cout;
                for (int j=0;j<X.length;j++){
                    Y[j]=X[j];
                }
            }

        }
        for(int i=0;i<8;i++){
            System.out.println("Y["+i+"]="+Y[i]);

        }
        System.out.print("coutopt="+coutopt);
    }



}
