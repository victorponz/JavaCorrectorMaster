import java.io.IOException;

import javax.lang.model.element.NestingKind;

public class JavaCorrectorMaster{
    public static void main(String[] args) {

        /*Va a haber un ciclo continuo
        Si hay algo que hacer:
        1.- Se crea un hilo
        2.- Se ejecuta un docker 
        3.- Se parsea la salida de este docker    
        */
        // ProcessBuilder pb = new ProcessBuilder("docker", "run", "--rm", "myimage");
        // pb.redirectErrorStream(true);
        // Process p = pb.start();
        // InputStream is = p.getInputStream();
        // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // String line;
        // while ((line = reader.readLine()) != null) {
        //     System.out.println(line);
        // }
        // p.waitFor();

        try {
            final Job nextJob = getNextJob();
            final String programm = nextJob.getProgram();
            final Runtime re = Runtime.getRuntime();
            //TODO: De momento no usamos $(pwd) porque no estoy en el directorio que toca
            final Process command = re.exec("docker run --rm -v /home/victorponz/Documentos/repos/JavaCorrectorMaster/salida:/application/salida -v /home/victorponz/Documentos/repos/JavaCorrectorMaster/entrada:/application --name javacorrector victorponz/javacorrector:v0 " + programm);

            // Wait for the application to Fin
            command.waitFor();
          
            if (command.exitValue()!= 0) {
                throw new IOException("Failed to execute jar, " );
            }

        } catch (final IOException | InterruptedException e) {
           System.out.println(e.getMessage());

        }
        
    }
    public static Job getNextJob() {
        //TODO De momento lo hacemos ficticio 
        final Job job = new Job("Afortunados");
        return job;

    }
}