package com.jigdraw.draw.model;

/**
 * Jigsaw difficulty levels
 *
 * @author Jay Paulynice
 */
public enum Difficulty {
    EASY, MEDIUM, HARD;

    public static Difficulty valueOf(int val) {
        Difficulty[] values = Difficulty.values();
        return values[val];
    }

    public static int getMatrixByDifficulty(Difficulty level) {
        switch (level) {
            case EASY:
                return 2;
            case MEDIUM:
                return 4;
            case HARD:
                return 8;
        }
        return 2;
    }
}
