package game.loaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RuleLoader {

    private Path path;

    public RuleLoader(Path path) {
        this.path = path;
    }


    //loads a text file

    public String loadRules() throws IOException {
        //returns string of rules
        return Files.readString(path);
    }

}
