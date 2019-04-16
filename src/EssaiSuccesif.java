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
    private double C;           //Poids arbitraire

    public EssaiSuccesif(int n,double C){
        this.n=n;
        this.C=C;
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
        cout=C;
        ipred=0;
        X=new int[n];
        X[0]=1;
        X[n-1]=1;
        Y=new int[n];
        Y[0]=1;
        Y[n-1]=1;

    }

    public double nbSegment(){
        int som=0;
        for (int i=0;i<X.length;i++){
            som+=X[i];
        }
        return som-1;
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
        cout=cout+xi*distance(ipred,i)+xi*C;
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
       cout=cout-xi*distance(ipred,i)-xi*C;
       System.out.println("Nouveau cout="+cout);
    }


    public void appligbri(int i){
        for(int j=0;j<2;j++){
            if(satisfaisait(j)){
                enregistrer(j,i);
                if(soltrouvee(i)) {
                    if(cout==C){
                        cout=distance(0,n-1)+C;
                    }
                    if (optimal()) {
                        majvalopt();
                    }
                    if(cout==distance(0,n-1)+C){
                        cout=C;
                    }

                }
                else{
                    if(optencorepossible()){
                        if(j*i!=0){
                            ipred=j*i;
                            System.out.println("ipred="+ipred);
                        }
                        appligbri(i+1);
                    }
                }
                defaire(i,j);
            }
        }
    }




}
