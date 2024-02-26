package dev.igorsyrbu.youtubevideoupdater.youtube.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "youtube-auth", url = "https://oauth2.googleapis.com")
public interface YoutubeAuthFeignClient {

	@PostMapping(value = "/token", consumes = APPLICATION_FORM_URLENCODED_VALUE)
	YouTubeAccessToken refreshAccessToken(@RequestParam MultiValueMap<String, String> requestBody);
}
