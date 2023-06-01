package una.filesorganizeridoffice.business.util;

import una.filesorganizeridoffice.business.Protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A class containing all the tools that will be used by business package to perform their activities correctly.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public class Tools {
     /***
     * Contains the message and protocol for exception throwing.
     */
    public static final HashMap<String, Protocol> errorList = new HashMap<>();
    /***
     * Contains all the processes completed for log writing.
     */
    public static final List<String> approvedProcesses = new LinkedList<>();

    /**
     * Inner static class as member of Tools.
     */
    public static class LoggerWriter{
        /***
         * Writes a report of every process made in a txt file, keeping the previous information written in the txt file. And
         * only adding the new process information at the bottom of the file.
         */
        public static void createLog() {
        /*
        EXAMPLE LOG, DATE, HOUR, ROWS REMAIN TO BE ADDED TO THE MESSAGE,
        STILL NEED TO BE WROTE IN A TXT FILE.
         */
            Thread log = new Thread(new Runnable() {
                @Override
                public void run() {
                    String approvedMessage = "=======================================================================================\n" +
                            "Las siguientes gestiones se completaron correctamente: \n";
                    for (String message : approvedProcesses){
                        approvedMessage = approvedMessage.concat(message).concat("\n");
                    }

                    System.out.println(approvedMessage);

                    if(!errorList.isEmpty()){
                        String errorMessage = "=======================================================================================\n"
                                + "Los siguientes errores ocurrieron: \n";
                        for (String k : errorList.keySet()){
                            Protocol p = errorList.get(k);
                            errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
                        }
                        System.out.println(errorMessage);
                    }
                    System.out.println("====================================PROGRAM STOPED=====================================");
                    approvedProcesses.clear();
                    errorList.clear();
                }
            });
            log.start();
        }
    }
}
