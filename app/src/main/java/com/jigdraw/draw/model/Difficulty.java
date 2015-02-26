package com.jigdraw.draw.model;

/**
 * Jigsaw difficulty levels
 *
 * @author Jay Paulynice
 */
public enum Difficulty {
    EASY, MEDIUM, HARD;

    /**
     * Given level of difficulty return the number of rows and columns
     * for the jigsaw
     *
     * @param difficulty the difficulty level
     * @return the number of rows/columns
     */
    public static int getNumberOfPieces(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return 2;
            case MEDIUM:
                return 4;
            case HARD:
                return 8;
        }
        return 2;
    }

    public static Difficulty getLevel(int which) {
        switch (which) {
            case 0:
                return EASY;
            case 1:
                return MEDIUM;
            case 2:
                return HARD;
            default:
                throw new IllegalArgumentException("Unknown level");
        }
    }
}
