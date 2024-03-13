package game;

import java.util.ArrayList;
import java.util.Random;

public class Autoplay {
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int VERYHARD = 3;

    private static final int MAX_THRESHOLD = 10000;

    private static int diff = EASY;

    public static int[] computeMove(ArrayList<int[]> legalMoves, GameBoard board){
        int[] ret;
        switch(diff){
            case EASY:
                ret = leastCapture(legalMoves, board);
                break;
            case MEDIUM:
                ret = random(legalMoves);
                break;
            case HARD:
                ret = strategic(legalMoves, board, 0, 0);
                break;
            case VERYHARD:
                ret = strategic(legalMoves, board, 0.40, 0.60);
                break;
            default:
                ret = random(legalMoves);
        }
        return ret;
    }

    public static int[] computeBestMove(ArrayList<int[]> legalMoves, GameBoard board){
        if(legalMoves.size() == 0)
            return null;
        return strategic(legalMoves, board, 1, 1);
    }
    
    public static void setDifficulty(int _diff){
        diff = _diff;
    }

    public static int getDifficulty(){
        return diff;
    }

    private static int[] random(ArrayList<int[]> legalMoves){
        Random rng = new Random();
        return legalMoves.get(rng.nextInt(legalMoves.size()));
    }

    private static int[] leastCapture(ArrayList<int[]> legalMoves, GameBoard board){
        int[] ret = null;
        int minCapCount = Integer.MAX_VALUE;
        for (int[] move : legalMoves) {
            int capCount = board.captureCount(move[0], move[1], GameLogic.getInstance().getCurrentColour());
            if(capCount < minCapCount){
                ret = move;
                minCapCount = capCount;
            }   
        }
        return ret;
    }

    private static int[] strategic(ArrayList<int[]> legalMoves, GameBoard board, double cornerChance, double edgeChance){
        int[] ret = null;
        int maxCapCount = Integer.MIN_VALUE;
        boolean edgeFound = false;
        
        for (int[] move : legalMoves){
            if(isCorner(move[0], move[1]) && rollChance(cornerChance)){
                ret = move;
                return ret;
            }
            else if(isEdge(move[0], move[1]) && rollChance(edgeChance)){
                int capCount = board.captureCount(move[0], move[1], GameLogic.getInstance().getCurrentColour());
                if(capCount > maxCapCount || !edgeFound){
                    ret = move;
                    maxCapCount = capCount;
                    edgeFound = true;
                }
            }
            else if(!edgeFound){
                int capCount = board.captureCount(move[0], move[1], GameLogic.getInstance().getCurrentColour());
                if(capCount > maxCapCount){
                    ret = move;
                    maxCapCount = capCount;
                }
            }
            else 
                continue;
        }
        return ret != null ? ret : random(legalMoves);
    }

    private static boolean isCorner(int x, int y){
        return (x == 0 || x == GameBoard.boardLength - 1) && (y == 0 || y == GameBoard.boardLength - 1);
    }

    private static boolean isEdge(int x, int y){
        return (x == 0 || x == GameBoard.boardLength - 1 || y == 0 || y == GameBoard.boardLength - 1);
    }

    private static boolean rollChance(double thresholdDecimal){
        return (new Random().nextInt(MAX_THRESHOLD) < (thresholdDecimal * MAX_THRESHOLD));
    }
}
