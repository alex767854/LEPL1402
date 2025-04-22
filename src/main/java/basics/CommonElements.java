package basics;

public class CommonElements {

    /**
     *
     * @param tab1 is a non null array
     * @param tab2 is a non null array
     * @return the number of elements that are the same at the same index
     *         more exactly the size of set {i such that tab1[i] == tab2[i]}
     *         for instance count([1,3,5,5],[1,2,5,5,6]) = 3
     */
    public static int count(int [] tab1, int [] tab2) {
        int sum = 0;
        int elem = 0;
        if (tab1.length > tab2.length) {
            elem =  tab2.length;
        }
        else {
            elem = tab1.length;
        }
        for (int i = 0 ; i<elem ;i++) {
            if (tab1[i] == tab2[i]) {
                sum++;
            }
        }
        return sum;
    }

    /**
     *
     * @param tab1 is a non null 2D array
     * @param tab2 is a non null 2D array
     * @return the number of elements that are the same at the same index
     *         more exactly the size of set {(i,j) such that tab1[i][j] == tab2[i][j]}
     */
    public static int count(int [][] tab1, int [][] tab2) {
        int cnt = 0;
        int min = (tab1.length < tab2.length) ? tab1.length : tab2.length ;
        for (int i = 0; i<min;i++) {
            int min1 = (tab1[i].length < tab2[i].length) ? tab1[i].length : tab2[i].length ;
            for (int j = 0; j<min1;j++) {
                if (tab1[i][j]==tab2[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;

    }
}
