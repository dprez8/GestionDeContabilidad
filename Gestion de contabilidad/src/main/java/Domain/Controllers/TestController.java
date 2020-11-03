package Domain.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class TestController {

    public ModelAndView mostrarFormularioCargarArchivo(Request request, Response response) {
        return new ModelAndView(null, "carga-imagen.hbs");
    }

    public String cargarArchivo(Request request, Response response) throws IOException, ServletException {

        String file_name = "doc_comercial_file";
        File uploadDir = new File("src/main/resources/public/upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist


        // configuracion standard
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        String uploaded_file_name = "";
        // obtener el nombre del fichero para poder sacar la extension
        // si falla es porque no habia archivo seguramente
        try {
            uploaded_file_name = getFileName(request.raw().getPart(file_name));
        }
        catch (NullPointerException exception) {
            return "<h1>A null pointer exception occurred :C</h1>";
        }
        // creo el fichero temporal, con un nombre generico generado y la extension del archivo que fue subido
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "." + getExtension(uploaded_file_name));
        // copiar el stream de lo que me pasaron al fichero temporal
        try (InputStream input = request.raw().getPart(file_name).getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (NullPointerException exception) {
            // fallo, borro el archivo temporal
            Files.deleteIfExists(tempFile.toAbsolutePath());
            return "<h1>A null pointer exception occurred :C</h1>";
        }

        System.out.println("Uploaded file '" + getFileName(request.raw().getPart(file_name)) + "' saved as '" + tempFile.toAbsolutePath() + "'");
        return "<h1>You uploaded this image:</h1><img src='localhost/upload/" + tempFile.getFileName() + "'>";
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
