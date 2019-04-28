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

    /**
     * Constructeur de Essai Successif
     * Elle permet de charger les points dans la structure de données
     * @param C correspond à la valeur arbitraire que l'on veut donner à chaque segment
     */
    public EssaiSuccesif(double C){
        this.C=C;
        int j=0;
        Set<Point> points=Parser.recuperePoints();
        this.n=points.size();
        tab=new Point[n];
        for(Point p: points){
            tab[j]=p;
            j++;
        }
        /****************TRI DES POINTS PAR ABSCISSE CROISSANTE****************/
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
        /***********************************************************************/
        /********************INITIALISATION DES VARIABLES***********************/
        cout=0;
        X=new int[n];
        X[0]=1;
        X[n-1]=1;
        Y=new int[n];
        Y[0]=1;                             //On initialise la solution optimale à la corde reliant le premier et le dernier point
        Y[n-1]=1;
        coutopt=distance(0,n-1)+C;
        /***********************************************************************/
    }

    /**
     *  Ipred retourne le dernier point choisi avant le point d'abscisse i
     * @param i l'abscisse du point en cours d'étude
     * @return un entier représentant l'abscisse du dernier point choisi avant i sinon 0 par défaut
     */
    public int ipred(int i){
        for(int j=(i-1);j>=0;j--){
            if(X[j]==1){
                return j;
            }
        }
        return 0;
    }

    /**
     * Distance calcule la distance des points par rapport au segment dont les extrémités sont les points d'abscisse i et j.
     * @param i abscisse d'un point tel que i<j
     * @param j abscisse d'un point tel que i<j
     * @return un réel étant la somme des distances des points par rapport au segment
     */
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
        EssaiSuccesif essaiSuccesif=new EssaiSuccesif(1.5);
        long debut = System.currentTimeMillis();
        essaiSuccesif.appligbri(1); //On étudie les points du 2ème à l'avant-dernier puisqu'on est sûr de prendre le premier et le dernier
        System.out.println("Durée d'exécution en miliseconde d'essai successif:"+(System.currentTimeMillis()-debut));
        /***PREPARATION POUR AFFICHAGE***/
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
        /*******************************/

        //Partie permettant de lancer la méthode de brute force
     /* System.out.println("FORCE BRUTE");
       debut=System.currentTimeMillis();
       essaiSuccesif.force_brute();
        System.out.println("Durée d'exécution en milisecondes de force brute:"+(System.currentTimeMillis()-debut));*

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

    /**
     * Fonction indiquant si la solution xi est possible
     * @param xi vaut 0 ou 1 en fonction si on prend ou pas le point
     * @return vrai car cela reste une solution même si elle n'est pas forcément optimale
     */
    public boolean satisfaisait(int xi){
        return true;
    }

    /**
     * Fonction vérifiant si on a trouvé une solution
     * @param i l'abscisse du point que l'on vient de fixé
     * @return  vrai si on vient de décider le sort du point n-2 sinon faux. En effet, on a alors parcouru l'ensemble des points
     */
    public boolean soltrouvee(int i){
        if(i==n-2){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Fonction qui indique si on a trouvé une meilleure solution
     * @return  vrai si la solution est meilleure
     */
    public boolean optimal(){
        cout=cout+distance(ipred(n-1),n-1)+C; //Ajoute le segment reliant le dernier point choisi et le point n
        if(cout<coutopt){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Enregistre le choix du point et le cout actuel
     * @param xi 0 ou 1 si on a pris le point i
     * @param i abscisse du point à enregistrer
     */
    public void enregistrer(int xi,int i){
        X[i]=xi;
        cout=cout+xi*distance(ipred(i),i)+xi*C;
    }

    /**
     * Met à jour le vecteur représentant la solution optimale
     */
    public void majvalopt(){
        for(int i=0;i<n;i++){
            Y[i]=X[i];
        }
        coutopt=cout;
    }

    /**
     * Fonction indiquant si on peut encore espérer battre l'optimal courant
     * @param i entier représentant l'abscisse du point en cours d'étude
     * @return
     */
    public boolean optencorepossible(int i){
        if (cout+minrestant(i)>coutopt){        //Ajout d'une fonction qui minimise le coût restant
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Fonction calculant une valeur minimale entre le point actuel et le point final
     * @param i l'abscisse du point actuel
     * @return
     */
    public double minrestant(int i){
        double distance=distance(i,n-1)/(n-i);
        return distance;
    }

    /**
     * Fonction opposée à enregistrer qui diminue le coût actuel
     * @param i l'abscisse du point en cours d'étude
     * @param xi 0 ou 1 en fonction si on a pris le point ou pas
     */
    public void defaire(int i,int xi){
        cout=cout-xi*distance(ipred(i),i)-xi*C;
    }

    /**
     * Procédure principal de la méthode avec essai successif
     * @param i indique le point d'abscisse en cours d'étude
     */
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

    /**
     * Convertit un entier sous forme décimal en sa forme binaire en renvoyant un tableau de 0 et 1
     * @param x un entier positif
     * @return un tableau représentant la forme binaire du nombre en entrée
     */
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

    /**
     * Teste l'ensemble des solutions possibles une par une sans élagage
     */
    public void force_brute(){
        coutopt=999999999;
        for(int k=0;k<=Math.pow(2,n-2)-1;k++){                  //On parcout l'ensemble des solutions possibles
            cout=0;                                             //Cela correspond à parcourir l'ensemble des représentations binaires de 0 à 2^(n-2)
            int lol[]=binaire(k);
            LinkedList<Integer> positionUn=new LinkedList<Integer>();
            positionUn.add(0);
            for(int j=0;j<lol.length;j++){
                X[j+1]=lol[j];
                if(lol[j]==1){                                  //Stocke les abscisses des points des points qu'on prend
                    positionUn.add(j+1);
                }
            }
            positionUn.add(n-1);
            for(int j=0;j<positionUn.size()-1;j++){
                cout+=distance(positionUn.get(j),positionUn.get(j+1))+C;    //Le coût correspond à la somme des distances des points par rapport à leur segment respective + C car on ajoute un segment
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
