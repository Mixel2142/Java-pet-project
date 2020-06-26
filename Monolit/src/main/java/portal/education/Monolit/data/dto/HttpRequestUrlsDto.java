package portal.education.Monolit.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
@AllArgsConstructor
public class HttpRequestUrlsDto {

    String serverName;
    Integer serverPort;
    String contextPath;

    public HttpRequestUrlsDto(HttpServletRequest request) {
        this.serverName = request.getServerName();
        this.serverPort = request.getServerPort();
        this.contextPath = request.getContextPath();
    }

}