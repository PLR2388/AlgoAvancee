import java.util.Set;
public class ProgDynamiqueV2 {

    Point[] S;
    double matrice[][];
    int n;
    double C;
    double cout_opt;

    public ProgDynamiqueV2(double C){
        creerJeuPointsDynamique();
        this.n = S.length;
        matrice= new double[n][n];
        this.C=C;
        initMatrice();
        remplissageMatrice();
        approx_opt();
    }

    public void initMatrice(){
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

    public void remplissageMatrice(){
        for(int j=1;j<this.n;j++){
            for(int i=0;i<this.n-j;i++){
                double min=distance(i+1,i+j+1,this.S)+this.C;
                for(int k=i+1;k<j+i;k++){
                    double challenger=this.matrice[i][k]+this.matrice[k][i+j];
                    if (challenger<min) {
                        min = challenger;
                    }
                }
                this.matrice[i][i+j]=min;
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

    public void creerJeuPointsDynamique(){
        int j=0;
        Set<Point> points=Parser.recuperePoints();
        this.S=new Point[points.size()];
        for(Point p: points){
            this.S[j]=p;
            j++;
        }
        int i;
        Point cle;
        for (i = 1; i < this.S.length; i++) {
            cle = this.S[i];
            j = i;
            while ((j >= 1) && (this.S[j - 1].getx() > cle.getx())) {
                this.S[j]  = this.S[j - 1] ;
                j = j - 1;
            }
            this.S[j] = cle;
        }
    }

    public static void main(String arg[]){
        ProgDynamiqueV2  pd = new ProgDynamiqueV2(1.5);
        System.out.println(pd.cout_opt);

    }
}
