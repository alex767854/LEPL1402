package basics; // METTRE QUIZ POUR INGINIOUS : package quizz;


// SEE THE explanations in the Videos

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Flipper {


    public static int run(char [][] map) {
        // TODO
        Set<String> inds = new HashSet<>();
        int currentx = 0;
        int currenty = 0;
        char dir = 'd';
        while (currenty>=0 && currenty<map[0].length && currentx>=0 && currentx<map.length){
            String current = currentx + ","+currenty;
            if(!inds.contains(current)){
                inds.add(current);
            }
            if (map[currentx][currenty]=='\\'){
                if (dir=='d'){
                    dir='b';
                }
                else if (dir=='g'){
                    dir='h';
                }
                else if (dir=='h'){
                    dir='g';
                }
                else if (dir=='b'){
                    dir='d';
                }
            }
            else if (map[currentx][currenty]=='/'){
                if (dir=='d'){
                    dir='h';
                }
                else if (dir=='g'){
                    dir='b';
                }
                else if (dir=='h'){
                    dir='d';
                }
                else if (dir=='b'){
                    dir='g';
                }
            }
            if (dir=='d'){
                currenty++;
            }
            else if (dir=='g'){
                currenty--;
            }
            else if (dir=='h'){
                currentx--;
            }
            else if (dir=='b'){
                currentx++;
            }
            if (currentx<0 || currenty<0){
                return inds.size();
            }
        }
        return inds.size();
    }

}

