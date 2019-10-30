package edu.metrostate.by8477ks.atm;

public class P2Exceptions {

    /**
     * A generic exception to give contextual feedback to the user
     */
    static class ImproperHeaderFileException extends Exception {
        public ImproperHeaderFileException(String s) {
            super(s);
        }
    }
}
