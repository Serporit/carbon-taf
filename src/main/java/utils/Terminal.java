package utils;

public class Terminal {
//    public static void execute(String command) {
//        try {
//            Process proc = Runtime.getRuntime().exec(command);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                System.out.print(line + "\n");
//            }
//            proc.waitFor();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void execute(String command) {
        String[] cmd = {
                "/bin/sh",
                "-c",
                command
        };
        try {
//            Process p =
            Runtime.getRuntime().exec(cmd);
//            p.waitFor();

//            InputStream i = p.getInputStream();
//            byte[] b = new byte[32];
//            i.read(b, 0, b.length);
//            System.out.println(new String(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
