package rtdc.core.i18n;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BundleInterfaceCreator{

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final Pattern VALID_VARIABLE = Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$");

    public static void main(String[] args) throws IOException {

        final Map<String, String> keyPairs = parsePropertiesFile("core/src/main/resources/rtdc/core/i18n/Bundle.properties");
        generateResBundleInterface(keyPairs, "core/src/main/java/rtdc/core/i18n/ResBundleInterface.java");
        generateJavaIOResBundle(keyPairs, "core/src/main/java/rtdc/core/i18n/JavaIOResBundle.java");
    }

    static void generateResBundleInterface(Map<String, String> map, String path) throws IOException{
        // Generate the Java interface
        final StringBuilder sb = new StringBuilder("package rtdc.core.i18n;\n\n" +
                "public interface ResBundleInterface {\n\n");

        for(Map.Entry<String, String> e: map.entrySet()) {
            sb.append("\t/**\n\t * ");
            String value = e.getValue();
            sb.append(value);
            sb.append("\n\t */\n\tString ");
            sb.append(e.getKey());
            sb.append("(");
            Matcher m = PATTERN.matcher(value);
            if(m.find()) {
                sb.append("String arg");
                sb.append(m.group(1));
            }
            while(m.find()){
                sb.append(", String arg");
                sb.append(m.group(1));
            }
            sb.append(");\n\n");
        }

        sb.append("}");

        //Write to file
        PrintWriter out = new PrintWriter(path);
        out.write(sb.toString());
        out.close();

        System.out.println("i18n: regenerated " + path);
    }

    static void generateJavaIOResBundle(Map<String, String> map, String path) throws IOException{
        // Generate the Java interface
        final StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(path));

        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {
            sb.append(sCurrentLine);
            sb.append("\n");
            if (sCurrentLine.trim().equals("/** AUTOMATIC GENERATION -- DO NOT CHANGE */"))
                break;
        }

        sb.append("\n");

        br.close();

        for(Map.Entry<String, String> e: map.entrySet()) {
            sb.append("\t/**\n\t * ");
            String value = e.getValue();
            sb.append(value);
            sb.append("\n\t */\n\tpublic String ");
            sb.append(e.getKey());
            sb.append("(");
            Matcher m = PATTERN.matcher(value);
            StringBuilder arguments = new StringBuilder();
            if(m.find()) {
                sb.append("String arg");
                sb.append(m.group(1));
                arguments.append("arg");
                arguments.append(m.group(1));
            }
            while(m.find()){
                sb.append(", String arg");
                sb.append(m.group(1));
                arguments.append(", arg");
                arguments.append(m.group(1));
            }
            sb.append(") {\n");

            if(arguments.toString().isEmpty()) {
                sb.append("\t\treturn BUNDLE.getString(\"");
                sb.append(e.getKey());
                sb.append("\");\n");
            } else{
                sb.append("\t\tFORMATTER.applyPattern(BUNDLE.getString(\"");
                sb.append(e.getKey());
                sb.append("\"));\n");
                sb.append("\t\treturn FORMATTER.format(new Object[]{");
                sb.append(arguments);
                sb.append("});\n");
            }

            sb.append("\t}\n\n");
        }

        sb.append("}");

        //Write to file
        PrintWriter out = new PrintWriter(path);
        out.write(sb.toString());
        out.close();

        System.out.println("i18n: regenerated " + path);
    }

    static Map<String, String> parsePropertiesFile(String path) throws IOException{
        //Write to file
        final Map<String, String> keyPairs = new TreeMap<>();

        BufferedReader br = new BufferedReader(new FileReader(path));

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

        System.out.println("i18n: read properties file " + path);

        return keyPairs;
    }

}