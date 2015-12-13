package rtdc.core.i18n;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

class BundleInterfaceCreator{

    public static void main(String[] args) throws IOException {

        //Write to file
        final Map<String, String> keyPairs = new TreeMap<>();

        final BufferedReader br = new BufferedReader(new FileReader("core/src/main/resources/rtdc/core/i18n/Bundle.properties"));

        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {
            sCurrentLine = sCurrentLine.trim();

            // We must ensure that it's not a comment
            if(!sCurrentLine.isEmpty() && !sCurrentLine.startsWith("#")){
                String parts[] = sCurrentLine.split("=");
                if(parts.length != 2)
                    System.err.println(String.format("Did not add %s because it is not the expected format (i.e. key = property)", sCurrentLine));
                else if(parts[0].isEmpty() || parts[1].isEmpty())
                    System.err.println(String.format("Key or property is empty: %s", sCurrentLine));
                else if(keyPairs.containsKey(parts[0].trim()))
                    System.err.println(String.format("Key %s is duplicated", parts[0].trim()));
                else
                    keyPairs.put(parts[0].trim(), parts[1].trim());
            }
        }

        br.close();

        // Generate the Java interface
        final StringBuilder sb = new StringBuilder("package rtdc.core.i18n;\n\n" +
                "public interface ResBundleInterface {\n\n");

        for(Map.Entry<String, String> e: keyPairs.entrySet()) {
            sb.append("\t/**\n\t * ");
            sb.append(e.getValue());
            sb.append("\n\t */\n\tString ");
            sb.append(e.getKey());
            sb.append("();\n\n");
        }

        sb.append("}");

        //Write to file
        PrintWriter out = new PrintWriter("core/src/main/java/rtdc/core/i18n/ResBundleInterface.java");
        out.write(sb.toString());
        out.close();

        System.out.println("i18n successfully regenerated");
    }

}