package dev.igorsyrbu.youtubevideoupdater.youtube.auth;

import dev.igorsyrbu.youtubevideoupdater.youtube.config.YoutubeProperties;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

@Service
@RequiredArgsConstructor
public class YoutubeAuthServiceImpl implements YoutubeAuthService {

	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";

	private final YoutubeAuthFeignClient client;
	private final YoutubeProperties properties;

	@Override
	public String refreshAccessToken() {
		val body = buildRefreshTokenRequestBody();
		val response = client.refreshAccessToken(body);

		return response.getAccessToken();
	}

	private LinkedMultiValueMap<String, String> buildRefreshTokenRequestBody() {
		val clientId = properties.clientId();
		val clientSecret = properties.clientSecret();
		val refreshToken = properties.refreshToken();

		val body = new LinkedMultiValueMap<String, String>();
		body.add(CLIENT_ID, clientId);
		body.add(CLIENT_SECRET, clientSecret);
		body.add(GRANT_TYPE, REFRESH_TOKEN);
		body.add(REFRESH_TOKEN, refreshToken);

		return body;
	}
}
