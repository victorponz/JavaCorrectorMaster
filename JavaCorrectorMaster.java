import java.io.IOException;

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
        int nextJobIndex = 0;
        do{
            try {
                final Job nextJob = getNextJob(nextJobIndex);
                
                if (nextJob == null) break;
                
                final String program = nextJob.getProgram();
                final Runtime re = Runtime.getRuntime();
                //TODO: De momento no usamos $(pwd) porque no estoy en el directorio que toca
                //final Process command = re.exec("docker run --rm -v /home/victorponz/Documentos/repos/JavaCorrector/io:/application/io --name javacorrector victorponz/javacorrector:v0 " + program + " job" + nextJob.getJobID());
                final Process command = re.exec("docker run --rm -v /home/victorponz/Documentos/repos/JavaCorrectorMaster/io/results:/io/results --name codetest codetest " + program + " job" + nextJob.getJobID());
                // Wait for the application to Fin
                command.waitFor();
                
            
                if (command.exitValue()!= 0) {
                    throw new IOException("Failed to execute jar, " );
                }

            } catch (final IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
            nextJobIndex++;
        }while(true);
        
    }
    public static Job getNextJob(int nextJobIndex) {
        //TODO: De momento lo hacemos ficticios
        if(nextJobIndex == 0){
            return new Job("AlReves", 0);
        }else if(nextJobIndex == 1){
            return new Job("Afortunados", 1);
        }
        return null;

    }
}
