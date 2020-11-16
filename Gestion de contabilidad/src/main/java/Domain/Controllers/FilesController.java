package Domain.Controllers;

import Domain.Controllers.Utils.FormFileManager;
import Domain.Controllers.jwt.TokenService;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class FilesController extends GenericController {

    public FilesController(TokenService tokenService, String tokenPrefix) {
        super(tokenService, tokenPrefix);
    }

    List<String> tiposImagenes = Arrays.asList("jpeg", "png", "bmp");

    public Object descargarArchivo(Request request, Response response) {

        HttpServletResponse raw = response.raw();

        String filename = request.params("filename");

        byte[] bytes = null;
        try {
          bytes = FormFileManager.obtenerFicheroPorFilename(filename);
        } catch (Exception exception) {}

        if (bytes == null)
            return error(response, "404 file not found ---> archivo no encontrado D:");
        try {
            raw.getOutputStream().write(bytes);
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
            String extension = FormFileManager.getExtension(filename);

            response.header("Content-Disposition", "attachment; filename="+filename);
            if (tiposImagenes.contains(extension)){
                // me esta pidiendo una imagen
                response.type("image/" + extension);
                response.status(200);
            } else {
                response.header("Content-Type", "application/download");
            }
        } catch (Exception exception) {
            return error(response, "algo paso mientras se procesaban los bytes");
        }

        return response.raw();
    }
}
