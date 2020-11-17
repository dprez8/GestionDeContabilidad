package Domain.Controllers.Utils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class FormFileManager {

    private static String location = "temp";          // the directory location where files will be stored temporarily
    private static long maxFileSize = 12000000;       // the maximum size allowed for uploaded files
    private static long maxRequestSize = 12000000;    // the maximum size allowed for multipart/form-data requests
    private static int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk

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
        tempFile = Files.createTempFile(uploadDir.toPath(), "", "." + getExtensionLocal(uploaded_file_name));

        // copiar el stream de lo que me pasaron al fichero temporal
        InputStream input = part.getInputStream();
        Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.getFileName().toString();
    }

    public static String reemplazarFicheroParte(Part part, String path) throws Exception {
        if (uploadDir == null) {
            throw new Exception("llame crearDirectorio antes de procesar un fichero");
        }
        File file = new File(realPath(path));
        if (!file.exists()) {
            throw new FileNotFoundException("fichero no existente");
        }

        // copiar el stream de lo que me pasaron al fichero temporal
        InputStream input = part.getInputStream();
        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file.toPath().getFileName().toString();
    }

    public static Boolean borrarFichero(String path) throws Exception {
        if (uploadDir == null) {
            throw new Exception("llame crearDirectorio antes de procesar un fichero");
        }

        File file = new File(realPath(path));
        if (!file.exists()) {
            throw new FileNotFoundException("fichero no existente");
        }

        return Files.deleteIfExists(file.toPath());
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

    public static String getExtensionLocal(String path) {
        return getExtension(path);
    }

    public static MultipartConfigElement crearMultiPartConfigElement() {
        return new MultipartConfigElement(
                location,
                maxFileSize,
                maxRequestSize,
                fileSizeThreshold
        );
    }
}
