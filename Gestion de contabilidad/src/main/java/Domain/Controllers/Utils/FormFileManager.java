package Domain.Controllers.Utils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class FormFileManager {

    private static String rutaFicheros = "src/main/resources/public/upload";

    private static File uploadDir = null;


    public static File getUploadDir() {
        return uploadDir;
    }

    public static String procesarFicheroParte(Part part) throws Exception {
        if (uploadDir == null) {
            throw new Exception("llame crearDirectorio antes de procesar un fichero");
        }
        String uploaded_file_name = getFileName(part);
        // creo el fichero temporal, con un nombre generico generado y la extension del archivo que fue subido
        Path tempFile = null;
        tempFile = Files.createTempFile(uploadDir.toPath(), "", "." + getExtension(uploaded_file_name));

        // copiar el stream de lo que me pasaron al fichero temporal
        InputStream input = part.getInputStream();
        Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.getFileName().toString();
    }

    public static byte[] obtenerFicheroPorFilename(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(rutaFicheros + "/" + filename));
        return bytes;
    }

    public static File crearDirectorio() {
        uploadDir = new File(rutaFicheros);
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        return uploadDir;
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static String realPath(String nombreFichero) {
        // TODO: verificar que en verdad este archivo existe, sino lanzar excepcion
        return rutaFicheros + "/" + nombreFichero;
    }

    public static String getExtension(String path) {
        return getExtension(path);
    }
}
