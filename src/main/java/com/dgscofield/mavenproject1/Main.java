package com.dgscofield.mavenproject1;

import java.io.IOException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 *
 * @author kleyguerth
 */
public class Main {
    public static void main(String[] args) throws IOException {
        MineradorAcordao minerador = new MineradorAcordao();
        CmdLineParser cliParser = new CmdLineParser(minerador);
        try {
            cliParser.parseArgument(args);
            minerador.run();
        } catch (CmdLineException ex) {
            cliParser.printUsage(System.err);
            System.exit(1);
        }
    }
}
