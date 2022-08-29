package webserver.controller;

import http.httprequest.HttpRequest;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.HttpMethod;
import http.httprequest.requestline.Path;
import http.httprequest.requestline.Protocol;
import http.httprequest.requestline.ProtocolType;
import http.httprequest.requestline.RequestLine;
import http.httprequest.requestline.RequestParams;
import http.httprequest.requestline.Version;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListControllerTest {
    @Test
    @DisplayName("경로가 잘 설정됐는지 확인")
    void getPath() {
        Controller controller = new UserListController();
        assertThat(controller.getPath()).isEqualTo("/user/list");
    }

    @Test
    @DisplayName("execute")
    void execute() throws IOException, URISyntaxException {
        Controller controller = new UserListController();
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(
                        HttpMethod.GET,
                        new Path("/user/list", new RequestParams(Collections.emptyMap())),
                        new Protocol(ProtocolType.HTTP, new Version("1.1"))
                ),
                RequestHeader.from(Collections.emptyList()),
                RequestBody.empty()
        );

        HttpResponse httpResponse = controller.serve(httpRequest);

        assertAll(
                () -> assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.UNAUTHORIZED),
                () -> assertThat(httpResponse.getResponseHeader().getHeaderEntries().contains(Map.entry("Location", "/index.html")))
        );
    }

}