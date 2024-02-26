package dev.igorsyrbu.youtubevideoupdater.youtube.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YouTubeAccessToken {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;
}