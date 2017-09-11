import java.io.File;
import java.util.regex.Pattern;

public class CleanDeveloperCache {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("use: java CleanDeveloperCache [args]");
            return;
        }
        for (String s : args) {
            File file = new File(s);
            searchFile(file);
        }
    }

    private static final String[] keysAndroid = {
            //AndroidStudio:Android
            ".idea",
            ".gradle",
            ".externalNativeBuild",
            ".DS_Store",
            "build",
            "local.properties",
            "\\w*\\.iml"
    };


    private static final String[] keysJava = {
            //IntelliJ IDEA:Java
            ".DS_Store",
            "out",
    };


    private static final String[] keysC = {
            //CLion:C/C++
            ".DS_Store",
            "cmake\\-build\\-debug"
    };

    private static void searchFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {
                if (matchFile(f)) {
                    System.out.println("Delete: " + f.getPath());
                    deleteFile(f);
                } else {
                    searchFile(f);
                }
            }
        } else if (matchFile(file)) {
            deleteFile(file);
        }
    }

    private static boolean matchFile(File file) {
        System.out.println(file.getName());
        boolean isMatch = false;
        for (String k : keysAndroid) {
            if (Pattern.matches(k, file.getName())) {
                isMatch = true;
                break;
            }
        }
        return isMatch;
    }

    private static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return false;
            }
            for (File f : files) {
                boolean success = deleteFile(f);
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
