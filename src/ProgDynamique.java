import java.util.Set;
public class ProgDynamique{

    Point[] S;
    double matrice[][];
    int n;
    double C;
    /**
     * Constructeur de ProgDynamique
     * Elle permet de calculer dynamiquement la valeur de approx(1,n)
     * @param C correspond à la valeur arbitraire que l'on veut donner à chaque segment
     */
    public ProgDynamique(double C){
        this.C=C;

        /****************CREATION DU JEU DE POINTS****************/
        creerJeuPointsDynamique();

        this.n = S.length;
        matrice= new double[n][n];

        /****************REMPLISSAGE DE LA MATRICE DES COÛTS OPTIMAUX****************/
        remplissageMatrice();

        /****************AFFICHAGE DU COÛT OPTIMAL****************/
        System.out.println(this.matrice[0][n-1]);
    }
    /**
     * Procédure de remplissage de la matrice des coûts optimaux
     * Elle permet d'obtenir le coefficient M_i,_j = approx-opt(i,j)
     */
    public void remplissageMatrice(){
        /***Parcours des n-2 diaonales supérieures de la matrice à remplir***/
        for(int j=1;j<this.n;j++){
            /***Parcours des n-j-1 coefficient de chacunes des diagonales à remplir***/
            for(int i=0;i<this.n-j;i++){
                /***Initialisation du minimum à SD_i,_i+j + C***/
                double min=distance(i+1,i+j+1)+this.C;
                /***Calcul des j-1 sommes et remplacement du minimum si l'une des sommes est plus petite***/
                for(int k=i+1;k<j+i;k++){
                    double challenger=this.matrice[i][k]+this.matrice[k][i+j];
                    if (challenger<min) {
                        min = challenger;
                    }
                }
                /***Initialisation du coefficient avec le minimum trouvé***/
                this.matrice[i][i+j]=min;
            }
        }
    }
    /**
     * Distance calcule la distance des points par rapport au segment dont les extrémités sont les points d'abscisse i et j.
     * @param i abscisse d'un point tel que i<j
     * @param j abscisse d'un point tel que i<j
     * @return un réel étant la somme des distances des points par rapport au segment
     */
    public double distance(int i,int j){ //i<j
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
    /**
     * CreerJeuPointsDynamique créer le jeu de points S à partir d'un fichier .txt contenant les coordonnées de chacun des points
     */
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
       new ProgDynamique(1.5);
    }
}
