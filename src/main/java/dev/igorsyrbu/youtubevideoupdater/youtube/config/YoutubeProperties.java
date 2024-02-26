package dev.igorsyrbu.youtubevideoupdater.youtube.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "youtube")
public record YoutubeProperties(@NotBlank String clientId,
								@NotBlank String clientSecret,
								@NotBlank String refreshToken,
								@NotBlank String videoId) {

}
