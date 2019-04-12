import java.util.Set;

import static jdk.nashorn.internal.objects.Global.print;

public class ProgDynamique {

    static Point[] S;
    double matrice[][];
    int n;
    double C;
    Point tab[];
    double cout_opt;

    public ProgDynamique(Point[] S,double C,int n){
        matrice= new double[n][n];
        this.C=C;
        this.n=n;

        initMatrice();
        remplissageMatrice(this.S,this.C,this.matrice,this.n);
        System.out.println("2");
        approx_opt();
        System.out.println("3");

    }

    public void initMatrice(){ //validé
        int compteur=0;
        for(int i=0;i<this.matrice.length;i++) {
            for (int j = 0; j < this.matrice[0].length; j++) {
                compteur=compteur+1;
                this.matrice[i][j]=0;
            }
        }
    }

    public void approx_opt(){
        this.cout_opt=this.matrice[0][n-1];
    }

    public void remplissageMatrice(Point[] S,double C, double[][] matrice, int n){
        System.out.println("debut remplissage matrice,"+n);

        for(int j=1;j<n;j++){
            System.out.println("premiere boucle");
            for(int i=0;i<n-j;i++){
                System.out.println("seconde boucle");
                double min=distance(i+1,i+j+1,S)+C;
                //System.out.println("distance entre("+(i+1)+","+(i+j+1)+")="+(distance(i+1,i+j+1,S)+C));
                for(int k=i+1;k<j+i;k++){
                    double challenger=matrice[i][k]+matrice[k][i+j];
                    //System.out.println("j="+j+"|i="+i+"|k="+k);
                    //System.out.println("matrice["+i+"]["+k+"]="+matrice[i][k]+"|matrice["+k+"]["+(i+j)+"]="+matrice[k][i+j]);
                    //System.out.println("Le challenger est:"+challenger+" et le min est:"+min);
                    if (challenger<min) {
                        min = challenger;
                    }
                }
                //System.out.println(min);
                //System.out.println("i="+i+"|j="+(j+i));
                matrice[i][i+j]=min;
            }
        }
    }

    public static double distance(int i,int j, Point[] S){ //i<j
        double distance=0;
        Point a=S[i-1];
        Point b=S[j-1];
        double m=(b.gety()-a.gety())/(b.getx()-a.getx());
        double p=(a.gety()-m*a.getx());
        for (int k=(i-1);k<=(j-1);k++){
            double yA=S[k].gety();
            double xA=S[k].getx();
            distance+=Math.abs(yA-m*xA-p)/Math.sqrt(1+m*m);
        }
        return distance;

    }

    public static Point[] creerJeuPoints(int n){
        int j=0;
        S=new Point[n];
        Set<Point> points=Parser.recuperePoints();
        for(Point p: points){
            S[j]=p;
            j++;
        }
        int i;
        Point cle;
        for (i = 1; i < S.length; i++) {
            cle = S[i];
            j = i;
            while ((j >= 1) && (S[j - 1].getx() > cle.getx())) {
                S[j]  = S[j - 1] ;
                j = j - 1;
            }
            S[j] = cle;
        }
        return S;
    }

    public static void main(String arg[]){
        System.out.println((new ProgDynamique(creerJeuPoints(8),1.5,8).cout_opt));

    }
}
